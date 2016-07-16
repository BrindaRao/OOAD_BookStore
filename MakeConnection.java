package com.cs521.team3.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.cs521.team3.model.BookDetails;

public class MakeConnection {
	public static Connection getDafaultConnection() {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = null;

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "beegees,9030");
			System.out.println("\n Driver is connected Successfully");

			return conn;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		} catch (Exception ex) {
			// handle the error
			ex.printStackTrace();
			return null;
		}
	}
	public static Connection getConnection(String databaseName) {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = null;

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databaseName, "root", "beegees,9030");
			System.out.println("\n Driver is connected Successfully");

			return conn;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		} catch (Exception ex) {
			// handle the error
			ex.printStackTrace();
			return null;
		}
	}
	public List<BookDetails> getAllBooks() throws Exception {
		List<BookDetails> list = new ArrayList<BookDetails>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection("book_store");
			System.out.println("Inside getAllBooks()");
			myStmt = myConn.createStatement();
			System.out.println("Connection create successfully");
			myRs = myStmt.executeQuery("select * from book_store.book");

			while (myRs.next()) {
				System.out.println("Data obtained in while loop - getAllBooks");
				BookDetails tempBooks = convertRowToBooks(myRs);
				list.add(tempBooks);
			}
			System.out.println("List "+ list);
			return list;	

		}
		finally {
			myRs.close();
			myConn.close();
			myStmt.close();
		}
	}

	private BookDetails convertRowToBooks(ResultSet myRs) throws SQLException {

		String bookID = myRs.getString("BookID");
		String bookTitle = myRs.getString("BookTitle");
		String author = myRs.getString("AuthorName");
		String price = myRs.getString("Price");
		String category = myRs.getString("Category");
		String sellerID = myRs.getString("SellerId");

		BookDetails tempBook = new BookDetails(bookID,bookTitle, author, price,sellerID, category);

		return tempBook;
	}


	public List<BookDetails> searchBooks(String bookName, String bookTitle, String price, String category) throws Exception {
		List<BookDetails> list = new ArrayList<BookDetails>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String query = null;
		Connection conn = null;

		try {

			conn = MakeConnection.getConnection("book_store");
			query = buildQueryForSearch(bookName,bookTitle, price, category);
			System.out.println("Query for search books is "+ query);
			System.out.println("Query : " + query);
			myStmt = conn.prepareStatement(query);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				BookDetails tempBook = convertRowToBooks(myRs);
				list.add(tempBook);
			}

			return list;
		}
		finally {
			myRs.close();
			conn.close();
			myStmt.close();
		}
	}

	private String buildQueryForSearch(String bookTitle,String bookAuthor,String bookPrice,String bookCat) {
		String sqlBookQ = null;
		String sqlAuthorQ = null;
		String sqlPriceQ = null;
		String sqlCatQ = null;
		String sqlQuery = "SELECT * FROM BOOK_STORE.BOOK WHERE ";
		int countOfFull = 0	;

		if(bookTitle.length()>0)
		{
			countOfFull = countOfFull + 1;
			sqlBookQ =  " BookTitle like '" + bookTitle  + "%" + "'";
		}
		if(bookAuthor.length()>0)
		{
			countOfFull = countOfFull + 1;
			sqlAuthorQ = " AuthorName like '"+bookAuthor + "%" +"'";
		}
		if(bookPrice.length()>0)
		{
			countOfFull = countOfFull + 1;
			sqlPriceQ = " Price =  '"+bookPrice +"'";
		}
		if(bookCat.length()>0)
		{
			countOfFull = countOfFull + 1;
			sqlCatQ = " Category like '"+ bookCat + "%" +"'";
		}
		System.out.println("sqlBookq" + sqlBookQ);
		System.out.println("sqlauthorq" + sqlAuthorQ);
		System.out.println("sqlpriceq" + sqlPriceQ);
		System.out.println("sqlcatq" + sqlCatQ);
		System.out.println(countOfFull);
		switch(countOfFull)
		{
		case 4:
			sqlQuery = sqlQuery  + sqlBookQ + "AND" + sqlAuthorQ + "AND" + sqlPriceQ  + "AND" + sqlCatQ;
			break;
		case 3:
			if(bookTitle.length()>0 && bookAuthor.length()>0 && bookPrice.length()>0)
				sqlQuery = sqlQuery + sqlBookQ + "AND" + sqlAuthorQ + "AND" + sqlPriceQ;
			else if(bookTitle.length()>0 && bookAuthor.length()>0 && bookCat.length()>0)
				sqlQuery = sqlQuery + sqlBookQ + "AND" + sqlAuthorQ + "AND" + sqlCatQ;
			else if(bookAuthor.length()>0 && bookPrice.length()>0 && bookCat.length()>0)
				sqlQuery = sqlQuery + sqlAuthorQ + "AND" + sqlPriceQ + "AND" + sqlCatQ;
			else if(bookTitle.length()>0 && bookPrice.length()>0 && bookCat.length()>0)
				sqlQuery = sqlQuery + sqlBookQ + "AND" + sqlPriceQ + "AND" + sqlCatQ;
			break;
		case 2:
			if(bookTitle.length()>0 && bookAuthor.length()>0)
				sqlQuery = sqlQuery + sqlBookQ + "AND" + sqlAuthorQ;
			else if(bookAuthor.length()>0 && bookPrice.length()>0)
				sqlQuery = sqlQuery + sqlAuthorQ + "AND" + sqlPriceQ;
			else if(bookCat.length()>0 && bookPrice.length()>0)
				sqlQuery = sqlQuery + sqlCatQ + "AND" + sqlPriceQ;
			else if(bookTitle.length()>0 && bookCat.length()>0)
				sqlQuery = sqlQuery + sqlBookQ + "AND" + sqlCatQ;
			else if(bookTitle.length()>0 && bookPrice.length()>0)
				sqlQuery = sqlQuery + sqlBookQ + "AND" + sqlPriceQ;
			else if(bookAuthor.length()>0 && bookCat.length()>0)
				sqlQuery = sqlQuery + sqlAuthorQ + "AND" + sqlCatQ;
			break;
		case 1:
			if(bookTitle.length()>0)
				sqlQuery = sqlQuery + sqlBookQ;
			else if(bookAuthor.length()>0)
				sqlQuery = sqlQuery + sqlAuthorQ;
			else if(bookPrice.length()>0)
				sqlQuery = sqlQuery + sqlPriceQ;
			else if(bookCat.length()>0)
				sqlQuery = sqlQuery + sqlCatQ;
			break;
		case 0:
			JOptionPane.showMessageDialog(null,"No Records Found!");
			break;
		default:
			JOptionPane.showMessageDialog(null,"No Records Found!");
			break;
		}
		/*if(bookTitle.length()>0)
		sqlQuery = sqlQuery + " BookTitle like '" + bookTitle  + "%" + "'";
	else if(bookAuthor.length()>0)
		sqlQuery =  sqlQuery + " AuthorName like '"+bookAuthor + "%" +"'";
	else if(bookPrice.length()>0)
		sqlQuery = sqlQuery + " Price =  '"+bookPrice + "$" +"'";
	else if(bookCat.length()>0)
		sqlQuery = sqlQuery + " Category like '"+bookCat + "%" +"'";
	else if(sqlAuthorQ.length()>0 && sqlBookQ.length()>0 && sqlCatQ.length()>0 && sqlPriceQ.length()>0)
		sqlQuery = sqlQuery + "WHERE " + sqlAuthorQ + "AND " + sqlBookQ + "AND " + sqlCatQ + "AND " + sqlPriceQ ;
	else if(sqlAuthorQ.length()>0 && sqlBookQ.length()>0 && sqlCatQ.length()>0)
		sqlQuery = sqlQuery + "WHERE " + sqlBookQ + "AND " + sqlAuthorQ + "AND " + sqlCatQ;
	else if(sqlBookQ.length()>0 && sqlCatQ.length()>0 && sqlPriceQ.length()>0)
		sqlQuery = sqlQuery + "WHERE " + sqlBookQ + "AND " + sqlCatQ + "AND " + sqlPriceQ ;
	else if(sqlCatQ.length()>0 && sqlPriceQ.length()>0 && sqlAuthorQ.length()>0)
		sqlQuery = sqlQuery + "WHERE " + sqlAuthorQ + "AND " + sqlCatQ + "AND " + sqlPriceQ ;
	else if(sqlPriceQ.length()>0 && sqlAuthorQ.length()>0 && sqlBookQ.length()>0)
		sqlQuery = sqlQuery + "WHERE " + sqlPriceQ + "AND " + sqlBookQ + "AND " + sqlAuthorQ;*/

		System.out.println("SqlQuery" + sqlQuery);
		return sqlQuery;
	}



}
