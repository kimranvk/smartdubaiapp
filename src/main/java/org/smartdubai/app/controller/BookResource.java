package org.smartdubai.app.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdubai.app.beans.Book;
import org.smartdubai.app.beans.BookClassification;
import org.smartdubai.app.beans.BookPromoCode;
import org.smartdubai.app.beans.BookType;
import org.smartdubai.app.exception.BookNotFoundException;
import org.smartdubai.app.repository.BookClassificationRepository;
import org.smartdubai.app.repository.BookPromoCodeRepository;
import org.smartdubai.app.repository.BookRepository;
import org.smartdubai.app.repository.BookTypeRepository;
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
    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookTypeRepository bookTypeRepository;

	@Autowired
	private BookClassificationRepository bookClassificationRepository;

	@Autowired
	private BookPromoCodeRepository bookPromoCodeRepository;

	@GetMapping("/Books")
	public List<Book> retrieveAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/Books/{id}")
	public Book retrieveBook(@PathVariable long id) {
		Optional<Book> Book = bookRepository.findById(id);

		if (!Book.isPresent())
			throw new BookNotFoundException("id-" + id);

		return Book.get();
	}

	@DeleteMapping("/Books/{id}")
	public void deleteBook(@PathVariable long id) {
		bookRepository.deleteById(id);
	}

	@PostMapping("/Books")
	public ResponseEntity<Object> createBook(@RequestBody Book Book) {
		Book savedBook = bookRepository.save(Book);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedBook.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	
	public static class Order {
		private List<Long> bookIds = new ArrayList<>();
		private String promoCode;
		public List<Long> getBookIds() {
			return bookIds;
		}
		public void setBookIds(List<Long> bookIds) {
			this.bookIds = bookIds;
		}
		public String getPromoCode() {
			return promoCode;
		}
		public void setPromoCode(String promoCode) {
			this.promoCode = promoCode;
		}
	}
	
	public static class TotalCost {
		private double totalCost=0.0;
        
		public double getTotalCost() {
			return totalCost;
		}

		public void setTotalCost(double totalCost) {
			this.totalCost = totalCost;
		}
		public TotalCost(double totalCost) {
			this.totalCost= totalCost;
		}
	}
	
	@PostMapping("/CheckOut")
	public TotalCost checkout(@RequestBody Order order) {
		List<Book> books = bookRepository.findAllById(order.getBookIds());
		double total=0.0;

		for (Book book: books) {
			double discountPerBook = 0.0;
			
			if(book!=null && book.getType()!=null) {
				System.out.println(book.getType());
				
				Optional<BookType> discountBookType = bookTypeRepository.findById(book.getType());
				logger.debug("discountBookType:"+discountBookType);
				if(discountBookType!=null && discountBookType.isPresent()) {
					discountPerBook+=discountBookType.get().getDiscount();
				}
			}
			if(book!=null && book.getClassification()!=null) {
				logger.debug(book.getClassification());
				logger.debug("book.getClassification():"+book.getClassification());
				Optional<BookClassification> discountBookClassification = bookClassificationRepository.findById(book.getClassification());
				logger.debug("discountBookClassification:"+discountBookClassification);
				if(discountBookClassification!=null && discountBookClassification.isPresent()) {
					discountPerBook+=discountBookClassification.get().getDiscount();
				}
			}
			double discountedPrice =book.getPrice()*(1-discountPerBook); 
			logger.info("Book ["+book.getId()+"] Actual Price ["+book.getPrice()+"] after discount ["+(discountPerBook*100)+"] book cost is ===>"+discountedPrice);
			
			total+=discountedPrice;
		}
		
		if(order.getPromoCode()!=null) {
			Optional<BookPromoCode> discountBookPromoCode = bookPromoCodeRepository.findById(order.getPromoCode());
			if(discountBookPromoCode!=null && discountBookPromoCode.isPresent()) {
				double discount =discountBookPromoCode.get().getDiscount(); 
				double discountedPrice =total*(1-discount); 
				logger.info("PROMOCODE ["+order.getPromoCode()+"] Actual Total ["+total+"] after discount ["+(discount*100)+"] TOTAL cost is:"+discountedPrice);
				total = discountedPrice;
			}
		}
		
		return new TotalCost(total);

	}
	
	
	@PutMapping("/Books/{id}")
	public ResponseEntity<Object> updateBook(@RequestBody Book Book, @PathVariable long id) {

		Optional<Book> BookOptional = bookRepository.findById(id);

		if (!BookOptional.isPresent())
			return ResponseEntity.notFound().build();

		Book.setId(id);
		
		bookRepository.save(Book);

		return ResponseEntity.noContent().build();
	}
}
