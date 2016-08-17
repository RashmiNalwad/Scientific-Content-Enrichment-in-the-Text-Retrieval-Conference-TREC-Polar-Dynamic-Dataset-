package org.usc.edu.doi.mysql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class ExtractShortURLs {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;

  public void readDataBase(String outputDOCPath) throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/surls?"+ "user=aditya&useSSL=false");
      

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from `polar.usc.edu url`");
      //System.out.println("Result is "+ resultSet);
     // writeMetaData(resultSet);
      writeResultSet(resultSet,outputDOCPath);
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }
  private void writeMetaData(ResultSet resultSet) throws SQLException {
	   // System.out.println("The columns in the table are: ");
	    //System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
	    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
	      //System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
	    }
  }
  private void writeResultSet(ResultSet resultSet,String outputDOCPath) throws SQLException, IOException {
	    // ResultSet is initially before the first data set
	    while (resultSet.next()) {
	      String keyword = resultSet.getString("keyword");
	      String url = resultSet.getString("url");
	      String title = resultSet.getString("title");
	      String modifiedKeyword="http://polar.usc.edu/"+keyword;
	      System.out.println("DOI " + modifiedKeyword);
	      //System.out.println("URL: " + url);
	      System.out.println("FileName " + title);
	      writeJSONFile(modifiedKeyword,title,keyword,outputDOCPath);
	      System.out.println("JSON Files are written");
	     }
  }
  public void writeJSONFile(String modifiedKeyword,String title,String keyword,String outputDOCPath) throws IOException
  {
	  File file = new File(outputDOCPath+"/"+title+"_Output.doc");
		FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
		bufferedwriter.write("{\n");
		bufferedwriter.write("\"DOI\""+":");
		bufferedwriter.write("\""+modifiedKeyword+"\""+",\n");
		bufferedwriter.write("\"Keyword\""+":");
		bufferedwriter.write("\""+keyword+"\""+"\n");
		bufferedwriter.write("}");
		bufferedwriter.close();
  }
    private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

} 