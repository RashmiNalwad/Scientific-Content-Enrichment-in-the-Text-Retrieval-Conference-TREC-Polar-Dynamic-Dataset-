package grobid.src.main;

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

public class GrobidParser {

    public static void main(String[] args) {
        try {
            System.out.println("Opening GeoParsing");
            Runtime runTime = Runtime.getRuntime();
            Path path = Paths.get("/Users/Shashank/Desktop/Rashmi/Nasa4_Pdf/");
    		File dir = new File(path.toString());
    		File[] files = dir.listFiles();
    		Process process;

    		for (File f : files)
    		{
             process = runTime.exec("java -classpath /Users/Shashank/src/grobidparser-resources/:/Users/Shashank/src/tika/tika-app/target/tika-app-1.13-SNAPSHOT.jar org.apache.tika.cli.TikaCLI --config=/Users/Shashank/src/grobidparser-resources/tika-config.xml -J "+f);
             BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                System.out.println("Here is the standard output of the command:\n");
                String strFilePath="/Users/Shashank/Desktop/Rashmi/Nasa4_Pdf_JSON/"+f.getName()+".json";
    			FileWriter fileWriter = new FileWriter(strFilePath);
    			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                String s = null;
                while ((s = stdInput.readLine()) != null) {
                	bufferedWriter.write(s);
                    System.out.println(s);
                }

    		
                // read any errors from the attempted command
                System.out.println("Here is the standard error of the command (if any):\n");
                while ((s = stdError.readLine()) != null) 
                {
                    System.out.println(s);
                }
                bufferedWriter.close();
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  }
