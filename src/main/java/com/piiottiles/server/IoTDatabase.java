package com.piiottiles.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piiottiles.database.DataManager;
import com.piiottiles.piiottilesux.PiIoTTilesUX;
import com.piiottiles.model.Event;

public class IoTDatabase extends Thread {
	private int totalConnections = 0; // Total number of connection

	private boolean alive = true;

	private final String DateFormat = "yyyy-MM-dd HH:mm:ss.ms"; // MM/dd/yyyy HH:mm:ss
	private String listEventServerTime = "";

	private DataManager dataManager;

	private final Logger logger = LoggerFactory.getLogger(IoTDatabase.class);

	public IoTDatabase(DataManager dataManager) {
		this.dataManager = dataManager;
		System.out.println("Pi IoT Tiles Tron Drools-jBPM AI-IoTBPM Server Started.");
	}

	@Override
	public void run() {
		while (alive) {
			listEvents();
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				System.err.format("IOException: %s%n", e);
			}
		}
	}

	public void listEvents() {

		if ((listEventServerTime.equals(null)) || (listEventServerTime.equals(""))) {
			listEventServerTime = new SimpleDateFormat(DateFormat).format(new Date());
		} else {

			incConnection();
			List<Event> events = dataManager.getEventsServerTime(listEventServerTime);
			for (Event e : events) {
				listEventServerTime = e.serverTime;

				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().processIoTTilesCommand(e);
			}
		}
	}

	public synchronized void incConnection() {
		totalConnections += 1;
	}

	public synchronized int getTotalConnection() {
		return totalConnections;
	}

	public void killServer() {
		alive = false;
		System.out.println("Pi IoT Tiles Tron Drools-jBPM AI-IoTBPM Server Stopped");
	}
}
