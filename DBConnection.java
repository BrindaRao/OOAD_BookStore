package com.cs521.team3.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cs521.team3.model.Books;
import com.cs521.team3.model.ReviewandRating;
import com.cs521.team3.model.Wishlist;

public class DBConnection {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL= "jdbc:mysql://localhost:3306/book_store";
	static final String USER = "root";
	static final String PASS = "beegees,9030";
	
	private Connection myConn;
	
	public DBConnection() throws Exception {
		
		//Connection conn = null;
		//Statement stmt = null;
		try{
		//STEP 2: Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		//STEP 3: Open a connection
		System.out.println("Connecting to a selected database...");
		myConn = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected database successfully...");
		
		// connect to database
		//myConn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		System.out.println("DB connection successful to: " + DB_URL);
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
			}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
			}
	}					
	
	public List<Books> getAllBooks() throws Exception {
		List<Books> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from book_store.book");
			//System.out.println(myRs);
			while (myRs.next()) {
				//System.out.println(myRs.getInt(1));
				Books book = convertRowToBooks(myRs);
				list.add(book);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Wishlist> getWishlist() throws Exception {
		List<Wishlist> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from book_store.wishlist");
			//System.out.println(myRs);
			while (myRs.next()) {
				//System.out.println(myRs.getInt(1));
				Wishlist wish = convertRowToWishlist(myRs);
				list.add(wish);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void insertWishlist(String a){
		Statement myStmt = null;
		//ResultSet myRs = null;
		String CustomerID = "1";
		
		try {
			System.out.println("inside insertwish");
			myStmt = myConn.createStatement();
			myStmt.executeUpdate("insert into book_store.wishlist(CustomerID, BookID) values('"+CustomerID+"','"+a+"')");
			//System.out.println(myRs);
			System.out.println("insert successful");
					
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
	}
	
	public void insertReviewRating(String review,int rating){
		Statement myStmt = null;
		//ResultSet myRs = null;
		String CustomerId = "1";
		int BookID = 22;
		
		try {
			System.out.println("inside insertRR");
			myStmt = myConn.createStatement();
			myStmt.executeUpdate("insert into book_store.reviewandrating (BookID,Review,Rating,CustomerId) values("+BookID+",'"+review+"',"+rating+","+CustomerId+")");
			//System.out.println(myRs);
			System.out.println("insert successful");
			
			
		}catch(Exception ex){
			System.out.println("this is the error message"+ex.getMessage());
		}		
	}
	
	public List<ReviewandRating> getReviewRating() throws Exception {
		List<ReviewandRating> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from book_store.reviewandrating");
			//System.out.println(myRs);
			while (myRs.next()) {
				System.out.println(myRs.getInt(1));
				ReviewandRating wish = convertRowToRR(myRs);
				list.add(wish);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	private Wishlist convertRowToWishlist(ResultSet myRs) throws SQLException {
		
		String customer_id = myRs.getString("CustomerID");
		String book_id = myRs.getString("BookID");
		
		Wishlist tempWish = new Wishlist(customer_id, book_id);
		
		return tempWish;
	}
	
	private ReviewandRating convertRowToRR(ResultSet myRs) throws SQLException {
		
		
		String book_id = myRs.getString("BookID");
		String review = myRs.getString("Review");
		int rating = myRs.getInt("Rating");
		String customer_id = myRs.getString("CustomerID");
		ReviewandRating temprev = new ReviewandRating(book_id, review, rating, customer_id);
		
		return temprev;
	}
	
private Books convertRowToBooks(ResultSet myRs) throws SQLException {
		
		String book_id = myRs.getString("BookID");
		String book_title = myRs.getString("BookTitle");
		String author_name = myRs.getString("AuthorName");
		int price = myRs.getInt("Price");
		String sellerId = myRs.getString("SellerId");
		
		Books tempBooks = new Books(book_id, book_title, author_name, price, sellerId);
		
		return tempBooks;
	}

	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}

	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}

	public void createTables() {
		Statement myStmt = null;
		//ResultSet myRs = null;
		try {
			myStmt = myConn.createStatement();
			String createTableBooks = "CREATE TABLE `book_store`.`book` ("
					  + "`BookID` VARCHAR(45) NOT NULL,"
					  + "`BookTitle` VARCHAR(45) NULL, "
					  + "`AuthorName` VARCHAR(45) NOT NULL," 
					  + "`Price` VARCHAR(45) NULL, "
					  + "`SellerId` VARCHAR(45) NULL, "
					  + "PRIMARY KEY (`BookID`));";

			myStmt.executeUpdate(createTableBooks);
			
			String createTableCustomer = "CREATE TABLE `book_store`.`customer` ("
  + "`CustomerID` VARCHAR(45) NOT NULL,"
  + "`FirstName` VARCHAR(45) NULL, "
  + "`LastName` VARCHAR(45) NULL, "
  + "`DOB` DATE NULL, "
  + "`EmailID` VARCHAR(45) NULL, "
  + "`Address` VARCHAR(45) NULL, "
  + "`PhoneNumber` VARCHAR(45) NULL, "
  + "`UserName` VARCHAR(45) NULL, "
  + "`Password` VARCHAR(45) NULL, "
  + "PRIMARY KEY (`CustomerID`));";
			myStmt.executeUpdate(createTableCustomer);
			
			String createTableWishlist = "CREATE TABLE `book_store`.`wishlist` ( "
 + "`CustomerID` VARCHAR(45) NOT NULL, "
 + "`BookID` VARCHAR(45) NULL, "
 + "PRIMARY KEY (`CustomerID`,`BookID`), "
 + "CONSTRAINT `CustomerID` "
  +  "FOREIGN KEY (`CustomerID`) "
   + "REFERENCES `book_store`.`customer` (`CustomerID`) "
   + "ON DELETE NO ACTION "
   + "ON UPDATE NO ACTION);";
			myStmt.executeUpdate(createTableWishlist);
			
			String createTableReviewRating = "CREATE TABLE `book_store`.`reviewandrating` ("
 + "`BookID` VARCHAR(45) NOT NULL, "
 + "`Review` VARCHAR(45) NULL, "
 + "`Rating` VARCHAR(45) NULL, "
 + "`CustomerId` VARCHAR(45) NOT NULL, "
 + "PRIMARY KEY (`BookID`, `CustomerId`), "
 + "INDEX `CustId_review_idx` (`CustomerId` ASC), "
 + "CONSTRAINT `BookId` "
  + "FOREIGN KEY (`BookID`) "
   + "REFERENCES `book_store`.`book` (`BookID`) "
   + "ON DELETE NO ACTION "
   + "ON UPDATE NO ACTION, "
 + "CONSTRAINT `CustId_review` "
   + "FOREIGN KEY (`CustomerId`) "
   + "REFERENCES `book_store`.`customer` (`CustomerID`) "
   + "ON DELETE NO ACTION "
   + "ON UPDATE NO ACTION);";
			myStmt.executeUpdate(createTableReviewRating);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void createTablespace() {
     	// query
		String createDatabaseQuery = "CREATE DATABASE BS";
		String useDB = "USE BS";
		String createTablespaceCommand = "CREATE TABLESPACE BS ADD DATAFILE 'BS.ibd';";
		Statement statement = null;
		try {
			statement = myConn.createStatement();
			statement.executeUpdate(createDatabaseQuery);
			statement.executeUpdate(useDB);
			statement.executeUpdate(createTablespaceCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// statement

	}
	
	public static void main(String[] args) throws Exception {
		
		DBConnection dao = new DBConnection();
		System.out.println(dao.getAllBooks());
        System.out.println(dao.getWishlist());
		//System.out.println(dao.getAllEmployees());
	}
}
