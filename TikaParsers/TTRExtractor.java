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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;


public class TTRExtractor {

	double[] smoothed_values=new double [500000];
	public static Map<Integer, Double> ttr = new LinkedHashMap<Integer,Double>();
	public static Map<Integer, String> ttr_str = new LinkedHashMap<Integer,String>();
	public String[] extractText(String html, int k,String string) {
		String[] str = null;
		Double res;
		String line;
		html = html.replaceAll("(?s)<!--.*?-->", "");
		html = html.replaceAll("(?s)<script.*?>.*?</script>", "");
		html = html.replaceAll("(?s)<SCRIPT.*?>.*?</SCRIPT>", "");
		html = html.replaceAll("(?s)<style.*?>.*?</style>", "");


		BufferedReader br = new BufferedReader(new StringReader(html));
		int numLines = 0;
		try
		{
			while(br.readLine()!=null)
			{

				int tag = 0;
				int text = 0;
				line = br.readLine();
				for (int i = 0; i >= 0 && i < line.length(); i++) {
					if (line.charAt(i) == '<') {
						tag++;
						i = line.indexOf('>', i);
						if (i == -1) {
							break;
						}
					} else if (tag == 0 && line.charAt(i) == '>') {
						text = 0;
						tag++;
					} else {
						text++;
					}

				}
				if (tag == 0) {
					tag = 1;
				}
				res= (double) text / (double) tag;
				ttr_str.put(numLines, line);
				smoothed_values[numLines]=res;
				numLines++;
			}

			//Smoothening of values
			smoothed_values=smooth(smoothed_values,2);
			for(int i=0; i<numLines;i++)
				ttr.put(i, smoothed_values[i]);
			ArrayList<Integer> arr= new ArrayList<Integer>();
			ttr = sortHashMapByValuesD(ttr);
			int var = 0;
			for(Entry<Integer, Double> entry:ttr.entrySet())
			{
				Integer key = entry.getKey();
				if(var >= numLines/2)
				{
					arr.add(key);
				}
				var++;
			}
			String strFilePath="/Users/Shashank/Desktop/Rashmi/ALL_INTERMEDIATE/"+string+".txt";
			FileWriter fileWriter = new FileWriter(strFilePath);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);


			for(int i=0;i<arr.size();i++)
			{   
				bufferedWriter.write(ttr_str.get(arr.get(i)));
				bufferedWriter.write("\n");
			}
			bufferedWriter.close();


			// to read from each output file and put in the json format

			 
			try {
				System.out.println("Opening Tag ratios");
				Runtime runTime = Runtime.getRuntime();
				
				Path path = Paths.get("/Users/Shashank/Desktop/Rashmi/ALL_INTERMEDIATE/");
				File dir = new File(path.toString());
				File[] files = dir.listFiles();
				Process process;
				process = runTime.exec("cd /Users/Shashank/src");

				
				String command = "java -Dner.impl.class=org.apache.tika.parser.ner.regex.RegexNERecogniser -classpath /Users/Shashank/tika/tika-ner-resources:/Users/Shashank/src/tika/tika-app/target/tika-app-1.13-SNAPSHOT.jar org.apache.tika.cli.TikaCLI --config=/Users/Shashank/src/tika-config.xml -j " + strFilePath;  
				process = runTime.exec(command);	
				BufferedReader stdInput1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

				String strFilePath1="/Users/Shashank/Desktop/Rashmi/ALL_3rd_OUTPUT_CSV/"+string+".csv";
				
				FileWriter fileWriter1 = new FileWriter(strFilePath1);
				BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);

				String s = null;
				while ((s = stdInput1.readLine()) != null) {
					bufferedWriter1.write(s);
					System.out.println(s);
				}
				// read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) 
				{
						System.out.println(s);
				}
				bufferedWriter1.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return str;
	}

	public LinkedHashMap<Integer,Double> sortHashMapByValuesD(Map<Integer, Double> ttr2) {
		List<Integer> mapKeys = new ArrayList(ttr2.keySet());
		List<Double> mapValues = new ArrayList(ttr2.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<Integer,Double> sortedMap = new LinkedHashMap<Integer,Double>();

		Iterator<Double> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator<Integer> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = ttr2.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)){
					ttr2.remove(key);
					mapKeys.remove(key);
					sortedMap.put((Integer) key, (Double)val);
					break;
				}

			}

		}
		return sortedMap;
	}

	private double[] smooth(double in[], int f) {
		int size = in.length;
		double tmp[] = new double[size];

		for (int i = 0; i < size; i++) {
			int cnt = 0;
			int sum = 0;
			for (int j = (f * -1); j <= f; j++) {
				try {
					sum += in[i + j];
					cnt++;
				} catch (ArrayIndexOutOfBoundsException e) {
					// don't increment count
				}
			}
			tmp[i] = (double) sum / (double) cnt;
		}
		return tmp;
	}


	public static void main(String[] args) {
		Path path = Paths.get("/Users/Shashank/Desktop/Rashmi/d3_3/");
		//Path path = Paths.get("/Users/Shashank/Downloads/CSCI-599_DATA/TREC/cm_cn_co/data/");
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		String type=null;
		int j=0;
		TTRExtractor temp= new TTRExtractor();

		for (File f : files)
		{

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
						temp.extractText(handler.toString(),100000,f.getName());
						j++;
						System.out.println(handler.toString());
					}
				}
				catch (Exception e) {  
					e.printStackTrace();  

				}  


			}	

		}


	}

}
