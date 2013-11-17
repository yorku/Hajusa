package com.yorku.Hajus.model.Log;

import org.apache.log4j.Logger;

public class MyLogger  {

	static Logger logger = Logger.getLogger(MyLogger.class);

	public static void Log(String method_name, String message) {

		String log_row = "APP_ERROR: "  + method_name + " error_message = " + message;

		try {
			logger.info(log_row);

		} catch(Exception ex) {  
			System.out.println("MyLogger.Log(): " + ex.getMessage());
		}
	}

	public static void LogMessage(String message) {

		String log_row = "APP_MESSAGE: "  + message;

		try {
			logger.info(log_row); 
			
		} catch(Exception ex) {  
			System.out.println("MyLogger.Log(): " + ex.getMessage());
		}
	}
}