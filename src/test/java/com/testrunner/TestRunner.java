package com.testrunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
	
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/OpenMrs", // Path to feature files
    glue = {"org.openmrs.demo.steps"},              // Package containing step definitions
    plugin = {"pretty", "html:target/cucumber-reports.html"}, // Report format
    monochrome = true                       // Display readable console output
  //  tags = "@testeing"                      // Run scenarios with a specific tag (optional)
 // 
)
public class TestRunner {
}
