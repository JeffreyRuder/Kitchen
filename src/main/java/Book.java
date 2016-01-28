import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Book {
  private String mTitle;
  private int mId;

  public Book(String title) {
    mTitle = title;
  }

  public String getTitle() {
    return mTitle;
  }

  public int getId() {
    return mId;
  }

  @Override
  public boolean equals(Object otherBook) {
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book book = (Book) otherBook;
      return this.getTitle().equals(book.getTitle());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books(title) VALUES (:title)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("title", mTitle)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Book> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, title AS mTitle FROM books ORDER BY title";
      List<Book> books = con.createQuery(sql).executeAndFetch(Book.class);
      return books;
    }
  }

  public static Book find(int searchId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, title AS mTitle FROM books WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", searchId)
        .executeAndFetchFirst(Book.class);
    }
  }

  public void update(String newTitle) {
    mTitle = newTitle;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET title = :newtitle WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newtitle", newTitle)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String deleteAuthorships = "DELETE FROM authors_books WHERE book_id = :id;";
      String deleteGenreRelationships = "DELETE FROM books_genres WHERE book_id = :id;";
      String deleteCopies = "DELETE FROM copies WHERE book_id = :id;";
      String deleteBook = "DELETE FROM books WHERE id = :id;";

      con.createQuery(deleteAuthorships + deleteGenreRelationships + deleteCopies + deleteBook)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  //TODO: Prevent deletion if any copies are checked out

  public List<Author> getAllAuthors() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT authors.id AS mId, first_name AS mFirstName, last_name AS mLastName FROM authors " +
                   "JOIN authors_books ON (authors.id = authors_books.author_id) " +
                   "WHERE authors_books.book_id = :id " +
                   "ORDER BY authors.last_name, authors.first_name";
      return con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Author.class);
    }
  }

  public String getAuthorsString() {
    List<Author> authors = this.getAllAuthors();
    String results = "|";
    for ( Author author : authors ) {
      String newAuthor = author.getFullName();
      results += (" " + newAuthor + " |");
    }
    return results;
  }

  public boolean assignAuthor(int authorId) {
    boolean isNotDuplicate = true;
    for (Author author : this.getAllAuthors()) {
      if (author.getId() == authorId) {
        isNotDuplicate = false;
      }
    }
    if (isNotDuplicate) {
      try (Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO authors_books (author_id, book_id) VALUES " +
                     "(:author_id, :book_id)";
        con.createQuery(sql)
          .addParameter("author_id", authorId)
          .addParameter("book_id", mId)
          .executeUpdate();
      }
    }
    return isNotDuplicate;
  }

  public void removeAuthor(int authorId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM authors_books WHERE author_id = :author_id AND book_id = :book_id";
      con.createQuery(sql)
        .addParameter("author_id", authorId)
        .addParameter("book_id", mId)
        .executeUpdate();
    }
  }

  public List<Copy> getAllCopies() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, book_id AS mBookId FROM copies " +
                   "WHERE book_id = :id ";
      return con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Copy.class);
    }
  }
  public List<Copy> getAvailableCopies() {
    ArrayList<Copy> availableCopies = new ArrayList<Copy>();
    for (Copy copy : this.getAllCopies()) {
      if (!(copy.isCheckedOut())) {
        availableCopies.add(copy);
      }
    }
    return availableCopies;
  }

  public boolean hasCopyAvailable() {
    if (this.getAvailableCopies().size() >= 1) {
      return true;
    } else {
      return false;
    }
  }

}
