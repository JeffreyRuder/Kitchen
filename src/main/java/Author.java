import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Author {
  private int mId;
  private String mFirstName;
  private String mLastName;

  public int getId() {
    return mId;
  }

  public String getFirstName() {
    return mFirstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public String getFullName() {
    return mFirstName + " " + mLastName;
  }

  public Author(String firstName, String lastName) {
    mFirstName = firstName;
    mLastName = lastName;
  }

  @Override
  public boolean equals(Object otherAuthor) {
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getFirstName().equals(newAuthor.getFirstName()) &&
             this.getLastName().equals(newAuthor.getLastName()) &&
             this.getId() == newAuthor.getId();
    }
  }

  public static List<Author> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName " +
                   "FROM authors ORDER BY last_name, first_name";
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  public static Author find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName " +
                   "FROM authors WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Author.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors (first_name, last_name) VALUES (:first_name, :last_name)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("first_name", mFirstName)
        .addParameter("last_name", mLastName)
        .executeUpdate()
        .getKey();
    }
  }

  public void update(String firstName, String lastName) {
    mFirstName = firstName;
    mLastName = lastName;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE authors SET first_name = :first_name, last_name = :last_name " +
                   "WHERE id = :id";
      con.createQuery(sql)
        .addParameter("first_name", firstName)
        .addParameter("last_name", lastName)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String deleteAuthorships = "DELETE FROM authors_books WHERE author_id = :id;";
      String deleteAuthor = "DELETE FROM authors WHERE id = :id;";

      con.createQuery(deleteAuthorships + deleteAuthor)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public List<Book> getAllBooks() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT books.id AS mId, title AS mTitle FROM books " +
                   "JOIN authors_books ON (books.id = authors_books.book_id) " +
                   "WHERE authors_books.author_id = :id " +
                   "ORDER BY books.title";
      return con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Book.class);
    }
  }

  public boolean assignBook(int bookId) {
    boolean isNotDuplicate = true;
    for (Book book : this.getAllBooks()) {
      if (book.getId() == bookId) {
        isNotDuplicate = false;
      }
    }
    if (isNotDuplicate) {
      try (Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO authors_books (author_id, book_id) VALUES " +
                     "(:author_id, :book_id)";
        con.createQuery(sql)
          .addParameter("author_id", mId)
          .addParameter("book_id", bookId)
          .executeUpdate();
      }
    }
    return isNotDuplicate;
  }

  public void removeBook(int bookId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM authors_books WHERE author_id = :author_id AND book_id = :book_id";
      con.createQuery(sql)
        .addParameter("author_id", mId)
        .addParameter("book_id", bookId)
        .executeUpdate();
    }
  }
}
