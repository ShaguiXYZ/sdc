package com.shagui.sdc.util.documents;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.shagui.sdc.core.exception.SdcCustomException;

public class JsonDocument implements SdcDocument {
	private static final JsonFactory JSON_FACTORY = new JsonFactory();
	private ByteArrayOutputStream baos;

	@Override
	public void input(InputStream data) throws IOException {
		this.baos = new ByteArrayOutputStream();
		data.transferTo(baos);
	}

	@Override
	public Optional<String> fromPath(String path) {
		try {
			List<JSONKey> keys = Arrays
					.stream((path.startsWith("[") ? path : String.valueOf("." + path)).split("(?=[\\[\\]\\.])"))
					.filter(x -> !"]".equals(x)).map(JSONKey::new).collect(Collectors.toList());

			return getValueAt(JSON_FACTORY.createParser(new ByteArrayInputStream(baos.toByteArray())), keys, 0, false);
		} catch (IOException e) {
			throw new SdcCustomException(String.format("ERROR in metric '%s'.", path), e);
		}
	}

	private Optional<String> getValueAt(final JsonParser parser, final List<JSONKey> keys, final int idx,
			final boolean strict) throws IOException {
		try {
			if (parser.isClosed()) {
				return Optional.empty();
			}

			if (idx >= keys.size()) {
				parser.nextToken();

				if (null == parser.getValueAsString()) {
					throw new SdcCustomException("The selected node is not a leaf");
				}

				return Optional.of(parser.getValueAsString());
			}

			keys.get(idx).advanceCursor(parser);

			return getValueAt(parser, keys, idx + 1, strict);
		} catch (final SdcCustomException e) {
			if (strict) {
				throw (null == e.getCause() ? new SdcCustomException(
						e.getMessage() + String.format(", at path: '%s'", this.toKeyString(keys, idx)), e) : e);
			}

			return Optional.empty();
		}
	}

	private String toKeyString(List<JSONKey> keys, final int idx) {
		return ((Function<String, String>) x -> x.startsWith(".") ? x.substring(1) : x)
				.apply(keys.subList(0, idx).stream().map(JSONKey::toString).collect(Collectors.joining()));
	}

	private static class JSONKey {
		private final String key;
		private final JsonToken startToken;

		public JSONKey(final String str) {
			this(str.substring(1), str.startsWith("[") ? JsonToken.START_ARRAY : JsonToken.START_OBJECT);
		}

		private JSONKey(final String key, final JsonToken startToken) {
			this.key = key;
			this.startToken = startToken;
		}

		/**
		 * Advances the cursor until finding the current {@link JSONKey}, or having
		 * consumed the entirety of the current JSON Object or Array.
		 */
		public void advanceCursor(final JsonParser parser) throws IOException {
			final JsonToken token = parser.nextToken();
			if (!this.startToken.equals(token)) {
				throw new SdcCustomException(
						String.format("Expected token of type '%s', got: '%s'", this.startToken, token));
			}

			if (JsonToken.START_ARRAY.equals(this.startToken)) {
				// Moving cursor within a JSON Array
				for (int i = 0; i != Integer.parseInt(this.key); i++) {
					JSONKey.skipToNext(parser);
				}
			} else {
				// Moving cursor in a JSON Object
				String name;
				for (parser.nextToken(), name = parser.getCurrentName(); !this.key.equals(name); parser
						.nextToken(), name = parser.getCurrentName()) {
					JSONKey.skipToNext(parser);

					if (name == null)
						throw new SdcCustomException("Could not find requested key");
				}
			}
		}

		/**
		 * Advances the cursor to the next entry in the current JSON Object or Array.
		 */
		private static void skipToNext(final JsonParser parser) throws IOException {
			final JsonToken token = parser.nextToken();
			if (JsonToken.START_ARRAY.equals(token) || JsonToken.START_OBJECT.equals(token)
					|| JsonToken.FIELD_NAME.equals(token)) {
				skipToNextImpl(parser, 1);
			} else if (JsonToken.END_ARRAY.equals(token) || JsonToken.END_OBJECT.equals(token)) {
				throw new SdcCustomException("Could not find requested key");
			}
		}

		/**
		 * Recursively consumes whatever is next until getting back to the same depth
		 * level.
		 */
		private static void skipToNextImpl(final JsonParser parser, final int depth) throws IOException {
			if (depth == 0) {
				return;
			}

			final JsonToken token = parser.nextToken();
			if (JsonToken.START_ARRAY.equals(token) || JsonToken.START_OBJECT.equals(token)
					|| JsonToken.FIELD_NAME.equals(token)) {
				skipToNextImpl(parser, depth + 1);
			} else {
				skipToNextImpl(parser, depth - 1);
			}
		}

		@Override
		public String toString() {
			return String.format(this.startToken.equals(JsonToken.START_ARRAY) ? "[%s]" : ".%s", this.key);
		}
	}
}
