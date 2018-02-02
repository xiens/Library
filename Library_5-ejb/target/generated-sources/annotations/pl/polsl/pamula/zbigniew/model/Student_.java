package pl.polsl.pamula.zbigniew.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.polsl.pamula.zbigniew.model.Book;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-15T15:03:13")
@StaticMetamodel(Student.class)
public class Student_ { 

    public static volatile SingularAttribute<Student, Integer> studentID;
    public static volatile ListAttribute<Student, Book> books;
    public static volatile SingularAttribute<Student, String> name;
    public static volatile SingularAttribute<Student, Integer> age;

}