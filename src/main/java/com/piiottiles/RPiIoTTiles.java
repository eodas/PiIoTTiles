package com.piiottiles;

/**
 * Executive Order Corporation we make Things Smart
 * Raspberry Pi IoT Tiles control smart office automation and monitoring is a control panel (dashboard) for Raspberry Pi IoT Tron Internet of Things(IoT)
 *
 * Raspberry Pi IoT Tron :: Internet of Things Drools-jBPM Expert System using Raspberry Pi Tron AI-IoTBPM Processing
 * Raspberry Pi IoT Tron Drools-jBPM :: Executive Order Raspberry Pi Tron Sensor AI-IoTBPM Client using AI-IoTBPM Drools-jBPM
 *
 * Executive Order Corporation
 * Copyright © 1978, 2020: Executive Order Corporation, All Rights Reserved
 */

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import com.piiottiles.model.AgentsList;
import com.piiottiles.model.StateList;
import com.piiottiles.server.AgentConnect;
import com.piiottiles.server.IoTServer;
import com.piiottiles.iottiles.IoTTiles;

/**
 * This is the main class for Reapberry IoT Tron AI-IoT Drools-jBPM Expert System
 */
public class RPiIoTTiles {

	AgentsList agentsList;
	RPiIoTTiles rpiiottiles;
	private static IoTServer iotServer = null;

	private String base_path = "";
	private String appVer = "1.01A";
	private String buildDate = "0304";
	private boolean is64bitJMV = false;
	private String knowledgeDebug = "none"; // none, debug

	public static int port = 0;
	public static String id = ""; // 123456
	public static String name = ""; // IoT_Parking_Kiosk
	public static String process = ""; // com.IoTParkingKiosk
//	public static String server = ""; // http://10.0.0.2:5055
	public static String gpio = ""; // create gpio controller
	
	public RPiIoTTiles(String[] args) {

		this.rpiiottiles = this;
		agentsList = new AgentsList();
		System.out.println("Raspberry Pi IoT Tiles :: Internet of Things Drools-jBPM Expert System"
				+ " using Raspberry Pi Tron AI-IoT Processing -version: " + appVer + " (" + buildDate + ")");

		getIPAddress();
		readProperties();

		if (knowledgeDebug.indexOf("none") == -1) {
			System.out.println("os.name: " + System.getProperty("os.name"));
			System.out.println("os.arch: " + System.getProperty("os.arch"));
			is64bitJMV = (System.getProperty("os.arch").indexOf("64") != -1);
			String result = (is64bitJMV == true) ? "64bit" : "32bit";

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

	public void init(final boolean exitOnClose) {
		// set up and show main window
		Locale.setDefault(Locale.US);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					IoTTiles iotTiles = new IoTTiles(exitOnClose);
					// iotTiles.show(); // .setVisible(true);
					iotTiles.show(); // setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		AgentConnect agentConnect = new AgentConnect(agentsList);
		
		StateList stateList = new StateList();
		startIoTServer();
	}

	public void readProperties() {
		try {
			File file = new File("iotbpm.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration<?> enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				if (key.indexOf("port") != -1) {
					String portStr = value;
					port = Integer.parseInt(portStr);
				}
				if (key.indexOf("id") != -1) {
					id = value;
				}
				if (key.indexOf("name") != -1) {
					name = value;
				}
				if (key.indexOf("process") != -1) {
					process = value;
				}
				if (key.indexOf("server") != -1) {
//					server = value;
				}
				if (key.indexOf("agentDevice") != -1) {
					agentsList.parseAgents(value);
				}
				if (key.indexOf("gpio") != -1) {
					gpio = value;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startIoTServer() {
		if (port != 0) {
			iotServer = new IoTServer(port);
			iotServer.start();
		}
	}

	public static void stopIoTServer() {
		if (port != 0) {
			iotServer.killServer();
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
			URL url_name = new URL("http://bot.whatismyipaddress.com");
			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

			// reads system IPAddress
			systemipaddress = sc.readLine().trim();
		} catch (Exception e) {
			systemipaddress = "Cannot Execute Properly";
		}
		System.out.println("  Public IP: " + systemipaddress);
	}

	public static void main(String[] args) {
		System.out.println("Raspberry Pi IoT Tiles control smart office automation "
				+ "and monitoring is a control panel (dashboard) for Raspberry Pi Tron IoT Things");

		new RPiIoTTiles(args).init(true);
	}
}
