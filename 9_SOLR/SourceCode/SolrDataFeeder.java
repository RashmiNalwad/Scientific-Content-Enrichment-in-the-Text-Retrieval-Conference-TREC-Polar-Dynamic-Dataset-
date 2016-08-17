import java.io.IOException;


public class SolrDataFeeder {

	public static void main(String[] args) throws IOException
	{
		
		/* HW2 collection is added to solr 4.10.4 in the path "D:\solr-4.10.4\solr-4.10.4\example\solr".
		 * start.jar is started from example folder with the help of command java -jar start.jar  
		 * This program should be executed after solr is up which can verified by going to url localhost:8983/solr. 
		 * All the json documents present in example/exampledocs will be indexed.This can be verified by Querying in Solr UI.
		 */
		Runtime runTime = Runtime.getRuntime();
		Process p = runTime.exec("java -Durl=http://localhost:8983/solr/HW2/update/extract?wt=json,commit=true -Dauto=yes -jar D:/solr-4.10.4/solr-4.10.4/example/exampledocs/post.jar D:/solr-4.10.4/solr-4.10.4/example/exampledocs/");
	}

}
