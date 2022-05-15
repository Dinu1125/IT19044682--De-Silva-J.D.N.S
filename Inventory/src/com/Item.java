package com; 
import java.sql.*; 
public class Item 
{ //A common method to connect to the DB
	private Connection connect() 
	{ 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 

			//Provide the correct details: DBServer/DBName, username, password 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogridinventory", "root", "1234"); 
		} 
		catch (Exception e) 
		{e.printStackTrace();} 
		return con; 
	} 

	//Insert Item

	public String insertInventoryItem(String itemName, String itemQty, String itemCode, String descripation)
	{
	   String output = "";
	  try
	   {
	      Connection con = connect();
	      if (con == null)
	   {
	    return "Error while connecting to the database";
	   }
	   // create a prepared statement
	      String query = " insert into inventory (`itemID`,`itemName`,`itemQty`,`itemCode`,`descripation`)"
					+ " values (?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, itemName); 
			preparedStmt.setString(3, itemQty); 
			preparedStmt.setString(4, itemCode); 
			preparedStmt.setString(5, descripation); 
			// execute the statement

			preparedStmt.execute(); 
			con.close(); 
			String newItem = viewItems();
	   output = "{\"status\":\"success\", \"data\": \"" + newItem + "\"}";
	  }
	  catch (Exception e)
	  {
	  output = "{\"status\":\"error\", \"data\": \"Error while inserting the Item.\"}";
	  System.err.println(e.getMessage());
	  }
	  return output;
	  }

	//View Items

	public String viewItems() 
	{ 
		 String output = "";
	     try
	    {
	      Connection con = connect();
	      if (con == null)
	    {
	        return "Error while connecting to the database for reading.";
	    }
	// Prepare the html table to be displayed
	   output = "<table border='2'><tr>"
	   			+ "<th>Item Name</th>"
	            +"<th>Item Qty</th>"
	            + "<th>Item Code</th>"
	            + "<th>Description</th>"
	            + "<th>Update</th>"
	            + "<th>Remove</th></tr>";
	           
	   
	    String query = "select * from inventory";
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	// iterate through the rows in the result set
	   while (rs.next())
	   {
		    String itemID = Integer.toString(rs.getInt("itemID")); 
			String itemName = rs.getString("itemName"); 
			String itemQty = rs.getString("itemQty"); 
			String itemCode = rs.getString("itemCode"); 
			String descripation = rs.getString("descripation"); 
                
                 output += "<tr><td><input id=\'hiditemIDUpdate\' name=\'hiditemIDUpdate\' type=\'hidden\' value=\'"
					+ itemID + "'>" + itemName + "</td>";
			output += "<td>" + itemQty + "</td>";
			output += "<td>" + itemCode + "</td>";
			output += "<td>" + descripation + "</td>";
			
			

			// buttons
			output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemID='"
					+ itemID + "'>" + "</td></tr>";

	   }
	    con.close();
	// Complete the html tables
	    output += "</table>";
	    
	    }
	   catch (Exception e)
	   {
	      output = "Error while reading the Items.";
	      System.err.println(e.getMessage());
	   }
	    return output;
	 }

	//Update Item

	public String updateInventoryItem(String itemID, String itemName, String itemQty, String itemCode, String descripation)
	
	{
	    String output = "";
	try
	{
	   Connection con = connect();
	   if (con == null)
	    {  
		   return "Error while connecting to the database for updating."; 
		}
	// create a prepared statement
	  String query = "UPDATE inventory SET itemName=?,itemQty=?,itemCode=?,descripation=? WHERE itemID=?";
	  PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	  	preparedStmt.setString(1, itemName); 
		preparedStmt.setString(2, itemQty); 
		preparedStmt.setString(3, itemCode); 
		preparedStmt.setString(4, descripation); 
		preparedStmt.setInt(5, Integer.parseInt(itemID)); 
	// execute the statement
	   preparedStmt.execute();
	   con.close();
	   
               String newItem = viewItems();
	   output = "{\"status\":\"success\", \"data\": \"" + newItem + "\"}";
	}
	catch (Exception e)
	{
	   output = "{\"status\":\"error\", \"data\": \"Error while updating the Details.\"}";
	   System.err.println(e.getMessage());
	}
	   return output;
 }

	//Delete Item

	public String deleteInventoryItem(String itemID) 
	{ 
		 String output = "";
		 try
		  {
		    Connection con = connect();
		    if (con == null)
		   {
		       return "Error while connecting to the database for deleting.";
		   }
			// create a prepared statement
			String query = "delete from inventory where itemID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(itemID)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			
			String newItem = viewItems();
		    output = "{\"status\":\"success\", \"data\": \"" + newItem + "\"}";
		} 
		catch (Exception e) 
		{ 
			output = "Error while deleting the item."; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
} 
