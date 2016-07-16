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
import com.cs521.team3.model.Customer;
import com.cs521.team3.model.CustomerModel;
import com.sun.rowset.CachedRowSetImpl;

public class CustomerDetailsFormUI extends JFrame implements RowSetListener {

	CustomerModel customerModel;
	Connection connection;
	CachedRowSet  customerRowSet;
	JTable table;
	
	JLabel labelFirstName;
	JLabel labelLastName;
	JLabel labelAddress;
	JLabel labelPhoneNumber;
	JLabel labelEmailId;
	JLabel labelCustomerId;

	JTextField textFieldFirstName;
	JTextField textFieldLastName;
	JTextField textFieldAddress;
	JTextField textFieldPhoneNumber;
	JTextField textFieldEmailId;
	JTextField textFieldCustomerId;

	JButton button_UPDATE_DETAILS;

	
	public CustomerDetailsFormUI() throws SQLException {
		
		// get the connection
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

				// create the frame components
				CachedRowSet myCachedRowSet = getContentsOfCustomerTable();
				customerModel = new CustomerModel(myCachedRowSet);
				customerModel.addEventHandlersToRowSet(this);

				table = new JTable(); // Displays the table
				table.setModel(customerModel);

				labelFirstName = new JLabel();
				labelLastName = new JLabel();
				labelAddress = new JLabel();
				labelPhoneNumber = new JLabel();
				labelEmailId = new JLabel();
				labelCustomerId = new JLabel();

				textFieldFirstName = new JTextField(10);
				textFieldLastName = new JTextField(10);
				textFieldAddress = new JTextField(10);
				textFieldPhoneNumber = new JTextField(10);
				textFieldEmailId = new JTextField(10);
				textFieldCustomerId = new JTextField(10);

				button_UPDATE_DETAILS = new JButton();

				labelFirstName.setText("First Name:");
				labelLastName.setText("Last Name:");
				labelAddress.setText("Address:");
				labelPhoneNumber.setText("Phone Number:");
				labelEmailId.setText("Email Id:");
				labelCustomerId.setText("Customer ID:");

				textFieldFirstName.setText("Enter first name here");
				textFieldLastName.setText("Enter last name here");
				textFieldAddress.setText("Enter the address here");
				textFieldPhoneNumber.setText("Enter the phone number here");
				textFieldEmailId.setText("Enter the email ID here");
				textFieldCustomerId.setText("Enter the customer ID whose details are to be updated");
				button_UPDATE_DETAILS.setText("Update Customer Details");

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
				contentPane.add(labelFirstName, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 1;
				c.gridwidth = 1;
				contentPane.add(textFieldFirstName, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 0.25;
				c.weighty = 0;
				c.anchor = GridBagConstraints.LINE_START;
				c.gridx = 0;
				c.gridy = 2;
				c.gridwidth = 1;
				contentPane.add(labelLastName, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 2;
				c.gridwidth = 1;
				contentPane.add(textFieldLastName, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_START;
				c.weightx = 0.25;
				c.weighty = 0;
				c.gridx = 0;
				c.gridy = 3;
				c.gridwidth = 1;
				contentPane.add(labelAddress, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 3;
				c.gridwidth = 1;
				contentPane.add(textFieldAddress, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_START;
				c.weightx = 0.25;
				c.weighty = 0;
				c.gridx = 0;
				c.gridy = 4;
				c.gridwidth = 1;
				contentPane.add(labelPhoneNumber, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 4;
				c.gridwidth = 1;
				contentPane.add(textFieldPhoneNumber, c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_START;
				c.weightx = 0.25;
				c.weighty = 0;
				c.gridx = 0;
				c.gridy = 5;
				c.gridwidth = 1;
				contentPane.add(labelEmailId, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 5;
				c.gridwidth = 1;
				contentPane.add(textFieldEmailId, c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_START;
				c.weightx = 0.25;
				c.weighty = 0;
				c.gridx = 0;
				c.gridy = 6;
				c.gridwidth = 1;
				contentPane.add(labelCustomerId, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 6;
				c.gridwidth = 1;
				contentPane.add(textFieldCustomerId, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_START;
				c.weightx = 0.5;
				c.weighty = 0;
				c.gridx = 0;
				c.gridy = 8;
				c.gridwidth = 1;
				contentPane.add(button_UPDATE_DETAILS, c);


				button_UPDATE_DETAILS.addActionListener(new ActionListener() {

					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e) {

						JOptionPane.showMessageDialog(CustomerDetailsFormUI.this,
								new String[] { "Adding the following row:",
										"First Name: [" + textFieldFirstName.getText() + "]",
										"Last Name: [" + textFieldLastName.getText() + "]",
										"Address:["+textFieldAddress.getText()+"]",
										"Phone Number: [" + textFieldPhoneNumber.getText() + "]",
										"Email Id: [" + textFieldEmailId.getText() + "]" });

						try {
								customerModel.customerRowSet.acceptChanges();
								Customer customer = new Customer();
								customer.setFirstName(textFieldFirstName.getText().trim());
								customer.setLastName(textFieldLastName.getText().trim());
								customer.setAddress(textFieldAddress.getText().trim());
								customer.setPhoneNumber(textFieldPhoneNumber.getText().trim());
								customer.setEmailId(textFieldEmailId.getText().trim());
								customer.setCustomerID(textFieldCustomerId.getText().trim());
								updateCustomerTable(customer);
								createNewTableModel();
						} catch (SQLException sqle) {
							displaySQLExceptionDialog(sqle);
						}
					}

				});
	}
	public void updateCustomerTable(Customer customer){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String updateQuery = "UPDATE customer SET FirstName='"+customer.getFirstName()+
					"',LastName='"+customer.getLastName()+
					"',EmailID='"+customer.getEmailId()+"',Address='"+customer.getAddress()+
					"',PhoneNumber='"+customer.getPhoneNumber()+"' WHERE CustomerID='"+customer.getCustomerID()+"'";
			System.out.println(updateQuery);
			statement.executeUpdate(updateQuery);
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
	private void createNewTableModel() throws SQLException {
		customerModel = new CustomerModel(getContentsOfCustomerTable());
		customerModel.addEventHandlersToRowSet(this);
		table.setModel(customerModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(CustomerDetailsFormUI.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}
	private CachedRowSet getContentsOfCustomerTable() {
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

			crs.setCommand("select FirstName,LastName,Address,PhoneNumber,EmailID,CustomerID from customer");
			crs.execute();

		} catch (SQLException e) {
			printSQLException(e);
		}
		return crs;
		
	}
	private void printSQLException(SQLException ex) {
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
	@Override
	public void rowSetChanged(RowSetEvent event) {

	}

	@Override
	public void rowChanged(RowSetEvent event) {

		CachedRowSet currentRowSet = this.customerModel.customerRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			customerModel = new CustomerModel(customerModel.getCustomerRowSet());
			table.setModel(customerModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(CustomerDetailsFormUI.this, new String[] {
					ex.getClass().getName() + ": ", ex.getMessage() });
		}
	}

	@Override
	public void cursorMoved(RowSetEvent event) {

	}
}
