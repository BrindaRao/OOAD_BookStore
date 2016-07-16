package com.cs521.team3.model;

public class Wishlist {
	private String customer_id;
	private String book_id;
	
	public Wishlist(String customer_id, String book_id){
		super();
		this.customer_id=customer_id;
		this.book_id=book_id;
		
	}
	
	public String getcustomer_id(){
		return customer_id;
	}
	public void setcustomer_id(String customer_id){
		this.customer_id=customer_id;
	}
	
	public String getbook_id(){
		return book_id;
	}
	public void setbook_id(String book_id){
		this.book_id=book_id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Wishlist [customer_id=%s,book_id=%s]",customer_id,book_id);
	}
	
}
