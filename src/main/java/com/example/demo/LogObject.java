package com.example.demo;

import java.util.logging.*;

public class LogObject {

	private static Logger logger;

	static
	{
		logger = Logger.getLogger("test.log");	
		logger.setUseParentHandlers(false);
		Handler handler = new ConsoleHandler();
		logger.addHandler(handler);
	}
	
	public static void info(Object obj)
	{
		info(obj.toString());
	}
	
	public static void info(String logStr)
	{
		logger.log(Level.INFO, logStr);
	}
	
	public static void info(String logStr, Throwable e)
	{
		logger.log(Level.INFO, logStr, e);
	}
	
	public static void info(String className, String methodName, Object logStr)
	{
		logger.logp(Level.INFO, className, methodName, getLogStr(logStr));
	}
	
	public static void info(String className, String methodName, Throwable e)
	{
		logger.logp(Level.INFO, className, methodName, "", e);
	}
	
	public static void info(String className, String methodName, Object logStr, Throwable e)
	{
		logger.logp(Level.INFO, className, methodName, getLogStr(logStr), e);
	}
	
	public static void warn(String className, String methodName, Object logStr)
	{
		logger.logp(Level.WARNING, className, methodName, getLogStr(logStr));
	}
	
	public static String getLogStr(Object logStr)
	{
		return logStr!=null?logStr.toString():null;
	}
}
