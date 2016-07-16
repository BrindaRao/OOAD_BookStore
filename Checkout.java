package com.cs521.team3.model;


public class Checkout {
	
	private String orderID;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;
	private String phoneNumber;
	private String email;
	private String typeOfShipping;
	private String creditCardType;
	private String ccNumber;
	private String expirationDate;
	private int cvv;
	
	public String getOrderID(){
		return orderID;
	}
	public void setOrderID(String orderID){
		this.orderID = orderID;
	}
	public String getAddress1(){
		return address1;
	}
	public void setAddress1(String address1){
		this.address1 = address1;
	}
	public String getAddress2(){
		return address2;
	}
	public void setAddress2(String address2){
		this.address2 = address2;
	}
	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city = city;
	}
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
	}
	public String getZipcode(){
		return zipcode;
	}
	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}
	public String getPhoneNumber(){
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getTypeOfShipping(){
		return typeOfShipping;
	}
	public void setTypeOfShipping(String typeOfShipping){
		this.typeOfShipping = typeOfShipping;
	}
	public String getCreditCardType(){
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType){
		this.creditCardType = creditCardType;
	}
	public String getCreditCardNumber(){
		return ccNumber;
	}
	public void setCreditCardNumber(String ccNumber){
		this.ccNumber = ccNumber;
	}
	public String getExpirationDate(){
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate){
		this.expirationDate = expirationDate;
	}
	public int getCVV(){
		return cvv;
	}
	public void setCVV(int cvv){
		this.cvv = cvv;
	}
}
