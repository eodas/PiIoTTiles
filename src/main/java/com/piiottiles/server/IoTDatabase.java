package com.piiottiles.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piiottiles.database.DataManager;

public class IoTDatabase extends Thread {
	private int currrentConnections = 0;
	private int totalConnections = 0; // Total number of connection
	private int maxConnections = 30;

	private boolean alive = true;
	private DataManager dataManager;

	private final Logger logger = LoggerFactory.getLogger(IoTDatabase.class);

	public IoTDatabase(DataManager dataManager) {
		this.dataManager = dataManager;
		System.out.println("Pi IoT Tiles Tron Drools-jBPM AI-IoTBPM Server Started.");
	}

	@Override
	public void run() {
		while (alive) {
			try {
				// new IoTServerThread(server.accept(), this, jbpmRules, dataManager);
				incConnection();
			} catch (Exception localSocketException) {
				try {
					Thread.sleep(50L);
				} catch (Exception localException1) {
				}
			}
		}
	}

	public synchronized void incConnection() {
		currrentConnections += 1;
		totalConnections += 1;
	}

	public synchronized void decConnection() {
		currrentConnections -= 1;
	}

	public synchronized int getCurrentConnection() {
		return currrentConnections;
	}

	public synchronized int getTotalConnection() {
		return totalConnections;
	}

	public void killServer() {
		alive = false;
		System.out.println("Pi IoT Tron Drools-jBPM AI-IoTBPM Server Port, Stopped");
	}
}
