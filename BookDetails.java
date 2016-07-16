/**
 * 
 */
package com.cs521.team3.model;

/**
 * @author Brinda Rao
 *
 */
public class BookDetails {
	
	
	
	private String BookID;
	private String BookName;
	private String Author;
	private String OriginalPrice;
	private String SellerID;
	private String Category;

	
	public BookDetails(String BookID, String BookName, String Author, String OriginalPrice , String SellerID , String Category) {
		super();
		this.Author = Author;
		this.BookID = BookID;
		this.BookName = BookName;
		this.OriginalPrice = OriginalPrice;
		this.SellerID = SellerID;
		this.Category = Category;
	}
	/**
	 * @return the bookID
	 */
	public String getBookID() {
		return BookID;
	}
	/**
	 * @param bookID the bookID to set
	 */
	public void setBookID(String bookID) {
		this.BookID = bookID;
	}
	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return BookName;
	}
	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.BookName = bookName;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return Author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.Author = author;
	}
	/**
	 * @return the originalPrice
	 */
	public String getOriginalPrice() {
		return OriginalPrice;
	}
	/**
	 * @param originalPrice the originalPrice to set
	 */
	public void setOriginalPrice(String originalPrice) {
		this.OriginalPrice = originalPrice;
	}
	
	/**
	 * @return the sellerID
	 */
	public String getSellerID() {
		return SellerID;
	}
	/**
	 * @param sellerID the sellerID to set
	 */
	public void setSellerID(String sellerID) {
		this.SellerID = sellerID;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return Category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.Category = category;
	}
	
	
	@Override
	public String toString() {
		return String.format("Book [BookID=%s, BookName=%s, Author=%s, OriginalPrice=%s, SellerID=%s, Category=%s]",
						BookID,BookName,Author,OriginalPrice,SellerID,Category);
	}
	

}
