package com.usc.edu.vanila;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class ExtractText {

	public static void main(String[] args) throws IOException, SAXException, TikaException {
		
		Tika tika = new Tika();
		Path path = Paths.get(args[0]);
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		for (File file:files)
		{
			if(file.isFile())
			{
				
			  try	
			  {
				FileInputStream inputstream = new FileInputStream(file);
				System.out.println(file.getName());
				String filecontent = tika.parseToString(file);
				
				FileWriter fileWriter = new FileWriter("./src/geot/"+file.getName().replaceFirst("[.][^.]+$", "")+".geot");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			    System.out.println("Extracted Content:\n" + filecontent);
				bufferedWriter.write(filecontent);
				
				
				/*Parser parser = new AutoDetectParser();
				BodyContentHandler handler = new BodyContentHandler();
				Metadata metadata = new Metadata();
				ParseContext context = new ParseContext();
				parser.parse(inputstream, handler, metadata, context);
				String[] metadataNames = metadata.names();
			      for(String name : metadataNames) {		        
			         System.out.println(name + ": " + metadata.get(name));
			         String result = (name+": "+ metadata.get(name));
			         System.out.println(result); 
			      }*/
			      inputstream.close();
			      bufferedWriter.close();
			  }//end try
			  catch (Exception exception)
			  {
				  
			  }
			}
		}
		
	}

}
