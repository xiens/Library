package pl.polsl.pamula.zbigniew.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Book entity
 *
 * @author Zbigniew Pamuła
 * @version 1.0
 */

@NamedQueries(
     {
        @NamedQuery(name = "Book.selectAll", query="SELECT b FROM Book b"),
        //@NamedQuery(name = "Book.selectBooks", query="SELECT b FROM Book b JOIN Student s ON b.id=s.studentID WHERE s.studentID= :given_id")
         @NamedQuery(name = "Book.selectBooks", query="SELECT b FROM Book b JOIN b.student s WHERE s.studentID=:given_id")
     })

@Entity
@Table(name = "book_info")

public class Book implements Serializable {
    
    /**
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Book title
     */
    @Column(length = 50, name="title", nullable = false)
    private String title;
    /**
     * Book author
     */
    @Column(name = "author")
    private String author;
    /**
     * Book availability
     */
    @Column(name="isAvailable")
    private Boolean isAvailable;
    /**
     *  Student borrowing the book
     */
    //@ManyToMany
    //@JoinColumn(name = "book_id")
    //private List<Student> students = new ArrayList();
    
    @ManyToOne
    @JoinColumn(name = "studentID")
    /**
     * refers to the student borrowing this book
     */
    private Student student;
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
      
    
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    @Override
    public String toString(){

    StringBuilder sb = new StringBuilder();

    sb.append(title);
    sb.append(" ");
    sb.append(author);

    return sb.toString();
    }
    

    
    
    
    
    
    
}
