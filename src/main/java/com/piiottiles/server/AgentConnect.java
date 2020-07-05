package com.piiottiles.server;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import com.piiottiles.RPiIoTTiles;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.piiottiles.model.AgentsList;

import java.net.URL;
import java.util.Date;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class AgentConnect {

	private AgentsList agentsList;
	private String knowledgeDebug = "none";
	private final String USER_AGENT = "Mozilla/5.0";
	private static AgentConnect AGENTCONNECT_INSTANCE = null;

//	private final Logger logger = LoggerFactory.getLogger(AgentConnect.class);

	public AgentConnect(AgentsList agentsList) {
		this.agentsList = agentsList;
		AgentConnect.AGENTCONNECT_INSTANCE = this;
	}

	public static AgentConnect getInstance() {
		return AGENTCONNECT_INSTANCE;
	}

	// HTTP GET request
	public void sendGet(String agentName, String command) {
		String agentIP = agentsList.getAgent(agentName);
		if ((agentIP == "") || (agentIP.indexOf("0.0.0.0") != -1)) {
			agentNotDefined(agentName);
			return;
		}

		String urlString = agentIP + command;
		try {
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// By default it is GET request
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("Send GET request: " + url);
			System.out.println("Response code: " + responseCode);

			// Reading response from input Stream
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer response = new StringBuffer();
			String output;

			while ((output = in.readLine()) != null) {
				response.append(output);
			}
			in.close();

			// printing result from response
			System.out.println(response.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// HTTP Post request
	public void sendPost(String agentName, String command) {
		String agentIP = agentsList.getAgent(agentName);
		if ((agentIP == "") || (agentIP.indexOf("0.0.0.0") != -1)) {
			agentNotDefined(agentName);
			return;
		}

		String postMsg = agentIP + "/?id=" + RPiIoTTiles.id;

		java.util.Date date = new Date();
		long fixtime = date.getTime();
		fixtime = (long) (fixtime * 0.001);
		postMsg = postMsg + "&timestamp=" + Long.toString(fixtime);

		//postMsg = postMsg + "&event=" + Main.USER.getID();
		postMsg = postMsg + "&process=" + RPiIoTTiles.process;
		postMsg = postMsg + "&name=" + RPiIoTTiles.name;
		//postMsg = postMsg + "&keypress=1.0";
		postMsg = postMsg + command;
		
		try {
			URL obj = new URL(postMsg);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// Setting basic post request
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/json");

			String postJsonData = "AI-IoTBPM";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postJsonData);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if (knowledgeDebug.indexOf("none") == -1) {
				System.out.println("Send 'POST' request to URL: " + postMsg);
				System.out.println("Post Data: " + postJsonData);
				System.out.println("Response Code: " + responseCode);
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer response = new StringBuffer();
			String output;

			while ((output = in.readLine()) != null) {
				response.append(output);
			}
			in.close();

			// printing result from response
			if (knowledgeDebug.indexOf("none") == -1) {
				System.out.println(response.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// get Agent URL
	public String agentURL(String agentName) {
		String agentIP = agentsList.getAgent(agentName);
		if ((agentIP == "") || (agentIP.indexOf("0.0.0.0") != -1)) {
			agentNotDefined(agentName);
		}
		return agentIP;
	}
	
	public void agentNotDefined(String agentName) {
		System.err.println("Note: Send Arduino Command " + agentName
				+ " in agentDevice=[AgentName,http://10.0.0.2,...] defined in iotbpm.properties file.");
	}
}
