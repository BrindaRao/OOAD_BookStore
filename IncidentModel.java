package com.cs521.team3.model;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


public class IncidentModel implements TableModel {

	public CachedRowSet incidentRowSet; // The ResultSet to interpret
	ResultSetMetaData metadata; // Additional information about the results
	int numcols, numrows; // How many rows and columns in the table

	public CachedRowSet getCoffeesRowSet() {
		return incidentRowSet;
	}

	public IncidentModel(CachedRowSet myCachedRowSet) throws SQLException {
		this.incidentRowSet = myCachedRowSet;
		this.metadata = this.incidentRowSet.getMetaData();
		numcols = metadata.getColumnCount();
    
		// Retrieve the number of rows.
		this.incidentRowSet.beforeFirst();
		this.numrows = 0;
		while (this.incidentRowSet.next()) {
			this.numrows++;
		}
		this.incidentRowSet.beforeFirst();
	}

	public void addEventHandlersToRowSet(RowSetListener listener) {
		this.incidentRowSet.addRowSetListener(listener);
	}

	public void insertRow(String incidentId, String custId,String empId, String description,String status)
			throws SQLException {

		try {
			System.out.println("test1");
			this.incidentRowSet.moveToInsertRow();
			this.incidentRowSet.updateString("IncidentID", incidentId);
			this.incidentRowSet.updateString("CustomerID", custId);
			this.incidentRowSet.updateString("EmployeeID", empId);
			this.incidentRowSet.updateString("Description", description);
			this.incidentRowSet.updateString("status", status);
			//this.incidentRowSet.updateString("createdAt", null);
			this.incidentRowSet.insertRow();
			this.incidentRowSet.moveToCurrentRow();
			System.out.println("test2");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print("SQL Exception:" + e.getMessage());
		}
	}

	public void close() {
		try {
			incidentRowSet.getStatement().close();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	private void printSQLException(SQLException ex) {
		// TODO Auto-generated method stub
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {
					e.printStackTrace(System.err);
					System.err.println("SQLState: " + ((SQLException) e).getSQLState());
					System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
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

	private boolean ignoreSQLException(String sqlState) {
		// TODO Auto-generated method stub
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


	public int getRowCount() {
		// TODO Auto-generated method stub
		return numrows;
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return numcols;
	}

	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		try {
			return this.metadata.getColumnLabel(columnIndex + 1);
		} catch (SQLException e) {
			return e.toString();
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return String.class;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		try {
			this.incidentRowSet.absolute(rowIndex + 1);
			Object o = this.incidentRowSet.getObject(columnIndex + 1);
			if (o == null)
				return null;
			else
				return o.toString();
		} catch (SQLException e) {
			return e.toString();
		}
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		System.out.println("Calling setValueAt row " + rowIndex + ", column " + columnIndex);

	}

	public void addTableModelListener(TableModelListener l) {

	}

	public void removeTableModelListener(TableModelListener l) {

	}

}
