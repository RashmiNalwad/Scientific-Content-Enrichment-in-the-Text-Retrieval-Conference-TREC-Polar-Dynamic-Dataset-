package org.usc.edu.classpathtest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class ParseJSON {
	
		int length=0;
		String line = null;
		int count =0;
	
	public void removeSquares(String InterFilePath) throws IOException
	{
		Path path = Paths.get(InterFilePath);
		System.out.println("Inside removeSquares in ParseJSON "+InterFilePath);
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		for(File file:files)
		{	
			FileInputStream fis = new FileInputStream(file);
			FileWriter fileWriterOutput = new FileWriter("./src/output/"+file.getName().replaceFirst("[.][^.]+$", "")+"_Output.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriterOutput);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while ((line = br.readLine()) != null) {
				length=line.length();
				if(length!=0)
				{
					String result = line.substring(1, length-1);
					System.out.println(line.substring(1, length-1));
					bufferedWriter.write(result);
					bufferedWriter.newLine();
				}
				//bufferedWriter.newLine();
			}
			br.close();
			bufferedWriter.close();
		}//End for loop
	}//end removeSquares

	public void getLatLangAlt(String OutputFilePath) throws IOException
	{
		
		Path path = Paths.get(OutputFilePath);
		System.out.println("Inside getDetails in ParseJSON "+OutputFilePath);
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		//FileWriter finalOutput = new FileWriter("./src/output/Output.json");
		//BufferedWriter finalbufferedWriter = new BufferedWriter(finalOutput);
		for(File file:files)
		{
			//System.out.println(file.getName());
			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(new FileReader(file));
				JSONObject jsonObject = (JSONObject) obj;
				//System.out.println("Removing brackets "+jsonObject.toJSONString());
				String Latitude = (String) jsonObject.get("Geographic_LATITUDE");
				String Longitude = (String) jsonObject.get("Geographic_LONGITUDE");
				String Altitude = (String) jsonObject.get("Geographic_ALTITUDE");
				String Geographic_NAME = (String) jsonObject.get("Geographic_NAME");
				
				
				//if(Latitude!=null || Longitude!=null)
				//{
				FileWriter finalOutput = new FileWriter("./src/json/"+file.getName().replaceFirst("[.][^.]+$", "")+".json");
				BufferedWriter finalbufferedWriter = new BufferedWriter(finalOutput);
				//finalbufferedWriter.write("{"+"\""+file.getName().replaceFirst("[.][^.]+$", "")+".json"+"\""+":["+"\n");
				finalbufferedWriter.write("{"+"\"Geographic_LATITUDE\":" + "\""+Latitude+"\""+","+"\n");
				finalbufferedWriter.write("\"Geographic_LONGITUDE\":" +"\""+Longitude+"\""+","+"\n");
				finalbufferedWriter.write("\"Geographic_NAME\":" +"\""+Geographic_NAME+"\""+","+"\n");
				finalbufferedWriter.write("\"Geographic_ALTITUDE\":" +"\""+Altitude+"\""+"}"+"\n");
				//finalbufferedWriter.write("]}");
				finalbufferedWriter.write("\n");
				finalbufferedWriter.close();
				System.out.println(file.getName());
				System.out.println("Geographic_LATITUDE: " + Latitude);
				System.out.println("Geographic_LONGITUDE: " + Longitude);
				System.out.println("Geographic_NAME: " + Geographic_NAME);
					
				//}
			 }//end try 
			catch (Exception e) 
				{
				e.printStackTrace();
				}
			}
		//finalbufferedWriter.close();
	  }
}
	 

