package com.assignment.microservices.bookservice;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.assignment.microservices.bookservice.bean.Book;
import com.assignment.microservices.bookservice.controller.BookServiceController;
import com.assignment.microservices.bookservice.service.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookServiceController.class)
public class BookServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	Optional<Book> mockBook = Optional.ofNullable(new Book(1, "Sky is the limit", "General", "Prikshit", 800.0F));

	String addBookJson = "{\"id\":4,\"title\":\"MySQL\",\"type\":\"Database\",\"author\":\"Rohit\",\"price\":1000}";

	List<Book> mockBooks = Arrays.asList(new Book(1, "Sky is the limit", "General", "Prikshit", 800.0F),
			new Book(2, "Spring Boot 2.0", "Framework", "Yatin", 800.0F));

	@Test
	public void retrieveBookDetails() throws Exception {

		Mockito.when(bookService.getBookDetail(Mockito.anyInt())).thenReturn(mockBook);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"id\":1, \"title\":\"Sky is the limit\", \"type\":\"General\", \"author\":\"Prikshit\", \"price\":800.0}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void retrieveBooks() throws Exception {
		Mockito.when(bookService.getBooks()).thenReturn(mockBooks);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "[{\"id\":1, \"title\":\"Sky is the limit\", \"type\":\"General\", \"author\":\"Prikshit\", \"price\":800.0},"
				+ "			{\"id\":2, \"title\":\"Spring Boot 2.0\", \"type\":\"Framework\", \"author\":\"Yatin\", \"price\":800.0}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void addBook() throws Exception {

		boolean bookAdded = true;

		Mockito.when(bookService.addBook(Mockito.any(Book.class))).thenReturn(bookAdded);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book/add").accept(MediaType.APPLICATION_JSON)
				.content(addBookJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		String expectedResponse = "Book is succesfully added!";

		assertEquals(expectedResponse, result.getResponse().getContentAsString());

	}

	@Test
	public void retrieveBooksIssued() throws Exception {

		Mockito.when(bookService.getIssuedBooks(Mockito.anyInt())).thenReturn(Optional.ofNullable(mockBooks));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/user/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "[{\"id\":1, \"title\":\"Sky is the limit\", \"type\":\"General\", \"author\":\"Prikshit\", \"price\":800.0},"
				+ "			{\"id\":2, \"title\":\"Spring Boot 2.0\", \"type\":\"Framework\", \"author\":\"Yatin\", \"price\":800.0}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void issueBook() throws Exception {

		boolean bookIssued = true;

		Mockito.when(bookService.issueBook(Mockito.anyInt(), Mockito.anyInt())).thenReturn(bookIssued);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book/issue/1/user/1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedResponse = "Book successfully issued to User with user id: 1";

		assertEquals(expectedResponse, result.getResponse().getContentAsString());
	}
}
