package pl.polsl.pamula.zbigniew.test;

import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.polsl.pamula.zbigniew.model.Book;
import pl.polsl.pamula.zbigniew.model.BookBean;

/**
 * Book testing unit
 *
 * @author Zbigniew Pamuła
 * @version 1.0
 */
public class BookTest {

    /**
     * object of BookBean used to perform CRUD operations on database
     */
    private static BookBean bookbean;
    /**
     * Properties object
     */
    private static Properties properties;

    /**
     * setup of database
     */
    @BeforeClass
    public static void prepare() throws NamingException {
        properties = new Properties();
        properties.put("Test-PU", "new://Resource?type=DataSource");
        properties.put("Test-PU.Username", "root");
        properties.put("Test-PU.Password", "root");
        properties.put("Test-PU.JtaManaged", "true");
        properties.put("Test-PU.JdbcDriver", "com.mysql.jdbc.Driver");
        properties.put("Test-PU.JdbcUrl", "jdbc:mysql://localhost:3306/library?zeroDateTimeBehavior=convertToNull");
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        bookbean = (BookBean) new InitialContext(properties).lookup("BookBeanLocalBean");

    }

    /**
     * creating new books using BookBean createBook method
     */
    @Before
    public void setup() {
        Book b1 = new Book();
        b1.setTitle("Opowiesci z narni");
        b1.setAuthor("C.S.Lewis");
        bookbean.createBook(b1);
        Book b2 = new Book();
        b2.setTitle("Harry Potter");
        b2.setAuthor("J.K.Rowling");
        bookbean.createBook(b2);
        Book b3 = new Book();
        b3.setTitle("Wiedzmin");
        b3.setAuthor("Sapkowski");
        bookbean.createBook(b3);
    }

    /**
     * testing the retrieve operation
     */
    @Test
    public void testRead() {
        assertEquals("Should be 3 books", 3, bookbean.findAllBooks().size());
        for (Book b : bookbean.findAllBooks()) {
            assertNotNull("Author of book should not be null", b.getAuthor());
        }
    }

    /**
     * testing the update operation
     */
    @Test
    public void testUpdate() {
        List<Book> books = bookbean.findAllBooks();
        for (Book b : books) {
            bookbean.updateBook(b, "aaa", "some_author", null);
        };
        for (Book b : books) {
            Assert.assertNotNull("Should be not null", b.getTitle());
            Assert.assertNotNull("Should be not null", b.getAuthor());
            assertEquals("testing name of book", "aaa", b.getTitle());
        };
    }
    
    /**
     * testing the delete operation
     */
    @Test
    public void testDelete() {
        for (Book b : bookbean.findAllBooks()) {
            bookbean.deleteBook(b);
        }
        Assert.assertNotEquals("Size should not be 3 anymore", 3, bookbean.findAllBooks().size());
    }

    
    /**
     * Deleting the Book objects created for the tests
     */
    @After
    public void destroy() {
        for (Book b : bookbean.findAllBooks()) {
            bookbean.deleteBook(b);
        }
    }

}
