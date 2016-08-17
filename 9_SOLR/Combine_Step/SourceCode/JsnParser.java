import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class JsnParser {

	//Extracting Author information from extracted TEI metadata
	public static String parseJsonAuthor(String path)
	{
		String author = null;
		JSONParser parser = new JSONParser();
		try
		{
			JSONArray a = (JSONArray)parser.parse(new FileReader(path));
			for(Object o:a)
			{
				JSONObject jsonObject = (JSONObject)o;
				author = (String)jsonObject.get("Author");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(author != null)
		{
			System.out.println(author);
			return author;
		}
		return null;
	}
	
	//Extracting Title information from extracted TEI metadata
	public static String parseJsonTitle(String path)
	{
		String title = null;
		JSONParser parser = new JSONParser();
		try
		{
			JSONArray a = (JSONArray)parser.parse(new FileReader(path));
			for(Object o:a)
			{
				JSONObject jsonObject = (JSONObject)o;
				title = (String)jsonObject.get("grobid:header_Title");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if( title != null)
		{
			System.out.println(title);
			return title;
		}
		return null;
	}
	
	//Extracting Year information from extracted TEI metadata
	public static String parseJsonDate(String path)
	{
		String date = null;
		JSONParser parser = new JSONParser();
		try
		{
			JSONArray a = (JSONArray)parser.parse(new FileReader(path));
			for(Object o:a)
			{
				JSONObject jsonObject = (JSONObject)o;
				date = (String)jsonObject.get("meta:creation-date");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if( date != null)
		{
			System.out.println(date);
			return date;
		}
		return null;
	}

	public static void parseJsonFile(String path,HashMap<String, HashMap<String, String>> fileContentsMap, String fileName, File file)
	{
		JSONParser parser = new JSONParser();
		try
		{
				String res = readFileToString(path);
				JSONObject jsonObject = (JSONObject)parser.parse(res);
				for(Object set:jsonObject.entrySet())
				{
				   String[] setString = set.toString().split("=");
				   if(setString[0].equals("Geographic_LATITUDE"))
				   {
					   String latitude = setString[1]; 
					   if(latitude.equals("null"))
					   {
						   fileContentsMap.get(fileName).put("Geographic_LATITUDE", "0.0");
					   }
					   else
					   {
						   fileContentsMap.get(fileName).put("Geographic_LATITUDE", latitude);
					   }
				   }
				   
				   if(setString[0].equals("Geographic_LONGITUDE"))
				   {
					   String longitude = setString[1]; 
					   if(longitude.equals("null"))
					   {
						   fileContentsMap.get(fileName).put("Geographic_LONGITUDE", "0.0");
					   }
					   else
					   {
						   fileContentsMap.get(fileName).put("Geographic_LONGITUDE", longitude);
					   }
				   }
				   
				   if(setString[0].equals("Geographic_ALTITUDE"))
				   {
					   String altitude = setString[1]; 
					   fileContentsMap.get(fileName).put("Geographic_ALTITUDE", "0.0");
				   }
				   if(setString[0].equals("Related Publications"))
				   {
					   String relatedPub = set.toString();
					   //Extract Author and Affiliation information from related Publications
					   relatedPub = processRelatedPublications(relatedPub);
					   fileContentsMap.get(fileName).put("Related_Publications",relatedPub);
				   }
				   
				   if(setString[0].equals("NER_PHONE_NUMBER"))
				   {
					   String nerMeasurementGeneral = setString[1];
					   fileContentsMap.get(fileName).put("NER_PHONE_NUMBER",nerMeasurementGeneral);
				   }
				   
				   if(setString[0].equals("NER_MEASUREMENT_MONEY"))
				   {
					   String nerMeasurementMoney = setString[1];
					   fileContentsMap.get(fileName).put("NER_MEASUREMENT_MONEY",nerMeasurementMoney);
				   }
				   
				   if(setString[0].equals("NER_MEASUREMENT_TEMP"))
				   {
					   String nerMeasurementTemp = setString[1];
					   fileContentsMap.get(fileName).put("NER_MEASUREMENT_TEMP",nerMeasurementTemp);
				   }
				   
				   if(setString[0].equals("NER_MEASUREMENT_PRESSURE"))
				   {
					   String nerMeasurementPressure = setString[1];
					   fileContentsMap.get(fileName).put("NER_MEASUREMENT_PRESSURE",nerMeasurementPressure);
				   }
				   
				   if(setString[0].equals("DOI"))
				   {
					   String doi = setString[1];
					   fileContentsMap.get(fileName).put("DOI",doi);
				   }
				   
				   if(setString[0].equals("Keyword"))
				   {
					   String keyword = setString[1];
					   fileContentsMap.get(fileName).put("Keyword",keyword);
				   }
				   
				   
				   if(setString[0].equals("Author"))
				   {
					   String author = setString[1];
					   fileContentsMap.get(fileName).put("Author",author);
				   }
				   
				   if(setString[0].equals("Year"))
				   {
					   String year = setString[1];
					   fileContentsMap.get(fileName).put("Year",year);
				   }
				   
				   if(setString[0].equals("Title"))
				   {
					   String title = setString[1];
					   fileContentsMap.get(fileName).put("Title",title);
				   }
				   
				   if(setString[0].equals("Geographic_NAME"))
				   {
					   String name = setString[1];
					   fileContentsMap.get(fileName).put("Geographic_NAME",name);
				   }
				   
				   if(setString[0].equals("SWEET"))
				   {
					   String sweet = setString[1];
					   fileContentsMap.get(fileName).put("SWEET",sweet);
				   }
				   
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(fileName);
		}
	}
	
	//Extract Author and Affiliation information from related Publications
	private static String processRelatedPublications(String relatedPub) throws IOException, TikaException 
	{
		StringBuilder sb = new StringBuilder();
		
			String[] pub = relatedPub.split("---");
			ArrayList<String> arr = new ArrayList<String>();
			for(int i=0;i<pub.length;i++)
			{
				String value = "";
				try
				{
					value = extractRelatedInfo(pub[i]);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				if(i == pub.length-1)
				{
					arr.add("\"" + pub[i] + "   Authors&Affiliations  " + value + "\"");
				}
				else
				{
					arr.add("\"" + pub[i] + "   Authors&Affiliations  " + value + "\",\n");
				}
			}
			for(String a:arr)
			{
				sb.append(a);
			}
		return sb.toString();
	}

	//Extract Author and Affiliation information from related Publications
	private static String extractRelatedInfo(String string) throws IOException, TikaException
	{
		String authorsAffiliations = "";
		try
		{
			int titleIndex = string.indexOf("Title");
			int urlIndex = string.indexOf("URL");
			int yearIndex = string.indexOf("Year");
			if( urlIndex != -1 && yearIndex != -1 && titleIndex != -1)
			{
				String title = string.subSequence(titleIndex, urlIndex).toString();
				title = title.split("Title ")[1];
				String url = string.subSequence(urlIndex, yearIndex).toString();
				Tika tika = new Tika();
				url = url.split("URL ")[1];
				tika.detect(url.trim()); //tika used to extract the data from the URL captured by extracted related Publications data.
				URL u = new URL(url);
				String parsedText = tika.parseToString(u);
				parsedText = parsedText.toLowerCase();
				title = title.toLowerCase();
				int tIndex = parsedText.indexOf(title.trim());
				int abstractIndex = parsedText.indexOf("\n\n\nabstract");
				if(abstractIndex != -1 && tIndex != -1)
				{
					authorsAffiliations = parsedText.subSequence(tIndex,abstractIndex).toString();
					authorsAffiliations = authorsAffiliations.trim();
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return authorsAffiliations;	
	}

	private static String readFileToString(String path) throws IOException 
	{
		File file = new File(path.toString());
		FileReader fr = new FileReader(file);
		BufferedReader br  = new BufferedReader(fr);
		String line = "";
        StringBuilder sb = new StringBuilder();
		while ((line = br.readLine())!=null)
		{
			if(line.isEmpty())
			{
				sb.append("---");
			}
			else
			{
				sb.append(line);
			}
		}
		br.close();
        return sb.toString();
	}
}
