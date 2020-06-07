package com.assignment.microservices.bookservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.microservices.bookservice.bean.Book;
import com.assignment.microservices.bookservice.bean.Record;
import com.assignment.microservices.bookservice.dao.BookRepository;
import com.assignment.microservices.bookservice.dao.RecordRepository;
import com.assignment.microservices.bookservice.exceptions.BookNotFoundException;
import com.assignment.microservices.bookservice.exceptions.TypeNotAllowedException;
import com.assignment.microservices.bookservice.exceptions.UserNotFoundException;
import com.assignment.microservices.bookservice.proxy.UserServiceProxy;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class BookService implements IBookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	RecordRepository recordRepository;

	@Autowired
	UserServiceProxy userServiceProxy;

	@Override
	public Optional<Book> getBookDetail(Integer id) throws BookNotFoundException {

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book not found with this ID"));

		return Optional.ofNullable(book);
	}

	@Override
	public boolean addBook(Book book) {

		List<Book> list = bookRepository.findByTitle(book.getTitle());

		if (list.size() > 0) {
			return false;
		} else {
			bookRepository.save(book);
			return true;
		}
	}

	@HystrixCommand(fallbackMethod = "callgetIssuedBooks_Fallback")
	@Override
	public Optional<List<Book>> getIssuedBooks(Integer uid) {
		
		if (!userServiceProxy.userExist(uid)) {
			throw new UserNotFoundException("User Not Found with id: " + uid);
		}
		
		List<Book> issuedBooks = new ArrayList<Book>();
		Optional<List<Record>> records = recordRepository.findAllByUid(uid);
		records.orElseThrow(() -> new BookNotFoundException("No book is issued to this user: " + uid));
		if (records.isPresent()) {
			records.get().stream().forEach(r -> issuedBooks.add(bookRepository.findById(r.getBid()).get()));
		}

		return Optional.ofNullable(issuedBooks);
	}

	@Override
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

	@Override
	public boolean issueBook(Integer uid, Integer bid) {

		if (!userServiceProxy.userExist(uid)) {
			throw new UserNotFoundException("User Not Found with id: " + uid);
		}
		if (!checkBookExist(bid)) {
			throw new BookNotFoundException("Book not found with id: " + bid);
		}

		Book book = getBookDetail(bid).get();
		
		Optional<List<Record>> bookrecords = recordRepository.findAllByBid(bid);
		
		if (bookrecords.isPresent()) {
			return false;
		}

		Optional<List<Record>> records = recordRepository.findAllByUid(uid);

		if (records.isPresent()) {
			boolean sameTypeAlreadyIssued = records.get().stream().anyMatch(r -> r.getType().equals(book.getType()));
			if (sameTypeAlreadyIssued) {
				throw new TypeNotAllowedException("Similar type of book is aleardy issued to user id: " + uid);
			}
		}

		Record record = new Record();
		record.setBid(bid);
		record.setUid(uid);
		record.setType(book.getType());
		recordRepository.save(record);
		return true;

	}

	@Override
	public boolean checkBookExist(Integer id) {
		return bookRepository.existsById(id);
	}
	
	@SuppressWarnings("unused")
	private Optional<List<Book>> callgetIssuedBooks_Fallback(Integer uid) {
			List<Book> books  = new ArrayList<>();
			Book b = new Book();
			b.setId(0);
			b.setTitle("Unable to connect user service at this moment! Service will be back shortly!");
			b.setAuthor("");
			b.setPrice(0F);
			b.setType("");
			books.add(b);
			return Optional.ofNullable(books);
	}	

}
