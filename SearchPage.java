package com.cs521.team3.ui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.cs521.team3.dbconnection.MakeConnection;
import com.cs521.team3.model.Billing;
import com.cs521.team3.model.BookDetails;
import com.cs521.team3.model.Order;
import com.cs521.team3.model.SearchTableModel;


public class SearchPage extends JFrame {
	
	Connection connection;
	JTable table; // The table for displaying data
	
	private JScrollPane scrollPane;
	private JPanel contentPane;
	public JFrame frmSearchBooks;
	private JTextField txtBookTitle;
	private JTextField txtBookPrice;
	private JTextField txtAuthor;
	private JTextField txtCategory;
	
	JButton btnSearch;
	JButton btnPlaceOrder;
	
	private MakeConnection makeConnection;
	private JScrollPane scrollPane_1;
	private JTable table_1;
	private SearchTableModel model;
	public static String orderId;
	

	public static void NewSearchPage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchPage window = new SearchPage();
					window.frmSearchBooks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public SearchPage(){
		
		try {
			System.out.println("Inside searchpage constructor");
			makeConnection = new MakeConnection();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE); 
		}
		
		initialize();
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("In actionlistener");
					//String sqlQuery = "SELECT * FROM BOOK_STORE.BOOK";
					String bookTitle = txtBookTitle.getText();
					String bookAuthor = txtAuthor.getText();
					String bookPrice = txtBookPrice.getText();
					String bookCat = txtCategory.getText();
					System.out.println("before if statement");
					List<BookDetails> bookDetails = null;
					
					if(bookTitle.length()==0 && bookAuthor.length()==0 && bookPrice.length()==0&& bookCat.length()==0)
					{
						System.out.println("In if: Where all books are searched!");
						bookDetails = makeConnection.getAllBooks();
						
						
					}
					else
					{
						System.out.println("in else ");
						bookDetails = makeConnection.searchBooks(bookTitle,bookAuthor,bookPrice,bookCat);
					}
					
					System.out.println("book details before displaying the model" + bookDetails);
					
					model = new SearchTableModel(bookDetails);	
					table_1.setModel(model);
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(SearchPage.this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		btnPlaceOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PlaceOrderUI placeOrder;
				try {
					List<Order> orders = createOrderList(table_1.getSelectedRows());
					List<Billing> billing = createBillingList(table_1.getSelectedRows());
					System.out.println("Size of order list"+orders.size());
					System.out.println("Size of billing list"+billing.size());
					placeOrder = new PlaceOrderUI();
					placeOrder.insertRowIntoOrderJTable(orders, billing);
					placeOrder.setSize(500,500);
					placeOrder.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
	
	
	public List<Order> createOrderList(int[] rowIndex){
		List<Order> orderItems = new ArrayList<Order>();
		
		for(int i:rowIndex){
			Order order = new Order();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			order.setBookID(model.getValueAt(i,0).toString());
			order.setQuantity(1);
			int num = (int)Math.ceil(Math.random() * 100);
			order.setOrderID("ORD"+num+ i);
			orderId = "ORD"+num+ i;
			order.setOrderedTimeStamp(dateFormat.format(date));
			order.setOrderStatus("Processing");
			order.setCustomerID("GUEST");
			order.setTotalAmount(2000.00);
			orderItems.add(order);
		}
		return orderItems;
	}
	public List<Billing> createBillingList(int[] rowIndex){
		List<Billing> billingItems = new ArrayList<Billing>();
		for(int i:rowIndex){
			Billing billing = new Billing();
			billing.setOrderID(orderId);
			billing.setInvoiceNumber("A100" + i);
			billing.setBillAmount(2000.00);
			billingItems.add(billing);
		}
		return billingItems;
	}
	
	/*
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSearchBooks = new JFrame();
		frmSearchBooks.setTitle("Search Books");
		frmSearchBooks.setBounds(100, 100, 801, 893);
		//frmSearchBooks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSearchBooks.getContentPane().setLayout(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblSearchForBooks = new JLabel("Search For Books");
		lblSearchForBooks.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblSearchForBooks.setBounds(42, 26, 225, 29);
		frmSearchBooks.getContentPane().add(lblSearchForBooks);
		
		JLabel lblSearchByFilter = new JLabel("Search By Filter");
		lblSearchByFilter.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblSearchByFilter.setBounds(42, 117, 135, 16);
		frmSearchBooks.getContentPane().add(lblSearchByFilter);
		
		JLabel lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setBounds(78, 166, 56, 16);
		frmSearchBooks.getContentPane().add(lblBookTitle);
		
		txtBookTitle = new JTextField();
		txtBookTitle.setBounds(175, 163, 116, 22);
		frmSearchBooks.getContentPane().add(txtBookTitle);
		txtBookTitle.setColumns(10);
		
		JLabel lblBookPrice = new JLabel("Book Price");
		lblBookPrice.setBounds(78, 214, 66, 16);
		frmSearchBooks.getContentPane().add(lblBookPrice);
		
		txtBookPrice = new JTextField();
		txtBookPrice.setBounds(175, 211, 116, 22);
		frmSearchBooks.getContentPane().add(txtBookPrice);
		txtBookPrice.setColumns(10);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(78, 262, 56, 16);
		frmSearchBooks.getContentPane().add(lblAuthor);
		
		txtAuthor = new JTextField();
		txtAuthor.setBounds(175, 259, 116, 22);
		frmSearchBooks.getContentPane().add(txtAuthor);
		txtAuthor.setColumns(10);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(78, 310, 56, 16);
		frmSearchBooks.getContentPane().add(lblCategory);
		
		txtCategory = new JTextField();
		txtCategory.setBounds(175, 307, 116, 22);
		frmSearchBooks.getContentPane().add(txtCategory);
		txtCategory.setColumns(10);
		
		btnSearch = new JButton("Search");
		
		btnSearch.setBounds(363, 237, 97, 25);
		frmSearchBooks.getContentPane().add(btnSearch);
		
		btnPlaceOrder = new JButton("Place Order");
		
		btnPlaceOrder.setBounds(480, 237, 120, 25);
		frmSearchBooks.getContentPane().add(btnPlaceOrder);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(37, 385, 705, 448);
		frmSearchBooks.getContentPane().add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		//contentPane.add(btnSearch);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

	
	


	
	
	
		 
       

	
	
	
}
