package com.homex.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="classpath:features",
		glue="stepDefinition",
		tags="userflows",
		plugin = {"pretty", "html:target"}
		)
public class TestRunner {
	//Test runner class
}
