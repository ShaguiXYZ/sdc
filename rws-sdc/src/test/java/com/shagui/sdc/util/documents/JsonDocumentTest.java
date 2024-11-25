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

import com.shagui.sdc.util.documents.lib.json.JsonDocument;

class JsonDocumentTest {
	@Test
	void urlConstructorTest() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		String json = """
				{
				  "name": "ngx-sdc",
				  "version": "0.0.0",
				  "scripts": {
				    "ng": "ng",
				    "start": "ng serve",
				    "start:local": "ng serve --configuration=local --watch",
				    "build": "ng build",
				    "watch": "ng build --watch --configuration development",
				    "test": "ng test",
				    "coverage": "ng test --no-watch --code-coverage",
				    "lint": "ng lint"
				  },
				  "private": true,
				  "dependencies": {
				    "@angular/animations": "~15.2.7",
				    "@angular/cdk": "~15.2.7",
				    "@angular/common": "~15.2.7",
				    "@angular/compiler": "~15.2.7",
				    "@angular/core": "~15.2.7",
				    "@angular/forms": "~15.2.7",
				    "@angular/platform-browser": "~15.2.7",
				    "@angular/platform-browser-dynamic": "~15.2.7",
				    "@angular/router": "~15.2.7",
				    "@aposin/ng-aquila": "~15.1.0",
				    "@fortawesome/fontawesome-free": "^6.4.0",
				    "@ngx-translate/core": "~14.0.0",
				    "@ngx-translate/http-loader": "~7.0.0",
				    "echarts": "^5.4.2",
				    "echarts-for-angular": "^0.4.0",
				    "iban": "~0.0.14",
				    "lodash": "^4.17.21",
				    "moment": "~2.29.4",
				    "rxjs": "~7.5.4",
				    "tslib": "~2.3.1",
				    "zone.js": "~0.11.4"
				  },
				  "devDependencies": {
				    "@angular-devkit/build-angular": "~15.2.6",
				    "@angular-eslint/builder": "15.2.1",
				    "@angular-eslint/eslint-plugin": "15.2.1",
				    "@angular-eslint/eslint-plugin-template": "15.2.1",
				    "@angular-eslint/schematics": "15.2.1",
				    "@angular-eslint/template-parser": "15.2.1",
				    "@angular/cli": "~15.2.6",
				    "@angular/compiler-cli": "~15.2.7",
				    "@types/jasmine": "~4.0.0",
				    "@types/lodash": "^4.14.191",
				    "@types/node": "^18.15.11",
				    "@typescript-eslint/eslint-plugin": "^5.43.0",
				    "@typescript-eslint/parser": "^5.43.0",
				    "eslint": "^8.29.0",
				    "eslint-plugin-import": "~2.26.0",
				    "eslint-plugin-jsdoc": "~39.6.4",
				    "eslint-plugin-prefer-arrow": "~1.2.3",
				    "husky": "~8.0.2",
				    "jasmine-core": "~4.3.0",
				    "jasmine-spec-reporter": "~7.0.0",
				    "karma": "~6.4.0",
				    "karma-chrome-launcher": "~3.1.0",
				    "karma-coverage": "~2.2.0",
				    "karma-jasmine": "~5.1.0",
				    "karma-jasmine-html-reporter": "~2.0.0",
				    "karma-mocha-reporter": "~2.2.5",
				    "protractor": "~7.0.0",
				    "swagger-express-middleware": "~4.0.2",
				    "typescript": "~4.9.5"
				  }
				}\
				""";

		JsonDocument document = SdcDocumentFactory.newInstance(json, JsonDocument.class);

		Optional<String> data = document.value("devDependencies.@angular/cli");

		assertNotNull(data);
		assertTrue(data.isPresent());
		assertEquals("~15.2.6", data.get());
	}

}
