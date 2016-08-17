package org.usc.edu.rename;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

public class JSONToDOC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String filePath="E:\\TikaWorkspace\\DOI\\src\\org\\usc\\edu\\rename\\json";
		String fileoutputPath="E:\\TikaWorkspace\\DOI\\src\\org\\usc\\edu\\rename\\doc";
		File dir = new File(filePath);
		System.out.println("Path given is "+ filePath);
		File[] files = dir.listFiles();
		
		for(File file:files)
		{
			//System.out.println(file.getName());
			//file.renameTo(fileoutputPath+"/"+file.getName()+".doc");
			
			System.out.println(file.getName().substring(0, file.getName().lastIndexOf(".")));
			String newFileName=file.getName().substring(0, file.getName().lastIndexOf("."));
			File newFile = new File(fileoutputPath+"/"+newFileName+".doc");
			file.renameTo(newFile);
		}

	}

}
