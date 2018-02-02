package pl.polsl.pamula.zbigniew.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.polsl.pamula.zbigniew.model.Student;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-15T15:03:13")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile SingularAttribute<Book, Boolean> isAvailable;
    public static volatile SingularAttribute<Book, Student> student;
    public static volatile SingularAttribute<Book, String> author;
    public static volatile SingularAttribute<Book, Integer> id;
    public static volatile SingularAttribute<Book, String> title;

}