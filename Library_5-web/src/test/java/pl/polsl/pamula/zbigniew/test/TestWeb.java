/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.pamula.zbigniew.test;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import org.junit.Test;
import org.junit.Before;

/**
 * Web testing unit
 *
 * @author Zbigniew Pamuła
 * @version 1.0
 */
public class TestWeb {

    /**
     * setup
     */
    @Before
    public void setup() {
//        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);    // use HtmlUnit
//        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_WEBDRIVER);    // use WebDriver
        setBaseUrl("http://localhost:8080/Library_5-web/faces/");
    }
    
    @Test
    public void testIndexPage() {
        beginAt("createBook.xhtml");       
          
    }
    
    /**
     * testing createBook page
     */
    @Test
    public void testCreateBook() {
        beginAt("createBook.xhtml");
        assertFormElementPresent("bookName");
        assertFormElementPresent("bookAuthor");
        setTextField("bookName", "Dzuma");
        setTextField("bookAuthor", "Camus");
        submit();

    }

    /**
     * testing createStudent page
     */
    @Test
    public void testCreateStudent() {
        beginAt("createStudent.xhtml");
        assertFormElementPresent("studentName");
        assertFormElementPresent("studentAge");
        setTextField("studentName", "Jan");
        setTextField("studentAge", "25");
        submit();
    }

    /**
     * testing deleteBook page
     */
    @Test
    public void testDeleteBook() {
        beginAt("deleteBook.xhtml");
//        assertSelectedOptionEquals("selectBook", "aaa");
        submit();
    }

    /**
     * testing deleteStudent page
     */
    @Test
    public void testDeleteStudent() {
        beginAt("deleteStudent.xhtml");
//        assertSelectedOptionEquals("selectStudent", "Andrzej"); //should be the first one
        submit();
    }

    /**
     * testing updateBook page
     */
    @Test
    public void testUpdateBook() {
        beginAt("updateBook.xhtml");
//        assertSelectedOptionEquals("chooseBook", "aaa");
        assertFormElementPresent("bookName");
        assertFormElementPresent("bookAuthor");
        setTextField("bookName", "Dzuma");
        setTextField("bookAuthor", "Camus");
        submit();
    }

    /**
     * testing updateStudent page
     */
    @Test
    public void testUpdateStudent() {
        beginAt("updateStudent.xhtml");
//        assertSelectedOptionEquals("selectStudent", "Andrzej");
        assertFormElementPresent("studentName");
        assertFormElementPresent("studentAge");
        setTextField("studentName", "Jan");
        setTextField("studentAge", "25");
        submit();
    }
}
