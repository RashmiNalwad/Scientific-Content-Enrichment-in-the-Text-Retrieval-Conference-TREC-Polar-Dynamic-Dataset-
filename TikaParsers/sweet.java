package sweet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class sweet {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//Path path = Paths.get("/Users/Shashank/Desktop/Rashmi/Nasa_1/");
		Path path = Paths.get("/Users/Shashank/Desktop/Rashmi/ALL");
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		String type=null;
		int j=0;
		HashMap<String, Integer> hmap_ontologies = new HashMap<String, Integer>();
		HashMap<String, Integer> tika_type = new HashMap<String, Integer>();
        hmap_ontologies.put("Climate", 1);
        hmap_ontologies.put("Environmental",1);
		for (File f : files)
		{

			if(f.isFile())
			{
				Tika tika =  new Tika();


				try{
					type=tika.detect(f);
					/*if(!tika_type.containsKey(type))
		    		{
		    			//if(s.length()>1)
		    				tika_type.put(type,1);
		    		}
					else
					{ 
						int val=(int)tika_type.get(type).intValue();
						val++;
						tika_type.put(type,val);
						
					    
						
					}*/
					
					ContentHandler handler = new ToXMLContentHandler();

					AutoDetectParser parser = new AutoDetectParser();
					Metadata metadata = new Metadata();
					try (InputStream stream = new FileInputStream(f)) {
						parser.parse(stream, handler, metadata);
						

						String teststring=handler.toString();
						//System.out.println(teststring);
						
						    Pattern regex = Pattern.compile("[#][a-zA-Z]+");
						    Matcher regexMatcher = regex.matcher(teststring);
						    while (regexMatcher.find()) {
						    	
						    	String s=regexMatcher.group();
						    		s=s.replaceAll("#","");
						    		
						    		if(!hmap_ontologies.containsKey(s))
						    		{
						    			if(s.length()>1)
						    			hmap_ontologies.put(s,1);
						    		}
						    		
			}
					}
				}
				catch (Exception e) {  
					e.printStackTrace();  

				} 
			}	
		}
		System.out.println("done");
		
		Path path1 = Paths.get("/Users/Shashank/Desktop/Rashmi/ALL_DATA/");
		//Path path = Paths.get("/Users/Shashank/Desktop/Rashmi/Nasa_1/");
		File dir1 = new File(path1.toString());
		File[] files1 = dir1.listFiles();
		
		
		for (File f : files1)
		{
			
			String strFilePath="/Users/Shashank/Desktop/Rashmi/SWEET/" + f.getName() + ".tsv";
			
			FileWriter fileWriter = new FileWriter(strFilePath);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			if(f.isFile())
			{
				Tika tika =  new Tika();
				try{
					type=tika.detect(f);
					ContentHandler handler = new ToXMLContentHandler();
					AutoDetectParser parser = new AutoDetectParser();
					Metadata metadata = new Metadata();
					try (InputStream stream = new FileInputStream(f)) {
					
						parser.parse(stream, handler, metadata);
						String str=handler.toString();
						
						//System.out.println(str);
					//String str=tika.parseToString(f);
	
	   	             String line;
	   	          str = str.replaceAll("(?s)<!--.*?-->", "");
	   	       str = str.replaceAll("(?s)<script.*?>.*?</script>", "");
	   	    str = str.replaceAll("(?s)<SCRIPT.*?>.*?</SCRIPT>", "");
	   	 str = str.replaceAll("(?s)<style.*?>.*?</style>", "");
	   	str = str.replaceAll("\\s","");

	   		String search="";
	             		
	       	           	
	             				 for(Entry<String, Integer> entry:hmap_ontologies.entrySet())
	             				 {
	          	 
	             					search=entry.getKey(); 
	             					   				 
	             				if (str.toLowerCase().indexOf(search.toLowerCase()) != -1 ) {
	             					int val=entry.getValue();
	             					val++;
	             					hmap_ontologies.put(search, val);
	             					bufferedWriter.write(search + "\n");
	             				}
	             				else
	             				{
//	             					System.out.println(" Not Success");
	             				}
	             				
	             				}	
	             			}
				}
				catch (Exception e) {  
					e.printStackTrace();  

				}
				finally
				{
					
				}
				bufferedWriter.close();

			}	

		}
		
		String strFilePath2="/Users/Shashank/Desktop/sweet.csv";
		FileWriter fileWriter2 = new FileWriter(strFilePath2);
		BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
		Integer f=12;
		
			
		
		
		//bufferedWriter2.write("\""+ "id"+ "\""+ "," + "\""+ "id"+ "\""+ "," +","score","weight","color","label");
		//"FIS",1.1,59,0.5,"#9E0041","Fisheries"
		for(Entry<String, Integer> entry:hmap_ontologies.entrySet())
		 {
            String str="\""+f+"\"";
            String str1;
            Random generator = new Random();
    		Integer num = generator.nextInt(899999) + 100000;
    		int finalcode=Integer.valueOf(String.valueOf(num), 16);
    		finalcode/=10;
          bufferedWriter2.write("\""+entry.getKey()+"\","+entry.getValue()+",0.5,#"+finalcode+",\""+entry.getKey()+"\"");
		 
            bufferedWriter2.newLine(); 
		 }
		
		bufferedWriter2.close();
		System.out.println("done");
	}
}


