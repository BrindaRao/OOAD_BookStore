package com.cs521.team3.ui;
import java.awt.EventQueue;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.cs521.team3.dbconnection.MakeConnection;


public class TrackOrderFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textTrackOrder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackOrderFrame frame = new TrackOrderFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String trackOrder(Connection conn,String orderID) {
		// TODO Auto-generated method stub
		Statement statement;
		String orderStatus= null;
		try {
			statement = conn.createStatement();
		    String sql  = "select orderStatus from book_store.order WHERE orderID = '"+orderID+"';";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				orderStatus= rs.getString(1);
				System.out.println("the status is "+orderStatus);
				break;
			}
			
			return orderStatus;
		}catch(SQLException e){
			e.printStackTrace();
		 System.out.println("SQL Exception "+e.getMessage());
		}
		return orderStatus;
	}

	/**
	 * Create the frame.
	 */
	public TrackOrderFrame() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Track Order");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn = MakeConnection.getConnection("book_store");
				//MainClass mainClass = new MainClass();
				String orderStatus = trackOrder(conn, textTrackOrder.getText().trim());
				JOptionPane.showMessageDialog(TrackOrderFrame.this,
						new String[] { "Status of order:",
								"Order ID: [" + textTrackOrder.getText().trim() + "]",
								"Status: [" + orderStatus + "]" });

			}
		});
		btnNewButton.setBounds(246, 130, 167, 29);
		contentPane.add(btnNewButton);
		
		textTrackOrder = new JTextField();
		textTrackOrder.setBounds(129, 51, 284, 50);
		contentPane.add(textTrackOrder);
		textTrackOrder.setColumns(10);
		
		JLabel lblEnterOrderId = new JLabel("Enter Order ID");
		lblEnterOrderId.setBounds(7, 51, 117, 50);
		contentPane.add(lblEnterOrderId);
		
		
	}
}
