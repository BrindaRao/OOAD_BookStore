package com.cs521.team3.ui;

import java.awt.Button;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
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
import com.cs521.team3.model.Category;
import com.cs521.team3.model.CategoryModel;
import com.sun.rowset.CachedRowSetImpl;

public class CategoryUI extends JFrame implements RowSetListener {
	
	CategoryModel categoryModel;
	Connection connection;
	CachedRowSet  categoryRowSet;
	JTable table;
	TextField textField_2;
	JLabel labelCategoryId;
	JLabel labelCategoryName;
	JTextField textFieldCategoryId;
	JTextField textFieldCategoryName;
	JButton button_ADD_CATEGORY;
	JButton button_DELETE_CATEGORY;
	public static int i = 1;
	public CategoryUI() throws SQLException{
		connection = MakeConnection.getConnection("book_store");
		// event listen when window is closed
				/*addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						//System.out.println("Window is closed..");
						try {
							connection.close();
						} catch (SQLException ex) {
							printSQLException(ex);
						}
						//System.exit(0);
					}
					
				});*/
				categoryRowSet = getContentsOfCategoryTable();
				categoryModel = new CategoryModel(categoryRowSet);
				categoryModel.addEventHandlersToRowSet(this);

				table = new JTable(); // Displays the table
				table.setModel(categoryModel);
				table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
				
				labelCategoryId = new JLabel();
				labelCategoryName = new JLabel();

				textFieldCategoryId = new JTextField(10);
				textFieldCategoryName = new JTextField(10);

				button_ADD_CATEGORY = new JButton();
				button_DELETE_CATEGORY = new JButton();

				labelCategoryId.setText("CategoryID:");
				labelCategoryName.setText("Category Name:");

				textFieldCategoryId.setText("Enter category Id here");
				textFieldCategoryName.setText("Enter category name here");
				
				button_ADD_CATEGORY.setText("Add Category");
				button_DELETE_CATEGORY.setText("Delete Category");

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
				contentPane.add(labelCategoryId, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 1;
				c.gridwidth = 1;
				contentPane.add(textFieldCategoryId, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 0.25;
				c.weighty = 0;
				c.anchor = GridBagConstraints.LINE_START;
				c.gridx = 0;
				c.gridy = 2;
				c.gridwidth = 1;
				contentPane.add(labelCategoryName, c);

				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_END;
				c.weightx = 0.75;
				c.weighty = 0;
				c.gridx = 1;
				c.gridy = 2;
				c.gridwidth = 1;
				contentPane.add(textFieldCategoryName, c);


				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_START;
				c.weightx = 0.5;
				c.weighty = 0;
				c.gridx = 0;
				c.gridy = 6;
				c.gridwidth = 1;
				contentPane.add(button_ADD_CATEGORY, c);


				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.LINE_START;
				c.weightx = 0.5;
				c.weighty = 0;
				c.gridx = 0;
				c.gridy = 7;
				c.gridwidth = 1;
				contentPane.add(button_DELETE_CATEGORY, c);

				button_ADD_CATEGORY.addActionListener(new ActionListener() {

					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e) {

						JOptionPane.showMessageDialog(CategoryUI.this,
								new String[] { "Adding the following row:",
										"Category ID: [" + textFieldCategoryId.getText() + "]",
										"Category Name: [" + textFieldCategoryName.getText() + "]"});

						try {

							categoryModel.insertRow(textFieldCategoryId.getText().trim(),
									textFieldCategoryName.getText().trim());
							Category category_data =  new Category();
							category_data.setCategoryId(textFieldCategoryId.getText().trim());
							category_data.setCategoryName(textFieldCategoryName.getText().trim());
							insertIntoCategoryTable(category_data);
						} catch (SQLException sqle) {
							displaySQLExceptionDialog(sqle);
						}
					}

				});
				button_DELETE_CATEGORY.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (table.getSelectedRow() != -1) {
					            // remove selected row from the model
							Category category_data =  new Category();
							category_data.setCategoryId(categoryModel.getValueAt(table.getSelectedRow(), 0).toString());
							category_data.setCategoryName(categoryModel.getValueAt(table.getSelectedRow(), 1).toString());
							deleteFromCategoryTable(category_data);
							createNewTableModel();
							}
						} catch (SQLException sqle) {
							displaySQLExceptionDialog(sqle);
						}
					}
				});
				
	}
	private void createNewTableModel() throws SQLException {
		categoryModel = new CategoryModel(getContentsOfCategoryTable());
		categoryModel.addEventHandlersToRowSet(this);
		table.setModel(categoryModel);
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
	private CachedRowSet getContentsOfCategoryTable() {
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

			crs.setCommand("select CategoryId,CategoryName from Category");
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

		CachedRowSet currentRowSet = this.categoryModel.categoryRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			categoryModel = new CategoryModel(categoryModel.getCategoryRowSet());
			table.setModel(categoryModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(CategoryUI.this, new String[] {
					ex.getClass().getName() + ": ", ex.getMessage() });
		}
	}

	@Override
	public void cursorMoved(RowSetEvent event) {

	}
	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(CategoryUI.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}
}
