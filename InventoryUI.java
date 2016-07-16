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
import com.sun.rowset.CachedRowSetImpl;

public class InventoryUI extends JFrame implements RowSetListener {
	BookModel bookModel;
	Connection connection;
	CachedRowSet  inventoryRowSet;
	JTable table;
	
	JLabel labelBookId;
	JLabel labelNumberOfBooks;
	JLabel labelCategoryId;

	JTextField textFieldBookId;
	JTextField textFieldNumberOfBooks;
	JTextField textFieldCategoryId;

	JButton button_ADD_BOOK;
	JButton button_UPDATE_BOOK;
	JButton button_DELETE_BOOK;
	
	public InventoryUI() throws SQLException{
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
				inventoryRowSet = getContentsOfInventoryTable();
				bookModel = new BookModel(inventoryRowSet);
				bookModel.addEventHandlersToRowSet(this);

				table = new JTable(); // Displays the table
				table.setModel(bookModel);
				
				labelBookId = new JLabel();
				labelNumberOfBooks = new JLabel();
				labelCategoryId = new JLabel();
				
				textFieldBookId = new JTextField(10);
				textFieldNumberOfBooks = new JTextField(10);
				textFieldCategoryId = new JTextField(10);
				
				button_ADD_BOOK = new JButton();
				button_UPDATE_BOOK = new JButton();
				button_DELETE_BOOK = new JButton();
				
				labelBookId.setText("BookId:");
				labelNumberOfBooks.setText("Number of Books:");
				labelCategoryId.setText("CategoryId:");
				
				textFieldBookId.setText("Enter the book ID");
				textFieldNumberOfBooks.setText("Enter the number of books");
				textFieldCategoryId.setText("Enter the category ID");
				
				button_ADD_BOOK.setText("Add Book");
				button_UPDATE_BOOK.setText("Update Book Details");
				button_DELETE_BOOK.setText("Delete Book");
				
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
				contentPane.add(labelBookId, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 1;
				c.gridwidth = 1;
				contentPane.add(textFieldBookId, c);

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
				contentPane.add(labelCategoryId, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 3;
				c.gridwidth = 1;
				contentPane.add(textFieldCategoryId, c);


				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_START;
				c.weightx = 0.5;
				c.weighty = 0;
				c.gridx = 0;
				c.gridy = 6;
				c.gridwidth = 1;
				contentPane.add(button_ADD_BOOK, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.5;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 6;
				c.gridwidth = 1;
				contentPane.add(button_UPDATE_BOOK, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_START;
				c.weightx = 0.5;
				c.weighty = 0;
				c.gridx = 0;
				c.gridy = 7;
				c.gridwidth = 1;
				contentPane.add(button_DELETE_BOOK, c);

				button_ADD_BOOK.addActionListener(new ActionListener() {

					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e) {

						JOptionPane.showMessageDialog(InventoryUI.this,
								new String[] { "Adding the following row:",
										"Book Id: [" + textFieldBookId.getText() + "]",
										"Number of Books: [" + textFieldNumberOfBooks.getText() + "]",
										"Category Id: [" + textFieldCategoryId.getText() + "]"});

						try {

							bookModel.insertRow(textFieldBookId.getText().trim(),
									Integer.parseInt(textFieldNumberOfBooks.getText().trim()),
									textFieldCategoryId.getText().trim());
							Inventory inv = new Inventory();
							inv.setBookId(textFieldBookId.getText().trim());
							inv.setNumberOfBooks(Integer.parseInt(textFieldNumberOfBooks.getText().trim()));
							inv.setCategoryId(textFieldCategoryId.getText().trim());
							insertIntoInventoryTable(inv);
						} catch (SQLException sqle) {
							displaySQLExceptionDialog(sqle);
						}
					}

				});

				button_UPDATE_BOOK.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						try {
							bookModel.bookRowSet.acceptChanges();
							// Update customer table as well
							// Assuming customer id is 10000082
							// This section can be improvised to bring dynamic customer id.
							Inventory inv = new Inventory();
							inv.setBookId(textFieldBookId.getText().trim());
							inv.setNumberOfBooks(Integer.parseInt(textFieldNumberOfBooks.getText().trim()));
							inv.setCategoryId(textFieldCategoryId.getText().trim());
							updateInventoryTable(inv);
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

				button_DELETE_BOOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (table.getSelectedRow() != -1){
								Inventory inv = new Inventory();
								inv.setBookId(bookModel.getValueAt(table.getSelectedRow(), 0).toString());
								inv.setNumberOfBooks(Integer.parseInt(bookModel.getValueAt(table.getSelectedRow(), 1).toString()));
								inv.setCategoryId(bookModel.getValueAt(table.getSelectedRow(), 2).toString());
								deleteFromInventoryTable(inv);
								createNewTableModel();
							}
						} catch (SQLException sqle) {
							displaySQLExceptionDialog(sqle);
						}
					}
				});
				
	}
	private void createNewTableModel() throws SQLException {
		bookModel = new BookModel(getContentsOfInventoryTable());
		bookModel.addEventHandlersToRowSet(this);
		table.setModel(bookModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(InventoryUI.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}
	public void updateInventoryTable(Inventory inv){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String updateQuery = "UPDATE book_store.inventory SET NumberOfBooks="+inv.getNumberOfBooks()+
					",CategoryID='"+inv.getCategoryId()+"' WHERE BookID='"+inv.getBookId()+"'";
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
	public void insertIntoInventoryTable(Inventory inv){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String insertQuery = "INSERT INTO INVENTORY VALUES('"+inv.getBookId()+"','"+inv.getNumberOfBooks()+"','"+inv.getCategoryId()+"')";
			System.out.println(insertQuery);
			statement.executeUpdate(insertQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			displaySQLExceptionDialog(e);
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
	public void deleteFromInventoryTable(Inventory inv){
		Connection conn = MakeConnection.getConnection("book_store");
		try {
			Statement statement = conn.createStatement();
			String deleteQuery = "DELETE FROM INVENTORY WHERE BookID='"+inv.getBookId()+"'";
			statement.executeUpdate(deleteQuery);
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
	private CachedRowSet getContentsOfInventoryTable() {
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

			crs.setCommand("select BookID,NumberOfBooks,CategoryID from Inventory");
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
	@Override
	public void rowSetChanged(RowSetEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rowChanged(RowSetEvent event) {

		CachedRowSet currentRowSet = this.bookModel.bookRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			bookModel = new BookModel(bookModel.getBookRowSet());
			table.setModel(bookModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(InventoryUI.this, new String[] {
					ex.getClass().getName() + ": ", ex.getMessage() });
		}
	}

	@Override
	public void cursorMoved(RowSetEvent event) {

	}
}
