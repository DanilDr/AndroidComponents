package com.danildr.androidcomponents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;

public class GetJSONfromUrl {
	private String urlstr;
	
	public GetJSONfromUrl(String urlstr) {
		this.urlstr = urlstr;
	}
	
	public JSONObject getJson() {
		URL url;
		String inputLine;
		String inputInfo = "";
		JSONObject inputJson = null;
		InputStream in = null;
		try {
			url = new URL(urlstr);
	        in = url.openStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        while ( (inputLine = br.readLine()) != null) {
	        	inputInfo += inputLine; 
	        };
	        br.close();
	        in.close();
	        inputJson = new JSONObject(inputInfo);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return inputJson;
	}
}
