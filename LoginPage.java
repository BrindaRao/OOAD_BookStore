package com.cs521.team3.ui;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.cs521.team3.dbconnection.MakeConnection;


public class LoginPage {
	
	
	JFrame frmBookStore;
	JTextField txtUserName;
	JTextField txtPassword;
	public static String userName="";
	public static String password="";


	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connection conn = MakeConnection.getDafaultConnection();
					createTables(conn);
					conn.close();
					LoginPage window = new LoginPage();
					window.frmBookStore.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBookStore = new JFrame();
		frmBookStore.setTitle("Book  Store");
		frmBookStore.setBounds(100, 100, 450, 466);
		frmBookStore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBookStore.getContentPane().setLayout(null);
		
		JLabel lblLoginPageName = new JLabel("Book Store ");
		lblLoginPageName.setBounds(0, 0, 432, 36);
		lblLoginPageName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginPageName.setFont(new Font("Tempus Sans ITC", Font.BOLD, 27));
		frmBookStore.getContentPane().add(lblLoginPageName);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(95, 116, 72, 16);
		frmBookStore.getContentPane().add(lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(175, 113, 116, 22);
		frmBookStore.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setBounds(95, 171, 56, 16);
		frmBookStore.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(175, 168, 116, 22);
		frmBookStore.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String UName = txtUserName.getText();
				userName = UName;
				String pwd = txtPassword.getText();
				password = pwd;
				if(UName.equalsIgnoreCase("admin")){
					BookStoreApp.loadUI();
				}
				else if(validateUserLogin(UName, pwd))
				{
					frmBookStore.setVisible(false);
					BookStoreApp.loadUI();
					//xSearchPage.NewSearchPage();
				}
				else
				{
					txtUserName.setText("");
					txtPassword.setText("");
				}
			}
		});
		btnLogin.setBounds(281, 276, 97, 25);
		frmBookStore.getContentPane().add(btnLogin);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(38, 276, 97, 25);
		frmBookStore.getContentPane().add(btnCancel);
		
		JLabel lblNewUser = new JLabel("New User?");
		lblNewUser.setBounds(38, 390, 72, 16);
		frmBookStore.getContentPane().add(lblNewUser);
		
		JButton btnCreateNewAccount = new JButton("Create New Account");
		btnCreateNewAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmBookStore.setVisible(false);
				//NewUserAccPage newAcc = new NewUserAccPage();
				NewUserAccPage.NewAccountCreation();
			}
		});
		btnCreateNewAccount.setBounds(148, 386, 178, 25);
		frmBookStore.getContentPane().add(btnCreateNewAccount);
		
		JButton btnSkipLogin = new JButton("Skip Login");
		btnSkipLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmBookStore.setVisible(false);
				BookStoreApp.loadUI();
				//SearchPage sp = new SearchPage();
				//SearchPage.NewSearchPage();
			}
		});
		btnSkipLogin.setBounds(155, 276, 97, 25);
		frmBookStore.getContentPane().add(btnSkipLogin);
	}

	
	public boolean validateUserLogin(String userName, String passWord)
	{
		Connection conn = null;
		boolean result = false;
		String displayresult = "Incorrect Login";
		try
		{
			System.out.println("Inside method for validating");
			conn = MakeConnection.getConnection("BOOK_STORE");
			Statement statement = null;
			ResultSet rs = null;
			if(userName!=null && passWord!=null)
			{
				System.out.println("Inside first if - when username and pwd is not null");
				try {
					statement  = conn.createStatement();
					String sqlstmt = "Select * from Book_Store.Customer where UserName='" + userName + "' and Password='" + passWord + "'";
					rs = statement.executeQuery(sqlstmt);
					System.out.println("Count of rs " + rs.getFetchSize());
					System.out.println("Username " + userName);
					System.out.println("Pwd : " + passWord);
					 while (rs.next())
				     {
				            if (userName.equals(rs.getString("UserName")))
				            {
				                if (passWord.equals(rs.getString("Password")))
				                {
				                		
				                			displayresult = "Login Successfull";
				                          //JOptionPane.showMessageDialog(null,"Login Successfully");
				                          result = true;
				                         
				                }
				            }
				                
				            
				      }
				        statement.close();
				        conn.close();
				        rs.close();

				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		finally
		{
			JOptionPane.showMessageDialog(null,displayresult);
		}
		return result;
	}
	
	public static void createTablespace(Connection conn) {

		// query
		String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS book_store";
		String useDB = "USE book_store";
		String createTablespaceCommand = "CREATE TABLESPACE book_store ADD DATAFILE 'book_store.ibd';";
		Statement statement = null;
		try {
			statement = conn.createStatement();
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
	public static void createTables(Connection conn) throws SQLException{
		createTablespace(conn);
		if (conn != null){
			conn = MakeConnection.getConnection("book_store");
			System.out.println("Connection established");
			Statement statement = null;
			try {
				statement = conn.createStatement();
				String createCategoryTableQuery = "CREATE TABLE IF NOT EXISTS `book_store`.`category` "
						+ "(`CategoryId` VARCHAR(45) NOT NULL,"
						+ "`CategoryName` VARCHAR(45) NULL,"
						+ "PRIMARY KEY(`CategoryId`));";

				statement.executeUpdate(createCategoryTableQuery);
				
				String createDiscountTableQuery ="CREATE TABLE IF NOT EXISTS `book_store`.`discount` "
						+ "(`DiscountID` VARCHAR(5) NOT NULL,"
						+ "`Amount` INT NULL,"
						+ "PRIMARY KEY (`DiscountID`));";
				statement.executeUpdate(createDiscountTableQuery);
				String createEmployeeTableQuery="CREATE TABLE IF NOT EXISTS `book_store`.`employee` "
						+ "(`EmployeeID` VARCHAR(45) NOT NULL,"
						+ "`UserName` VARCHAR(45) NULL,"
						+ "`Role` VARCHAR(45) NULL,"
						+ "`Password` VARCHAR(45) NULL);";
				statement.executeUpdate(createEmployeeTableQuery);
				String createBookTableQuery="CREATE TABLE IF NOT EXISTS `book_store`.`book` "
						+ "(`BookID` VARCHAR(45) NOT NULL,"
						+ "`BookTitle` VARCHAR(45) NULL,"
						+ "`AuthorName` VARCHAR(45) NOT NULL,"
						+ "`Price` VARCHAR(45) NULL,"
						+ "`SellerId` VARCHAR(45) NULL,"
						+"`Category` VARCHAR(45) NULL,"
						+ "PRIMARY KEY (`BookID`))COMMENT = 'Table to contain details of Book';";
				statement.executeUpdate(createBookTableQuery);
				String createInventoryTableQuery="CREATE TABLE IF NOT EXISTS `book_store`.`inventory` "
						+ "(`BookID` VARCHAR(45) NOT NULL,"
						+ "`NumberOfBooks` INT NULL,`CategoryID` VARCHAR(45) NULL,"
						+ "PRIMARY KEY (`BookID`),INDEX `category_id_idx` (`CategoryID` ASC),"
						+ "CONSTRAINT `category_id` FOREIGN KEY (`CategoryID`) REFERENCES `book_store`.`category` (`CategoryId`) "
						+ "ON DELETE CASCADE ON UPDATE CASCADE);";
				statement.executeUpdate(createInventoryTableQuery);
				String createCustomerTableQuery="CREATE TABLE IF NOT EXISTS `book_store`.`customer` "
						+ "(`CustomerID` VARCHAR(45) NOT NULL,"
						+ "`FirstName` VARCHAR(45) NULL,"
						+ "`LastName` VARCHAR(45) NULL,"
						+ "`DOB` DATE NULL,"
						+ "`EmailID` VARCHAR(45) NULL,"
						+ "`Address` VARCHAR(45) NULL,"
						+ "`PhoneNumber` VARCHAR(45) NULL,"
						+ "`UserName` VARCHAR(45) NULL,"
						+ "`Password` VARCHAR(45) NULL,"
						+ "PRIMARY KEY (`CustomerID`));";
				//System.out.println(createCustomerTableQuery);
				statement.executeUpdate(createCustomerTableQuery);
				String createOrderTableQuery="CREATE TABLE IF NOT EXISTS `book_store`.`order` "
						+ "(`orderID` VARCHAR(45) NOT NULL,"
						+ "`quantity` VARCHAR(45) NULL,"
						+ "`BookID` VARCHAR(45) NULL,"
						+ "`OrderPlacedDate` DATE NULL,"
						+ "`customerID` VARCHAR(45) NULL,"
						+ "`price` INT NULL,`orderStatus` VARCHAR(45) NULL,"
						+ "PRIMARY KEY (`orderID`),INDEX `bookid_order_idx` (`BookID` ASC),"
						+ "CONSTRAINT `bookid_order`FOREIGN KEY (`BookID`)REFERENCES `book_store`.`book` (`BookID`)ON DELETE CASCADE ON UPDATE CASCADE);";
				statement.executeUpdate(createOrderTableQuery);
				String createTableIncident = "CREATE TABLE IF NOT EXISTS book_store.incident( "+
		      			"IncidentID VARCHAR(45)  PRIMARY KEY,"+
		      			"CustomerID VARCHAR(45),"+
		      			"EmployeeID VARCHAR(45),"+
		      			"Status VARCHAR(20),"+
		      			"Description VARCHAR(200),"+
		      			//"Comment VARCHAR (200)," +
		      			"CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
				statement.executeUpdate(createTableIncident);
				String createTableWishlist = "CREATE TABLE IF NOT EXISTS `book_store`.`wishlist` ( "
						 + "`CustomerID` VARCHAR(45) NOT NULL, "
						 + "`BookID` VARCHAR(45) NOT NULL, "
						 + "PRIMARY KEY (`CustomerID`,`BookID`), "
						 + "CONSTRAINT `CustomerID` "
						  +  "FOREIGN KEY (`CustomerID`) "
						   + "REFERENCES `book_store`.`customer` (`CustomerID`) "
						   + "ON DELETE NO ACTION "
						   + "ON UPDATE NO ACTION);";
				System.out.println(createTableWishlist);
				statement.executeUpdate(createTableWishlist);
									
				String createTableReviewRating = "CREATE TABLE IF NOT EXISTS `book_store`.`reviewandrating` ("
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
				System.out.println(createTableReviewRating);
				statement.executeUpdate(createTableReviewRating);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
