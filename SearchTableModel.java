package com.cs521.team3.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class SearchTableModel extends AbstractTableModel {

	private static final int BOOK_ID_COL = 0;
	private static final int BOOK_TITLE_COL = 1;
	private static final int AUTHOR_NAME_COL = 2;
	private static final int PRICE_COL = 3;
	private static final int SELLER_ID_COL = 4;
	private static final int CATEGORY_COL = 5;

	private String[] columnNames = { "Book ID" , "Book Title", "Author Name", "Price", "Seller ID" , "Category" };
	private List<BookDetails> bookDetails;

	public SearchTableModel(List<BookDetails> theBookDetails) {
		bookDetails = theBookDetails;
	}


	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return bookDetails.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {

		BookDetails tempBookDetails = bookDetails.get(row);

		switch (col) {
		case BOOK_ID_COL:
			return tempBookDetails.getBookID();
		case BOOK_TITLE_COL:
			return tempBookDetails.getBookName();
		case AUTHOR_NAME_COL:
			return tempBookDetails.getAuthor();
		case PRICE_COL:
			return tempBookDetails.getOriginalPrice();
		case SELLER_ID_COL:
			return tempBookDetails.getSellerID();
		case CATEGORY_COL:
			return tempBookDetails.getCategory();
		default:
			return tempBookDetails.getBookName();
		}
	}


	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
