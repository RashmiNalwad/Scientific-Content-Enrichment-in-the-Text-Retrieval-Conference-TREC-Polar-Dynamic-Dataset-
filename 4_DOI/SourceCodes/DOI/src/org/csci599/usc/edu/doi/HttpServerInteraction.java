
package org.csci599.usc.edu.doi;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author Aditya Ramachandra Desai 
 * The following program connects to PHP file running on localhost via REST architecture.
 */

public class HttpServerInteraction {
	public final static String USER_AGENT = "Mozilla/5.0";
	
	public static void sendGet(String FilePath,String FileName) throws Exception {
		String url = "http://localhost/YOURLS/newAPIs/sample.php?FILE="+FilePath+"&TITLE="+FileName;
		URL obj = new URL(url);
		//System.out.println(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
	}
}