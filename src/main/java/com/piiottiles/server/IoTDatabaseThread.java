package com.piiottiles.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoTDatabaseThread extends Thread {
	private PrintStream out;
	private String response = "";
	private IoTDatabase ws = null;
	private Socket socket = null;

	private String id = "";
	private String name = "";
	private String event = "";
	private String process = "";
	private String protocol = "";
	public String serverTime = "";
	public String deviceTime = "";
	public String fixTime = "";
	public boolean outdated = false;
	public boolean valid = false;
	public double lat = 0;
	public double lon = 0;
	public double hdop = 0;
	public String cell = "";
	public String wifi = "";
	public double altitude = 0; // value in meters
	public double speed = 0; // value in mph
	public double course = 0;
	public String address = "";
	public double accuracy = 0;
	public double bearing = 0;
	public String network = "";

	public double batteryLevel;
	public String textMessage = "";
	public double temp = 0;
	public double ir_temp = 0;
	public double humidity = 0;
	public double mbar = 0;
	public double accel_x = 0;
	public double accel_y = 0;
	public double accel_z = 0;
	public double gyro_x = 0;
	public double gyro_y = 0;
	public double gyro_z = 0;
	public double magnet_x = 0;
	public double magnet_y = 0;
	public double magnet_z = 0;
	public double light = 0;
	public double keypress = 0;
	public String alarm = "";
	public double distance = 0;
	public double totalDistance = 0;
	public double agentCount = 0;
	public boolean motion = false;

//	private final Logger logger = LoggerFactory.getLogger(IoTServerThread.class);

	public IoTDatabaseThread(Socket socket, IoTDatabase ws) {
		this.ws = ws;
		this.socket = socket;
		setName("IoT Server Thread");

		start();
	}

	@Override
	public void run() {
		try {
			String ipAddress = socket.getInetAddress().getHostAddress();
			System.out.println("> TRACE IoT device connected from IP " + ipAddress);

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream());
			String request = "";
			while (true) {
				request = in.readLine();
				System.out.println("> TRACE " + request);

				boolean moreLines = true;
				while (moreLines) {
					String reqtmp = in.readLine();

					if ((reqtmp.equals(null)) || (reqtmp.equals(""))) {
						break;
					}
				}

				// DeviceEvent deviceEvent = new DeviceEvent();
				String[] req = Pattern.compile(" ").split(request);

				if (req[0].equals("GET") || req[0].equals("POST")) {

					String arg = req[1].substring(req[1].indexOf('?') + 1);
					String[] tokens = arg.split("&");

					for (String token : tokens) {
						try {
							String key = token.substring(0, token.indexOf('='));
							String value = token.substring(token.indexOf('=') + 1);

							switch (key) {
							case "id":
							case "deviceid":
								id = value;
								break;
							case "name":
								name = value;
								break;
							case "event":
								event = value;
								break;
							case "process":
								process = value;
								break;
							case "protocol":
								protocol = value;
								break;
							case "servertime":
								// serverTime(parseDate(value);
								break;
							case "timestamp":
								// setDeviceTime(parseDate(value);
								break;
							case "fixtime":
								// setFixTime(parseDate(value);
								break;
							case "outdated":
								outdated = Boolean.parseBoolean(value);
								break;
							case "valid":
								valid = Boolean.parseBoolean(value);
								break;
							case "lat":
								lat = Double.parseDouble(value);
								break;
							case "lon":
								lon = Double.parseDouble(value);
								break;
							case "hdop":
								hdop = Double.parseDouble(value);
								break;
							case "location":
								String[] location = value.split(",");
								lat = Double.parseDouble(location[0]);
								lon = Double.parseDouble(location[1]);
								break;
							case "cell":
								cell = value;
								break;
							case "wifi":
								wifi = value;
								break;
							case "altitude":
								altitude = Double.parseDouble(value);
								break;
							case "speed":
								speed = Double.parseDouble(value);
								break;
							case "course":
								course = Double.parseDouble(value);
								break;
							case "address":
								address = value;
								break;
							case "accuracy":
								accuracy = Double.parseDouble(value);
								break;
							case "bearing":
							case "heading":
								bearing = Double.parseDouble(value);
								break;
							case "network":
								network = value;
								break;
							case "batteryLevel":
							case "batt":
								batteryLevel = Double.parseDouble(value);
								break;
							case "textMessage":
								textMessage = value;
								break;
							case "temp":
								temp = Double.parseDouble(value);
								break;
							case "ir_temp":
								ir_temp = Double.parseDouble(value);
								break;
							case "humidity":
								humidity = Double.parseDouble(value);
								break;
							case "mbar":
								mbar = Double.parseDouble(value);
								break;
							case "accel_x":
								accel_x = Double.parseDouble(value);
								break;
							case "accel_y":
								accel_y = Double.parseDouble(value);
								break;
							case "accel_z":
								accel_z = Double.parseDouble(value);
								break;
							case "gyro_x":
								gyro_x = Double.parseDouble(value);
								break;
							case "gyro_y":
								gyro_y = Double.parseDouble(value);
								break;
							case "gyro_z":
								gyro_z = Double.parseDouble(value);
								break;
							case "magnet_x":
								magnet_x = Double.parseDouble(value);
								break;
							case "magnet_y":
								magnet_y = Double.parseDouble(value);
								break;
							case "magnet_z":
								magnet_z = Double.parseDouble(value);
								break;
							case "light":
								light = Double.parseDouble(value);
								break;
							case "keypress":
								keypress = Double.parseDouble(value);
								break;
							case "alarm":
								alarm = value;
								break;
							case "distance":
								distance = Double.parseDouble(value);
								break;
							case "totalDistance":
								totalDistance = Double.parseDouble(value);
								break;
							case "agentCount":
								agentCount = Double.parseDouble(value);
								break;
							case "motion":
								motion = Boolean.parseBoolean(value);
								break;
							default:
								System.out.println("> Extended Event Token " + key + "=" + value);
							}
						} catch (IndexOutOfBoundsException e) {
							System.err.println("Error: Unexpected exception caught: " + e.getMessage());
							e.printStackTrace();
						}
					}
					// response = jbpmRules.receive(deviceEvent);
					if ((response != null) && (response.length() > 0)) {
						out.println(response);
						System.out.println("> TRACE RSP " + response);
					}
					sendHttpTextResp(200, "OK");
				}
			}

		} catch (Exception localException3) {
			ws.decConnection();
		}

		processIoTTiles();
	}

	private void processIoTTiles() {
		switch (id) {
		case "100111": // 100111 - Arduino Tron IoT
			System.out.println("100111 - Arduino Tron IoT");
			break;
		case "100222": // rule "processID rule for device ID: 100222 - Temperature-Humidity"
			com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_5Temp(temp + "' " + humidity + "%");
			break;
		case "100223": // rule "processID rule for device ID: 100223 - Temperature-Humidity"
			com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_18Temp(temp + "' " + humidity + "%");
			break;
		case "100333": // rule "processID rule for device ID: 100333 - Door Lock IoT-MCU"
/*			String door = com.piiottiles.model.StateList.getInstance().getState("DoorLock");
			if (door.indexOf("Locked") != -1) {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_8DoorUnlocked();
				com.piiottiles.model.StateList.getInstance().putState("DoorLock", "Unlocked");
			} else {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_8DoorLocked();
				com.piiottiles.model.StateList.getInstance().putState("DoorLock", "Locked");
			} */
			break;
		case "100444": // 100444 - Arduino IoT-SensorTag
			System.out.println("100444 - Arduino IoT-SensorTag");
			break;
		case "100555": // rule "Rules for device ID: 100555 - Arduino Dash Button"
			com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_15DashButtonAlert(alarm);
			break;
		case "100666": // rule "processID rule for device ID: 100666 - Door Open Sensor ESP01"
			com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_9DoorOpened();
			break;
		case "100777": // rule "processID rule for device ID: 100777 - Light Module IoT-MCU"
/*			String light = com.piiottiles.model.StateList.getInstance().getState("LightModule");
			if (light.indexOf("On") != -1) {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_10LightOff();
				com.piiottiles.model.StateList.getInstance().putState("LightModule", "Off");
			} else {
				com.piiottiles.piiottilesux.PiIoTTilesUX.getInstance().panel_10LightOn();
				com.piiottiles.model.StateList.getInstance().putState("LightModule", "On");
			}
			break; */
		case "100888": // Rules for device ID: 100888 - Arduino Tron IoT Display
			System.out.println("Rules for device ID: 100888 - Arduino Tron IoT Display");
			break;
		default:
			System.out.println("> Extended Event Token ");
		}
	}

	private void sendHttpTextResp(int status, String response) throws IOException {
		String headerText = "OK";

		switch (status) {
		case 200:
			headerText = "OK";
			break;
		case 404:
			headerText = "File not found";
			break;
		case 401:
			headerText = "Unauthorized";
			break;
		default:
			headerText = "OK";
		}

		out.println("HTTP/1.1 " + status + " " + headerText);
		out.println("Content-Length: 0");
		out.println(""); // do not forget this one

		out.flush();
		out.close();
		socket.close();
	}
}
