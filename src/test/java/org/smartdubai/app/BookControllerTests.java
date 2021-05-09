package org.smartdubai.app;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.smartdubai.app.beans.Book;
import org.smartdubai.app.controller.BookResource;
import org.smartdubai.app.service.BookService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { Main.class })
@WebAppConfiguration
@SpringBootTest
public class BookControllerTests {

	private MockMvc mvc;

	private BookService bookService = Mockito.mock(BookService.class);

	public static final MediaType APPLICATION_JSON = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype());

	@Before
	public void setup() {
		BookResource bookResource = new BookResource();
		Whitebox.setInternalState(bookResource, "bookService", bookService);
		mvc = MockMvcBuilders.standaloneSetup(bookResource).build();
	}

	@org.junit.Test
	public void testRetrieveBook() throws Exception {
		Book foundBook = new Book(1000l, "JAVA Programming", "Coding", "Ahmed", "fiction", "CLASS-1", 200,
				"13123-121-121");
		when(bookService.retrieveBook(1000l)).thenReturn(foundBook);

		mvc.perform(get("/Books/1000")).andExpect(status().is2xxSuccessful()).andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1000)));
		Mockito.verify(bookService, Mockito.times(1)).retrieveBook(Mockito.eq(1000l));
	}

	@org.junit.Test
	public void testRetrieveAllBooks() throws Exception {
		List<Book> books = new ArrayList<>();
		books.add(new Book(1000l, "JAVA Programming", "Coding", "Ahmed", "fiction", "CLASS-1", 200,
				"13123-121-121"));
		
		when(bookService.retrieveAllBooks()).thenReturn(books);

		mvc.perform(get("/Books")).andExpect(status().is2xxSuccessful()).andExpect(content().contentType(APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(1)));

		Mockito.verify(bookService, Mockito.times(1)).retrieveAllBooks();
	}

	@org.junit.Test
	public void testDeleteBook() throws Exception {
		//when(bookService.deleteBook(1000));
		mvc.perform(delete("/Books/1000")).andExpect(status().is2xxSuccessful());
		Mockito.verify(bookService, Mockito.times(1)).deleteBook(1000);
	}

	@org.junit.Test
	public void testCreateBook() throws Exception {
		Book book = new Book(1000l, "JAVA Programming", "Coding", "Ahmed", "fiction", "CLASS-1", 200,
				"13123-121-121");
		when(bookService.createBook(book)).thenReturn(book);
		mvc.perform(post("/Books").content(getJson(book)).contentType(APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
		Mockito.verify(bookService, Mockito.times(1)).createBook(book);
	}

	@org.junit.Test
	public void testUpdateBook() throws Exception {
		Book book = new Book(1000l, "JAVA Programming", "Coding", "Ahmed", "fiction", "CLASS-1", 200,
				"13123-121-121");
		when(bookService.updateBook(book, 1000l)).thenReturn(true);
		mvc.perform(put("/Books/1000").
				content(getJson(book)).contentType(APPLICATION_JSON)).andExpect( status().is2xxSuccessful());
		Mockito.verify(bookService, Mockito.times(1)).updateBook(book, 1000l);
	}
	
	@org.junit.Test
	public void testCheckout() throws Exception {
		org.smartdubai.app.beans.Order order = new org.smartdubai.app.beans.Order();
		List<Long> ids = new ArrayList<>();
		ids.add(1000l);
		ids.add(1001l);
		order.setBookIds(ids);
		order.setPromoCode("PRO111");
		org.smartdubai.app.beans.TotalCost totalCost = new org.smartdubai.app.beans.TotalCost(100);
				
		when(bookService.checkout(order)).thenReturn(totalCost);
		
	   	mvc.perform(post("/CheckOut").
			content(getJson(order)).contentType(APPLICATION_JSON)).
			andExpect(status().is2xxSuccessful());
		Mockito.verify(bookService, Mockito.times(1)).checkout(order);
	}
	
	private String getJson(Object obj) throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    return ow.writeValueAsString(obj);
	}
	
}