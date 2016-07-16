package com.cs521.team3.model;

public class Books {
	private String book_id;
	private String book_title;
	private String author_name;
	private int price;
	private String seller_id;
	
	public Books(String book_id, String book_title,String author_name, int price, String seller_id){
		super();
		this.book_id=book_id;
		this.book_title=book_title;
		this.author_name=author_name;
		this.price=price;
		this.seller_id=seller_id;
	}
	
	public String getbook_id(){
		return book_id;
	}
	public void setbook_id(String book_id){
		this.book_id=book_id;
	}
	public String getbook_title(){
		return book_title;
	}
	
	public void setbook_title(String book_title){
		this.book_title=book_title;
	}

	public String getauthor_name(){
		return author_name;
	}
	public void setauthor_name(String author_name){
		this.author_name=author_name;
	}
	
	public int getPrice(){
		return price;
	}
	public void setPrice(int price){
		this.price=price;
	}
	public String getsellerId(){
		return seller_id;
	}
	public void setsellerId(String seller_id){
		this.seller_id=seller_id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Books [book_id=%s,book_title=%s,author_name=%s,price=%s,seller_id=%s]",book_id,book_title,author_name,price,seller_id);
	}
	
	
	
	
	
}
