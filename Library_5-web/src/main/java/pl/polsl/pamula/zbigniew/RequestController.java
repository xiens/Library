/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.pamula.zbigniew;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import pl.polsl.pamula.zbigniew.model.Book;
import pl.polsl.pamula.zbigniew.model.BookBean;
import pl.polsl.pamula.zbigniew.model.Student;

/**
 *
 * @author xiens
 */
@ManagedBean
@RequestScoped
public class RequestController {

    /**
     * List of student's books
     */
    private List<Book> studentBooks;
    /**
     * book object
     */
    private Book book;
    /**
     * student object
     */
    private Student student;
    /**
     * library database bean
     */
    private BookBean bookbean;

    @PostConstruct
    public void init() {
        student = new Student();
        book = new Book();

    }

    public List<Book> getStudentBooks() {
        return studentBooks;
    }



    public Student getStudent() {
        return student;
    }

    public BookBean getBookBean() {
        return bookbean;
    }

    public void listStudentBooks() {

        Integer studentId = student.getStudentID();
        if (studentId != null) {
            student = bookbean.readStudentById(studentId);
            studentBooks = bookbean.listStudentBooks(student);
            //books = studentBooks;
        }
        System.out.println("books added to student's list");
    }

    public void deleteStudentBook() {
        Integer bookId = book.getId();
        Integer studentId = student.getStudentID();
        if (bookId != null && studentId != null) {
            book = bookbean.readBookById(bookId);
            student = bookbean.readStudentById(studentId);
            System.out.println("student id:");
            System.out.println(studentId);
            System.out.println("book id:");
            System.out.println(bookId);
            bookbean.deleteBookFromStudent(student, book);
        }
        studentBooks = bookbean.listStudentBooks(student);
        System.out.println("Book deleted from student");
    }
}
