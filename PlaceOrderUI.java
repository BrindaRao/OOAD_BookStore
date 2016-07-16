package com.cs521.team3.ui;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.spi.SyncProviderException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.cs521.team3.dbconnection.MakeConnection;
import com.cs521.team3.model.Billing;
import com.cs521.team3.model.Order;
import com.cs521.team3.model.OrderModel;
import com.sun.rowset.CachedRowSetImpl;

public class PlaceOrderUI extends JFrame implements RowSetListener {
	
	public OrderModel orderModel;
	Connection connection;
	CachedRowSet  orderRowSet;
	JTable table;
	
	JLabel labelNumberOfBooks;
	JLabel labelBookId;
	JTextField textFieldNumberOfBooks;
	JTextField textFieldBookId;

	JButton button_SUBMIT_ORDER;
	JButton button_CHECKOUT_ORDER;
	public static int billAmount = 0;
	
	int i =0;

	public PlaceOrderUI() throws SQLException{
		connection = MakeConnection.getConnection("book_store");
		// event listen when window is closed
				/*addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.out.println("Window is closed..");
						try {
							connection.close();
						} catch (SQLException ex) {
							printSQLException(ex);
						}
						System.exit(0);
					}
					
				});*/
		orderRowSet = getContentsOfOrderTable();
		orderModel = new OrderModel(orderRowSet);
		orderModel.addEventHandlersToRowSet(this);
		
		table = new JTable();
		
		table.setModel(orderModel);
		
		//labelBookId = new JLabel();
		labelNumberOfBooks = new JLabel();
		
		textFieldNumberOfBooks = new JTextField(10);
		//textFieldBookId = new JTextField(10);
		
		button_SUBMIT_ORDER = new JButton();
		button_CHECKOUT_ORDER = new JButton();
		
		//labelBookId.setText("BookId:");
		labelNumberOfBooks.setText("Number of Books:");
		
		//textFieldBookId.setText("Enter the book ID");
		textFieldNumberOfBooks.setText("Enter the number of books");
		
		button_SUBMIT_ORDER.setText("Update Quantity");
		button_CHECKOUT_ORDER.setText("Checkout");
		
		Container contentPane = getContentPane();
		contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		contentPane.add(new JScrollPane(table), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(labelNumberOfBooks, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(textFieldNumberOfBooks, c);

		/*c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(labelBookId, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(textFieldBookId, c);
*/

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_SUBMIT_ORDER, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_CHECKOUT_ORDER, c);


		button_SUBMIT_ORDER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() != -1){
						int quantity = Integer.parseInt(textFieldNumberOfBooks.getText().trim());
						String orderID = orderModel.getValueAt(table.getSelectedRow(), 0).toString();
						updateOrderTable(quantity,orderID);
					}else{
						JOptionPane.showMessageDialog(PlaceOrderUI.this,"Please select a row to update");
					}
					createNewTableModel();
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
					// Now revert back changes
					try {
						createNewTableModel();
					} catch (SQLException sqle2) {
						displaySQLExceptionDialog(sqle2);
					}
				}
			}

			
		});

		button_CHECKOUT_ORDER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//try {
					if (table.getSelectedRow() != -1){
						/*Billing billing = new Billing();
						
						JOptionPane.showMessageDialog(PlaceOrderUI.this,
								new String[] { "You have placed the following order:",
										"Order Id: [" + "ORD" +i + "]",
										"Total Amount: [" + billing.getBillAmount() + "]"});
						orderModel.orderRowSet.acceptChanges();*/
						for(int j:table.getSelectedRows()){
							int quantity = Integer.parseInt(orderModel.getValueAt(j, 1).toString()); 
							int singlePrice = Integer.parseInt(orderModel.getValueAt(j, 5).toString());
							billAmount += quantity * singlePrice;
						}
						PaymentFrame.loadPaymentUI();
					}
				/*} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}*/
			}
		});
		
	}
	private CachedRowSet getContentsOfOrderTable() {
		// write the code to fetch the records from category table
		// setting for scroll option
		CachedRowSet crs = null;
		try {
			connection = MakeConnection.getConnection("book_store");
			crs = new CachedRowSetImpl();
			crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
			crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
			crs.setUsername("root");
			crs.setPassword("beegees,9030");

			// In MySQL, to disable auto-commit, set the property
			// relaxAutoCommit to
			// true in the connection URL.

			crs.setUrl("jdbc:mysql://localhost:3306/book_store?relaxAutoCommit=true");

			// Regardless of the query, fetch the contents of Order

			String query = "select orderID,quantity,BookID,OrderPlacedDate,customerID,price,orderStatus from book_store.order where customerID =(select customerID from customer where UserName='"+LoginPage.userName+"')";
			System.out.println(query);
			crs.setCommand(query);
			crs.execute();

		} catch (SQLException e) {
			printSQLException(e);
		}
		return crs;
	}
	private void printSQLException(SQLException ex) {
		// TODO Auto-generated method stub
		for (Throwable e : ex) {
		      if (e instanceof SQLException) {
		        if (ignoreSQLException(((SQLException)e).getSQLState()) == false) {
		          e.printStackTrace(System.err);
		          System.err.println("SQLState: " + ((SQLException)e).getSQLState());
		          System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
		          System.err.println("Message: " + e.getMessage());
		          Throwable t = ex.getCause();
		          while (t != null) {
		            System.out.println("Cause: " + t);
		            t = t.getCause();
		          }
		        }
		      }
		    }
	}

	private static boolean ignoreSQLException(String sqlState) {
	    if (sqlState == null) {
	      System.out.println("The SQL state is not defined!");
	      return false;
	    }
	    // X0Y32: Jar file already exists in schema
	    if (sqlState.equalsIgnoreCase("X0Y32"))
	      return true;
	    // 42Y55: Table already exists in schema
	    if (sqlState.equalsIgnoreCase("42Y55"))
	      return true;
	    return false;
	  }
	public void createNewTableModel() throws SQLException {
		orderModel = new OrderModel(getContentsOfOrderTable());
		orderModel.addEventHandlersToRowSet(this);
		table.setModel(orderModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(PlaceOrderUI.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}
	@Override
	public void rowSetChanged(RowSetEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rowChanged(RowSetEvent event) {

		CachedRowSet currentRowSet = this.orderModel.orderRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			orderModel = new OrderModel(orderModel.getOrderRowSet());
			table.setModel(orderModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(PlaceOrderUI.this, new String[] {
					ex.getClass().getName() + ": ", ex.getMessage() });
		}
	}

	@Override
	public void cursorMoved(RowSetEvent event) {

	}
	public void insertOrderTable(Order order){
		Connection conn = MakeConnection.getConnection("book_store");
		
		try {
			Statement statement = conn.createStatement();
			ResultSet rst = statement.executeQuery("Select customerID from customer where userName='"+LoginPage.userName+"'");
			while(rst.next()){
				String customerID = rst.getString(1);
				order.setCustomerID(customerID);
			}
			String insertQuery = "INSERT INTO book_store.order VALUES('"+order.getOrderID()+"',"+order.getQuantity()+
					",'"+order.getBookID()+"'"+
					",'"+order.getOrderedTimeStamp()+
					"','"+order.getCustomerID()+
					"',"+order.getTotalAmount()+
					",'"+order.getOrderStatus()+"')";
			System.out.println(insertQuery);
			statement.executeUpdate(insertQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void insertBillingTable(Billing billing){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String insertQuery = "INSERT INTO book_store.billing VALUES('"+billing.getOrderID()+
					"','"+billing.getInvoiceNumber()+
					"',"+billing.getBillAmount()+")";
			System.out.println(insertQuery);
			statement.executeUpdate(insertQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void insertRowIntoOrderJTable(List<Order> orderList,List<Billing> billingList){
		for(Order order:orderList){
			insertOrderTable(order);
		}
		for(Billing billing:billingList){
			insertBillingTable(billing);
		}
		try {
			orderModel.orderRowSet.acceptChanges();
			createNewTableModel();
		} catch (SyncProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateOrderTable(int quantity,String orderID){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String updateQuery = "UPDATE book_store.order SET quantity="+quantity+" WHERE orderID='"+orderID+"'";
			statement.executeUpdate(updateQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
