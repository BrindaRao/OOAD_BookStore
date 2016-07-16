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

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.cs521.team3.dbconnection.MakeConnection;
import com.cs521.team3.model.BookModel;
import com.cs521.team3.model.Inventory;
import com.cs521.team3.model.Order;
import com.cs521.team3.model.OrderModel;
import com.sun.rowset.CachedRowSetImpl;

public class OrderUI extends JFrame implements RowSetListener {
	OrderModel orderModel;
	Connection connection;
	CachedRowSet  orderRowSet;
	JTable table;
	
	JLabel labelOrderId;
	JLabel labelNumberOfBooks;
	JLabel labelBookId;

	JTextField textFieldBookId;
	JTextField textFieldNumberOfBooks;
	JTextField textFieldOrderId;

	JButton button_UPDATE_ORDER;
	JButton button_DELETE_ORDER;

	public OrderUI() throws SQLException{
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
		
		labelBookId = new JLabel();
		labelNumberOfBooks = new JLabel();
		labelOrderId = new JLabel();
		
		textFieldOrderId = new JTextField(10);
		textFieldNumberOfBooks = new JTextField(10);
		textFieldBookId = new JTextField(10);
		
		button_UPDATE_ORDER = new JButton();
		button_DELETE_ORDER = new JButton();
		
		labelBookId.setText("BookId:");
		labelNumberOfBooks.setText("Number of Books:");
		labelOrderId.setText("OrderId:");
		
		textFieldBookId.setText("Enter the book ID");
		textFieldNumberOfBooks.setText("Enter the number of books");
		textFieldOrderId.setText("Enter the order ID");
		
		button_UPDATE_ORDER.setText("Update Order");
		button_DELETE_ORDER.setText("Delete Order");
		
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
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(labelOrderId, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(textFieldOrderId, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(labelNumberOfBooks, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(textFieldNumberOfBooks, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(labelBookId, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(textFieldBookId, c);


		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_UPDATE_ORDER, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_DELETE_ORDER, c);


		button_UPDATE_ORDER.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(OrderUI.this,
							new String[] { "Adding the following row:",
									"Book Id: [" + textFieldBookId.getText() + "]",
									"Number of Books: [" + textFieldNumberOfBooks.getText() + "]",
									"Order Id: [" + textFieldOrderId.getText() + "]"});
					orderModel.orderRowSet.acceptChanges();
					// Update customer table as well
					// Assuming customer id is 10000082
					// This section can be improvised to bring dynamic customer id.
					Order order = new Order();
					order.setBookID(textFieldBookId.getText().trim());
					order.setQuantity(Integer.parseInt(textFieldNumberOfBooks.getText().trim()));
					order.setOrderID(textFieldOrderId.getText().trim());
					updateOrderTable(order);
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

		button_DELETE_ORDER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() != -1){
						Order order = new Order();
						order.setOrderID(orderModel.getValueAt(table.getSelectedRow(), 0).toString());
						order.setQuantity(Integer.parseInt(orderModel.getValueAt(table.getSelectedRow(), 1).toString()));
						order.setBookID(orderModel.getValueAt(table.getSelectedRow(), 2).toString());
						deleteFromOrderTable(order);
						createNewTableModel();
					}
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
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

			// Regardless of the query, fetch the contents of Reservation

			crs.setCommand("select orderID,quantity,BookID from book_store.order");
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
	private void createNewTableModel() throws SQLException {
		orderModel = new OrderModel(getContentsOfOrderTable());
		orderModel.addEventHandlersToRowSet(this);
		table.setModel(orderModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(OrderUI.this,
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

			JOptionPane.showMessageDialog(OrderUI.this, new String[] {
					ex.getClass().getName() + ": ", ex.getMessage() });
		}
	}

	@Override
	public void cursorMoved(RowSetEvent event) {

	}
	public void updateOrderTable(Order order){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String updateQuery = "UPDATE book_store.order SET quantity="+order.getQuantity()+
					",BookID='"+order.getBookID()+"' WHERE orderID='"+order.getOrderID()+"'";
			System.out.println(updateQuery);
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
	public void deleteFromOrderTable(Order order){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String deleteQuery = "DELETE FROM book_store.order WHERE orderID='"+order.getOrderID()+"'";
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
}
