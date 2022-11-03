package com.luv2code.springdemo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy="")
	private int id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
}
