6.GeoParser
-The package com.usc.edu.vanila contains the source code that will extract the text from the files and forms application/geot MIME type files in the folder src/geot
-The package com.usc.edu.classpathtest will take the input from the src/geot folder and runs the GeoGazetter to get the output, writes them to the folder inter.
-The source code that does this is in the package org.usc.edu.classpathtest
-The source code org.usc.edu.ParseJSON will extract the Geocoordinate data from the intermediate files and writes them to the respective JSON files in the src/json folder.
-The sample JSON produced as the final output is provided as Sample_Output.json

BUILD STEPS

-	Build the Lucene Gazetter from the source code and using Geonames.org dataset. Let the Lucene Gazetter REST server run on the port 8765. 
         $ cd /home/aditya/src
         $ git clone https://github.com/chrismattmann/lucene-geo-gazetteer.git
         $ cd lucene-geo-gazetteer
         $ mvn install assembly:assembly
         $ add /home/aditya/src/lucene-geo-gazetteer/src/main/bin to PATH environment variable
        $ cd /home/aditya/src/lucene-geo-gazetteer
        $ curl -O http://download.geonames.org/export/dump/allCountries.zip
        $ unzip allCountries.zip
        $ lucene-geo-gazetteer -i geoIndex -b allCountries.txt

-	Install the location based NER model in the specified directory. 
      $ mkdir /home/aditya/src/location-ner-model && cd /home/aditya/src/location-ner-model
     $ curl -O http://opennlp.sourceforge.net/models-1.5/en-ner-location.bin
     $ mkdir -p org/apache/tika/parser/geo/topic
     $ mv en-ner-location.bin org/apache/tika/parser/geo/topic

-	JAVA Class Path for TIKA
$ java -classpath tika-app-1.13-SNAPSHOT.jar:$HOME/src/location-ner-model:$HOME/src/geotopic-mime org.apache.tika.cli.TikaCLI –m <Sample Files in Polar Data Set>

