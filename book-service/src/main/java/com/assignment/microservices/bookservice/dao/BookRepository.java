package com.assignment.microservices.bookservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.microservices.bookservice.bean.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findByTitle(String title);
	
}
