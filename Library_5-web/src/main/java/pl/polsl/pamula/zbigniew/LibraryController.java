package pl.polsl.pamula.zbigniew;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import pl.polsl.pamula.zbigniew.model.Book;
import pl.polsl.pamula.zbigniew.model.BookBean;
import pl.polsl.pamula.zbigniew.model.Student;

/**
 * Library controller
 *
 * @author Zbigniew Pamuła
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class LibraryController {

    /**
     * injecting database provider for Book entity
     */
    @EJB
    private BookBean bookbean;
    /**
     * Instance of Book class
     */
    private Book book;
    /**
     * Instance of Student class
     */
    private Student student;
    /**
     * List of all books
     */
    private List<Book> books;
    /**
     * List of students
     */
    private List<Student> students;
    /**
     * List of student's books
     */
    private List<Book> studentBooks;
    /**
     * Message for information about wrong or correct input
     */
    private String message;

    /**
     * Initialization of book and student objects Also the lists of students and
     * books are filled in with data from the database
     */
    @PostConstruct
    protected void init() {
        message = "no data provided";
        book = new Book();
        student = new Student();
        //studentBooks = bookbean.listStudentBooks(student);
        //books = student.getBooks();
        studentBooks = new ArrayList<>();
        books = bookbean.findAllBooks();
        students = bookbean.findAllStudents();
        System.out.println("Library controller initialized");
    }

    public List<Book> getStudentBooks() {
        return studentBooks;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book getBook() {
        return book;
    }

    public Student getStudent() {
        return student;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * creates book object using database provider prints message using growl
     */
    public void createBook() {
        bookbean.createBook(book);
        setMessage("Created book: " + book.getTitle() + " written by " + book.getAuthor());
        saveMessage();
    }

    /**
     * creates student object using database provider 
     * prints message using growl
     */
    public void createStudent() {
        bookbean.createStudent(student);
        setMessage("Created student: " + student.getName() + ", " + student.getAge().toString());
        saveMessage();
    }

    /**
     * updates student object using database provider 
     * prints message using growl
     */
    public void updateStudent() {
        Integer studentAge = student.getAge();
        String studentName = student.getName();
        bookbean.updateStudent(student, studentName, studentAge);
        setMessage("Student updated");
        saveMessage();
    }

    /**
     * updates book object using database provider 
     * prints message using growl
     */
    public void updateBook() {
        Integer bookId = book.getId();
        Integer studentId = student.getStudentID();//added
        String bookName, bookAuthor;
        bookName = book.getTitle();
        bookAuthor = book.getAuthor();
        book = bookbean.readBookById(bookId);
        if (studentId != null) {
            student = bookbean.readStudentById(studentId);//added
        }
        bookbean.updateBook(book, bookName, bookAuthor, student);
        setMessage("Book updated");
        saveMessage();
    }

    /**
     * edits student's books and updates the book object using database provider
     * prints message using growl
     */
    public void editStudentBooks() {
        String bookName, bookAuthor;
        bookName = book.getTitle();
        bookAuthor = book.getAuthor();
        Integer bookId = book.getId();
        Integer studentId = student.getStudentID();
        if (bookId != null && studentId != null) {
            book = bookbean.readBookById(bookId);
            student = bookbean.readStudentById(studentId);
            bookbean.updateBook(book, bookName, bookAuthor, student);
        }
        setMessage("Student's book edited");
        saveMessage();
    }

    /**
     * deletes student object using database provider 
     * prints message using growl
     */
    public void deleteStudent() {
        Integer studentID = student.getStudentID();
        if (studentID != null) {
            student = bookbean.readStudentById(studentID);
            bookbean.deleteStudent(student);
        }
        setMessage("Student deleted");
        saveMessage();
    }

    /**
     * deletes book object using database provider 
     * prints message using growl
     */
    public void deleteBook() {
        Integer bookID = book.getId();
        if (bookID != null) {
            book = bookbean.readBookById(bookID);
            bookbean.deleteBook(book);
        }
        setMessage("Book deleted");
        saveMessage();

    }

    /**
     * adds book object to chosen student using database provider 
     * prints message using growl
     */
    public void addBookToStudent() {
        Integer bookId = book.getId();
        Integer studentId = student.getStudentID();
        if (bookId != null && studentId != null) {
            book = bookbean.readBookById(bookId);
            student = bookbean.readStudentById(studentId);
            bookbean.addBookToStudent(student, book);
        }
        setMessage("Book " + book.getTitle() + " written by " + book.getAuthor() + " added to " + student.getName());
        saveMessage();
    }

    /**
     * deletes book object from student's list of books and sets book's
     * studentId to null using database provider 
     * prints message using growl
     */
    public void deleteStudentBook() {
        Integer bookId = book.getId();
        Integer studentId = student.getStudentID();
        if (bookId != null && studentId != null) {
            book = bookbean.readBookById(bookId);
            student = bookbean.readStudentById(studentId);
            studentBooks = bookbean.listStudentBooks(student);
            System.out.println("student id:");
            System.out.println(studentId);
            System.out.println("book id:");
            System.out.println(bookId);
            student.setBooks(studentBooks);
            bookbean.deleteBookFromStudent(student, book);
        }
        
        setMessage("Deleted book ");
        saveMessage();
    }

    /**
     * lists books lent by chosen student using database provider
     */
    public void listStudentBooks() {

        Integer studentId = student.getStudentID();
        if (studentId != null) {
            student = bookbean.readStudentById(studentId);
            studentBooks = bookbean.listStudentBooks(student);
        }
    }
    /**
     * Saves message in FacesContext
     */
    public void saveMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "Your message: " + message));
    }
}
