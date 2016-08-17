/**
 * 
 */
package org.csci599.usc.edu.doi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Aditya Ramachandra Desai 
 * The following program fetches the documents from the given dataset provided as args[0].
 * identifies the MIME type and generates the short URL using YOURLS PHP library. 
 * args[0] takes value of the path of the Dataset and args[1] takes the path of the tika-mimetypes.xml
 */
public class DOI {

	/** Program to Identify the file type and modify the extension
	 * @param args
	 * @throws IOException 
	 */
	static HashMap<String, String> Hmap = new HashMap<String,String>();
	static String extension="";
	static String changeExtension="";
	public static void main(String[] args) throws Exception {

		String filePath=args[0];
		File dir = new File(filePath);
		System.out.println("Path given is "+ filePath);
		File[] files = dir.listFiles();
		System.out.println(files.length);
		Tika tika = new Tika();
		String type="";
		
		
		int AOSCount=0;
		int mimeCount=0;
		File testFile = null;
		boolean success;

		
		/*
		 * Call the function to populate the HashMap
		 * */
		PopulateHashMap(args[1]);
		
		for(File f: files)
		{
			type = tika.detect(f);
			if(type.equalsIgnoreCase("application/octet-stream"))
				{
				 AOSCount=AOSCount+1;
				 }	
			else
			{	
				mimeCount=mimeCount+1;
				for (Map.Entry entry :Hmap.entrySet())
				{
					if(type.equalsIgnoreCase(entry.getKey().toString()))
					{
					extension=(String) entry.getValue();
					System.out.println("MIME TYPE MATCHED IS " + entry.getKey() +"  "+ f.getName()+"  "+extension);
					switch(extension)
					{
					case ".xwelcome": 
					case "README":
					case "abs-linkmap":
					case "abs-menulinks":
					case ".aart":
					case ".ac":
					case ".am":
					case ".classpath":
					case ".cmd":
					case ".config":
					case ".cwiki":
					case ".data":
					case ".dcl":
					case ".egrm":
					case ".ent":
					case ".ft":
					case ".fn":
					case ".fv":
					case ".grm":
					case ".g":
					case ".htaccess":
					case ".ihtml":
					case ".jmx":
					case ".junit":
					case ".jx":
					case ".manifest":
					case ".m4":
					case ".mf":
					case ".MF":
					case ".meta":
					case ".n3":
					case ".pen":
					case ".pod":
					case ".pom":
					case ".project":
					case ".properties":
					case ".rng":
					case ".rnx":
					case ".roles":
					case ".tld":
					case ".types":
					case ".vm":
					case ".vsl":
					case ".wsdd":
					case ".xargs":
					case ".xcat":
					case ".xconf":
					case ".xegrm":
					case ".xgrm":
					case ".xlex":
					case ".xlog":
					case ".xmap":
					case ".xroles":
					case ".xsamples":
					case ".xsp":
					case ".xweb":
					case ".txt":
					case ".conf":
					case ".def":
					case ".list":
					case ".in":
								  changeExtension=".txt"; break;
					case ".xht" : changeExtension=".xht"; break;
					case ".htm" : changeExtension=".html"; break;
					case ".jpg"	:
					case ".jpeg":
					case ".jpe":
					case ".jif":
					case ".jfif":
					case ".jfi": changeExtension=".jpeg";break;
					
					default: changeExtension=extension;
					}
					//Check if file is already renamed.
					if(f.getName().lastIndexOf(".")==-1)
					{
						System.out.println("NEW Extension "+ changeExtension);
						testFile = new File(args[0],f.getName()+changeExtension);
						success= f.renameTo(testFile);
						if(success)
						{
							//System.out.println("Renamed");
						}
				    }
					}
				}
			}
		}//END FOR for(File f: files)
		System.out.println("AOSCount: " + AOSCount + " mimeCount: " +mimeCount);
		try
		{
			generateShortURL(filePath);
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace().toString());
		}
		
		System.out.println("Short URLs generated");
	}//END MAIN
	
	public static void PopulateHashMap(String MIME_XMLfile)
	{
		try
		{	
			File file = new File(MIME_XMLfile);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("glob");
			for (int s = 0; s < nodeList.getLength(); s++) 
			{
				Node firstNode = nodeList.item(s);

				if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
					Element firstElement = (Element) firstNode;
					String GLOB=firstElement.getAttribute("pattern");
					Node MIME_TYPE=firstElement.getParentNode().getAttributes().item(0);
					//System.out.println("GLOB : "+GLOB.substring(1, GLOB.length()));
					//System.out.println("MIME_TYPE : " +firstElement.getParentNode().getAttributes().item(0) );
					//System.out.println(GLOB.substring(1, GLOB.length()) + " " + MIME_TYPE.toString().substring(6, MIME_TYPE.toString().length()-1));
					
					/*
					 * Made changes to the GLOB and MIME_TYPE so that they can be easily handled in the HashMap and 
					 * easy to access in String format. Check the definition of Hmap which is not Global Static
					 * */
					GLOB=GLOB.substring(1, GLOB.length());
					String MIME_TYPE_STRING=MIME_TYPE.toString().substring(6, MIME_TYPE.toString().length()-1);

					//System.out.println(GLOB + " " + MIME_TYPE_STRING);
					Hmap.put(MIME_TYPE_STRING, GLOB);
					
					
				}
			}
		}
		
		catch (Exception e) 
		{
			System.out.println("Inside Identifier catch");
			e.printStackTrace();
		}
		System.out.println("HASH MAP IS POPULATED");
		for (Map.Entry entry :Hmap.entrySet())
		{
			System.out.println("KEY is " +entry.getKey() + " VALUE is "+entry.getValue() );
		}
	}
	
	public static void generateShortURL(String filePath) throws Exception
	{
		
		HttpServerInteraction http = new HttpServerInteraction();
		File dir = new File(filePath);
		System.out.println("Path given is "+ filePath);
		File[] files = dir.listFiles();
		System.out.println(files.length);
		
		for(File file:files)
		{
			try
			{
				http.sendGet(filePath+file.getName(),file.getName().substring(0, file.getName().lastIndexOf(".")));
			}
			catch(Exception e)
			{
				System.out.println(file.getName());
				System.out.println(e.getStackTrace().toString());
			}
		}
		
	}
	
}

