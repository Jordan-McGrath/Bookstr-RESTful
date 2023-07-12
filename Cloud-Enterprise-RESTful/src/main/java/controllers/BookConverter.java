package controllers;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import com.google.gson.Gson;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import models.Book;

public class BookConverter {

	// Convert to XML

	public String toXml(ArrayList<Book> books) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(BookList.class);
			System.out.println("JAXBContext created successfully");
			Marshaller marshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();
			BookList bl = new BookList(books);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(bl, sw);
			String toXML = sw.toString();
			return toXML;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Convert to JSON

	public String toJson(ArrayList<Book> books) {
		Gson gson = new Gson();
		return gson.toJson(books);
	}

	// Convert to Plain Text

	public String toText(ArrayList<Book> books) {
		StringBuilder sb = new StringBuilder();
		for (Book book : books) {
			sb.append(book.toString()).append(", ");
		}
		return sb.toString();
	}



		public Book fromXml(String xml) {
	        try {
	            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
	            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	            StringReader reader = new StringReader(xml);
	            Book book = (Book) unmarshaller.unmarshal(reader);
	            return book;
	        } catch (JAXBException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	   public Book fromJson(String json) {
	        Gson gson = new Gson();
	        Book book = gson.fromJson(json, Book.class);
	        return book;
	    }
	    
	    public Book fromText(String text) {
	        Book book = null;
	        try {
	            String[] lines = text.split("\\r?\\n");
	            String title = lines[0].substring(lines[0].indexOf(':') + 1).trim();
	            String author = lines[1].substring(lines[1].indexOf(':') + 1).trim();
	            String date = lines[2].substring(lines[2].indexOf(':') + 1).trim();
	            String genres = lines[3].substring(lines[3].indexOf(':') + 1).trim();
	            String characters = lines[4].substring(lines[4].indexOf(':') + 1).trim();
	            String synopsis = lines[5].substring(lines[5].indexOf(':') + 1).trim();
	            book = new Book(0, title, author, date, genres, characters, synopsis);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return book;
	    }

	}
