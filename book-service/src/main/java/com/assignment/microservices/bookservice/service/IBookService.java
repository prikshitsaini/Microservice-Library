package com.assignment.microservices.bookservice.service;

import java.util.List;
import java.util.Optional;

import com.assignment.microservices.bookservice.bean.Book;
import com.assignment.microservices.bookservice.exceptions.BookNotFoundException;

public interface IBookService {
	
	Book getBookDetail(Integer id) throws BookNotFoundException;
	
	boolean addBook(Book book);
	
	boolean checkBookExist(Integer id);
	
	List<Book> getBooks();
	
	boolean issueBook(Integer uid,Integer bid);

	List<Book> getIssuedBooks(Integer id);

}
