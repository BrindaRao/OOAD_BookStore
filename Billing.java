package com.cs521.team3.model;

public class Billing {

	private String orderID;
	private String invoiceNumber;
	private double billAmount;
	

	public String getOrderID(){
		return orderID;
	}
	public void setOrderID(String OrderID){
		this.orderID = OrderID;
	}
	public String getInvoiceNumber(){
		return invoiceNumber;
	}
	public void setInvoiceNumber(String InvoiceNumber){
		this.invoiceNumber = InvoiceNumber;
	}
	public double getBillAmount(){
		return billAmount;
	}
	public void setBillAmount(double BillAmount){
		this.billAmount = BillAmount;
	}
}
