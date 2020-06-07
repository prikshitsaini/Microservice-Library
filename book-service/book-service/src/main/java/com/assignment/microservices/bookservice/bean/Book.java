package com.assignment.microservices.bookservice.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "books")
public class Book {
	private Integer id;

	@NotNull(message = "Title cannot be empty")
	@Size(min = 3, message = "Title should have atleast 3 characters")
	private String title;

	@NotNull(message = "Type cannot be empty")
	@Size(min = 3, message = "Type should have atleast 2 characters")
	private String type;

	@NotNull(message = "Author cannot be empty")
	@Size(min = 3, message = "Author should have atleast 3 characters")
	private String author;

	@NotNull(message = "Price cannot be empty")
	@Positive(message = "Price cannot be zero or negative")
	private Float price;

	public Book() {
	}

	public Book(Integer id, String title, String type, String author, Float price) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
		this.author = author;
		this.price = price;
	}

	@Id
	@Column(name = "bid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(precision = 2)
	@NumberFormat(pattern = "#####.##")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
