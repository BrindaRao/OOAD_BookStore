package com.cs521.team3.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ReviewRatingModel extends AbstractTableModel {
	private static final int BOOK_ID_COL= 0;
	private static final int REVIEW_COL = 1;
	private static final int RATING_COL = 2;
	private static final int CUSTOMER_ID_COL = 3;
	;

	private String[] columnNames = { "Book_id", "Review", "Rating",
			"customer_id"};
	private List<ReviewandRating> reviewrating;

	public ReviewRatingModel(List<ReviewandRating> theRR) {
		reviewrating = theRR;
	}


	public int getColumnCount() {
		return columnNames.length;
	}


	public int getRowCount() {
		return reviewrating.size();
	}


	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {

		ReviewandRating tempRR = reviewrating.get(row);

		switch (col) {
		case BOOK_ID_COL:
			return tempRR.getbook_id();
		case REVIEW_COL:
			return tempRR.getreview();
		case RATING_COL:
			return tempRR.getrating();
		case CUSTOMER_ID_COL:
			return tempRR.getcustomer_id();
		default:
			return tempRR.getbook_id();
		}
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
