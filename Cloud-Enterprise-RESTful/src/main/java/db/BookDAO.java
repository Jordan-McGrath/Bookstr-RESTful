package db;

/*
 * Book DAO connected to a mudfoot server to retrieve data. 
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import models.Book;
import utils.nextID;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;



public class BookDAO {

	Book oneBook = null;
	Connection conn = null;
	Statement stmt = null;
	String user = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";
	String url = "YOU_MySQL_DATABASE_URL" + user;
	

	public BookDAO() {
	}

	
	private void openConnection() {
		// loading jdbc driver for mysql
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}

		// connecting to database
		try {
			// connection string for demos database, username demos, password demos
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Method for getting

	private Book getNextBook(ResultSet rs) {
		Book thisBook = null;
		try {
			thisBook = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("date"),
					rs.getString("genres"), rs.getString("characters"), rs.getString("synopsis"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return thisBook;
	}

	public ArrayList<Book> getAllBooks() {

		ArrayList<Book> allBooks = new ArrayList<Book>();
		openConnection();

		// Create select statement and execute it
		try {
			String selectSQL = "select * from books";
			ResultSet rs = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while (rs.next()) {
				oneBook = getNextBook(rs);
				allBooks.add(oneBook);
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return allBooks;
	}

	public Book getBookByID(int id) {

		openConnection();
		oneBook = null;
		// Create select statement and execute it
		try {
			String selectSQL = "select * from books where id=" + id;
			ResultSet rs = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while (rs.next()) {
				oneBook = getNextBook(rs);
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return oneBook;
	}

	/*
	 * Insert Book method using prepared statements. The ID for a new book is auto populated
	 * with the next available id using 'nextID' function.
	 */

	public boolean insertBook(Book b) {
		boolean success = false;
		openConnection();
		try {
			int nextId = nextID.getNextBookId();
			String sql = "INSERT INTO books (id, title, author, date, genres, characters, synopsis) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nextId);
			pstmt.setString(2, b.getTitle());
			pstmt.setString(3, b.getAuthor());
			pstmt.setString(4, b.getDate());
			pstmt.setString(5, b.getGenres());
			pstmt.setString(6, b.getCharacters());
			pstmt.setString(7, b.getSynopsis());
			int rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
				success = true;
			}
			pstmt.close();

			// Create a catch and close connection
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			closeConnection();
		}
		return success;
	}

	/*
	 * Update book method using prepared statements
	 */

	public boolean updateBook(Book b) {
		boolean success = false;
		openConnection();
		try {
			String query = "UPDATE books SET id = ?, title = ?, author = ?, date = ?, genres = ?, characters = ?, synopsis = ? WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, b.getId());
			pstmt.setString(2, b.getTitle());
			pstmt.setString(3, b.getAuthor());
			pstmt.setString(4, b.getDate());
			pstmt.setString(5, b.getGenres());
			pstmt.setString(6, b.getCharacters());
			pstmt.setString(7, b.getSynopsis());
			pstmt.setInt(8, b.getId());
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				success = true;
			}
			pstmt.close();

			// Create a catch and close connection
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			closeConnection();
		}
		return success;
	}

	/*
	 * Delete book method
	 */

	public boolean deleteBook(int id) throws SQLException {
		boolean success = false;
		try {
			openConnection();
			String sql = "DELETE FROM books WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			// Check to see if ID
			int del = stmt.executeUpdate();

			// Statement to see if book has been deleted or print else statements if not

			if (del > 0) {
				System.out.println("Book with ID " + id + " deleted successfully.");
				success = true;
			} else {
				System.out.println("No book found with ID " + id + ".");
			}
			closeConnection();
		} catch (SQLException e) {
			throw new SQLException("Failed to delete book with ID " + id + ".");
		}
		return success;
	}

	/*
	 * Search book method using prepared statements
	 */

	public ArrayList<Book> searchBooks(String keyword) {
		ArrayList<Book> matchingBooks = new ArrayList<Book>();
		openConnection();
		try {
			String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR synopsis LIKE ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				matchingBooks.add(getNextBook(rs));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			closeConnection();
		}
		return matchingBooks;
	}

	public ArrayList<Book> randomBooks(String keyword) {
		ArrayList<Book> randBooks = new ArrayList<Book>();
		openConnection();
		try {

			Random randomGenerator = new Random();
			int randomId = randomGenerator.nextInt();

				String query = "SELECT * FROM books WHERE id LIKE ? ";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + randomId + "%");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					randBooks.add(getNextBook(rs));
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				closeConnection();
			}
			return randBooks;
		}
	
	}
