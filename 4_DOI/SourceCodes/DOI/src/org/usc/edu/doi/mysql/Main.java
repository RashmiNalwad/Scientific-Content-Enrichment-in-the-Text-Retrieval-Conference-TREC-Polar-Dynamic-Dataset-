package org.usc.edu.doi.mysql;

public class Main {

	public static void main(String[] args) throws Exception {
		ExtractShortURLs extract = new ExtractShortURLs();
		extract.readDataBase(args[0]);
	}

}
