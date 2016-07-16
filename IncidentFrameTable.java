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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import com.cs521.team3.model.IncidentModel;
import com.sun.rowset.CachedRowSetImpl;

public class IncidentFrameTable extends JFrame implements RowSetListener {

	public static Connection conn = null;
	//static MainClass mainClass = new MainClass();
	
	Connection connection;
	JTable table; // The table for displaying data

	JLabel labelIncidentNumber;
	JLabel labelEmployeeNumber;
	JLabel labelCustomerIdNumber;
	JLabel labeldescription;
	JLabel labelstatus;


	JTextField textIncidentNumber;
	JTextField textEmployeeNumber;
	JTextField textCustomerIdNumber;
	JTextField textdescription;
	JTextField textstatus;
	

	JButton button_ADD_ROW;
	JButton button_UPDATE_DATABASE;
	JButton button_DISCARD_CHANGES;

	IncidentModel incidentModel;

	public IncidentFrameTable() throws SQLException {

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
		});
*/
		// create te frame components
		CachedRowSet myCachedRowSet = getContentsOfIncidentTable();
	    incidentModel = new IncidentModel(myCachedRowSet);
		incidentModel.addEventHandlersToRowSet(this);

		table = new JTable(); // Displays the table
		table.setModel(incidentModel);

		labelIncidentNumber = new JLabel();
		labelCustomerIdNumber = new JLabel();
		labelEmployeeNumber = new JLabel();
		labeldescription = new JLabel();
		labelstatus= new JLabel();
		

		textIncidentNumber = new JTextField(10);
		textCustomerIdNumber = new JTextField(10);
		textEmployeeNumber= new JTextField(10);
		textdescription = new JTextField(10);
		textstatus = new JTextField(10);

		button_ADD_ROW = new JButton();
		button_UPDATE_DATABASE = new JButton();
		button_DISCARD_CHANGES = new JButton();

		labelIncidentNumber.setText("ID:");
		labelCustomerIdNumber.setText("CustId:");
		labelEmployeeNumber.setText("EmpId");
		labeldescription.setText("Desc");
		labelstatus.setText("Status");
		
		textIncidentNumber.setText("");
		textEmployeeNumber.setText("");
		textCustomerIdNumber.setText("");
		

		button_ADD_ROW.setText("Add row to table");
		button_UPDATE_DATABASE.setText("Update database");
		button_DISCARD_CHANGES.setText("Delete Incident");

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
		contentPane.add(labelIncidentNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(textIncidentNumber, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(labelCustomerIdNumber, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(textCustomerIdNumber, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(labelEmployeeNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(textEmployeeNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(labeldescription, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(textdescription, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(labelstatus, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(textstatus, c);

		/*c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(labelDatePaid, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(textFieldDatePaid, c);*/

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_ADD_ROW, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_UPDATE_DATABASE, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		contentPane.add(button_DISCARD_CHANGES, c);

		button_ADD_ROW.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(IncidentFrameTable.this,
						new String[] { "Adding the following row:",
								"Incident Number: [" + textIncidentNumber.getText() + "]",
								"Status: [" + textstatus.getText() + "]" });

				try {

					incidentModel.insertRow(textIncidentNumber.getText().trim(),
							textCustomerIdNumber.getText().trim(),
							textEmployeeNumber.getText().trim(),
							textdescription.getText().trim(),
							textstatus.getText().trim());
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
			}

		});

		button_UPDATE_DATABASE.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					incidentModel.incidentRowSet.acceptChanges();
					// Update customer table as well
					// Assuming customer id is 10000082
					// This section can be improvised to bring dynamic customer id.
					updateIncidentTable(textIncidentNumber.getText().trim());
					
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

		button_DISCARD_CHANGES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					incidentModel.incidentRowSet.acceptChanges();
					// Update customer table as well
					// Assuming customer id is 10000082
					// This section can be improvised to bring dynamic customer id.
					deleteIncidentTable(textIncidentNumber.getText().trim());
					
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

	}

	private void createNewTableModel() throws SQLException {
		incidentModel = new IncidentModel(getContentsOfIncidentTable());
		incidentModel.addEventHandlersToRowSet(this);
		table.setModel(incidentModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(IncidentFrameTable.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}

	private CachedRowSet getContentsOfIncidentTable() {

		// write the codd to fetch the records form reservation table
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

			// Regardless of the query, fetch the contents of COFFEES

			crs.setCommand("select IncidentID, CustomerID, EmployeeID, Description, Status from incident");
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
	
	private void updateIncidentTable(String incidentId) {
  
		String sql = "UPDATE book_store.incident SET Description = ?," +
				"Status = ? WHERE IncidentID = ?";
		
		try{
			
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			// System.out.println("12...");
			preparedStatement.setString(1,textdescription.getText().trim());
			// System.out.println("13...");
			preparedStatement.setString(2,textstatus.getText().trim());
			// System.out.println("14...");
			preparedStatement.setString(3,textIncidentNumber.getText().trim());
			// System.out.println("15...");
			//preparedStatement.setInt(4,incident.getIncidentId());
			preparedStatement.executeUpdate(); 
				
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		
		try {
			createNewTableModel();
		} catch (SQLException sqle2) {
			displaySQLExceptionDialog(sqle2);
		}
		 System.out.println("2...");
	}
	
	
	private void deleteIncidentTable(String incidentId) {
		  
		String sql  = "DELETE from book_store.incident WHERE IncidentID = ?";
		try{
			
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1,incidentId);
			preparedStatement.executeUpdate(); 
				
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		try {
			createNewTableModel();
		} catch (SQLException sqle2) {
			displaySQLExceptionDialog(sqle2);
		}
		 System.out.println("2...");
	}
	
	public static void main(String args[]) {
		try {
			
		
			conn = MakeConnection.getDafaultConnection();
			if (conn != null) {


				
				// create database and tablespace
				//mainClass.createTablespace(conn);
				//mainClass.createTables(conn);
			}
			   
			IncidentFrameTable reservationFrame = new IncidentFrameTable();
			reservationFrame.setTitle("Incident");
			reservationFrame.setSize(600, 600);
			reservationFrame.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void rowSetChanged(RowSetEvent event) {
		// TODO Auto-generated method stub

	}

	public void rowChanged(RowSetEvent event) {

		CachedRowSet currentRowSet = this.incidentModel.incidentRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			incidentModel = new IncidentModel(incidentModel.getCoffeesRowSet());
			table.setModel(incidentModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(IncidentFrameTable.this, new String[] { // Display
																				// a
																				// 2-line
																				// message
					ex.getClass().getName() + ": ", ex.getMessage() });
		}
	}

	public void cursorMoved(RowSetEvent event) {

	}

}
