package com.cs521.team3.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class FinalWishModel extends AbstractTableModel{
	private static final int CUSTOMER_ID_COL= 0;
	private static final int BOOK_ID_COL= 1;
	
	private String[] columnNames = { "Customer_id","Book_id"};
	private List<Wishlist> wishlist;

	public FinalWishModel(List<Wishlist> theWish) {
		wishlist = theWish;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return wishlist.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {

		Wishlist tempWish = wishlist.get(row);

		switch (col) {
		case CUSTOMER_ID_COL:
			return tempWish.getcustomer_id();
		case BOOK_ID_COL:
			return tempWish.getbook_id();
				default:
			return tempWish.getcustomer_id();
		}
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
