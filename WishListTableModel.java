package com.cs521.team3.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class WishListTableModel extends AbstractTableModel{
	private static final int BOOK_ID_COL= 0;
	private static final int BOOK_TITLE_COL = 1;
	private static final int AUTHOR_NAME_COL = 2;
	private static final int PRICE_COL = 3;
	private static final int SELLER_ID_COL = 4;

	private String[] columnNames = { "Book_id", "Book_title", "author_name",
			"price","seller_id" };
	private List<Books> books;

	public WishListTableModel(List<Books> theBooks) {
		books = theBooks;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return books.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Books tempbooks = books.get(row);

		switch (col) {
		case BOOK_ID_COL:
			return tempbooks.getbook_id();
		case BOOK_TITLE_COL:
			return tempbooks.getbook_title();
		case AUTHOR_NAME_COL:
			return tempbooks.getauthor_name();
		case PRICE_COL:
			return tempbooks.getPrice();
		case SELLER_ID_COL:
			return tempbooks.getsellerId();
		default:
			return tempbooks.getbook_title();
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}


}
