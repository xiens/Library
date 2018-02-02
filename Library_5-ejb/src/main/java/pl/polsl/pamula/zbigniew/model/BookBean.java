package pl.polsl.pamula.zbigniew.model;

import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Model executing CRUD operations on class Book
 *
 * @author Zbigniew Pamuła
 * @version 1.0
 */
@Stateless
@LocalBean
public class BookBean {

    /**
     * entity manager object
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Creates a new book in database
     *
     * @param book Book object
     */
    public void createBook(Book book) {

        em.persist(book);
    }

    /**
     * Adds book object to the database, and sets the Student that lents this
     * book
     *
     * @param student student objec
     * @param book book object
     */
    public void addBookToStudent(Student student, Book book) {
        book.setStudent(student);
        //add book to book list in student
        List<Book> books = student.getBooks();
        books.add(book);
        em.merge(book);
        em.merge(student);
    }
//     public void addBookToStudent(Student student, Book book){
//         book.setStudent(student);
//         em.persist(book);
//     }

    /**
     * Deletes book from Student list of books, and from Book studentId
     *
     * @param student student object
     * @param book book object
     */
    public void deleteBookFromStudent(Student student, Book book) {

        book.setStudent(null);
        //delete book from student's list of books
        List<Book> books = student.getBooks();
        for (Book b : books) {
            if (Objects.equals(b.getId(), book.getId())) {
                books.remove(b);
            }
        }
        em.merge(student);
        em.merge(book);
    }

    /**
     * Finds all books lent by student using named query without parameters
     *
     * @param student student object
     * @return list of all books satisfying the named query
     */
    public List<Book> listStudentBooks(Student student) {

        return em.createNamedQuery("Book.selectBooks").setParameter("given_id", (student.getStudentID())).getResultList();
        //return em.createNamedQuery("Book.selectBooks").getResultList();
    }

    /**
     * Finds book with a specified id in database
     *
     * @param bookId id of book
     * @return book Book object
     */
    public Book readBookById(Integer bookId) {
        Book book = em.find(Book.class, bookId);
        return book;
    }

    /**
     * updates book with bookName and bookAuthor in the database checks if
     * student was also added, if his id is not null, then assigns him to this
     * book
     *
     * @param book chosen book
     * @param bookName book name
     * @param bookAuthor author name
     * @param student Student to assign the book to
     */
    public void updateBook(Book book, String bookName, String bookAuthor, Student student) {
        Integer studentId=null;
        if(student!=null){
        studentId = student.getStudentID();
        }
        book.setTitle(bookName);
        book.setAuthor(bookAuthor);
        if (studentId != null) {
            book.setStudent(student);
            //add book to list of student's books
            List<Book> books = student.getBooks();
            books.add(book);
            student.setBooks(books);
            em.merge(student);
        }

        em.merge(book);
    }

    /**
     * deletes book from the database
     *
     * @param book, chosen book
     */
    public void deleteBook(Book book) {
        em.remove(em.merge(book));
    }

    /**
     * Finds all books using named query without parameters
     *
     * @return list of all books satisfying the named query
     */
    public List<Book> findAllBooks() {

        return em.createNamedQuery("Book.selectAll").getResultList();
    }

    /**
     * Finds all books using criteria query
     *
     * @return list of all books satisfying the query
     */
    public List<Book> findAllBooks2() {

        Root<Book> root;
        Expression<String> title;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);

        root = cq.from(Book.class);
        cq.select(root);
        title = root.get("title");
        cq.where(cb.like(title, "D%"));
        return em.createQuery(cq).getResultList();
    }

    //////////////////////////////////////////////////////////
    //////////////Student class operations/////////////////////////
    /////////////////////////////////////////////////////////
    /**
     * Creates a new student in the database
     *
     * @param student student object
     */
    public void createStudent(Student student) {

        em.persist(student);
    }

    /**
     * Finds student with a specified id in database
     *
     * @param studentId id of student
     * @return student student object
     */
    public Student readStudentById(Integer studentId) {

        Student student = em.find(Student.class, studentId);
        return student;
    }

    /**
     * updates student with studentName and studentAge in the database
     *
     * @param student, chosen student
     * @param studentName student name
     * @param studentAge student age
     */
    public void updateStudent(Student student, String studentName, Integer studentAge) {

        student.setName(studentName);
        student.setAge(studentAge);
        em.merge(student);

    }

    /**
     * deletes student from the database
     *
     * @param student, chosen student
     */
    public void deleteStudent(Student student) {

        em.remove(em.merge(student));
    }

    /**
     * Finds all students using named query without parameters
     *
     * @return list of all students satisfying the named query
     */
    public List<Student> findAllStudents() {

        return em.createNamedQuery("Student.selectAll").getResultList();
    }

    /**
     * closes the EntityManager
     */
    public void exitDatabase() {
        em.close();
    }
}
