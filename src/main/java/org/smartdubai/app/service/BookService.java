package org.smartdubai.app.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdubai.app.beans.Book;
import org.smartdubai.app.beans.BookClassification;
import org.smartdubai.app.beans.BookPromoCode;
import org.smartdubai.app.beans.BookType;
import org.smartdubai.app.beans.Order;
import org.smartdubai.app.beans.TotalCost;
import org.smartdubai.app.exception.BookNotFoundException;
import org.smartdubai.app.repository.BookClassificationRepository;
import org.smartdubai.app.repository.BookPromoCodeRepository;
import org.smartdubai.app.repository.BookRepository;
import org.smartdubai.app.repository.BookTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class solely designed to provide service level functionality to
 * controller or other end points.
 * 
 * @author kaimran
 *
 */
@Service
public class BookService {
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookTypeRepository bookTypeRepository;
	@Autowired
	private BookClassificationRepository bookClassificationRepository;
	@Autowired
	private BookPromoCodeRepository bookPromoCodeRepository;

	/**
	 * This method used to find after checkout total cost of purchase.
	 * 
	 * @param order
	 * @return
	 */
	public TotalCost checkout(Order order) {

		if (order == null) {
			return new TotalCost(0l);
		}

		List<Book> books = bookRepository.findAllById(order.getBookIds());
		double total = 0.0;

		for (Book book : books) {
			double discountPerBook = 0.0;
			if (book != null) {
				if (book.getType() != null) {
					System.out.println(book.getType());

					Optional<BookType> discountBookType = bookTypeRepository.findById(book.getType());
					logger.debug("discountBookType:" + discountBookType);
					if (discountBookType != null && discountBookType.isPresent()) {
						discountPerBook += discountBookType.get().getDiscount();
					}
				}
				if (book.getClassification() != null) {
					logger.debug(book.getClassification());
					logger.debug("book.getClassification():" + book.getClassification());
					Optional<BookClassification> discountBookClassification = bookClassificationRepository
							.findById(book.getClassification());
					logger.debug("discountBookClassification:" + discountBookClassification);
					if (discountBookClassification != null && discountBookClassification.isPresent()) {
						discountPerBook += discountBookClassification.get().getDiscount();
					}
				}
				double discountedPrice = book.getPrice() * (1 - discountPerBook);
				logger.info("Book [" + book.getId() + "] Actual Price [" + book.getPrice() + "] after discount ["
						+ (discountPerBook * 100) + "] book cost is ===>" + discountedPrice);

				total += discountedPrice;
			}
		}

		if (order.getPromoCode() != null) {
			Optional<BookPromoCode> discountBookPromoCode = bookPromoCodeRepository.findById(order.getPromoCode());
			if (discountBookPromoCode != null && discountBookPromoCode.isPresent()) {
				double discount = discountBookPromoCode.get().getDiscount();
				double discountedPrice = total * (1 - discount);
				logger.info("PROMOCODE [" + order.getPromoCode() + "] Actual Total [" + total + "] after discount ["
						+ (discount * 100) + "] TOTAL cost is:" + discountedPrice);
				total = discountedPrice;
			}
		}

		return new TotalCost(total);

	}

	/**
	 * This method used to get all records of Book entity.
	 * 
	 * @return
	 */
	public List<Book> retrieveAllBooks() {
		return bookRepository.findAll();
	}

	/**
	 * This method is used to get Book entity against an ID.
	 * 
	 * @param id
	 * @return
	 */
	public Book retrieveBook(long id) {
		Optional<Book> book = bookRepository.findById(id);

		if (!book.isPresent())
			throw new BookNotFoundException("id-" + id);

		return book.get();
	}

	/**
	 * This method used to delete a book record.
	 * 
	 * @param id
	 */
	public void deleteBook(long id) {
		bookRepository.deleteById(id);
	}

	/**
	 * This method used to create a book record.
	 * 
	 * @param Book
	 * @return
	 */
	public Book createBook(Book Book) {
		return bookRepository.save(Book);
	}

	/**
	 * This method used to update a selected book.
	 * 
	 * @param Book
	 * @param id
	 */
	public boolean updateBook(Book book, long id) {
		Optional<Book> bookOptional = bookRepository.findById(id);

		if (!bookOptional.isPresent())
			return false;

		book.setId(id);

		bookRepository.save(book);

		return true;

	}
}
