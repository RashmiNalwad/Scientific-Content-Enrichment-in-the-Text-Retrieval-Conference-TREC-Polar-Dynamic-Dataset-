package com.usc.edu.newmime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;

public class NewMimeType {


	public static void main(String[] args) throws IOException {

		/*Tika tika = new Tika();
		File file = new File("/home/aditya/src/geotopic-mime/polar.geot");
		Runtime runTime = Runtime.getRuntime();
		
		System.out.println(tika.detect(file));
		Process process = runTime.exec("java -classpath /home/aditya/src/tika/tika-app/target/tika-app-1.13-SNAPSHOT.jar:/home/src/location-ner-model:/home/src/geotopic-mime org.apache.tika.cli.TikaCLI -m "+file);
		BufferedReader stdInput = new BufferedReader(new 
				InputStreamReader(process.getInputStream()));
		String s = null;
		while ((s = stdInput.readLine()) != null) {
		System.out.println(s);
		}*/
		Path path = Paths.get("/media/aditya/Work and Personal/rawDataSet/nasa/nasa");
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		String type=null;
		for (File f : files)
		{
			if(f.isFile())
			{
				Tika tika =  new Tika();
				type=tika.detect(f);
				String currentLocation=f.toString();
				String newLocation="./src/geot"+f.getName();
				File _file=new File(currentLocation);
				if(type.equalsIgnoreCase("application/geotopic"))
				{
					_file.renameTo(new File(newLocation));
					System.out.println("GEOT File found" + f.getName());
				}
			}	
		}
	}

}
