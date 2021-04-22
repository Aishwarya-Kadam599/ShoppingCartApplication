package com.customer.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter 
@Setter 
public class Customer {

	 @Id
     @GeneratedValue(strategy=GenerationType.AUTO)
     private int id;
	 private String name;
	 private String email;
	 private String address;
	 private Date dob;
	public Customer(String name, String email, String address, Date dob) {
		super();
		this.name = name;
		this.email = email;
		this.address = address;
		this.dob = dob;
	}
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", dob=" + dob
				+ "]";
	}
	
	 
}
