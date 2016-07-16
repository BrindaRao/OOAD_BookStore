package com.cs521.team3.ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.cs521.team3.dbconnection.MakeConnection;
import com.cs521.team3.model.BookModel;
import com.cs521.team3.model.Category;
import com.cs521.team3.model.CustomerModel;
import com.cs521.team3.model.Inventory;
import com.cs521.team3.model.Order;
import com.cs521.team3.model.OrderModel;

public class AdminUI extends JFrame{

	Connection connection;
	JTable table; // The table for displaying data
	BookModel bookModel;
	
	CustomerModel customerModel;
	OrderModel orderModel;
	
	
	JFrame frame;
	private JTextField textField;
	private Button button_1;
	public static int i =0;
	
	private String[] quantity = {"1","2","3","4","5","6","7","8","9","10"};
	private JComboBox<String> comboBox;
	private TextField textField_1;
	private JButton button_6;
	private JButton button;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	
	
	/**
	 * Launch the application.
	 * @throws SQLException 
	 *//*
	public static void main(String[] args) throws SQLException {
		Connection conn = MakeConnection.getDafaultConnection();
		createTables(conn);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminUI window = new AdminUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	public AdminUI() {
		initialize();
	}
	public void insertIntoCategoryTable(Category category){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String insertQuery = "INSERT INTO CATEGORY VALUES( '"+category.getCategoryId()+"','"+category.getCategoryName()+"' )";
			System.out.println(insertQuery);
			statement.executeUpdate(insertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteFromCategoryTable(Category category){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String deleteQuery = "DELETE FROM CATEGORY WHERE CategoryName='"+category.getCategoryName()+"'";
			statement.executeUpdate(deleteQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void insertIntoInventoryTable(Inventory inv){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String insertQuery = "INSERT INTO INVENTORY VALUES('"+inv.getBookId()+"','"+inv.getNumberOfBooks()+"','"+inv.getCategoryId()+"')";
			System.out.println(insertQuery);
			statement.executeUpdate(insertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteFromInventoryTable(Inventory inv){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String deleteQuery = "DELETE FROM INVENTORY WHERE BookID='"+inv.getBookId()+"'";
			statement.executeUpdate(deleteQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteFromOrderTable(Order order){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String deleteQuery = "DELETE FROM ORDER WHERE orderID="+order.getOrderID();
			statement.executeUpdate(deleteQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLUE);
		frame.setBounds(100, 50, 500, 450);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		textField = new JTextField();
		textField.setBackground(new Color(192, 192, 192));
		textField.setForeground(Color.BLUE);
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 17));
		textField.setBounds(173, 11, 146, 50);
		textField.setText("ADMIN PAGE");
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		button_6 = new JButton("UPDATE CUSTOMER INFO");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerDetailsFormUI customerDetails;
				try {
					customerDetails = new CustomerDetailsFormUI();
					customerDetails.setSize(500,500);
					customerDetails.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button_6.setBounds(18, 307, 200, 58);
		frame.getContentPane().add(button_6);
		
		button = new JButton("CATEGORY");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				CategoryUI category = new CategoryUI();
				category.setSize(500, 500);
				category.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		button.setBounds(18, 110, 200, 50);
		frame.getContentPane().add(button);
		
		button_2 = new JButton("BOOK INVENTORY");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InventoryUI invUI;
				try {
					invUI = new InventoryUI();
					invUI.setSize(500,500);
					invUI.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_2.setBounds(18, 216, 200, 50);
		frame.getContentPane().add(button_2);
		
		button_3 = new JButton("EDIT ORDER");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderUI orderUI;
				try {
					orderUI = new OrderUI();
					orderUI.setSize(500,500);
					orderUI.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_3.setBounds(254, 216, 200, 50);
		frame.getContentPane().add(button_3);
		
		button_4 = new JButton("INCIDENT MANAGEMENT");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IncidentFrameTable incidentFrame;
				try {
					incidentFrame = new IncidentFrameTable();
					incidentFrame.setSize(500,500);
					incidentFrame.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_4.setBounds(254, 110, 200, 50);
		frame.getContentPane().add(button_4);
		
		
	}
}