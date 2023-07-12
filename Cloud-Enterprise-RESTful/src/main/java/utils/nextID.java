package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.BookDAO;

public class nextID {

	public static int getNextBookId() throws SQLException {
	    BookDAO bookDao = new BookDAO();

	    int[] bookIds = {};

	    Connection conn = bookDao.getConnection();
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT id FROM books");
	    ArrayList<Integer> ids = new ArrayList<Integer>();
	    while (rs.next()) {
	        ids.add(rs.getInt("id"));
	    }
	    bookIds = ids.stream().mapToInt(i -> i).toArray();
	    conn.close();

	    int n = bookIds.length;
	    boolean[] used = new boolean[n + 1];
	    for (int i = 0; i < n; i++) {
	        if (bookIds[i] > 0 && bookIds[i] <= n) {
	            used[bookIds[i]] = true;
	        }
	    }
	    for (int i = 1; i <= n; i++) {
	        if (!used[i]) {
	            return i;
	        }
	    }
	    return n + 1;
	}
}


