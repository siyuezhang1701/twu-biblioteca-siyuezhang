package com.twu.biblioteca;


import com.twu.biblioteca.module.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BibliotecaAppTest {

    PrintStream console = null;
    ByteArrayOutputStream bytes = null;
    BibliotecaApp bibliotecaApp;



    @Before
    public void init(){
        bytes = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));
    }


    @Test
    public void test() {
        assertEquals(1, 1);
    }

    @Test
    public void should_show_welcome_message(){
        bibliotecaApp = new BibliotecaApp();
        String s = "welcome!";
        bibliotecaApp.showWelcome(s);

        assertThat(bytes.toString(), containsString(s));
    }

    @Test
    public void should_list_book(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        bibliotecaApp.showBookList();

        assertThat(bytes.toString(), containsString(bookList.get(0).getName()));
    }

    @Test
    public void should_list_book_detail(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock", "Arthur Conan Doyle", "1886"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        bibliotecaApp.showBookList();

        assertThat(bytes.toString(), containsString(bookList.get(0).getAuthor()));
    }

    @Test
    public void should_show_menu(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock", "Arthur Conan Doyle", "1886"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        List<String> menu = new ArrayList<String>(){{
            add("List Books");
            add("Quiet");
        }};
        bibliotecaApp.setMenu(menu);
        bibliotecaApp.showMenu();

        assertThat(bytes.toString(), containsString(menu.get(0)));

        bytes = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));

        bibliotecaApp.doOption(1);
        assertThat(bytes.toString(), containsString(bookList.get(0).getAuthor()));
    }

    @Test
    public void should_show_message_when_choose_valid_option(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock", "Arthur Conan Doyle", "1886"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        List<String> menu = new ArrayList<String>(){{
            add("List Books");
            add("Quiet");
        }};
        bibliotecaApp.setMenu(menu);
        bibliotecaApp.doOption((menu.size()+1));


        assertThat(bytes.toString(), containsString("Select a valid option!"));
    }

    @Test
    public void should_check_out_book(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock", "Arthur Conan Doyle", "1886"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        List<String> menu = new ArrayList<String>(){{
            add("List Books");
            add("Check out a book");
            add("Quiet");
        }};
        bibliotecaApp.setMenu(menu);
        bibliotecaApp.checkOutBook(1);
        bibliotecaApp.showBookList();

        assertThat(bytes.toString().contains(bookList.get(0).getName()), is(false));
    }

    @Test
    public void should_check_out_a_book_successfully(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock", "Arthur Conan Doyle", "1886"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        List<String> menu = new ArrayList<String>(){{
            add("List Books");
            add("Check out a book");
            add("Quiet");
        }};
        bibliotecaApp.setMenu(menu);
        bibliotecaApp.checkOutBook(1);

        assertThat(bytes.toString(), containsString("Thank you! Enjoy the book"));
    }

    @Test
    public void should_not_checkout_invalid_book(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock", "Arthur Conan Doyle", "1886"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        List<String> menu = new ArrayList<String>(){{
            add("List Books");
            add("Check out a book");
            add("Quiet");
        }};
        bibliotecaApp.setMenu(menu);
        bibliotecaApp.checkOutBook(1);
        bibliotecaApp.checkOutBook(1);

        assertThat(bytes.toString(), containsString("That book is not available"));
    }

    @Test
    public void should_return_book(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock", "Arthur Conan Doyle", "1886"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        List<String> menu = new ArrayList<String>(){{
            add("List Books");
            add("Check out a book");
            add("Quiet");
        }};
        bibliotecaApp.setMenu(menu);
        bibliotecaApp.checkOutBook(1);
        bibliotecaApp.showBookList();

        assertThat(bytes.toString().contains(bookList.get(0).getName()), is(false));

        bytes = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));


        bibliotecaApp.returnBook(1);
        bibliotecaApp.showBookList();

        assertThat(bytes.toString().contains(bookList.get(0).getName()), is(true));
    }

    @Test
    public void should_return_book_successfully(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock", "Arthur Conan Doyle", "1886"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        List<String> menu = new ArrayList<String>(){{
            add("List Books");
            add("Check out a book");
            add("Quiet");
        }};
        bibliotecaApp.setMenu(menu);
        bibliotecaApp.checkOutBook(1);
        bibliotecaApp.returnBook(1);

        assertThat(bytes.toString(), containsString("Thank you for returning the book"));

    }

    @Test
    public void shouldR_not_return_invalid_book(){
        List<Book> bookList = new ArrayList<Book>(){{
            add(new Book("Sherlock", "Arthur Conan Doyle", "1886"));
        }};
        bibliotecaApp = new BibliotecaApp(bookList);
        List<String> menu = new ArrayList<String>(){{
            add("List Books");
            add("Check out a book");
            add("Quiet");
        }};
        bibliotecaApp.setMenu(menu);
        bibliotecaApp.returnBook(1);

        assertThat(bytes.toString(), containsString("That is not a valid book to return"));
    }


    @After
    public void tearDown() throws Exception {
        System.setOut(console);
    }

}