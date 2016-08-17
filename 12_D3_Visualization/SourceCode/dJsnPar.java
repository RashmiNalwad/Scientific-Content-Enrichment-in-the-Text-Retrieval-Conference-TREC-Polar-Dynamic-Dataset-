import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class dJsnPar {

	public static void parseJson_SWEET(String path)
	{
		JSONParser parser = new JSONParser();
		HashMap<String, Integer> hmap_ontologies = new HashMap<String, Integer>();
		try
		{
			JSONObject a = (JSONObject)parser.parse(new FileReader(path));
			JSONObject obj = (JSONObject)a.get("response");
			JSONArray arr= (JSONArray) obj.get("docs");
			for(int i=0;i<arr.size();i++)
			{
				JSONObject innerObj = (JSONObject)arr.get(i);
				JSONArray resArr1 = (JSONArray)innerObj.get("SWEET");
				if(innerObj.containsKey("SWEET"))
				{
				for(int k=0;k<resArr1.size();k++)
				{
					String s=resArr1.get(k).toString();
				   if(!s.equals("null"))
				   {
					if(!hmap_ontologies.containsKey(s))
                    {
                          if(s.length()>1)
                        	  hmap_ontologies.put(s,1);
                          
                    }
                    else
                    {
                        int val=(int)hmap_ontologies.get(s).intValue();
                        val++;
                        hmap_ontologies.put(s,val);
					System.out.println(resArr1.get(k));
                    }
					}
				}
				}
			}
			hmap_ontologies=sortHashMapByValuesD(hmap_ontologies);
			
			String strFilePath2="/Users/Shashank/Desktop/sweet.csv";
			FileWriter fileWriter2 = new FileWriter(strFilePath2);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			bufferedWriter2.write("\"id\",\"score\",\"weight\",\"color\",\"label\"");
			bufferedWriter2.newLine();
			int i=0;
	      for(Entry<String, Integer> entry:hmap_ontologies.entrySet())
			 {
	            i++;
	            if(i > hmap_ontologies.size()/2){
	            String str1;
	            Random generator = new Random();
	    		Integer num = generator.nextInt(899999) + 100000;
	    		int finalcode=Integer.valueOf(String.valueOf(num), 16);
	    		finalcode/=10;
	          bufferedWriter2.write("\""+entry.getKey()+"\","+entry.getValue()+",0.5,#"+finalcode+",\""+entry.getKey()+"\"");
			 
	            bufferedWriter2.newLine(); 
	            }
			 }
			
			bufferedWriter2.close();
			
			System.out.println("Done");
			
			
			
			//System.out.println(arr.size());
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	static LinkedHashMap<String,Integer> sortHashMapByValuesD(Map<String, Integer> ttr2) {
		List<String> mapKeys = new ArrayList(ttr2.keySet());
		List<Integer> mapValues = new ArrayList(ttr2.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<String,Integer> sortedMap = new LinkedHashMap<String,Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = ttr2.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)){
					ttr2.remove(key);
					mapKeys.remove(key);
					sortedMap.put((String) key, (Integer)val);
					break;
				}

			}

		}
		return sortedMap;
	}


	public static String parseJson_Grobid(String path)
	{
		JSONParser parser = new JSONParser();

		String s;
		String title,url,year,citation,version,cid;
		title=url=year=citation=version=cid="";
		HashMap<String, ArrayList<String>> hmap_author_related = new HashMap<String, ArrayList<String>>();
		try
		{
			JSONObject a = (JSONObject)parser.parse(new FileReader(path));
			JSONObject obj = (JSONObject)a.get("response");
			JSONArray arr= (JSONArray) obj.get("docs");
			for(int i=0;i<arr.size();i++)
			{   
				JSONObject innerObj = (JSONObject)arr.get(i);
				if(innerObj.containsKey("Author"))
				{
					JSONArray resArr = (JSONArray)innerObj.get("Author");

					for(int k=0;k<resArr.size();k++)
					{  System.out.println(resArr.get(k));
					s=resArr.get(k).toString();
					if(innerObj.containsKey("Related_Publications"))
					{

						JSONArray resArr_related = (JSONArray)innerObj.get("Related_Publications");
						for(int k1=0;k1<1;k1++)
						{
							String s1=resArr_related.get(k1).toString();
							ArrayList<String> temp=new ArrayList<String>();
							int s_Title=s1.indexOf("Title");
							int s_URL=s1.indexOf("URL");
							int s_Year=s1.indexOf("Year");
							int s_Citations=s1.indexOf("Citations");
							int s_Versions=s1.indexOf("Versions");
							int s_ClusterID=s1.indexOf("Cluster ID");
							int s_list=s1.indexOf("list");


							if(s_Title !=-1 && s_URL!=-1 && s_Year!=-1 && s_Citations!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1)
							{
								title=s1.substring(s_Title,s_URL).toString();
								url=s1.substring(s_URL,s_Year).toString();
								year=s1.substring(s_Year,s_Citations).toString();
								citation=s1.substring(s_Citations,s_Versions).toString();
								version=s1.substring(s_Versions,s_ClusterID).toString();
								cid=s1.substring(s_ClusterID,s_list).toString();



							}
							else if((s_Title ==-1) && (s_URL!=-1 && s_Year !=-1 && s_Citations!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1))
							{
								url=s1.substring(s_URL,s_Year).toString();
								year=s1.substring(s_Year,s_Citations).toString();
								citation=s1.substring(s_Citations,s_Versions).toString();
								version=s1.substring(s_Versions,s_ClusterID).toString();
								cid=s1.substring(s_ClusterID,s_list).toString();


							}
							else if(s_URL==-1 && (s_Title!=-1 && s_Year !=-1 && s_Citations!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1))
							{
								title=s1.substring(s_Title,s_Year).toString();

								year=s1.substring(s_Year,s_Citations).toString();
								citation=s1.substring(s_Citations,s_Versions).toString();
								version=s1.substring(s_Versions,s_ClusterID).toString();
								cid=s1.substring(s_ClusterID,s_list).toString();

							}
							else if(s_Year==-1 && (s_Title!=-1 && s_URL !=-1 && s_Citations!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1))
							{
								title=s1.substring(s_Title,s_URL).toString();
								url=s1.substring(s_URL,s_Citations).toString();
								citation=s1.substring(s_Citations,s_Versions).toString();
								version=s1.substring(s_Versions,s_ClusterID).toString();
								cid=s1.substring(s_ClusterID,s_list).toString();

							}
							else if(s_Citations ==-1 && (s_Title!=-1 && s_Year !=-1 && s_URL!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1))
							{
								title=s1.substring(s_Title,s_URL).toString();
								url=s1.substring(s_URL,s_Year).toString();
								year=s1.substring(s_Year,s_Versions).toString();
								version=s1.substring(s_Versions,s_ClusterID).toString();
								cid=s1.substring(s_ClusterID,s_list).toString();



							}

							else if(s_Versions ==-1 && (s_Title!=-1 && s_Year !=-1 && s_Citations!=-1 && s_URL!=-1 && s_ClusterID!=-1 && s_list!=-1))
							{
								title=s1.substring(s_Title,s_URL).toString();
								url=s1.substring(s_URL,s_Year).toString();
								year=s1.substring(s_Year,s_Citations).toString();
								citation=s1.substring(s_Citations,s_ClusterID).toString();
								cid=s1.substring(s_ClusterID,s_list).toString();
							}
							else
							{

							}


							cid=cid.replaceAll("Citations", "");

							if(title !=null || title!="")
								temp.add(title);
							if(year !=null || year!="")
								temp.add(year);
							if(url !=null || url!="")
								temp.add(url);
							if(citation !=null || citation!="")
								temp.add(citation);
							if(cid !=null || cid!="")
								temp.add(cid);
							hmap_author_related.put(s,temp);



							System.out.println(resArr_related.get(k1));
						}

					}


					}}
			}


			String strFilePath2="/Users/Shashank/Desktop/circlepacking.json";
			FileWriter fileWriter2 = new FileWriter(strFilePath2);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			int i=0,j=0;
			bufferedWriter2.write("{\"name\":\"Author\",\"children\":[");
			for(Entry<String, ArrayList<String>> entry:hmap_author_related.entrySet())
			{
				j=0;
				i++;
				String author=entry.getKey();
				if(!author.isEmpty())
				{
					System.out.println(author);	 
					bufferedWriter2.write("{\"name\":\""+entry.getKey()+"\",\"children\":[");

					ArrayList<String> arrList = entry.getValue();

					for(String val:arrList)
					{j++;
					if(!val.isEmpty()){

						if(j==arrList.size()){ 

							bufferedWriter2.write("{\"name\":\""+val+"\",\"size\":\"2122\"}");

						}
						else
						{
							bufferedWriter2.write("{\"name\":\""+val+"\",\"size\":\"2122\"},");

						}
					}
					}
					if(i==hmap_author_related.size())
					{	  
						bufferedWriter2.write("]}");
					}
					else
					{
						bufferedWriter2.write("]},");
					}
				}
			}
			bufferedWriter2.write("]}");
			bufferedWriter2.close();
			System.out.println("Done");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}



	public static void parseJson_Complete(String path)
	{
		JSONParser parser = new JSONParser();
		HashMap<String, Integer> hmap_ontologies = new HashMap<String, Integer>();

		try{
			String strFilePath2="/Users/Shashank/Desktop/complete.json";
			FileWriter fileWriter2 = new FileWriter(strFilePath2);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			int i=0,j=0;
			float doi_count = 0;
			float keyword_count=0;
			float siz;
			bufferedWriter2.write("{\"name\":\"Polar Dataset\",\"children\":[");

			bufferedWriter2.write("{\"name\":\"3\",\"children\":[");

			bufferedWriter2.write("{\"name\":\"NER_TEMPERATURE\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");

			bufferedWriter2.write("{\"name\":\"NER_NANOMETER\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");


			bufferedWriter2.write("{\"name\":\"NER_MONEY\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");


			bufferedWriter2.write("{\"name\":\"NER_KILOMETER\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");


			bufferedWriter2.write("{\"name\":\"NER_PRESSURE\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");

			bufferedWriter2.write("{\"name\":\"NER_MEASUREMENT_INCH\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");

			bufferedWriter2.write("{\"name\":\"NER_MEASUREMENT_KILOGRAM\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");


			bufferedWriter2.write("{\"name\":\"NER_MEASUREMENT_METERS\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");


			bufferedWriter2.write("{\"name\":\"NER_MEASUREMENT_METERS\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");


			bufferedWriter2.write("{\"name\":\"NER_MEASUREMENT_FEET\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");


			bufferedWriter2.write("{\"name\":\"NER_MEASUREMENT_MILES\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");


			bufferedWriter2.write("{\"name\":\"NER_KILOMETER\",\"size\":\"2122\"}");
			bufferedWriter2.write(",");


			bufferedWriter2.write("{\"name\":\"NER_PHONE_NUMBER\",\"size\":\"2122\"}");




			bufferedWriter2.write("]},");



			JSONObject a = (JSONObject)parser.parse(new FileReader(path));
			JSONObject obj = (JSONObject)a.get("response");
			JSONArray arr= (JSONArray) obj.get("docs");

			siz=arr.size();

			for(int ii=0;ii<arr.size();ii++)
			{

				JSONObject innerObj = (JSONObject)arr.get(ii);

				JSONArray resArrD = (JSONArray)innerObj.get("DOI");
				if(innerObj.containsKey("DOI"))
				{
					doi_count++;
				}

				JSONArray resArrK = (JSONArray)innerObj.get("Keyword");
				if(innerObj.containsKey("Keyword"))
				{
					keyword_count++;

				}
			}



			bufferedWriter2.write("{\"name\":\"4\",\"children\":[");

			bufferedWriter2.write("{\"name\":\"DOI_Present\",\"children\":[");
			bufferedWriter2.write("{\"name\":\""+((doi_count/siz)*100)+"%\",\"size\":\"2122\"}");
			bufferedWriter2.write("]},");

			bufferedWriter2.write("{\"name\":\"KEY_Present\",\"children\":[");
			bufferedWriter2.write("{\"name\":\""+((keyword_count/siz)*100)+"%\",\"size\":\"2122\"}");
			bufferedWriter2.write("]}");



			bufferedWriter2.write("]},");
			bufferedWriter2.write("{\"name\":\"4\",\"children\":[");

			//5th

			JSONParser parser1 = new JSONParser();

			String s;
			String title,url,year,citation,version,cid;
			title=url=year=citation=version=cid="";
			HashMap<String, ArrayList<String>> hmap_author_related = new HashMap<String, ArrayList<String>>();
			try
			{
				JSONObject a1 = (JSONObject)parser1.parse(new FileReader(path));
				JSONObject obj1 = (JSONObject)a1.get("response");
				JSONArray arr1= (JSONArray) obj1.get("docs");
				for(int w=0;w<arr1.size();w++)
				{   
					JSONObject innerObj = (JSONObject)arr.get(w);
					if(innerObj.containsKey("Author"))
					{
						JSONArray resArr = (JSONArray)innerObj.get("Author");

						for(int k=0;k<resArr.size();k++)
						{  System.out.println(resArr.get(k));
						s=resArr.get(k).toString();
						if(innerObj.containsKey("Related_Publications"))
						{

							JSONArray resArr_related = (JSONArray)innerObj.get("Related_Publications");
							for(int k1=0;k1<1;k1++)
							{
								String s1=resArr_related.get(k1).toString();
								ArrayList<String> temp=new ArrayList<String>();
								int s_Title=s1.indexOf("Title");
								int s_URL=s1.indexOf("URL");
								int s_Year=s1.indexOf("Year");
								int s_Citations=s1.indexOf("Citations");
								int s_Versions=s1.indexOf("Versions");
								int s_ClusterID=s1.indexOf("Cluster ID");
								int s_list=s1.indexOf("list");


								if(s_Title !=-1 && s_URL!=-1 && s_Year!=-1 && s_Citations!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1)
								{
									title=s1.substring(s_Title,s_URL).toString();
									url=s1.substring(s_URL,s_Year).toString();
									year=s1.substring(s_Year,s_Citations).toString();
									citation=s1.substring(s_Citations,s_Versions).toString();
									version=s1.substring(s_Versions,s_ClusterID).toString();
									cid=s1.substring(s_ClusterID,s_list).toString();



								}
								else if((s_Title ==-1) && (s_URL!=-1 && s_Year !=-1 && s_Citations!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1))
								{
									url=s1.substring(s_URL,s_Year).toString();
									year=s1.substring(s_Year,s_Citations).toString();
									citation=s1.substring(s_Citations,s_Versions).toString();
									version=s1.substring(s_Versions,s_ClusterID).toString();
									cid=s1.substring(s_ClusterID,s_list).toString();


								}
								else if(s_URL==-1 && (s_Title!=-1 && s_Year !=-1 && s_Citations!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1))
								{
									title=s1.substring(s_Title,s_Year).toString();

									year=s1.substring(s_Year,s_Citations).toString();
									citation=s1.substring(s_Citations,s_Versions).toString();
									version=s1.substring(s_Versions,s_ClusterID).toString();
									cid=s1.substring(s_ClusterID,s_list).toString();

								}
								else if(s_Year==-1 && (s_Title!=-1 && s_URL !=-1 && s_Citations!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1))
								{
									title=s1.substring(s_Title,s_URL).toString();
									url=s1.substring(s_URL,s_Citations).toString();
									citation=s1.substring(s_Citations,s_Versions).toString();
									version=s1.substring(s_Versions,s_ClusterID).toString();
									cid=s1.substring(s_ClusterID,s_list).toString();

								}
								else if(s_Citations ==-1 && (s_Title!=-1 && s_Year !=-1 && s_URL!=-1 && s_Versions!=-1 && s_ClusterID!=-1 && s_list!=-1))
								{
									title=s1.substring(s_Title,s_URL).toString();
									url=s1.substring(s_URL,s_Year).toString();
									year=s1.substring(s_Year,s_Versions).toString();
									version=s1.substring(s_Versions,s_ClusterID).toString();
									cid=s1.substring(s_ClusterID,s_list).toString();



								}

								else if(s_Versions ==-1 && (s_Title!=-1 && s_Year !=-1 && s_Citations!=-1 && s_URL!=-1 && s_ClusterID!=-1 && s_list!=-1))
								{
									title=s1.substring(s_Title,s_URL).toString();
									url=s1.substring(s_URL,s_Year).toString();
									year=s1.substring(s_Year,s_Citations).toString();
									citation=s1.substring(s_Citations,s_ClusterID).toString();
									cid=s1.substring(s_ClusterID,s_list).toString();
								}
								else
								{

								}

								cid=cid.replaceAll("Citations", "");

								if(title !=null || title!="")
									temp.add(title);
								if(year !=null || year!="")
									temp.add(year);
								if(url !=null || url!="")
									temp.add(url);
								if(citation !=null || citation!="")
									temp.add(citation);
								if(cid !=null || cid!="")
									temp.add(cid);
								hmap_author_related.put(s,temp);


								System.out.println(resArr_related.get(k1));
							}

						}

						}}
				}


				int u=0,g;
				bufferedWriter2.write("{\"name\":\"Author\",\"children\":[");
				for(Entry<String, ArrayList<String>> entry:hmap_author_related.entrySet())
				{
					g=0;
					u++;
					String author=entry.getKey();
					if(!author.isEmpty()&& (!(author.equals("null"))))
					{
						System.out.println(author);	 
						bufferedWriter2.write("{\"name\":\""+entry.getKey()+"\",\"children\":[");

						ArrayList<String> arrList = entry.getValue();

						for(String val:arrList)
						{g++;
						if(!val.isEmpty()){

							if(g==arrList.size()){ 

								bufferedWriter2.write("{\"name\":\""+val+"\",\"size\":\"2122\"}");

							}
							else
							{
								bufferedWriter2.write("{\"name\":\""+val+"\",\"size\":\"2122\"},");

							}
						}
						}
						if(u==hmap_author_related.size())
						{	  
							bufferedWriter2.write("]}");
						}
						else
						{
							bufferedWriter2.write("]},");
						}
					}
				}

				bufferedWriter2.write("]}]},");
			}
			catch(Exception e)
			{

			}
			try
			{
				JSONObject a2 = (JSONObject)parser.parse(new FileReader(path));
				JSONObject obj2 = (JSONObject)a2.get("response");
				JSONArray arr2= (JSONArray) obj2.get("docs");
				float latitude_count=0;
				float longitude_count=0;
				for(int i2=0;i2<arr2.size();i2++)
				{   
					JSONObject innerObj2 = (JSONObject)arr2.get(i2);


					if(innerObj2.containsKey("Geographic_LATITUDE")&innerObj2.containsKey("Geographic_LONGITUDE"))
					{
						JSONArray resArr = (JSONArray)innerObj2.get("Geographic_LATITUDE");

						for(int k=0;k<resArr.size();k++)
						{
							if(!((resArr.get(k).equals(0.0)))){
								System.out.println(resArr.get(k));
								latitude_count++;
							}
						}
						for(int k=0;k<resArr.size();k++)
						{
							if(!((resArr.get(k).equals(0.0)))){
								System.out.println(resArr.get(k));
								longitude_count++;
							}
						}

					}



				}

				bufferedWriter2.write("{\"name\":\"6\",\"children\":[");

				bufferedWriter2.write("{\"name\":\"Geo_Coords_Matched\",\"children\":[");
				bufferedWriter2.write("{\"name\":\""+((latitude_count/siz)*100)+"%\",\"size\":\"2122\"}");
				bufferedWriter2.write("]}");
				bufferedWriter2.write("]},");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			HashMap<String, Integer> hmap_ontologies3 = new HashMap<String, Integer>();
			try
			{
				JSONObject a3 = (JSONObject)parser.parse(new FileReader(path));
				JSONObject obj3 = (JSONObject)a3.get("response");
				JSONArray arr3= (JSONArray) obj3.get("docs");
				for(int i3=0;i3<arr3.size();i3++)
				{
					JSONObject innerObj3 = (JSONObject)arr3.get(i);
					JSONArray resArr3 = (JSONArray)innerObj3.get("SWEET");
					for(int k3=0;k3<resArr3.size();k3++)
					{
						String s3=resArr3.get(k3).toString();

						if(!hmap_ontologies3.containsKey(s3))
						{
							if(s3.length()>1)
								hmap_ontologies3.put(s3,1);

						}
						else
						{
							int val=(int)hmap_ontologies3.get(s3).intValue();
							val++;
							hmap_ontologies3.put(s3,val);
							System.out.println(resArr3.get(k3));
						}
					}
				}

				float realm_ontology_matched_count=0;
				realm_ontology_matched_count=hmap_ontologies3.size();
				float realm_ontology_count=964;

				bufferedWriter2.write("{\"name\":\"7\",\"children\":[");

				bufferedWriter2.write("{\"name\":\"SWEET_ONT_MAPPED\",\"children\":[");
				bufferedWriter2.write("{\"name\":\""+((realm_ontology_matched_count/realm_ontology_count)*100)+"%\",\"size\":\"2122\"}");
				bufferedWriter2.write("]}");

				bufferedWriter2.write("]}");


			}

			catch(Exception e)
			{
				System.out.println(e);
			}

			bufferedWriter2.write("]}");
			bufferedWriter2.close();

		}
		catch(Exception e)
		{
			System.out.println(e);

		}
	}

	public static void main(String[] args)
	{
		try{
			URL solr = new URL("http://localhost:8983/solr/HW2/select?q=*%3A*&wt=json&indent=true&rows=6100");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(solr.openStream()));
			String strFilePath2="/Users/Shashank/Desktop/Rashmi/SolrJson/solr.json";

			FileWriter fileWriter2 = new FileWriter(strFilePath2);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				bufferedWriter2.write(inputLine);

			}

			bufferedWriter2.close();
			in.close();


			Path path = Paths.get("/Users/Shashank/Desktop/Rashmi/SolrJson/");
			File dir = new File(path.toString());
			File[] files = dir.listFiles();

			for (File f : files)
			{
				parseJson_Grobid(f.getAbsolutePath());
				parseJson_Complete(f.getAbsolutePath());
				parseJson_SWEET(f.getAbsolutePath());
				
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
