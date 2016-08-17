import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.csci599.trec.Globals;

public class Scholar {

	public static void main(String[] args) throws IOException 
	{
		Path path = Paths.get("F:\\Rashmi\\Step_5\\Nasa4_Pdf_JSON");
//		Path path = Paths.get("D:\\CSCI599\\Tike_WS\\TREC_MIME\\src\\outputGrobidJSON");
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		String s = null;

		for(File file:files)
		{
			//Extracting Author information from extracted TEI metadata
			String author =  JsnParser.parseJsonAuthor(file.getAbsolutePath());
			//Extracting Title information from extracted TEI metadata
			String title =  JsnParser.parseJsonTitle(file.getAbsolutePath());
			//Extracting Year information from extracted TEI metadata
			String year = JsnParser.parseJsonDate(file.getAbsolutePath());
			Path pat = Paths.get("/src/outputGrobidJSON");
			String fileN = file.getName().split("\\.")[0];
			String strFilePath = Globals.getPWD() + pat.toString() +"\\" + fileN + "_Output.txt" ;
			BufferedWriter bw = new BufferedWriter(new FileWriter(strFilePath));
			try 
			{
				bw.write("{\n");
				bw.write("\"Author\":");
				if(author != null)
				{
					bw.write("\"" + author +"\",\n");
				}
				else
				{
					bw.write("null,\n");
				}
				bw.write("\"Title\":");
				if(title != null)
				{
					bw.write("\"" + title +"\",\n");
				}
				else
				{
					bw.write("null,\n");
				}
				bw.write("\"Year\":");
				if(year != null)
				{
					year = year.split("-")[0];
					bw.write("\"" + year +"\",\n");
				}
				else
				{
					bw.write("null,\n");
				}
				bw.write("\"Related Publications\"" + ":\n\"");
				if(author != null)
				{
					//Calling Scholar.py to extract related publications based on author name
					String command = "python D:\\CSCI599\\scholar.py-master\\scholar.py-master\\scholar.py -c  30 -author " + "\"" + author;
					Process p = Runtime.getRuntime().exec(command);
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
					while ((s = stdInput.readLine()) != null) 
					{
					    bw.write(s);
					    bw.write("\n");
					}
				}

				if(title != null)
				{
					//Calling Scholar.py to extract related publications based on title
					String command = "python D:\\CSCI599\\scholar.py-master\\scholar.py-master\\scholar.py -c  30 -title " + "\"" + title;
					Process p = Runtime.getRuntime().exec(command);
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((s = stdInput.readLine()) != null) 
					{
						bw.write(s);
					    bw.write("\n");
					}
				}
				bw.write("\"\n");
				bw.write("}");
				bw.close();
				try {
				    Thread.sleep(30000);
				    Thread.sleep(30000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
			catch (IOException e) {
				System.out.println("exception happened - here's what I know: ");
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

}
