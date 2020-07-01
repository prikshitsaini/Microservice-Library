package com.assignment.microservices.bookservice.service;

import com.assignment.microservices.bookservice.bean.Book;
import com.assignment.microservices.bookservice.bean.Record;
import com.assignment.microservices.bookservice.dao.BookRepository;
import com.assignment.microservices.bookservice.dao.RecordRepository;
import com.assignment.microservices.bookservice.exceptions.BookNotFoundException;
import com.assignment.microservices.bookservice.exceptions.TypeNotAllowedException;
import com.assignment.microservices.bookservice.exceptions.UserNotFoundException;
import com.assignment.microservices.bookservice.proxy.UserServiceProxy;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    UserServiceProxy userServiceProxy;

    @Override
    public Book getBookDetail(Integer id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with this ID"));
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

    @HystrixCommand(fallbackMethod = "callGetIssuedBooks_Fallback")
    @Override
    public List<Book> getIssuedBooks(Integer uid) {

        if (!userServiceProxy.userExist(uid)) {
            throw new UserNotFoundException("User Not Found with id: " + uid);
        }

        List<Record> records = recordRepository.findAllByUid(uid);

        if (records.isEmpty()) {
            new BookNotFoundException("No book is issued to this user: " + uid);
        }

        return records.stream()
                .filter(record -> bookRepository.findById(record.getBid()).isPresent())
                .map(record -> bookRepository.findById(record.getBid()).get())
                .collect(Collectors.toList());

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

        Book book = getBookDetail(bid);

        List<Record> bookRecords = recordRepository.findAllByBid(bid);

        if (!bookRecords.isEmpty()) {
            return false;
        }

        List<Record> records = recordRepository.findAllByUid(uid);

        if (!records.isEmpty()) {
            boolean sameTypeAlreadyIssued = records.stream().anyMatch(r -> r.getType().equals(book.getType()));
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
    private List<Book> callGetIssuedBooks_Fallback(Integer uid) {
        List<Book> books = new ArrayList<>();
        Book b = new Book();
        b.setId(0);
        b.setTitle("Unable to connect user service at this moment! Service will be back shortly!");
        b.setAuthor("");
        b.setPrice(0F);
        b.setType("");
        books.add(b);
        return books;
    }

}
