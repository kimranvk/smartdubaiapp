package org.smartdubai.app;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.smartdubai.app.beans.Book;
import org.smartdubai.app.beans.BookClassification;
import org.smartdubai.app.beans.BookPromoCode;
import org.smartdubai.app.beans.BookType;
import org.smartdubai.app.controller.BookResource;
import org.smartdubai.app.controller.BookResource.Order;
import org.smartdubai.app.repository.BookClassificationRepository;
import org.smartdubai.app.repository.BookPromoCodeRepository;
import org.smartdubai.app.repository.BookRepository;
import org.smartdubai.app.repository.BookTypeRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookStoreTests {
	private BookTypeRepository bookTypeRepository = Mockito.mock(BookTypeRepository.class);
	private BookClassificationRepository bookClassificationRepository = Mockito
			.mock(BookClassificationRepository.class);
	private BookPromoCodeRepository bookPromoCodeRepository = Mockito.mock(BookPromoCodeRepository.class);
	private BookRepository bookRepository = Mockito.mock(BookRepository.class);
	private BookResource bookResource = new BookResource();
	
	@Before
	public void setup() {
		Whitebox.setInternalState(bookResource, "bookTypeRepository", bookTypeRepository);
		Whitebox.setInternalState(bookResource, "bookClassificationRepository", bookClassificationRepository);
		Whitebox.setInternalState(bookResource, "bookPromoCodeRepository", bookPromoCodeRepository);
		Whitebox.setInternalState(bookResource, "bookRepository", bookRepository);
	}

	@Test
	public void testRetrieveBook() {
		Book foundBook = new Book(1000l, "JAVA Programming", "Coding", "Ahmed", "fiction", "CLASS-1", 200,
				"13123-121-121");
		when(bookRepository.findById(1000l)).thenReturn(Optional.ofNullable(foundBook));
		Assert.assertTrue(bookResource.retrieveBook(1000l).equals(foundBook));
	}

	@Test
	public void testCreateBook() {
		Book book = new Book(1000l, "JAVA Programming", "Coding", "Ahmed", "fiction", "CLASS-1", 200, "13123-121-121");
		when(bookRepository.save(book)).thenReturn(book);
		ResponseEntity<Object> obj = bookResource.createBook(book);
		System.out.println("Object :::: " + obj);
		Assert.assertTrue(obj.getStatusCodeValue() == 201);
	}

	@Test
	public void testUpdateBook() {
		Book foundBook = new Book(1000l, "JAVA Programming", "Coding", "Ahmed", "fiction", "CLASS-1", 200,
				"13123-121-121");
		when(bookRepository.findById(1000l)).thenReturn(Optional.ofNullable(foundBook));
		bookResource.updateBook(foundBook, 1000l);
		verify(bookRepository).save(foundBook);
	}

	@Test
	public void testDeleteBook() {
		bookResource.deleteBook(1000l);
		verify(bookRepository).deleteById(1000l);
	}

	@Test
	public void testCheckoutBooks() {
		BookType bookType = new BookType("fiction", 0.10);
		when(bookTypeRepository.findById("fiction")).thenReturn(Optional.of(bookType));

		BookClassification bookClassificationType = new BookClassification("CLASS-1", 0.10);
		when(bookClassificationRepository.findById("CLASS-1")).thenReturn(Optional.ofNullable(bookClassificationType));

		List<Long> checkForBookIds = new ArrayList<Long>();
		checkForBookIds.add(1000l);
		checkForBookIds.add(1001l);

		List<Book> foundBooks = new ArrayList<>();
		foundBooks.add(
				new Book(1000l, "JAVA Programming", "Coding", "Ahmed", "fiction", "CLASS-1", 200, "13123-121-121"));
		foundBooks
				.add(new Book(1001l, "C++ Programming", "Coding", "Junaid", "comic", "CLASS-1", 200, "13123-121-121"));

		when(bookRepository.findAllById(checkForBookIds)).thenReturn(foundBooks);
		BookPromoCode bookPromoCode = new BookPromoCode("PRO111", 0.20);
		when(bookPromoCodeRepository.findById("PRO111")).thenReturn(Optional.of(bookPromoCode));

		// BookResource bookResource = new BookResource();
		Order order = new Order();
		List<Long> ids = new ArrayList<>();
		ids.add(1000l);
		ids.add(1001l);
		order.setBookIds(ids);
		order.setPromoCode("PRO111");
		double total = bookResource.checkout(order).getTotalCost();
		System.out.print("TOTAL:::::" + total);
		Assert.assertTrue(total == 272.0);
	}

	@Test
	public void contextLoads() {
	}

}
