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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.cs521.team3.dbconnection.MakeConnection;
import com.cs521.team3.model.EmailOrder;
import com.cs521.team3.model.Order;


public class PaymentFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textCardNumber;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField textpasswordField;

	/**
	 * Launch the application.
	 */
	public static void loadPaymentUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentFrame frame = new PaymentFrame();
					frame.setSize(500,500);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PaymentFrame() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTotalAmount = new JLabel("Total Amount");
		lblTotalAmount.setBounds(10, 10, 161, 50);
		contentPane.add(lblTotalAmount);
		
		JLabel lblTotalPrice = new JLabel(""+PlaceOrderUI.billAmount);
		lblTotalPrice.setBounds(267, 10, 146, 50);
		contentPane.add(lblTotalPrice);
		
		JLabel lblPayment = new JLabel("Payment");
		lblPayment.setBounds(10, 50, 200, 50);
		contentPane.add(lblPayment);
		
		JLabel lblCreditdebitCardNo = new JLabel("Credit/Debit Card NO.");
		lblCreditdebitCardNo.setBounds(10, 93, 178, 31);
		contentPane.add(lblCreditdebitCardNo);
		
		textCardNumber = new JTextField();
		textCardNumber.setBounds(203, 93, 228, 31);
		contentPane.add(textCardNumber);
		textCardNumber.setColumns(10);
		
		JLabel lblCvv = new JLabel("CVV");
		lblCvv.setBounds(10, 140, 150, 31);
		contentPane.add(lblCvv);
		
		textpasswordField = new JPasswordField();
		textpasswordField.setBounds(202, 140, 61, 26);
		contentPane.add(textpasswordField);
		
		JButton btnConfirmPayment = new JButton("Confirm Payment");
		btnConfirmPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cardNum = textCardNumber.getText().trim();
				String cvv = textpasswordField.getText().trim();
				String regex = "[0-9]+";
				if(cardNum.matches(regex) && cvv.matches(regex) && cardNum.length()==16 && cvv.length()==3)
				{
				
				JOptionPane.showMessageDialog(PaymentFrame.this,
						new String[] { "Payment Sucessful" });
				Connection connection = MakeConnection.getConnection("book_store");
				String email= getEmail(connection,LoginPage.userName,LoginPage.password);
				EmailOrder emailOrder = new EmailOrder();
				Order order = new Order();
				EmailOrder.sendEmail(order, email, LoginPage.userName);

				java.awt.Window win[] = java.awt.Window.getWindows(); 
				for(int i=0;i<win.length;i++){ 
					win[i].dispose(); 
					} 
					BookStoreApp.loadUI();
				}
				
				else
				{
					JOptionPane.showMessageDialog(PaymentFrame.this,
							new String[] { "Please enter the correct card Details" });
				}
			}
		});
		btnConfirmPayment.setBounds(199, 187, 186, 29);
		contentPane.add(btnConfirmPayment);
		
				
	
	}
	public static String getEmail(Connection conn,String username, String password) {
		// TODO Auto-generated method stub
		Statement statement;
		String orderStatus= null;
		try {
			statement = conn.createStatement();
		    String sql  = "select emailid from customer where username = '"+username+"' and password ='"+password+"';";
		    
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				orderStatus= rs.getString(1);
				System.out.println("the email is "+orderStatus);
				break;
			}
			
			return orderStatus;
		}catch(SQLException e){
			e.printStackTrace();
		 System.out.println("SQL Exception "+e.getMessage());
		}
		return orderStatus;
	}

}
