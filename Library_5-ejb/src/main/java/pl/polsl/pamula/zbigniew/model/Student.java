package pl.polsl.pamula.zbigniew.model;

import pl.polsl.pamula.zbigniew.model.Book;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * Student entity
 *
 * @author Zbigniew Pamuła
 * @version 1.0
 */

@NamedQueries(
     {
        @NamedQuery(name = "Student.selectAll", query="SELECT s FROM Student s"),        
        @NamedQuery(name = "SelectStudents", query = "SELECT s FROM Student s")
     })

@Entity
@Table(name = "students_info")
public class Student implements Serializable{
    /**
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentID;
    /**
     * Student name
     */
    @Column(name = "full_name", length = 50, nullable = false)
    private String name;
    /**
     * Student age
     */
    @Column(name = "age")
    private Integer age;
    /**
     *  Books lent by students
     */   
    //@ManyToMany(mappedBy = "students",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //private List<Book> books = new ArrayList<>();
    @OneToMany(mappedBy = "student", orphanRemoval = true,fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    /**
     * stores list of books
     */
    private List<Book> books;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.studentID);
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
        final Student other = (Student) obj;
        if (!Objects.equals(this.studentID, other.studentID)) {
            return false;
        }
        return true;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }
           
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }   
    
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(name);
        sb.append(" ");
        sb.append(age);

        return sb.toString();
    }

    
}
