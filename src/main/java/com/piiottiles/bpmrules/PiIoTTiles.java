package com.piiottiles.bpmrules;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piiottiles.config.Config;
import com.piiottiles.database.DataManager;
import com.piiottiles.pi4j.Pi4jGPIO;
import com.piiottiles.piiottilesux.PiIoTTilesUX;

import com.piiottiles.server.IoTDatabase;
import com.piiottiles.server.IoTCommand;

//import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
//import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Executive Order Corporation we make Things Smart
 * Pi IoT Tiles control smart office automation and monitoring is a control panel (dashboard) for Pi AI-IoTBPM Tron Internet of Things(IoT)
 *
 * Pi IoT Tron :: Internet of Things Drools-jBPM Expert System using Pi AI-IoTBPM Tron IoT Device Processing
 * Pi IoT Tron Drools-jBPM :: Executive Order Pi AI-IoTBPM Tron Sensor AI-IoTBPM Client using AI-IoTBPM Drools-jBPM
 *
 * Executive Order Corporation
 * Copyright (c) 1978, 2021: Executive Order Corporation, All Rights Reserved
 */

/**
 * This is the main class for Pi IoT IoT Tron AI-IoT Drools-jBPM Expert System
 */
public class PiIoTTiles {

	PiIoTTiles piiottiles;
	private Config config;
	private static IoTDatabase iotDatabase = null;
	private static Pi4jGPIO pi4jgpio = null;

	private String base_path = "";
	private String appVer = "1.01A";
	private String buildDate = "0304";
	private boolean is64bitJMV = false;

	private String knowledgeDebug = "none"; // none, debug
	public static String gpio = ""; // create gpio controller
	private long startTime = 0; // Time the server started

	private DataManager dataManager;

	private final Logger logger = LoggerFactory.getLogger(PiIoTTiles.class);
	
	public PiIoTTiles(String configFile) {

		this.piiottiles = this;
		System.out.println("Pi AI-IoTBPM Tron Tiles :: Internet of Things Drools-jBPM Expert System"
				+ " using Pi AI-IoTBPM Tron AI-IoT Processing -version: " + appVer + " (" + buildDate + ")");

		getIPAddress();
		readProperties(configFile);

		initDataManager();
		logSystemInfo();
		
		startTime = System.currentTimeMillis();
	}

	public void init(final boolean exitOnClose) {
		startPi4jGPIO(); // Implementation for the Raspberry Pi4j GPIO example
		startIoTDatabase(dataManager);
		IoTCommand iotCommand = new IoTCommand(dataManager, knowledgeDebug);
		
		// set up and show main window
		Locale.setDefault(Locale.US);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PiIoTTilesUX piiottilesux = new PiIoTTilesUX(piiottiles, dataManager, exitOnClose);
					// iotTiles.show(); // .setVisible(true);
					piiottilesux.show(); // setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void logSystemInfo() {
		if (knowledgeDebug.indexOf("none") == -1) {
			System.out.println("os.name: " + System.getProperty("os.name"));
			System.out.println("os.arch: " + System.getProperty("os.arch"));
			is64bitJMV = (System.getProperty("os.arch").indexOf("64") != -1);
			String result = is64bitJMV ? "64bit" : "32bit";

			System.out.println("java.home: " + System.getProperty("java.home"));
			System.out.println("java.vendor: " + System.getProperty("java.vendor"));
			System.out.println("java.version: " + System.getProperty("java.version") + " " + result);
			long maxHeapBytes = Runtime.getRuntime().maxMemory();
			System.out.println("Max heap memory: " + maxHeapBytes / 1024L / 1024L + "M");
			System.out.println("java.io.tmpdir: " + System.getProperty("java.io.tmpdir"));
			System.out.println("user.home: " + System.getProperty("user.home"));

			base_path = (System.getProperty("user.home") + File.separator);

			System.out.println("base_path: " + base_path);
			System.out.println("File.separator: " + File.separator);
			System.out.println("Local language: " + Locale.getDefault().getLanguage());
		}
	}

	public void readProperties(String configFile) {
		try {
			config = new Config(configFile);
		} catch (Exception e) {
			config = new Config();
			e.printStackTrace();
		}

		try {
			config = new Config(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gpio = config.getString("gpio");
		knowledgeDebug = config.getString("knowledge.debug");
	}

	public void initDataManager() {
		try {
			dataManager = new DataManager(config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeManager() {
		dataManager.closeDatabase();
        System.out.println("Database Disconnected...");
	}

	public void startIoTDatabase(DataManager dataManager) {
		iotDatabase = new IoTDatabase(dataManager);
		iotDatabase.start();
	}

	public static void stopIoTDatabase() {
		iotDatabase.killServer();
	}

	public void startPi4jGPIO() {
		pi4jgpio = new Pi4jGPIO(); // Implementation for the Raspberry Pi4j GPIO example

		if ((gpio == "") || (gpio.indexOf("none") != -1)) {
			System.err.println("Note: create gpio controller e.g. gpio=GPIO_01 not defined in piiottron.xml file.");
		} else {
			pi4jgpio.gpioStartController();
	        System.out.println("Create GPIO Controller...");
		}
	}

	public void stopPi4jGPIO() {
		if (pi4jgpio.isPi4jActive()) {
			pi4jgpio.gpioShutdown();
			System.out.println("Stop all GPIO Activity / Threads");
		}
	}

	public void getIPAddress() {
		// Returns the instance of InetAddress containing local host name and address
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		System.out.print("System IP: " + (localhost.getHostAddress()).trim());

		// Find public IP address
		String systemipaddress = "";
		try {
			URL url_name = new URL("https://myexternalip.com/raw"); // URL("http://bot.whatismyipaddress.com"); No longer providing API
			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

			// reads system IPAddress
			systemipaddress = sc.readLine().trim();
		} catch (Exception e) {
			systemipaddress = "Cannot Execute Properly";
		}
		System.out.println("  Public IP: " + systemipaddress);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		String configFile = "";

		System.out.println("Pi AI-IoTBPM Tiles control smart office automation "
				+ "and monitoring is a control panel (dashboard) for Pi AI-IoTBPM Tron IoT Things");

		if (args.length <= 0) {
			System.out.println("Configuration file is not provided, using default piiottron.xml filename.");
			configFile = "piiottron.xml";
		} else {
			configFile = args[args.length - 1];
		}

		new PiIoTTiles(configFile).init(true);
	}
}
