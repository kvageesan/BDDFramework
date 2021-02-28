package com.homex.uilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Utilities {
	private final String propertyFilePath = "configs/GlobalConfiguration.properties";
	public Properties properties;

	public void loadProperty(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("GlobalConfiguration.properties not found at " + propertyFilePath);
		} 
	}

	//Read driver details from property file
	public String getDriverName() {	
		String driver = properties.getProperty("driver");
		return driver;
		
	}
	
	//Read environment details from property file
	public ArrayList<String> getEnvironment() {

		ArrayList<String> envDetails = new ArrayList<String>();
		envDetails.add(properties.getProperty("appurl"));
		envDetails.add(properties.getProperty("appworkspace"));
		envDetails.add(properties.getProperty("username"));
		envDetails.add(properties.getProperty("password"));		
		return envDetails;

	}
	
	//This class can be extended to add more global configs like implicit wait, results config..etc
}
