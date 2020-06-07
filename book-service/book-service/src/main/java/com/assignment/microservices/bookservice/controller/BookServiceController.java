package com.assignment.microservices.bookservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.microservices.bookservice.bean.Book;
import com.assignment.microservices.bookservice.service.IBookService;

@RestController
public class BookServiceController {
	
	@Autowired
	IBookService bookService;
	
	@GetMapping("/book/{bid}")
	public Book retrieveBook(@PathVariable int bid) {

		return bookService.getBookDetail(bid).get();
	}
	

	@GetMapping("/books")
	public List<Book> retrieveBooks() {
		return bookService.getBooks();
	}
	
	@GetMapping("/books/user/{uid}")
	public List<Book> retrieveBooksIssued(@PathVariable int uid){
		return bookService.getIssuedBooks(uid).get(); 
	}
	
	@PostMapping("/book/add")
	public ResponseEntity<String> addBook(@Valid @RequestBody Book book) {
		
		
		boolean result = bookService.addBook(book);
		if(!result) {
			return new ResponseEntity<String>("Similar book is already present!", HttpStatus.CONFLICT);
		}
		else {
			return 	new ResponseEntity<String>("Book is succesfully added!", HttpStatus.CREATED);
		}
	}
	
	@PostMapping("/book/issue/{bid}/user/{uid}")
	public ResponseEntity<String> issueBook(@PathVariable int bid, @PathVariable int uid){
		
		boolean result = bookService.issueBook(uid,bid);
		
		if(!result) {
			return new ResponseEntity<String>("Book is already issued!",HttpStatus.CONFLICT);
		}
		else {
			return new ResponseEntity<String>("Book successfully issued to User with user id: "+uid,HttpStatus.OK);
		}	
		
	}
	
}
