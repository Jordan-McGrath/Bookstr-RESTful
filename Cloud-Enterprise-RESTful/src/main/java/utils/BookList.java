package utils;

import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import models.Book;


@XmlAccessorType(XmlAccessType.FIELD) 
@XmlRootElement(name = "books") 

public class BookList {
@XmlElement(name = "book") private List<Book> bookList; public BookList() {}
public BookList(List<Book> bookList) {
            this.bookList = bookList;
       }
public List<Book> getBookList() {
            return bookList;
       }
public void setBookList(List<Book> bookList) {
            this.bookList = bookList;
       }
}