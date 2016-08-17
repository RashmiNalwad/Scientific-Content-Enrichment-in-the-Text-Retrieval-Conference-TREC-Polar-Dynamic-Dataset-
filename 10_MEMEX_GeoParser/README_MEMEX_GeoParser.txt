10.MEMEX Geo Parser
Setup required for MEMEX GeoParser running
-	Build the Lucene Gazetter from the source code and using Geonames.org dataset as mentioned in GeoTopic Parser. Let the Lucene Gazetter REST server run on the port 8765. 
 $ lucene-geo-gazetteer –server

-	Install the location based NER model in the specified directory. 
-	Run the independent Apache SOLR on port 8984
$ bin/solr start -p 8984

-	Run TIKA server on port 8001 with the suitable JAVA classpath  
$ java -classpath $HOME/src/location-ner-model:$HOME/src/geotopic-mime:tika-server/target/tika-server-1.13-SNAPSHOT.jar org.apache.tika.server.TikaServerCli -p 8001

-	Start the Apache SOLR that comes in the package of MEMEX GeoParser on default port 8983. 
$ bin/solr start 

-	Start the Django server on port 8000 and MEMEX GeoParser on browser
$ python manage.py runserver
http://localhost:8000/

