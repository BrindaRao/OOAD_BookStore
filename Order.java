package com.cs521.team3.model;

import java.util.Date;

public class Order {

	private String orderID;
	private String bookID;
	private int quantity;
	private String customerID;
	private String orderedTimeStamp;
	private double totalAmount;
	private String orderStatus;
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	public String getOrderedTimeStamp() {
		return orderedTimeStamp;
	}
	public void setOrderedTimeStamp(String orderedTimeStamp) {
		this.orderedTimeStamp = orderedTimeStamp;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * To apply predetermined discount when bill is beyond a certain amount
	 * @param order
	 */
	public void applyDiscount(Order order){
		if(order.getTotalAmount() >= 500){
			double newPrice = order.getTotalAmount() - (order.getTotalAmount() * 0.3);
			order.setTotalAmount(newPrice);
		}else if(order.getTotalAmount() >= 1000){
			double newPrice = order.getTotalAmount() - (order.getTotalAmount() * 0.4);
			order.setTotalAmount(newPrice);
		}else if(order.getTotalAmount() >= 1500){
			double newPrice = order.getTotalAmount() - (order.getTotalAmount() * 0.5);
			order.setTotalAmount(newPrice);
		}else if(order.getTotalAmount() > 2000){
			double newPrice = order.getTotalAmount() - (order.getTotalAmount() * 0.6);
			order.setTotalAmount(newPrice);
		}
	}
}