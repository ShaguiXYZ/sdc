package com.shagui.sdc.util.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class JsonDocumentTest {
	@Test
	void urlConstructorTest() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		String json = "{\r\n" + "  \"name\": \"ngx-sdc\",\r\n" + "  \"version\": \"0.0.0\",\r\n"
				+ "  \"scripts\": {\r\n" + "    \"ng\": \"ng\",\r\n" + "    \"start\": \"ng serve\",\r\n"
				+ "    \"start:local\": \"ng serve --configuration=local --watch\",\r\n"
				+ "    \"build\": \"ng build\",\r\n"
				+ "    \"watch\": \"ng build --watch --configuration development\",\r\n"
				+ "    \"test\": \"ng test\",\r\n" + "    \"coverage\": \"ng test --no-watch --code-coverage\",\r\n"
				+ "    \"lint\": \"ng lint\"\r\n" + "  },\r\n" + "  \"private\": true,\r\n"
				+ "  \"dependencies\": {\r\n"
				+ "    \"@allianz/ngx-ndbx\": \"file:dependencies/ngx-ndbx-15.1.0.tgz\",\r\n"
				+ "    \"@angular/animations\": \"~15.2.7\",\r\n" + "    \"@angular/cdk\": \"~15.2.7\",\r\n"
				+ "    \"@angular/common\": \"~15.2.7\",\r\n" + "    \"@angular/compiler\": \"~15.2.7\",\r\n"
				+ "    \"@angular/core\": \"~15.2.7\",\r\n" + "    \"@angular/forms\": \"~15.2.7\",\r\n"
				+ "    \"@angular/platform-browser\": \"~15.2.7\",\r\n"
				+ "    \"@angular/platform-browser-dynamic\": \"~15.2.7\",\r\n"
				+ "    \"@angular/router\": \"~15.2.7\",\r\n" + "    \"@aposin/ng-aquila\": \"~15.1.0\",\r\n"
				+ "    \"@fortawesome/fontawesome-free\": \"^6.4.0\",\r\n"
				+ "    \"@ngx-translate/core\": \"~14.0.0\",\r\n"
				+ "    \"@ngx-translate/http-loader\": \"~7.0.0\",\r\n" + "    \"echarts\": \"^5.4.2\",\r\n"
				+ "    \"echarts-for-angular\": \"^0.4.0\",\r\n" + "    \"iban\": \"~0.0.14\",\r\n"
				+ "    \"lodash\": \"^4.17.21\",\r\n" + "    \"moment\": \"~2.29.4\",\r\n"
				+ "    \"rxjs\": \"~7.5.4\",\r\n" + "    \"tslib\": \"~2.3.1\",\r\n"
				+ "    \"zone.js\": \"~0.11.4\"\r\n" + "  },\r\n" + "  \"devDependencies\": {\r\n"
				+ "    \"@angular-devkit/build-angular\": \"~15.2.6\",\r\n"
				+ "    \"@angular-eslint/builder\": \"15.2.1\",\r\n"
				+ "    \"@angular-eslint/eslint-plugin\": \"15.2.1\",\r\n"
				+ "    \"@angular-eslint/eslint-plugin-template\": \"15.2.1\",\r\n"
				+ "    \"@angular-eslint/schematics\": \"15.2.1\",\r\n"
				+ "    \"@angular-eslint/template-parser\": \"15.2.1\",\r\n" + "    \"@angular/cli\": \"~15.2.6\",\r\n"
				+ "    \"@angular/compiler-cli\": \"~15.2.7\",\r\n" + "    \"@types/jasmine\": \"~4.0.0\",\r\n"
				+ "    \"@types/lodash\": \"^4.14.191\",\r\n" + "    \"@types/node\": \"^18.15.11\",\r\n"
				+ "    \"@typescript-eslint/eslint-plugin\": \"^5.43.0\",\r\n"
				+ "    \"@typescript-eslint/parser\": \"^5.43.0\",\r\n" + "    \"eslint\": \"^8.29.0\",\r\n"
				+ "    \"eslint-plugin-import\": \"~2.26.0\",\r\n" + "    \"eslint-plugin-jsdoc\": \"~39.6.4\",\r\n"
				+ "    \"eslint-plugin-prefer-arrow\": \"~1.2.3\",\r\n" + "    \"husky\": \"~8.0.2\",\r\n"
				+ "    \"jasmine-core\": \"~4.3.0\",\r\n" + "    \"jasmine-spec-reporter\": \"~7.0.0\",\r\n"
				+ "    \"karma\": \"~6.4.0\",\r\n" + "    \"karma-chrome-launcher\": \"~3.1.0\",\r\n"
				+ "    \"karma-coverage\": \"~2.2.0\",\r\n" + "    \"karma-jasmine\": \"~5.1.0\",\r\n"
				+ "    \"karma-jasmine-html-reporter\": \"~2.0.0\",\r\n"
				+ "    \"karma-mocha-reporter\": \"~2.2.5\",\r\n" + "    \"protractor\": \"~7.0.0\",\r\n"
				+ "    \"swagger-express-middleware\": \"~4.0.2\",\r\n" + "    \"typescript\": \"~4.9.5\"\r\n"
				+ "  }\r\n" + "}";

		JsonDocument document = SdcDocumentFactory.newInstance(json, JsonDocument.class);

		Optional<String> data = document.value("devDependencies.@angular/cli");

		assertNotNull(data);
		assertTrue(data.isPresent());
		assertEquals("~15.2.6", data.get());
	}

}
