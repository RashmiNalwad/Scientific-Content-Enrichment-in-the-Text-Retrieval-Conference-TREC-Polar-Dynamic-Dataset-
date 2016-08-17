package org.usc.edu.metadata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Geographic;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class MetaExtract {

	

	public static void main(String[] args) throws IOException, SAXException, TikaException {
		
		//fileSysCheck();
		
		Path path = Paths.get(args[0]);
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileWriter fileWriter = new FileWriter("./src/output.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		for(File file:files){
		FileInputStream inputstream = new FileInputStream(file);
		bufferedWriter.write(file.getName());
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		ParseContext context = new ParseContext();
		parser.parse(inputstream, handler, metadata, context);
		//metadata.set(Geographic.LATITUDE, 34.56);
		//metadata.set(Geographic.LONGITUDE, 31.86);
		
		String[] metadataNames = metadata.names();
		System.out.println(file.getName());
	      for(String name : metadataNames) {		        
	         System.out.println(name + ": " + metadata.get(name));
	         String result = (name+": "+ metadata.get(name));
	         
	         bufferedWriter.write(result);
	         bufferedWriter.newLine();
	    	  
	      }
	      inputstream.close();
		}
		bufferedWriter.close();
		
	}
	public static void fileSysCheck()
	{
		FileSystem fileSystem = FileSystems.getDefault();

		System.out.println("FILE SYSTEM CHECK");
		for(FileStore store : fileSystem.getFileStores()){
		  System.out.println(store.toString());
		  for (String view : fileSystem.supportedFileAttributeViews()){
		    if ( store.supportsFileAttributeView(view)){
		      System.out.print(view+",");
		    }
		  }
		  System.out.println("");
		}
	}

}
