package com.piiottiles.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piiottiles.database.DataManager;
import com.piiottiles.model.Event;
import com.piiottiles.piiottilesux.PiIoTTilesUX;

public class IoTDatabaseUX {
	private String response = "";

	private DataManager dataManager;
	private Event event;

 	private final Logger logger = LoggerFactory.getLogger(IoTDatabaseUX.class);

	public IoTDatabaseUX(DataManager dataManager, Event event) {
		this.dataManager = dataManager;
		this.event = event;

		printIoTTiles();
		processIoTTiles();
	}

	private void printIoTTiles() {
		System.out.println(this.event.id + ",  " + this.event.name + ",  " + this.event.event + ",  "
				+ this.event.description + ",  " + this.event.process + ",  " + this.event.protocol + ",  "
				+ this.event.serverTime + ",  " + this.event.deviceTime + ",  " + this.event.fixTime + ",  "
				+ this.event.outdated + ",  " + this.event.valid + ",  " + this.event.lat + ",  " + this.event.lon + ",  "
   			    + this.event.altitude + ",  " + this.event.speed + ",   " + this.event.course + ",  "
				+ this.event.address + ",  " + this.event.accuracy + ",  " + this.event.bearing + ",  "
				+ this.event.network + this.event.hdop + ",  " + this.event.cell + ",  " + this.event.wifi + ",  "
				+ this.event.battery + ",  " + this.event.message + ",  " + this.event.temp + ",  " + this.event.ir_temp + ",  "
				+ this.event.humidity + ",  " + this.event.mbar + ",  " + this.event.accel_x + ",  "
				+ this.event.accel_y + ",  " + this.event.accel_z + ",  " + this.event.gyro_x + ",  "
				+ this.event.gyro_y + ",  " + this.event.gyro_z + ",  " + this.event.magnet_x + ",  "
				+ this.event.magnet_y + ",  " + this.event.magnet_z + ",  " + this.event.light + ",  "
				+ this.event.keypress + ",  " + this.event.alarm + ",  " + this.event.distance + ",  "
				+ this.event.totalDistance + ",  " + this.event.agentCount + ",  " + this.event.motion);
	}

	private void processIoTTiles() {
		switch (this.event.id) {
		case "100111": // 100111 - Arduino Tron IoT
			System.out.println("100111 - Arduino Tron IoT");
			break;
		case "100222": // 100222 - Temperature-Humidity
			if ((this.event.temp == 0) || (this.event.humidity == 0)) {
				System.out.println("100222 - Temperature or Humidity is 0");
			} else {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_5Temp(this.event.temp + "' " + this.event.humidity + "%");
			}
			break;
		case "100223": // 100223 - Temperature-Humidity Outside Temperature
			if ((this.event.temp == 0) || (this.event.humidity == 0)) {
				System.out.println("100222 - Temperature or Humidity is 0");
			} else {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_18Temp(this.event.temp + "' " + this.event.humidity + "%");
			}
			break;
		case "100333": // 100333 - Door Lock IoT-MCU
			if (PiIoTTilesUX.door_state.indexOf("Locked") != -1) {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_8DoorUnlocked();
				PiIoTTilesUX.door_state = "Unlocked";
			} else {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_8DoorLocked();
				PiIoTTilesUX.door_state = "Locked";
			} 
			break;
		case "100444": // 100444 - Arduino IoT-SensorTag
			System.out.println("100444 - Arduino IoT-SensorTag");
			break;
		case "100555": // 100555 - Arduino Dash Button
			com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_15DashButtonAlert(this.event.alarm);
			break;
		case "100666": // 100666 - Door Open Sensor ESP01
			com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_9DoorOpened();
			break;
		case "100777": // 100777 - Light Module IoT-MCU
			if (PiIoTTilesUX.light_state.indexOf("On") != -1) {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_10LightOff();
				PiIoTTilesUX.light_state = "Off";
			} else {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_10LightOn();
				PiIoTTilesUX.light_state = "On";
			} 
			break;
  		case "100888": // 100888 - Arduino Tron IoT Display
			System.out.println("100888 - Arduino Tron IoT Display");
			break;
  		case "100910": // 100910 - Jarvis Pi IoT Tron
			System.out.println("100910 - Jarvis Pi IoT Tron");
			break;
  		case "100920": // 100920 - EOSpy IoT GPS Position
			System.out.println("100920 - EOSpy IoT GPS Position");
			break;
  		case "100930": // 100930 - EOSpy TISensorTag GPS Environment
			System.out.println("100930 - EOSpy TISensorTag GPS Environment");
			break;
		default:
			System.out.println("> Extended Event Token ");
		}
	}
}