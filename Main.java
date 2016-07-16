package com.cs521.team3.ui;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cs521.team3.dbconnection.MakeConnection;
import com.cs521.team3.model.Billing;
import com.cs521.team3.model.Checkout;
import com.cs521.team3.model.Order;


public class Main {
	
	public static void main(String args[]) {

		Connection conn = null;
		conn = MakeConnection.getDafaultConnection();
		if (conn != null) {
			Main main = new Main();
			// create database and tablespace
			main.createTablespace(conn);
			conn = MakeConnection.getConnection("book_store");
			// create tables
			// if you don't want to create users again don't uncomment createUsers
			// function and grantPermission function.
			main.createTables(conn);
			// mainClass.createUsers(conn);
			// create users
			// grant permissions
			// mainClass.grantPermission(conn);
			main.insertValuesToTables(conn);
			// insert into tables
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						PlaceOrderUI window = new PlaceOrderUI();
						window.setSize(500,500);
						window.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			System.out.println("Driver is not connected");
		}
	}
	
	public void createTablespace(Connection conn) {

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
	}

	public void createTables(Connection conn) {
		Statement statement = null;
		try {
			statement = conn.createStatement();
						
			String createTableBillingQuery = "CREATE TABLE IF NOT EXISTS `book_store`.`billing`"
					+ "(`orderID` VARCHAR(10) NOT NULL,`invoiceNumber` VARCHAR(20) NOT NULL,"
					+ "`billAmount` DECIMAL(6,2) NOT NULL,"
					+ "PRIMARY KEY (`orderID`),CONSTRAINT `orderBillingFK` FOREIGN KEY (`orderID`) REFERENCES `book_store`.`order` (`orderID`) ON DELETE CASCADE ON UPDATE CASCADE);";
			System.out.println(createTableBillingQuery);
			statement.executeUpdate(createTableBillingQuery);
			
			String createTableCheckoutQuery = "CREATE TABLE IF NOT EXISTS `book_store`.`checkout`"
					+ " (`orderID` VARCHAR(45) NOT NULL, `typeOfShipping` VARCHAR(45), "
					+ "`email` VARCHAR(40), `phoneNumber` VARCHAR(15) NOT NULL, "
					+ "`address1` VARCHAR(40), `address2` VARCHAR(40), `city` VARCHAR(40), `state` VARCHAR(20),"
					+ " `zipcode` CHAR(5), `creditCardType` VARCHAR(20), `ccNumber` VARCHAR(16),"
					+ " `expirationDate` DATE, `cvv` VARCHAR(3), " 
					+ "CONSTRAINT `orderCheckoutFK` FOREIGN KEY (`orderID`) REFERENCES `book_store`.`order` (`orderID`) ON DELETE CASCADE ON UPDATE CASCADE);";

			System.out.println(createTableCheckoutQuery);
			statement.executeUpdate(createTableCheckoutQuery);

			String createTableOrderQuery="CREATE TABLE IF NOT EXISTS `order` "
					+ "(`orderID` VARCHAR(45) NOT NULL,"
					+ "`quantity` VARCHAR(45) NULL,"
					+ "`BookID` VARCHAR(45) NULL,"
					+ "`OrderPlacedDate` DATE NULL,"
					+ "`customerID` VARCHAR(45) NULL,"
					+ "`price` INT NULL,`orderStatus` VARCHAR(45) NULL,"
					+ "PRIMARY KEY (`orderID`),INDEX `bookid_order_idx` (`BookID` ASC),"
					+ "CONSTRAINT `bookid_order`FOREIGN KEY (`BookID`)REFERENCES `book_store`.`book` (`BookID`)ON DELETE CASCADE ON UPDATE CASCADE);";

			System.out.println(createTableOrderQuery);
			statement.executeUpdate(createTableOrderQuery);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// Function to create Billing objects and return list of those objects

		private List<Billing> getBillingObjects() {
			List<Billing> billingObjectList = new ArrayList<Billing>();

			Billing billingObject1 = new Billing();
			billingObject1.setOrderID("ORD1");
			billingObject1.setInvoiceNumber("A1001");
			billingObject1.setBillAmount(4500.00);

			Billing billingObject2 = new Billing();
			billingObject2.setOrderID("ORD2");
			billingObject2.setInvoiceNumber("A1002");
			billingObject2.setBillAmount(2000.60);

			Billing billingObject3 = new Billing();
			billingObject3.setOrderID("ORD3");
			billingObject3.setInvoiceNumber("A1003");
			billingObject3.setBillAmount(2013.00);

			Billing billingObject4 = new Billing();
			billingObject4.setOrderID("ORD4");
			billingObject4.setInvoiceNumber("A1004");
			billingObject4.setBillAmount(3015.67);

			billingObjectList.add(billingObject1);
			billingObjectList.add(billingObject2);
			billingObjectList.add(billingObject3);
			billingObjectList.add(billingObject4);

			return billingObjectList;
		}

		// Function to create Checkout objects and return list of those objects

		private List<Checkout> getCheckoutObjects() {
			List<Checkout> checkoutObjectList = new ArrayList<Checkout>();

			Checkout checkoutObject1 = new Checkout();
			checkoutObject1.setOrderID("ORD1");
			checkoutObject1.setAddress1("123 Example St");
			checkoutObject1.setAddress2("Apt# 123");
			checkoutObject1.setCity("Chicago");
			checkoutObject1.setState("IL");
			checkoutObject1.setZipcode("60616");
			checkoutObject1.setPhoneNumber("1234567890");
			checkoutObject1.setEmail("rob.mcd@gmail.com");
			checkoutObject1.setTypeOfShipping("Normal");
			checkoutObject1.setCreditCardType("Credit");
			checkoutObject1.setCreditCardNumber("1200456898651234");
			checkoutObject1.setExpirationDate("2019-05-14");
			checkoutObject1.setCVV(123);

			Checkout checkoutObject2 = new Checkout();
			checkoutObject2.setOrderID("ORD2");
			checkoutObject2.setAddress1("321 Example St");
			checkoutObject2.setAddress2("Apt# 321");
			checkoutObject2.setCity("San diago");
			checkoutObject2.setState("CA");
			checkoutObject2.setZipcode("54321");
			checkoutObject2.setPhoneNumber("3214567809");
			checkoutObject2.setEmail("robert.m@gmail.com");
			checkoutObject2.setTypeOfShipping("Normal");
			checkoutObject2.setCreditCardType("Credit");
			checkoutObject2.setCreditCardNumber("1200456898654568");
			checkoutObject2.setExpirationDate("2021-11-14");
			checkoutObject2.setCVV(321);
			
			Checkout checkoutObject3 = new Checkout();
			checkoutObject3.setOrderID("ORD5");
			checkoutObject3.setAddress1("456 Example St");
			checkoutObject3.setAddress2("Apt# 456");
			checkoutObject3.setCity("Springfield");
			checkoutObject3.setState("IL");
			checkoutObject3.setZipcode("62704");
			checkoutObject3.setPhoneNumber("3781245690");
			checkoutObject3.setEmail("pat.long43@gmail.com");
			checkoutObject3.setTypeOfShipping("Normal");
			checkoutObject3.setCreditCardType("Credit");
			checkoutObject3.setCreditCardNumber("1234456898651004");
			checkoutObject3.setExpirationDate("2025-05-04");
			checkoutObject3.setCVV(456);

			Checkout checkoutObject4 = new Checkout();
			checkoutObject4.setOrderID("ORD6");
			checkoutObject4.setAddress1("987 Example St");
			checkoutObject4.setAddress2("Apt# 987");
			checkoutObject4.setCity("Sacramento");
			checkoutObject4.setState("CA");
			checkoutObject4.setZipcode("12345");
			checkoutObject4.setPhoneNumber("4567123890");
			checkoutObject4.setEmail("mike.colins@gmail.com");
			checkoutObject4.setTypeOfShipping("Normal");
			checkoutObject4.setCreditCardType("Debit");
			checkoutObject4.setCreditCardNumber("1200456891234564");
			checkoutObject4.setExpirationDate("2017-01-21");
			checkoutObject4.setCVV(987);

			checkoutObjectList.add(checkoutObject1);
			checkoutObjectList.add(checkoutObject2);
			checkoutObjectList.add(checkoutObject3);
			checkoutObjectList.add(checkoutObject4);

			return checkoutObjectList;
		}

		private List<Order> getOrderObjects() {
			List<Order> orderObjectList = new ArrayList<Order>();

			Order orderObject1 = new Order();
			orderObject1.setOrderID("ORD1");
			orderObject1.setQuantity(12);
			orderObject1.setBookID("Book1");
			orderObject1.setOrderedTimeStamp("2016-06-26");
			orderObject1.setCustomerID("C0001");
			orderObject1.setTotalAmount(4500.00);
			orderObject1.setOrderStatus("Pending");

			Order orderObject2 = new Order();
			orderObject2.setOrderID("ORD2");
			orderObject2.setQuantity(1);
			orderObject2.setBookID("Book1");
			orderObject2.setOrderedTimeStamp("2016-06-27");
			orderObject2.setCustomerID("C0002");
			orderObject2.setTotalAmount(2000.60);
			orderObject2.setOrderStatus("Shipped");

			Order orderObject3 = new Order();
			orderObject3.setOrderID("ORD6");
			orderObject3.setQuantity(2);
			orderObject3.setBookID("Book1");
			orderObject3.setOrderedTimeStamp("2016-06-28");
			orderObject3.setCustomerID("C0003");
			orderObject3.setTotalAmount(2013.00);
			orderObject3.setOrderStatus("Shipped");
			
			Order orderObject4 = new Order();
			orderObject4.setOrderID("ORD5");
			orderObject4.setQuantity(1);
			orderObject4.setBookID("Book1");
			orderObject4.setOrderedTimeStamp("2016-06-26");
			orderObject4.setCustomerID("C0004");
			orderObject4.setTotalAmount(3015.67);
			orderObject4.setOrderStatus("Processed");
			
			orderObjectList.add(orderObject1);
			orderObjectList.add(orderObject2);
			orderObjectList.add(orderObject3);
			orderObjectList.add(orderObject4);

			return orderObjectList;

		}
		
		public void insertValuesToTables(Connection conn) {
			
			this.insertIntoBillingTable(conn);
			this.insertIntoCheckoutTable(conn);
			this.insertIntoOrderTable(conn);

		}

		private void insertIntoOrderTable(Connection conn) {
			// TODO Auto-generated method stub
			
			String insertIntoOrderTableQuery = "INSERT INTO `order` VALUES "
					+ "(?,?,?,?,?,?,?);";

			System.out.println("Size : " + this.getOrderObjects());
			PreparedStatement pst = null;
			for (Order orderObject : this.getOrderObjects()) {

				try {
					pst = conn.prepareStatement(insertIntoOrderTableQuery);

					pst.setString(1, orderObject.getOrderID());
					pst.setInt(2, orderObject.getQuantity());
					pst.setString(3, orderObject.getBookID());
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			        Date parsed = format.parse(orderObject.getOrderedTimeStamp());
			        java.sql.Date sql = new java.sql.Date(parsed.getTime());
					pst.setDate(4, sql);
					pst.setString(5, orderObject.getCustomerID());
					pst.setDouble(6, orderObject.getTotalAmount());
					pst.setString(7, orderObject.getOrderStatus());

					pst.execute();
					System.out.println("Done inserting");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void insertIntoCheckoutTable(Connection conn) {
			// TODO Auto-generated method stub
			
			String insertIntoCheckoutTableQuery = "INSERT INTO checkout VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?);";

			System.out.println("Size : " + this.getCheckoutObjects());
			PreparedStatement pst = null;
			for (Checkout checkoutObject : this.getCheckoutObjects()) {

				try {
					pst = conn.prepareStatement(insertIntoCheckoutTableQuery);

					System.out.println(checkoutObject.getOrderID());
					pst.setString(1, checkoutObject.getOrderID());
					pst.setString(2, checkoutObject.getTypeOfShipping());
					pst.setString(3, checkoutObject.getEmail());
					pst.setString(4, checkoutObject.getPhoneNumber());
					pst.setString(5, checkoutObject.getAddress1());
					pst.setString(6, checkoutObject.getAddress2());
					pst.setString(7, checkoutObject.getCity());
					pst.setString(8, checkoutObject.getState());
					pst.setString(9, checkoutObject.getZipcode());
					pst.setString(10, checkoutObject.getCreditCardType());
					pst.setString(11, checkoutObject.getCreditCardNumber());
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			        Date parsed = format.parse(checkoutObject.getExpirationDate());
			        java.sql.Date exp_date_sql = new java.sql.Date(parsed.getTime());
					pst.setDate(12, exp_date_sql);
					pst.setInt(13, checkoutObject.getCVV());

					pst.execute();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void insertIntoBillingTable(Connection conn) {
			// TODO Auto-generated method stub
			
			String insertIntoBillingTableQuery = "INSERT INTO billing VALUES "
					+ "(?,?,?);";

			System.out.println("Size : " + this.getBillingObjects());
			PreparedStatement pst = null;
			for (Billing billingObject : this.getBillingObjects()) {

				try {
					pst = conn.prepareStatement(insertIntoBillingTableQuery);

					pst.setString(1, billingObject.getOrderID());
					pst.setString(2, billingObject.getInvoiceNumber());
					pst.setDouble(3, billingObject.getBillAmount());

					pst.execute();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		


}
