package com.cs521.team3.model;

public class ReviewandRating {
	private String book_id;
	private String review;
	private int rating;
	private String customer_id;
	
	
	public ReviewandRating(String book_id, String review,int rating, String customer_id){
		super();
		this.book_id=book_id;
		this.review=review;
		this.rating=rating;
		this.customer_id=customer_id;
		
	}
	
	public String getbook_id(){
		return book_id;
	}
	public void setbook_id(String book_id){
		this.book_id=book_id;
	}
	public String getreview(){
		return review;
	}
	
	public void setreview(String review){
		this.review=review;
	}

	public int getrating(){
		return rating;
	}
	public void setrating(int rating){
		this.rating=rating;
	}
	
	public String getcustomer_id(){
		return customer_id;
	}
	public void setcustomer_id(String customer_id){
		this.customer_id=customer_id;
	}
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("ReviewandRating [book_id=%s,review=%s,rating=%s,customer_id=%s]",book_id,review,rating,customer_id);
	}
	
	

}
