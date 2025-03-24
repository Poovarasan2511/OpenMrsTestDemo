package com.openmrs.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/FeatureFiles", // feature files
		glue = { "com.openmrs.stepDefinitions" }, // Step Definitions package
		plugin = { "pretty", "html:target/cucumber-reports.html",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"json:target/cucumber-reports/cucumber.json" }, // Reports
		monochrome = true, tags = "@Smoke"
// dryRun=true
)

public class TestRunner extends AbstractTestNGCucumberTests {

}
