import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map.Entry;

import org.csci599.trec.Globals;


public class combineJSON {

	/*
	 * Code to combine output of 3,4,5,6,7 into one .json file which will be fed to Apache Solr.
	 */
	
	public static void main(String[] args) throws IOException
	{	
		Path path = Paths.get("D:\\CSCI599\\Tike_WS\\TREC_MIME\\src\\combineJSON");
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		HashMap<String, HashMap<String, String>> fileContentsMap = new HashMap<String, HashMap<String,String>>();
		int z = 0;
		for(File file:files)
		{
			String fileName = file.getName().split("\\.")[0];
			if( !fileContentsMap.containsKey(fileName))
			{
				fileContentsMap.put(fileName, new HashMap<String, String>());
			}
			//Parse all files and add in map file_name as key and extracted information as Arraylist of values.
			JsnParser.parseJsonFile(file.getAbsolutePath(),fileContentsMap,fileName,file);
		}
		//Write contents of map to output with filename being key and value as content of file.
		for(Entry<String, HashMap<String, String>> map:fileContentsMap.entrySet())
		{
			String fileName = map.getKey();
			Path pat = Paths.get("/src/FinalOutput");
			String fileN = fileName;
			String strFilePath = Globals.getPWD() + pat.toString() +"\\" + fileN + ".json";
			BufferedWriter bw = new BufferedWriter(new FileWriter(strFilePath));
			bw.write("{\n");
			HashMap<String, String> valueMap = map.getValue();
			int len = valueMap.size();
			int i=0;
			if(!valueMap.containsKey("DOI"))
			{
				z++;
			}
			bw.write("\"" + "id" + "\"" + " : " + "\"" + valueMap.get("Keyword") + "\"" + "," +"\n");
			for(Entry<String,String> entry:valueMap.entrySet())
			{
				i++;
				if( i > len-1)
				{
					if( entry.getKey() == "Keyword" )
					{
						
					}
					if(entry.getValue().toString().equals("null") || entry.getValue().toString().length() <= 8 || entry.getValue().toString().length() <= 9)
					{
						if(entry.getValue().toString().equals("null"))
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + "\"" + entry.getValue()+ "\"" + "]" +"\n");
						}
						else
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " +  entry.getValue()  +"\n");
						}
					}
					else if(entry.getKey() == "NER_MEASUREMENT_MONEY")
					{
						if(entry.getValue().toString().equals("null"))
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + "\"" + entry.getValue()+ "\"" + "]" +"\n");
						}
						else
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " +  entry.getValue()  +"\n");
						}
					}
					else if(entry.getKey() == "NER_PHONE_NUMBER")
					{
						if(entry.getValue().toString().equals("null"))
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + "\"" + entry.getValue()+ "\"" + "]" +"\n");
						}
						else
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + entry.getValue()  +"\n");
						}
					}
					else if(entry.getValue().toString().equals("null") || entry.getValue().toString().length() <= 8 || entry.getValue().toString().length() <= 9)
					{
						if(entry.getValue().toString().equals("null"))
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + "\"" + entry.getValue()+ "\"" + "]" +"\n");
						}
						else
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + entry.getValue()  +"\n");
						}
					}
					else if(entry.getKey() == "Related_Publications")
					{
						bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + entry.getValue() + "]" +"\n");
					}
					else if(entry.getKey() == "SWEET")
					{
						bw.write("\"" + entry.getKey() + "\"" + " : "  + entry.getValue() +"\n");
					}
					else
					{
						bw.write("\"" + entry.getKey() + "\"" + " : " + "\"" +entry.getValue() + "\"" +"\n");
					}
				}
				else
				{
					if( entry.getKey() == "Keyword" )
					{
						
					}
					else if(entry.getKey() == "NER_MEASUREMENT_TEMP")
					{
						if(entry.getValue().toString().equals("null") || entry.getValue().toString().length() == 8 || entry.getValue().toString().length() == 9)
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + "\""  + entry.getValue()+ "\""  + "]" + "," +"\n");
						}
						else
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + entry.getValue()  + "]" + "," +"\n");
						}
					}
					else if(entry.getKey() == "NER_MEASUREMENT_MONEY")
					{
						if(entry.getValue().toString().equals("null"))
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + "\""  + entry.getValue()+ "\""  + "]" + "," +"\n");
						}
						else
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " +  entry.getValue()  + "," +"\n");
						}
					}
					else if(entry.getKey() == "NER_PHONE_NUMBER")
					{
						if(entry.getValue().toString().equals("null"))
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + "\""  + entry.getValue()+ "\""  + "]" + "," +"\n");
						}
						else
						{
							bw.write("\"" + entry.getKey() + "\"" + " : "  + entry.getValue()  +  "," +"\n");
						}
					}
					else if(entry.getKey() == "NER_MEASUREMENT_PRESSURE")
					{
						if(entry.getValue().toString().equals("null") || entry.getValue().toString().length() <= 8 || entry.getValue().toString().length() <= 9)
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + "\""  + entry.getValue()+ "\""  + "]" + "," +"\n");
						}
						else
						{
							bw.write("\"" + entry.getKey() + "\"" + " : " + entry.getValue() + "," +"\n");
						}
					}
					else if(entry.getKey() == "Related_Publications")
					{
						bw.write("\"" + entry.getKey() + "\"" + " : " + "[" + entry.getValue()  + "]" + "," +"\n");
					}
					else if(entry.getKey() == "SWEET")
					{
						bw.write("\"" + entry.getKey() + "\"" + " : " +entry.getValue() + "," +"\n");
					}
					else
					{
						bw.write("\"" + entry.getKey() + "\"" + " : " + "\"" +entry.getValue() + "\"" + "," +"\n");
					}
				}
			}
			bw.write("}");
			bw.close();
		}
		System.out.println("Count of files missing DOI" + z );

	}

}
  