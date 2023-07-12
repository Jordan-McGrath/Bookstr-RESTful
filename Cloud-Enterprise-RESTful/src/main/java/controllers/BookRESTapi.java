package controllers;

import javax.servlet.annotation.WebServlet;
import db.BookDAO;
import models.Book;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/books" })
public class BookRESTapi extends HttpServlet {

	BookDAO dao = new BookDAO();
	BookConverter converter = new BookConverter();
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<Book> getBooks = null;
		String action = request.getParameter("action");

		if (action.equals("searchBooks")) {
			String bookSearch = request.getParameter("searchInput");
			getBooks = dao.searchBooks(bookSearch);

		} else {
			getBooks = dao.getAllBooks();
		}

		String format = request.getHeader("Accept");
		switch (format) {
		case "application/json":
			response.setContentType("application/json");
			String toJson = converter.toJson(getBooks);
			response.getWriter().write(toJson);
			break;
		case "text/plain":
			response.setContentType("text/plain");
			String toText = converter.toText(getBooks);
			response.getWriter().write(toText);
			break;
		default:
			response.setContentType("application/xml");
			response.setHeader("Content-Disposition", "attachment; filename=\"books.xml\"");
			String toXml = converter.toXml(getBooks);
			response.getWriter().write(toXml);
			break;
			
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String addFormat = request.getHeader("Content-Type");
	    StringBuilder sb = new StringBuilder();

	    switch (addFormat) {
	        case "application/xml":
	            response.setContentType("application/xml");
	            request.getReader().lines().forEach(sb::append);
	            String xmlBody = sb.toString();
	            Book addXml = converter.fromXml(xmlBody);
	            dao.insertBook(addXml);
	            break;
	        case "text/plain":
	            response.setContentType("text/plain");
	            BufferedReader reader = request.getReader();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	            String requestBody = sb.toString();
	            Book addText = converter.fromText(requestBody); // Convert plain text to Book object
	            dao.insertBook(addText);
	            break;
	        default:
	            response.setContentType("application/json");
	            request.getReader().lines().forEach(sb::append);
	            String jsonBody = sb.toString();
	            Book addJson = converter.fromJson(jsonBody);
	            dao.insertBook(addJson);
	            break;
	    }
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		String updateFormat = request.getHeader("Content-Type");
	    StringBuilder sb = new StringBuilder();

	    switch (updateFormat) {
	        case "application/xml":
	            response.setContentType("application/xml");
	            request.getReader().lines().forEach(sb::append);
	            String xmlBody = sb.toString();
	            Book updateXml = converter.fromXml(xmlBody);
	            dao.updateBook(updateXml);
	            break;
	        case "text/plain":
	            response.setContentType("text/plain");
	            BufferedReader reader = request.getReader();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	            String requestBody = sb.toString();
	            Book updateText = converter.fromText(requestBody); // Convert plain text to Book object
	            dao.updateBook(updateText);
	            break;
	        default:
	            response.setContentType("application/json");
	            request.getReader().lines().forEach(sb::append);
	            String jsonBody = sb.toString();
	            Book updateJson = converter.fromJson(jsonBody);
	            dao.updateBook(updateJson);
	            break;
	    }
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));

		try {
			dao.deleteBook(bookId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}