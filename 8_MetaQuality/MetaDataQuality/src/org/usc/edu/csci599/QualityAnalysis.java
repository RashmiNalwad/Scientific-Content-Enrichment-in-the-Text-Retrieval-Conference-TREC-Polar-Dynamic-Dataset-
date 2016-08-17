package org.usc.edu.csci599;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
/*
 * Author : Aditya R Desai
 * Parameters: Path locaiton of the Input Files and tika-mime
 * The following code will calculate the Quality analysis of the fiven document and writes to the file. These output files
 * needs to be created in src folder of the project with correct names.
 * */


public class QualityAnalysis {
	private HashMap<String,String> dubliCoreHashMap = new HashMap<String,String>();
	private HashMap<String,String> MSOffice = new HashMap<String,String>();
	private HashMap<String,String> PDF = new HashMap<String,String>();
	private HashMap<String,String> HTTP = new HashMap<String,String>();
	private HashMap<String,String> GIF = new HashMap<String,String>();
	private HashMap<String,String> JPEG = new HashMap<String,String>();
	private HashMap<String,String> PNG = new HashMap<String,String>();
	private HashMap<String,String> Text = new HashMap<String,String>();
	private HashMap<String,String> Video = new HashMap<String,String>();
	
	public QualityAnalysis() {
			
			dubliCoreHashMap.put("contributor","85");
			dubliCoreHashMap.put("coverage","86");
			dubliCoreHashMap.put("creator","88");
			dubliCoreHashMap.put("date","87");
			dubliCoreHashMap.put("description","88");
			dubliCoreHashMap.put("format","89");
			dubliCoreHashMap.put("identifier","93");
			dubliCoreHashMap.put("language","92");
			dubliCoreHashMap.put("publisher","90");
			dubliCoreHashMap.put("relation","90");
			dubliCoreHashMap.put("rights","98");
			dubliCoreHashMap.put("source","97");
			dubliCoreHashMap.put("subject","96");
			dubliCoreHashMap.put("title","94");
			dubliCoreHashMap.put("type","100");
			
			
			MSOffice.put("application-name", "99");
			MSOffice.put("application-version", "98");
			MSOffice.put("author", "100");
			MSOffice.put("category", "64");
			MSOffice.put("character-count", "63");
			MSOffice.put("character-count-with-spaces", "61");
			MSOffice.put("comments", "60");
			MSOffice.put("company", "87");
			MSOffice.put("content-status", "62");
			MSOffice.put("creation-date", "96");
			MSOffice.put("image-count", "60");
			MSOffice.put("keywords", "97");
			MSOffice.put("last-author", "72");
			MSOffice.put("last-printed", "71");
			MSOffice.put("last-saved", "70");
			MSOffice.put("line-count", "65");
			MSOffice.put("manager", "66");
			MSOffice.put("notes", "48");
			MSOffice.put("object-count", "70");
			MSOffice.put("page-count", "89");
			MSOffice.put("paragraph-count", "88");
			MSOffice.put("preservation-format", "82");
			MSOffice.put("revision-number", "85");
			MSOffice.put("security", "93");
			MSOffice.put("slide-count", "84");
			MSOffice.put("table-count", "50");
			MSOffice.put("template", "51");
			MSOffice.put("total-time", "52");
			MSOffice.put("user-defined-metadata-name-prefix", "49");
			MSOffice.put("version", "96");
			MSOffice.put("word-count", "81");
			MSOffice.put("protected", "98");
			MSOffice.put("resourcename", "98");
			MSOffice.put("meta:author","85");
			MSOffice.put("meta:creation-date","84");
			MSOffice.put("meta:last-author","86");
			MSOffice.put("meta:save-date","87");
			MSOffice.put("meta:slide-count","88");
			MSOffice.put("meta:word-count","89");
			MSOffice.put("xmptpg:npages", "81");
			MSOffice.put("aliases", "80");
			
			
			PDF.put("author","100");
			PDF.put("content-type","96");
			PDF.put("subject", "94");
			PDF.put("keywords", "93");
			PDF.put("pdf:encrypted","92");
			PDF.put("creation-date","91");
			PDF.put("content-length","90");
			PDF.put("meta:author","81"); 
			PDF.put("meta:creation-date","82");
			PDF.put("meta:save-date","83"); 
			PDF.put("modified","84"); 
			PDF.put("pdf:pdfversion","85");
			PDF.put("resourcename","70");
			PDF.put("created","69"); 
			PDF.put("creator","68");
			PDF.put("producer","60");
			PDF.put("xmp:creatortool","61");
			PDF.put("access_permission:assemble_document","59");
			PDF.put("access_permission:can_modify","58");
			PDF.put("access_permission:can_print","57"); 
			PDF.put("access_permission:can_print_degraded","56"); 
			PDF.put("access_permission:extract_content","55"); 
			PDF.put("access_permission:extract_for_accessibility","54");
			PDF.put("access_permission:fill_in_form","53"); 
			PDF.put("access_permission:modify_annotations","52");
			PDF.put("date", "50");
			PDF.put("last-modified","45"); 
			PDF.put("last-save-date","44");
			PDF.put("xmptpg:npages","80");
			PDF.put("aliases", "80");

			HTTP.put("author", "99");
			HTTP.put("title", "99");
			HTTP.put("keywords", "98");
			HTTP.put("name", "90");
			HTTP.put("resourcename", "100");
			HTTP.put("robots", "97");
			HTTP.put("description", "95");
			HTTP.put("content-length","94");
			HTTP.put("content_location","93");
			HTTP.put("content-type","92");
			HTTP.put("content-disposition","70");
			HTTP.put("content-encoding","71");
			HTTP.put("content-language","72");
			HTTP.put("last-modified","89");
			HTTP.put("location","88");
			HTTP.put("content-md5","73");
			HTTP.put("aliases", "80");
			
			JPEG.put("color balance","96");
			JPEG.put("compression type","98");
			JPEG.put("content-length","97");
			JPEG.put("content-type","95");
			JPEG.put("contrast","12");
			JPEG.put("creation-date","94");
			JPEG.put("data precision","91");
			JPEG.put("interoperability index","90");
			JPEG.put("interoperability version","91");
			JPEG.put("last-modified","92"); 
			JPEG.put("last-save-date","93");
			JPEG.put("tiff:bitspersample","80");
			JPEG.put("tiff:imagelength","81");
			JPEG.put("tiff:imagewidth","82");
			JPEG.put("tiff:make","83");
			JPEG.put("tiff:model","84");
			JPEG.put("tiff:orientation","85");
			JPEG.put("tiff:resolutionunit","86");
			JPEG.put("tiff:software","87");
			JPEG.put("tiff:xresolution","88"); 
			JPEG.put("tiff:ytesolution","89");
			JPEG.put("modified","94");
			JPEG.put("resourcename","100");
			JPEG.put("color space","79");
			JPEG.put("component 1","78");
			JPEG.put("component 2","77");
			JPEG.put("component 3","76");
			JPEG.put("meta:creation-date","74");
			JPEG.put("meta:save-date","75");
			JPEG.put("software","73");
			JPEG.put("gps version id","76");
			JPEG.put("exif:datetimeoriginal","63");
			JPEG.put("exif:exposuretime","64");
			JPEG.put("exif:fnumber","65");
			JPEG.put("exif:flash","66");
			JPEG.put("exif:focallength","67");
			JPEG.put("exif:isospeedratings","68");
			JPEG.put("camera serial number","50");
			JPEG.put("iso","58");
			JPEG.put("iso info","59");
			JPEG.put("image boundary","40");
			JPEG.put("image data size","41");
			JPEG.put("image height","42");
			JPEG.put("image width","43");
			JPEG.put("components configuration","45");
			JPEG.put("compressed bits per pixel","44");
			JPEG.put("file info","30");
			JPEG.put("file modified date","31");
			JPEG.put("file name","32");
			JPEG.put("file size","33");
			JPEG.put("file source","34");
			JPEG.put("exif image height","37");
			JPEG.put("exif image width","36");
			JPEG.put("exif version","35");
			JPEG.put("lens","25");
			JPEG.put("firmware version","21");
			JPEG.put("flash","26");			
			JPEG.put("thumbnail compression","22");
			JPEG.put("thumbnail length","23");
			JPEG.put("thumbnail offset","27");
			JPEG.put("date/time","20");
			JPEG.put("digital zoom ratio","24");
			JPEG.put("exposure bias value","18");
			JPEG.put("exposure difference","17");
			JPEG.put("exposure mode","16");
			JPEG.put("exposure program","15");
			JPEG.put("exposure sequence number","14");
			JPEG.put("exposure time","13");
			JPEG.put("exposure tuning","12");
			JPEG.put("auto flash mode","11");
			JPEG.put("cfa pattern","10");
			JPEG.put("afinfo2version","9");
			JPEG.put("crop sigh speed","8");
			JPEG.put("custom rendered","7");
			JPEG.put("f-number","5");
			JPEG.put("aliases", "80");
			
			GIF.put("chroma blackiszero","90");
			GIF.put("chroma numchannels","91");
			GIF.put("compression compressiontypename","92");
			GIF.put("compression lossless","67");
			GIF.put("compression numprogressivescans","58");
			GIF.put("content-length","93");
			GIF.put("content-type","94");
			GIF.put("data sampleformat","66");
			GIF.put("dimension horizontalpixeloffset","67");
			GIF.put("dimension imageOrientation","68");
			GIF.put("dimension verticalpixeloffset","69");
			GIF.put("graphiccontrolextension","71");
			GIF.put("imagedescriptor","70");
			GIF.put("height","96");
			GIF.put("resourcename","100");
			GIF.put("tiff:imagelength","88");
			GIF.put("tiff:imagewidth","89");
			GIF.put("width","97");
			GIF.put("version","98");
			GIF.put("aliases", "80");
			
			PNG.put("chroma blackiszero", "90");
			PNG.put("chroma colorsspacetype","91");
			PNG.put("chroma gamma", "92");
			PNG.put("chroma numchannels", "93");
			PNG.put("compression compressiontypename","94");
			PNG.put("compression lossless","90");
			PNG.put("compression numprogressivescans","58");
			PNG.put("content-length","93");
			PNG.put("content-type","94");
			PNG.put("data bitspersample", "89");
			PNG.put("data planarconfiguration","70");
			PNG.put("data sampleformat","66");
			PNG.put("dimension horizontalpixeloffset","67");
			PNG.put("dimension imageorientation","68");
			PNG.put("dimension verticalpixeloffset","69");
			PNG.put("dimension pixelaspectratio", "90");
			PNG.put("transparency alpha","80");
			PNG.put("gama", "85");
			PNG.put("resourcename","100");
			PNG.put("tiff:imagelength","88");
			PNG.put("tiff:imagewidth","89");
			PNG.put("tiff:bitspersample", "89");
			PNG.put("width","97");
			PNG.put("srgb","82");
			PNG.put("aliases", "80");
			
			Text.put("content-encoding","98");
			Text.put("content-length","97");
			Text.put("content-type","96");
			Text.put("resourcename","100");
			Text.put("aliases", "80");
			
			Video.put("content-length", "97");
			Video.put("content-type", "98");
			Video.put("resourcename", "100");
			Video.put("aliases", "80");

		
	}
	
	//Fall Back model as Dublin Core
	public void compareDublinCore(HashMap<String, String> hashmap,String FileName)
	{
		
		double scoreArray[] = new double[256];
		double scoreNormalizedArray[] = new double[256];
		for(int i=0;i<scoreArray.length;i++)
		{
			scoreArray[i]=0;
		}
		int i=0;
		double quality_inPercentage=0.0;
		System.out.println("Dublin Default MetaComapre Function\n");
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String inputKey = entry.getKey();
			String inputValue = entry.getValue();
			if(dubliCoreHashMap.containsKey(inputKey))
			{
				//System.out.println("INPUT KEY MATCHED WITH GLOBAL Dublin HASHMAP");
				//System.out.println(inputKey+" "+inputValue +" is given as input\n");
				String val = dubliCoreHashMap.get(inputKey);
				 double num = Double.parseDouble(val);
				scoreArray[i++]=num;
			}
		}
		double maxScore=findMaxArrayElement(scoreArray);
		//System.out.println("Max Score is " + findMaxArrayElement(scoreArray));
		
		//Normalize the array
		//System.out.println("Normalized Scores");
		int index_i=0;
		int numberOFElementsInNormalizedArray=0;
		for(int index_j=0;index_j<scoreArray.length;index_j++)
		{
			scoreArray[index_j]=(double)(scoreArray[index_j]/maxScore);
			if(scoreArray[index_j]!=0.0)
			{
				//System.out.print(scoreArray[index_j]+ "\t");
				scoreNormalizedArray[index_i++]=scoreArray[index_j];
				numberOFElementsInNormalizedArray++;
			}
			
		}
		
		double sumScore=0.0;
		double avgScore=0.0;
		for(int index_k=0;index_k<scoreNormalizedArray.length;index_k++)
		{
			sumScore=sumScore+scoreNormalizedArray[index_k];
		}
		avgScore=(double)sumScore/(numberOFElementsInNormalizedArray);
		
		quality_inPercentage = avgScore * 100;
		//System.out.println("\nConsidering Normalization Quality of the given file is " + avgScore + "\n Quality = " + quality_inPercentage + "%");
		String text_to_File=FileName+","+quality_inPercentage+"\n";
		try {
		    Files.write(Paths.get("./src/results/Dublin_Quality.csv"), text_to_File.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		  System.out.println(e.toString());
		}
	
	}
	public void comparePDF(HashMap<String, String> hashmap,String FileName)
	{
		
		double scoreArray[] = new double[256];
		double scoreNormalizedArray[] = new double[256];
		for(int i=0;i<scoreArray.length;i++)
		{
			scoreArray[i]=0;
		}
		int i=0;
		double quality_inPercentage=0.0;
		System.out.println("PDF MetaComapre Function\n");
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String inputKey = entry.getKey();
			String inputValue = entry.getValue();
			if(PDF.containsKey(inputKey))
			{
			//	System.out.println("INPUT KEY MATCHED WITH GLOBAL PDF HASHMAP");
				//System.out.println(inputKey+" "+inputValue +" is given as input\n");
				String val = PDF.get(inputKey);
				 double num = Double.parseDouble(val);
				scoreArray[i++]=num;
			}
		}
		double maxScore=findMaxArrayElement(scoreArray);
		//System.out.println("Max Score is " + findMaxArrayElement(scoreArray));
		
		//Normalize the array
		//System.out.println("Normalized Scores");
		int index_i=0;
		int numberOFElementsInNormalizedArray=0;
		for(int index_j=0;index_j<scoreArray.length;index_j++)
		{
			scoreArray[index_j]=(double)(scoreArray[index_j]/maxScore);
			if(scoreArray[index_j]!=0.0)
			{
				//System.out.print(scoreArray[index_j]+ "\t");
				scoreNormalizedArray[index_i++]=scoreArray[index_j];
				numberOFElementsInNormalizedArray++;
			}
			
		}
		
		double sumScore=0.0;
		double avgScore=0.0;
		for(int index_k=0;index_k<scoreNormalizedArray.length;index_k++)
		{
			sumScore=sumScore+scoreNormalizedArray[index_k];
		}
		avgScore=(double)sumScore/(numberOFElementsInNormalizedArray);
		
		quality_inPercentage = avgScore * 100;
		//System.out.println("\nConsidering Normalization Quality of the given file is " + avgScore + "\n Quality = " + quality_inPercentage + "%");
		String text_to_File=FileName+","+quality_inPercentage+"\n";
		try {
		    Files.write(Paths.get("./src/results/PDF_Quality.csv"), text_to_File.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		  System.out.println(e.toString());
		}
	
	}
	public void compareText(HashMap<String, String> hashmap,String FileName)
	{
		
		double scoreArray[] = new double[256];
		double scoreNormalizedArray[] = new double[256];
		for(int i=0;i<scoreArray.length;i++)
		{
			scoreArray[i]=0;
		}
		int i=0;
		double quality_inPercentage=0.0;
		System.out.println("Text MetaComapre Function\n");
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String inputKey = entry.getKey();
			String inputValue = entry.getValue();
			if(Text.containsKey(inputKey))
			{
			//	System.out.println("INPUT KEY MATCHED WITH GLOBAL PDF HASHMAP");
				//System.out.println(inputKey+" "+inputValue +" is given as input\n");
				String val = Text.get(inputKey);
				 double num = Double.parseDouble(val);
				scoreArray[i++]=num;
			}
		}
		double maxScore=findMaxArrayElement(scoreArray);
		//System.out.println("Max Score is " + findMaxArrayElement(scoreArray));
		
		//Normalize the array
		//System.out.println("Normalized Scores");
		int index_i=0;
		int numberOFElementsInNormalizedArray=0;
		for(int index_j=0;index_j<scoreArray.length;index_j++)
		{
			scoreArray[index_j]=(double)(scoreArray[index_j]/maxScore);
			if(scoreArray[index_j]!=0.0)
			{
			//	System.out.print(scoreArray[index_j]+ "\t");
				scoreNormalizedArray[index_i++]=scoreArray[index_j];
				numberOFElementsInNormalizedArray++;
			}
			
		}
		
		double sumScore=0.0;
		double avgScore=0.0;
		for(int index_k=0;index_k<scoreNormalizedArray.length;index_k++)
		{
			sumScore=sumScore+scoreNormalizedArray[index_k];
		}
		avgScore=(double)sumScore/(numberOFElementsInNormalizedArray);
		
		quality_inPercentage = avgScore * 100;
		//System.out.println("\nConsidering Normalization Quality of the given file is " + avgScore + "\n Quality = " + quality_inPercentage + "%");
		String text_to_File=FileName+","+quality_inPercentage+"\n";
		try {
		    Files.write(Paths.get("./src/results/Text_Quality.csv"), text_to_File.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		  System.out.println(e.toString());
		}
	
	}
	public void compareVideo(HashMap<String, String> hashmap,String FileName)
	{
		
		double scoreArray[] = new double[256];
		double scoreNormalizedArray[] = new double[256];
		for(int i=0;i<scoreArray.length;i++)
		{
			scoreArray[i]=0;
		}
		int i=0;
		double quality_inPercentage=0.0;
		//System.out.println("Video MetaComapre Function\n");
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String inputKey = entry.getKey();
			String inputValue = entry.getValue();
			if(Video.containsKey(inputKey))
			{
			//	System.out.println("INPUT KEY MATCHED WITH GLOBAL PDF HASHMAP");
				//System.out.println(inputKey+" "+inputValue +" is given as input\n");
				String val = Video.get(inputKey);
				 double num = Double.parseDouble(val);
				scoreArray[i++]=num;
			}
		}
		double maxScore=findMaxArrayElement(scoreArray);
		//System.out.println("Max Score is " + findMaxArrayElement(scoreArray));
		
		//Normalize the array
		//System.out.println("Normalized Scores");
		int index_i=0;
		int numberOFElementsInNormalizedArray=0;
		for(int index_j=0;index_j<scoreArray.length;index_j++)
		{
			scoreArray[index_j]=(double)(scoreArray[index_j]/maxScore);
			if(scoreArray[index_j]!=0.0)
			{
			//	System.out.print(scoreArray[index_j]+ "\t");
				scoreNormalizedArray[index_i++]=scoreArray[index_j];
				numberOFElementsInNormalizedArray++;
			}
			
		}
		
		double sumScore=0.0;
		double avgScore=0.0;
		for(int index_k=0;index_k<scoreNormalizedArray.length;index_k++)
		{
			sumScore=sumScore+scoreNormalizedArray[index_k];
		}
		avgScore=(double)sumScore/(numberOFElementsInNormalizedArray);
		
		quality_inPercentage = avgScore * 100;
		//System.out.println("\nConsidering Normalization Quality of the given file is " + avgScore + "\n Quality = " + quality_inPercentage + "%");
		String text_to_File=FileName+","+quality_inPercentage+"\n";
		try {
		    Files.write(Paths.get("./src/results/Video_Quality.csv"), text_to_File.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		  System.out.println(e.toString());
		}
	
	}
	public void comparePNG(HashMap<String, String> hashmap,String FileName)
	{
		
		double scoreArray[] = new double[256];
		double scoreNormalizedArray[] = new double[256];
		for(int i=0;i<scoreArray.length;i++)
		{
			scoreArray[i]=0;
		}
		int i=0;
		double quality_inPercentage=0.0;
		System.out.println("PNG MetaComapre Function\n");
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String inputKey = entry.getKey();
			String inputValue = entry.getValue();
			if(PNG.containsKey(inputKey))
			{
			//	System.out.println("INPUT KEY MATCHED WITH GLOBAL PDF HASHMAP");
				//System.out.println(inputKey+" "+inputValue +" is given as input\n");
				String val = PNG.get(inputKey);
				 double num = Double.parseDouble(val);
				scoreArray[i++]=num;
			}
		}
		double maxScore=findMaxArrayElement(scoreArray);
		//System.out.println("Max Score is " + findMaxArrayElement(scoreArray));
		
		//Normalize the array
		//System.out.println("Normalized Scores");
		int index_i=0;
		int numberOFElementsInNormalizedArray=0;
		for(int index_j=0;index_j<scoreArray.length;index_j++)
		{
			scoreArray[index_j]=(double)(scoreArray[index_j]/maxScore);
			if(scoreArray[index_j]!=0.0)
			{
			//	System.out.print(scoreArray[index_j]+ "\t");
				scoreNormalizedArray[index_i++]=scoreArray[index_j];
				numberOFElementsInNormalizedArray++;
			}
			
		}
		
		double sumScore=0.0;
		double avgScore=0.0;
		for(int index_k=0;index_k<scoreNormalizedArray.length;index_k++)
		{
			sumScore=sumScore+scoreNormalizedArray[index_k];
		}
		avgScore=(double)sumScore/(numberOFElementsInNormalizedArray);
		
		quality_inPercentage = avgScore * 100;
		//System.out.println("\nConsidering Normalization Quality of the given file is " + avgScore + "\n Quality = " + quality_inPercentage + "%");
		String text_to_File=FileName+","+quality_inPercentage+"\n";
		try {
		    Files.write(Paths.get("./src/results/PNG_Quality.csv"), text_to_File.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		  System.out.println(e.toString());
		}
	
	}
	public void compareGIF(HashMap<String, String> hashmap,String FileName)
	{
		
		double scoreArray[] = new double[256];
		double scoreNormalizedArray[] = new double[256];
		for(int i=0;i<scoreArray.length;i++)
		{
			scoreArray[i]=0;
		}
		int i=0;
		double quality_inPercentage=0.0;
		System.out.println("GIF MetaComapre Function\n");
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String inputKey = entry.getKey();
			String inputValue = entry.getValue();
			if(GIF.containsKey(inputKey))
			{
			//	System.out.println("INPUT KEY MATCHED WITH GLOBAL PDF HASHMAP");
				//System.out.println(inputKey+" "+inputValue +" is given as input\n");
				String val = GIF.get(inputKey);
				 double num = Double.parseDouble(val);
				scoreArray[i++]=num;
			}
		}
		double maxScore=findMaxArrayElement(scoreArray);
		//System.out.println("Max Score is " + findMaxArrayElement(scoreArray));
		
		//Normalize the array
		//System.out.println("Normalized Scores");
		int index_i=0;
		int numberOFElementsInNormalizedArray=0;
		for(int index_j=0;index_j<scoreArray.length;index_j++)
		{
			scoreArray[index_j]=(double)(scoreArray[index_j]/maxScore);
			if(scoreArray[index_j]!=0.0)
			{
			//	System.out.print(scoreArray[index_j]+ "\t");
				scoreNormalizedArray[index_i++]=scoreArray[index_j];
				numberOFElementsInNormalizedArray++;
			}
			
		}
		
		double sumScore=0.0;
		double avgScore=0.0;
		for(int index_k=0;index_k<scoreNormalizedArray.length;index_k++)
		{
			sumScore=sumScore+scoreNormalizedArray[index_k];
		}
		avgScore=(double)sumScore/(numberOFElementsInNormalizedArray);
		
		quality_inPercentage = avgScore * 100;
		//System.out.println("\nConsidering Normalization Quality of the given file is " + avgScore + "\n Quality = " + quality_inPercentage + "%");
		String text_to_File=FileName+","+quality_inPercentage+"\n";
		try {
		    Files.write(Paths.get("./src/results/GIF_Quality.csv"), text_to_File.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		  System.out.println(e.toString());
		}
	
	}
	public void compareWebData(HashMap<String, String> hashmap,String FileName)
	{
		
		double scoreArray[] = new double[256];
		double scoreNormalizedArray[] = new double[256];
		for(int i=0;i<scoreArray.length;i++)
		{
			scoreArray[i]=0;
		}
		int i=0;
		double quality_inPercentage=0.0;
		System.out.println("Web MetaComapre Function\n");
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String inputKey = entry.getKey();
			String inputValue = entry.getValue();
			if(HTTP.containsKey(inputKey))
			{
			//	System.out.println("INPUT KEY MATCHED WITH GLOBAL HTTP HASHMAP");
				//System.out.println(inputKey+" "+inputValue +" is given as input\n");
				String val = HTTP.get(inputKey);
				 double num = Double.parseDouble(val);
				scoreArray[i++]=num;
			}
		}
		double maxScore=findMaxArrayElement(scoreArray);
		//System.out.println("Max Score is " + findMaxArrayElement(scoreArray));
		
		//Normalize the array
		//System.out.println("Normalized Scores");
		int index_i=0;
		int numberOFElementsInNormalizedArray=0;
		for(int index_j=0;index_j<scoreArray.length;index_j++)
		{
			scoreArray[index_j]=(double)(scoreArray[index_j]/maxScore);
			if(scoreArray[index_j]!=0.0)
			{
			//	System.out.print(scoreArray[index_j]+ "\t");
				scoreNormalizedArray[index_i++]=scoreArray[index_j];
				numberOFElementsInNormalizedArray++;
			}
			
		}
		
		double sumScore=0.0;
		double avgScore=0.0;
		for(int index_k=0;index_k<scoreNormalizedArray.length;index_k++)
		{
			sumScore=sumScore+scoreNormalizedArray[index_k];
		}
		avgScore=(double)sumScore/(numberOFElementsInNormalizedArray);
		
		quality_inPercentage = avgScore * 100;
		//System.out.println("\nConsidering Normalization Quality of the given file is " + avgScore + "\n Quality = " + quality_inPercentage + "%");
		String text_to_File=FileName+","+quality_inPercentage+"\n";
		try {
		    Files.write(Paths.get("./src/results/HTTP_Quality.csv"), text_to_File.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		  System.out.println(e.toString());
		}
	
	}
	public void compareMSOffice(HashMap<String, String> hashmap,String FileName)
	{
		
		double scoreArray[] = new double[256];
		double scoreNormalizedArray[] = new double[256];
		for(int i=0;i<scoreArray.length;i++)
		{
			scoreArray[i]=0;
		}
		int i=0;
		double quality_inPercentage=0.0;
		System.out.println("MSOffice MetaComapre Function\n");
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String inputKey = entry.getKey();
			String inputValue = entry.getValue();
			if(MSOffice.containsKey(inputKey))
			{
				//System.out.println("INPUT KEY MATCHED WITH GLOBAL HTTP HASHMAP");
				//System.out.println(inputKey+" "+inputValue +" is given as input\n");
				String val = MSOffice.get(inputKey);
				 double num = Double.parseDouble(val);
				scoreArray[i++]=num;
			}
		}
		double maxScore=findMaxArrayElement(scoreArray);
		//System.out.println("Max Score is " + findMaxArrayElement(scoreArray));
		
		//Normalize the array
		//System.out.println("Normalized Scores");
		int index_i=0;
		int numberOFElementsInNormalizedArray=0;
		for(int index_j=0;index_j<scoreArray.length;index_j++)
		{
			scoreArray[index_j]=(double)(scoreArray[index_j]/maxScore);
			if(scoreArray[index_j]!=0.0)
			{
			//	System.out.print(scoreArray[index_j]+ "\t");
				scoreNormalizedArray[index_i++]=scoreArray[index_j];
				numberOFElementsInNormalizedArray++;
			}
			
		}
		
		double sumScore=0.0;
		double avgScore=0.0;
		for(int index_k=0;index_k<scoreNormalizedArray.length;index_k++)
		{
			sumScore=sumScore+scoreNormalizedArray[index_k];
		}
		avgScore=(double)sumScore/(numberOFElementsInNormalizedArray);
		
		quality_inPercentage = avgScore * 100;
		//System.out.println("\nConsidering Normalization Quality of the given file is " + avgScore + "\n Quality = " + quality_inPercentage + "%");
		String text_to_File=FileName+","+quality_inPercentage+"\n";
		try {
		    Files.write(Paths.get("./src/results/MSOffice_Quality.csv"), text_to_File.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		  System.out.println(e.toString());
		}
	
	}
	public void compareJPEG(HashMap<String, String> hashmap,String FileName)
	{
		
		double scoreArray[] = new double[256];
		double scoreNormalizedArray[] = new double[256];
		for(int i=0;i<scoreArray.length;i++)
		{
			scoreArray[i]=0;
		}
		int i=0;
		double quality_inPercentage=0.0;
		System.out.println("JPEG MetaComapre Function\n");
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String inputKey = entry.getKey();
			String inputValue = entry.getValue();
			if(JPEG.containsKey(inputKey))
			{
				//System.out.println("INPUT KEY MATCHED WITH GLOBAL HTTP HASHMAP");
				//System.out.println(inputKey+" "+inputValue +" is given as input\n");
				String val = JPEG.get(inputKey);
				 double num = Double.parseDouble(val);
				scoreArray[i++]=num;
			}
		}
		double maxScore=findMaxArrayElement(scoreArray);
		//System.out.println("Max Score is " + findMaxArrayElement(scoreArray));
		
		//Normalize the array
		//System.out.println("Normalized Scores");
		int index_i=0;
		int numberOFElementsInNormalizedArray=0;
		for(int index_j=0;index_j<scoreArray.length;index_j++)
		{
			scoreArray[index_j]=(double)(scoreArray[index_j]/maxScore);
			if(scoreArray[index_j]!=0.0)
			{
			//	System.out.print(scoreArray[index_j]+ "\t");
				scoreNormalizedArray[index_i++]=scoreArray[index_j];
				numberOFElementsInNormalizedArray++;
			}
			
		}
		
		double sumScore=0.0;
		double avgScore=0.0;
		for(int index_k=0;index_k<scoreNormalizedArray.length;index_k++)
		{
			sumScore=sumScore+scoreNormalizedArray[index_k];
		}
		avgScore=(double)sumScore/(numberOFElementsInNormalizedArray);
		
		quality_inPercentage = avgScore * 100;
		//System.out.println("\nConsidering Normalization Quality of the given file is " + avgScore + "\n Quality = " + quality_inPercentage + "%");
		String text_to_File=FileName+","+quality_inPercentage+"\n";
		try {
		    Files.write(Paths.get("./src/results/JPEG_Quality.csv"), text_to_File.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		  System.out.println(e.toString());
		}
	
	}
	public static double findMaxArrayElement(double[] scoreArray)
	{
		double max=scoreArray[0];
		for(int i=0;i<scoreArray.length;i++)
		{
			if(max<scoreArray[i])
			{
				max=scoreArray[i];
			}
		}
		return max;
		
	}

	public static void main(String arg[]) throws IOException, SAXException, TikaException, ParserConfigurationException
	{
		Tika tika = new Tika();
		QualityAnalysis qualityAnalysis = new QualityAnalysis();
		Metadata metadata = new Metadata();
		AutoDetectParser parser = new AutoDetectParser();
	    BodyContentHandler handler = new BodyContentHandler(-1);
	    String filePath=arg[0];
		File dir = new File(filePath);
		
		File[] files = dir.listFiles();
		for(File file:files)
		{
			try{
			
				int VND_FLAG = 0;
				HashMap<String, String> fileHashMap = new HashMap<String, String>();
				if(file.isFile())
				{	
					FileInputStream inputstream = new FileInputStream(file);
					ParseContext context = new ParseContext();
					
//					ToXMLContentHandler contentHandler = new ToXMLContentHandler();
				//	parser.parse(inputstream, contentHandler, metadata);
					parser.parse(inputstream, handler, metadata, context);
					String[] metadataNames = metadata.names();
					
					for(String name : metadataNames) {		        
						fileHashMap.put(name.toLowerCase(), metadata.get(name).toLowerCase());	    	  
	    	 			}
					String MIME_TYPE_OF_A_FILE=tika.detect(file);
					System.out.println(MIME_TYPE_OF_A_FILE);
					for (Map.Entry<String, String> entry : fileHashMap.entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();
					 }
					Pattern pattern = Pattern.compile("application/vnd");
					Matcher matcher = pattern.matcher(MIME_TYPE_OF_A_FILE);
				
					if(matcher.find())
					VND_FLAG=1;
				
					if(MIME_TYPE_OF_A_FILE.equalsIgnoreCase("application/pdf"))
					{
						qualityAnalysis.comparePDF(fileHashMap,file.getName());
					}
					else if(MIME_TYPE_OF_A_FILE.equalsIgnoreCase("image/gif"))
					{
						qualityAnalysis.compareGIF(fileHashMap,file.getName());
					}
					else if(MIME_TYPE_OF_A_FILE.equalsIgnoreCase("image/jpeg"))
					{
						qualityAnalysis.compareJPEG(fileHashMap,file.getName());
					}
					else if(MIME_TYPE_OF_A_FILE.equalsIgnoreCase("image/png"))
					{
						qualityAnalysis.comparePNG(fileHashMap,file.getName());
					}
				
					else if(MIME_TYPE_OF_A_FILE.equalsIgnoreCase("application/xhtml+xml") 
							|| MIME_TYPE_OF_A_FILE.equalsIgnoreCase("text/html")
						  
							)
					{
						qualityAnalysis.compareWebData(fileHashMap,file.getName());
					}
				
					else if(MIME_TYPE_OF_A_FILE.equalsIgnoreCase("text/plain")
							|| MIME_TYPE_OF_A_FILE.equalsIgnoreCase("text/x-php"))
					{
						qualityAnalysis.compareText(fileHashMap, file.getName());
					}
					else if(VND_FLAG==1)
					{
						qualityAnalysis.compareMSOffice(fileHashMap, file.getName());
					
					}
					else
					{	
						qualityAnalysis.compareDublinCore(fileHashMap,file.getName());
					}
					
			}//END if FILE
	    }//END FOR
	//END TRY
		catch(Exception e)
		{
			System.out.println(file.getName());
			System.out.println(e.getStackTrace().toString());
		}
		finally {
			
		}
		}	  
	}//END MAIN
	/*public static void readOutputMetaFile(String XMLOutputpath) 
	{
		File dir = new File(XMLOutputpath);
		System.out.println("Path given is "+ XMLOutputpath);
		File[] files = dir.listFiles();
		for(File file:files)
		{
			System.out.println("ReadXML");
			try
			
			{
				BufferedReader br  = new BufferedReader(new FileReader(file));
				String line = "";
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine())!=null)
				{
					sb.append(line);
					sb.append("\n");
				}
				br.close();
		      String outputString=sb.toString();
		      String [] outputSplitArray=outputString.split("=");
		      for(int i=0;i<outputSplitArray.length-1;i=i+2)
		      {
		    	  System.out.println(outputSplitArray[i] + " "+ outputSplitArray[i+1] );
		      }
		      
		      
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
		}
		
		}
	*/
		
}

