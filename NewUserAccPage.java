package com.cs521.team3.ui;	
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.cs521.team3.dbconnection.MakeConnection;
import com.cs521.team3.model.CustomerDetails;


public class NewUserAccPage {

	private JFrame frmCustomerRegistrationForm;
	private JTextField txtFName;
	private JTextField txtLname;
	private JTextField txtDOB;
	private JTextField txtPhNumber;
	private JTextField txtEmailID;
	private JTextField txtUserName;
	private JTextField txtPassword;
	private JTextField txtAddress;

	/**
	 * Launch the application.
	 */
	public static void NewAccountCreation() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewUserAccPage window = new NewUserAccPage();
					window.frmCustomerRegistrationForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NewUserAccPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCustomerRegistrationForm = new JFrame();
		frmCustomerRegistrationForm.setTitle("Customer Registration Form");
		frmCustomerRegistrationForm.setBounds(100, 100, 491, 513);
		frmCustomerRegistrationForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCustomerRegistrationForm.getContentPane().setLayout(null);
		
		JLabel lblCustomerRegistrationForm = new JLabel("Customer Registration Form");
		lblCustomerRegistrationForm.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblCustomerRegistrationForm.setBounds(33, 13, 279, 36);
		frmCustomerRegistrationForm.getContentPane().add(lblCustomerRegistrationForm);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(33, 62, 70, 16);
		frmCustomerRegistrationForm.getContentPane().add(lblFirstName);
		
		txtFName = new JTextField();
		txtFName.setBounds(137, 59, 116, 22);
		frmCustomerRegistrationForm.getContentPane().add(txtFName);
		txtFName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(33, 91, 70, 16);
		frmCustomerRegistrationForm.getContentPane().add(lblLastName);
		
		txtLname = new JTextField();
		txtLname.setBounds(137, 88, 116, 22);
		frmCustomerRegistrationForm.getContentPane().add(txtLname);
		txtLname.setColumns(10);
		
		JLabel lblDob = new JLabel("DOB");
		lblDob.setBounds(33, 128, 56, 16);
		frmCustomerRegistrationForm.getContentPane().add(lblDob);
		
		txtDOB = new JTextField();
		txtDOB.setBounds(137, 123, 116, 22);
		frmCustomerRegistrationForm.getContentPane().add(txtDOB);
		txtDOB.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(33, 157, 56, 16);
		frmCustomerRegistrationForm.getContentPane().add(lblAddress);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(33, 205, 91, 16);
		frmCustomerRegistrationForm.getContentPane().add(lblPhoneNumber);
		
		txtPhNumber = new JTextField();
		txtPhNumber.setBounds(137, 202, 116, 22);
		frmCustomerRegistrationForm.getContentPane().add(txtPhNumber);
		txtPhNumber.setColumns(10);
		
		JLabel lblEmailId = new JLabel("Email ID");
		lblEmailId.setBounds(33, 248, 56, 16);
		frmCustomerRegistrationForm.getContentPane().add(lblEmailId);
		
		txtEmailID = new JTextField();
		txtEmailID.setBounds(137, 245, 116, 22);
		frmCustomerRegistrationForm.getContentPane().add(txtEmailID);
		txtEmailID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setBounds(33, 298, 70, 16);
		frmCustomerRegistrationForm.getContentPane().add(lblNewLabel);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(137, 298, 116, 22);
		frmCustomerRegistrationForm.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(33, 341, 56, 16);
		frmCustomerRegistrationForm.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(137, 333, 116, 22);
		frmCustomerRegistrationForm.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnCreateProfile = new JButton("Create Profile");
		btnCreateProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String FName = txtFName.getText();
			    String LName = txtLname.getText();
			    String DOB = txtDOB.getText();
			    String phno = txtPhNumber.getText();
			    String email = txtEmailID.getText();
			    String address = txtAddress.getText();
			    String Uname = txtUserName.getText();
			    String Pwd = txtPassword.getText();
			    System.out.println(FName + "," + LName + "," + DOB + ","+phno+","+email+","+address+","+Uname+","+Pwd);
			    CustomerDetails custObj = new CustomerDetails();
			    custObj.setAddress(address);
			    custObj.setDOB(DOB);
			    custObj.setEmailID(email);
			    custObj.setFirstName(FName);
			    custObj.setLastName(LName);
			    custObj.setPassword(Pwd);
			    custObj.setUserName(Uname);
			    custObj.setPhoneNumber(phno);
				if(createCustomerAcc(custObj))
				{
					LoginPage window = new LoginPage();
					frmCustomerRegistrationForm.setVisible(false);
					window.frmBookStore.setVisible(true);
				}
			}
		});
		btnCreateProfile.setBounds(316, 404, 122, 25);
		frmCustomerRegistrationForm.getContentPane().add(btnCreateProfile);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginPage window = new LoginPage();
				window.frmBookStore.setVisible(true);
			}
		});
		btnCancel.setBounds(22, 404, 97, 25);
		frmCustomerRegistrationForm.getContentPane().add(btnCancel);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(137, 158, 116, 22);
		frmCustomerRegistrationForm.getContentPane().add(txtAddress);
		txtAddress.setColumns(10);
	}
	
	public boolean createCustomerAcc(CustomerDetails custObj)
	{
		Random rand = new Random();
		int HighNo = 10000;
		int LowNo = 1;
		
		Connection conn = null;
		boolean result = false;
		String displayresult = "CustomerDetails were not added!";
		try
		{
			System.out.println("Inside method for validating");
			conn = MakeConnection.getConnection("BOOK_STORE");
			System.out.println("After makeconnection");
			String insertIntoCustomerQuery = "INSERT INTO BOOK_STORE.CUSTOMER(CustomerID,FirstName,LastName,DOB,EmailID,Address,PhoneNumber,UserName,Password) "
					+ "VALUES (?,?,?,?,?,?,?,?,?);";
			java.sql.Date sqlDate = null;
			try {
				System.out.println("Inside Try block before prepare");
				PreparedStatement pst = conn.prepareStatement(insertIntoCustomerQuery);
				System.out.println("After prep statement");
				//System.out.println(rand.nextInt());
				pst.setString(1, String.valueOf(rand.nextInt(HighNo - LowNo)+ LowNo));
				pst.setString(2, custObj.getFirstName());
				pst.setString(3, custObj.getLastName());
				
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
				try {
					System.out.println("Inside 2nd try");
					date = format.parse(custObj.getDOB());
					System.out.println("Date value" + date);
					sqlDate = new java.sql.Date(date.getTime());
					System.out.println("Date after parse" + sqlDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				pst.setDate(4, sqlDate);
				
				pst.setString(5, custObj.getEmailID());
				pst.setString(6, custObj.getAddress());
				pst.setString(7, custObj.getPhoneNumber());
				pst.setString(8, custObj.getUserName());
				pst.setString(9, custObj.getPassword());
				int count = pst.executeUpdate();
				System.out.println("count values " + count);
				if(count>0)
				{
					displayresult = "Customer Details Added!";
					result = true;
				}
				
				
		        conn.close();
		       

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		finally
		{
			
			JOptionPane.showMessageDialog(null,displayresult);
		}
		return result;
	}
}
