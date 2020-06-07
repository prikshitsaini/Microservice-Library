package com.assignment.microservices.userservice.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {
	
	public User() {
	}
	
	
	
	public User(int id,
			@NotNull(message = "Please enter first name") @Size(min = 2, message = "First Name should have atleast 2 characters") String firstName,
			@NotNull(message = "Please enter last name") @Size(min = 2, message = "Last Name should have atleast 2 characters") String lastName,
			@NotNull(message = "Please enter Age") @Positive(message = "Age cannot be Zero or Negative") int age,
			@NotNull(message = "Please enter contact number") @Positive(message = "contact number cannot be negative") Long contactNo) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.contactNo = contactNo;
	}



	private int id;
	
	@NotNull(message = "Please enter first name")
    @Size(min = 2, message = "First Name should have atleast 2 characters")
	private String firstName;
	
	@NotNull(message = "Please enter last name")
    @Size(min = 2, message = "Last Name should have atleast 2 characters")
	private String lastName;
	
	@NotNull(message = "Please enter Age")
	@Positive(message = "Age cannot be Zero or Negative")
	private int age;
	
	@NotNull(message = "Please enter contact number")
	@Positive(message = "contact number cannot be negative")
	private Long contactNo;

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Column(name = "contact_no")
	public Long getContactNo() {
		return contactNo;
	}

	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}

}
