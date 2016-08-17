5.GROBID
-Git clone Grobid from https://github.com/kermitt2/grobid.git.
-Start the GROBID Service using the commands
	cd $HOME/src/grobid/grobid-service
	mvn -Dmaven.test.skip=true jetty:run-war
-The package grobid.src.main contains the source code that will extract the TEI metadata from the files. =======================================================================================================

