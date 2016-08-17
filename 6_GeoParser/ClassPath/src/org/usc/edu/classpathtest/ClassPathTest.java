package org.usc.edu.classpathtest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.omg.CORBA.portable.InputStream;

public class ClassPathTest {

	public static void main(String[] args) throws IOException {

		Runtime runTime = Runtime.getRuntime();
		Path path = Paths.get(args[0]);
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		String INTERMEDIATEPATH=null;
		String OUTPUTPATH=null;
		
		
		INTERMEDIATEPATH="./src/inter";
		OUTPUTPATH="./src/output";
		
		for (File file:files)
		{
        	try{
        		if(file.isFile())
				{
        			FileWriter fileWriter = null;
        			BufferedWriter bufferedWriter = null;
        			
        			fileWriter=new FileWriter("./src/inter/"+file.getName().replaceFirst("[.][^.]+$", "")+".txt");
        			bufferedWriter = new BufferedWriter(fileWriter);
					bufferedWriter.newLine();
					Process process = runTime.exec("java -classpath /home/aditya/src/tika/tika-app/target/tika-app-1.13-SNAPSHOT.jar:/home/aditya/EclipseTikaWorkspace/org.jar:/home/aditya/src/location-ner-model:/home/aditya/src/geotopic-mime org.apache.tika.cli.TikaCLI -J "+file);
					System.out.println("Gazzatter called for the file "+ file);
					BufferedReader stdInput = new BufferedReader(new 
						InputStreamReader(process.getInputStream()));
					BufferedReader stdError = new BufferedReader(new 
						InputStreamReader(process.getErrorStream()));
					String result = null;
					while ((result = stdInput.readLine()) != null) {
					//String result = (name+": "+ metadata.get(name));
				    bufferedWriter.write(result);
				    bufferedWriter.newLine();
					System.out.println(result);
					bufferedWriter.close();
					}
					while ((result = stdError.readLine()) != null) {
					System.out.println("Here is the standard error of the command (if any):\n");
					System.out.println(result);
					
					}
				}//END if FILE
			}//END try
			catch (IOException e) 
			{
				e.printStackTrace();
			}
        	
		}//END for files
		
		ParseJSON parseJSON = new ParseJSON();
		parseJSON.removeSquares(INTERMEDIATEPATH);
		parseJSON.getLatLangAlt(OUTPUTPATH);
		
	}	
}
