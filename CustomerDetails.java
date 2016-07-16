/**
 * 
 */
package com.cs521.team3.model;


/**
 * @author Brinda Rao
 *
 */
public class CustomerDetails {
	
	private String CustomerID;
	private String FirstName;
	private String LastName;
	private String DOB;
	private String Address;
	private String PhoneNumber;
	private String EmailID; 
	private String UserName;
	private String Password;
	
	//Getter Setter methods
	
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		this.LastName = lastName;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		
		
		this.DOB = dOB;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		this.Address = address;
	}
	
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.PhoneNumber = phoneNumber;
	}
	/**
	 * @return the emailID
	 */
	public String getEmailID() {
		return EmailID;
	}
	/**
	 * @param emailID the emailID to set
	 */
	public void setEmailID(String emailID) {
		this.EmailID = emailID;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.UserName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.Password = password;
	}
	/**
	 * @return the customerID
	 */
	public String getCustomerID() {
		return CustomerID;
	}
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(String customerID) {
		this.CustomerID = customerID;
	}
	
	

}
