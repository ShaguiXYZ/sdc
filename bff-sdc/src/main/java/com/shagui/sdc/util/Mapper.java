package com.shagui.sdc.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;
import com.shagui.sdc.api.domain.TimeCoverage;
import com.shagui.sdc.core.exception.ApiError;

import feign.FeignException;

/**
 * Utility class for mapping and transforming objects between different types.
 * This class provides static methods to handle conversions for API errors,
 * lists, paginated data, and historical coverage data.
 */
public class Mapper {
	/**
	 * Private constructor to prevent instantiation of this utility class.
	 */
	private Mapper() {
	}

	/**
	 * Parses a FeignException into an ApiError object.
	 *
	 * @param ex the FeignException to parse
	 * @return an ApiError object containing the status and message from the
	 *         exception
	 */
	public static ApiError parse(FeignException ex) {
		ApiError error = new ApiError();
		error.setStatus(HttpStatus.valueOf(ex.status()));
		error.setMessage(ex.getMessage());

		return error;
	}

	/**
	 * Converts a list of source objects into a list of target objects of the
	 * specified type.
	 *
	 * @param <V>    the target type
	 * @param <D>    the source type
	 * @param source the list of source objects
	 * @param clazz  the target class type
	 * @return a list of converted objects of type V
	 */
	public static <V, D> List<V> parse(List<D> source, Class<V> clazz) {
		return source.stream().map(CastFactory.getInstance(clazz)::parse).toList();
	}

	/**
	 * Converts a PageData object containing source objects into a PageData object
	 * containing target objects of the specified type.
	 *
	 * @param <V>    the target type
	 * @param <D>    the source type
	 * @param source the PageData object containing source objects
	 * @param clazz  the target class type
	 * @return a PageData object containing converted objects of type V
	 */
	public static <V, D> PageData<V> parse(PageData<D> source, Class<V> clazz) {
		PageData<V> target = new PageData<>();

		target.setPaging(CastFactory.getInstance(PageInfo.class).parse(source.getPaging()));
		target.setPage(source.getPage().stream().map(CastFactory.getInstance(clazz)::parse).toList());

		return target;
	}

	/**
	 * Converts a HistoricalCoverage object containing source data into a
	 * HistoricalCoverage object containing target data of the specified type.
	 *
	 * @param <V>    the target type
	 * @param source the HistoricalCoverage object containing source data
	 * @param clazz  the target class type
	 * @return a HistoricalCoverage object containing converted data of type V
	 */
	public static <V> HistoricalCoverage<V> parse(HistoricalCoverage<?> source, Class<V> clazz) {
		HistoricalCoverage<V> target = new HistoricalCoverage<>();

		target.setData(CastFactory.getInstance(clazz).parse(source.getData()));
		target.setHistorical(Mapper.parse(source.getHistorical(), TimeCoverage.class));

		return target;
	}

	/**
	 * Copies non-null properties from the source object to the target object.
	 * This method is useful for partially updating an object with values from
	 * another object.
	 *
	 * @param <S>    The type of the source object.
	 * @param <T>    The type of the target object.
	 * @param source The source object containing the properties to copy.
	 * @param target The target object to which the properties will be copied.
	 */
	public static <S, T> void parse(S source, T target) {
		parse(source, target, false);
	}

	/**
	 * @howto Copies properties from a source object to a target object.
	 *        If the `ignoreNull` parameter is set to `true`, properties with null
	 *        values in the source object
	 *        will not be copied to the target object.
	 *
	 * @param <S>        The type of the source object.
	 * @param <T>        The type of the target object.
	 * @param source     The source object from which properties are copied.
	 * @param target     The target object to which properties are copied.
	 * @param ignoreNull A Boolean flag indicating whether to ignore null properties
	 *                   in the source object.
	 *                   If `true`, null properties in the source object will not be
	 *                   copied to the target object.
	 *                   If `false` or `null`, all properties will be copied.
	 */
	public static <S, T> void parse(S source, T target, boolean ignoreNull) {
		if (!ignoreNull) {
			BeanUtils.copyProperties(source, target);
			return;
		}

		// Get all properties of the source object that are null
		// and store their names in an array
		// This is done using Java reflection to get the property descriptors of the
		String[] propertiesWithNullValues = List.of(BeanUtils.getPropertyDescriptors(source.getClass()))
				.stream()
				.map(pd -> {
					try {
						return pd.getReadMethod().invoke(source) == null ? pd.getName() : null;
					} catch (Exception e) {
						return null;
					}
				})
				.filter(Objects::nonNull)
				.toArray(String[]::new);

		BeanUtils.copyProperties(source, target, propertiesWithNullValues);
	}

	/**
	 * Patches properties of the target object with values from the source map.
	 * 
	 * @param <T>              The type of the target object.
	 * @param target           The target object to be patched.
	 * @param propertyValues   A map containing property names and their new values.
	 * @param ignoreProperties An optional list of property names to ignore during
	 *                         patching.
	 */
	public static <T> void parse(T target, Map<String, Object> propertyValues, String... ignoreProperties) {
		if (propertyValues == null || propertyValues.isEmpty()) {
			return;
		}

		// Convert ignoreProperties to a Set for faster lookup
		var ignoredProperties = ignoreProperties != null ? Set.of(ignoreProperties) : Set.of();

		propertyValues.forEach((propertyName, propertyValue) -> {
			if (ignoredProperties.contains(propertyName)) {
				return;
			}

			try {
				// Use reflection to set the property value on the target object
				var propertyDescriptor = BeanUtils.getPropertyDescriptor(target.getClass(), propertyName);

				if (propertyDescriptor != null && propertyDescriptor.getWriteMethod() != null) {
					propertyDescriptor.getWriteMethod().invoke(target, propertyValue);
				}
			} catch (Exception e) {
				throw new RuntimeException("Failed to set property: " + propertyName, e);
			}
		});
	}
}
