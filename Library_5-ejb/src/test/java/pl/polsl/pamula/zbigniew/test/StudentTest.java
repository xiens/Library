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
import pl.polsl.pamula.zbigniew.model.Student;

/**
 * Student testing unit
 *
 * @author Zbigniew Pamuła
 * @version 1.0
 */
public class StudentTest {

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
     * creating new students using BookBean createStudent method
     */
    @Before
    public void setup() {
        Student s1 = new Student();
        s1.setName("Jan");
        s1.setAge(23);
        bookbean.createStudent(s1);
        Student s2 = new Student();
        s2.setName("Anna");
        s2.setAge(25);
        bookbean.createStudent(s2);
        Student s3 = new Student();
        s3.setName("Joanna");
        s3.setAge(22);
        bookbean.createStudent(s3);
    }
    /**
     * testing the retrieve operation
     */
    @Test
    public void testRead() {
        assertEquals("Should be 3 students", 3, bookbean.findAllStudents().size());
        for (Student s : bookbean.findAllStudents()) {
            assertNotNull("Name of student should not be null", s.getName());
        }
    }
    /**
     * testing the update operation
     */
    @Test
    public void testUpdate() {
        List<Student> students = bookbean.findAllStudents();
        for (Student student : bookbean.findAllStudents()) {
            bookbean.updateStudent(student, "Andrzej", 22);
        };
        for (Student student : bookbean.findAllStudents()) {
            Assert.assertNotNull("Should be not null", student.getName());
            Assert.assertNotNull("Should be not null", student.getAge());
            assertEquals("testing student age", "22", student.getAge().toString());
            assertEquals("name test", "Andrzej", student.getName());
        };
    }
        /**
     * testing the delete operation
     */
    @Test
    public void testDelete() {
        for (Student student : bookbean.findAllStudents()) {
            bookbean.deleteStudent(student);
        }
        Assert.assertEquals("Size should be exactly 0", 0, bookbean.findAllBooks().size());
    }
    /**
     * Deleting the Student objects created for the tests
     */
    @After
    public void destroy() {
        //ss.getStudents().clear();
        for (Student s : bookbean.findAllStudents()) {
            bookbean.deleteStudent(s);
        }
    }

}
