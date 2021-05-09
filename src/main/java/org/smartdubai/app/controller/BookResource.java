package org.smartdubai.app.controller;

import java.net.URI;
import java.util.List;

import org.smartdubai.app.beans.Book;
import org.smartdubai.app.beans.Order;
import org.smartdubai.app.beans.TotalCost;
import org.smartdubai.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class BookResource {

	@Autowired
	private BookService bookService;

	@GetMapping("/Books")
	public List<Book> retrieveAllBooks() {
		return bookService.retrieveAllBooks();
	}

	@GetMapping("/Books/{id}")
	public Book retrieveBook(@PathVariable long id) {
		return bookService.retrieveBook(id);
	}

	@DeleteMapping("/Books/{id}")
	public void deleteBook(@PathVariable long id) {
		bookService.deleteBook(id);
	}

	@PostMapping("/Books")
	public ResponseEntity<Object> createBook(@RequestBody Book book) {
		Book savedBook = bookService.createBook(book);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedBook.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@PostMapping("/CheckOut")
	public TotalCost checkout(@RequestBody Order order) {
		return bookService.checkout(order);
	}

	@PutMapping("/Books/{id}")
	public ResponseEntity<Object> updateBook(@RequestBody Book book, @PathVariable long id) {
		if (!bookService.updateBook(book, id)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}
}
