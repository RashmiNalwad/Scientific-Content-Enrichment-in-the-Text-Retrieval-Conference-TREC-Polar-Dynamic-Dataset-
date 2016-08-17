1) The Tag Ratio algorithms takes polar dataset as input. Using TIKA's Autodetect Parser, each of the input file is parsed into xhtml and for each file text to tag ratio is calculated by storing the result initially into text format in Intermediate folder.

2) The measurement extraction is carried out by reading from each of the text file from Intermediate folder and by executing the command "java -Dner.impl.class=org.apache.tika.parser.ner.regex.RegexNERecogniser -classpath /Users/Shashank/tika/tika-ner-resources:/Users/Shashank/src/tika/tika-app/target/tika-app-1.13-SNAPSHOT.jar org.apache.tika.cli.TikaCLI --config=/Users/Shashank/src/tika-config.xml -j " using RegexNERecogniser by adding new regular expressions to to identify the measurement into the ner-regex file.This output will be available in Final_Output.	

3) Ner-RegEx.txt contains all the regular expressions used for measurement extraction.

The required measurement thus obtained in JSON Format and used to plot the D3 visualization.