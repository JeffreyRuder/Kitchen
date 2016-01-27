import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Copy {
  private int mId;
  private int mBookId;

  public int getId() {
    return mId;
  }

  public int getBookId() {
    return mBookId;
  }

  public String getBookTitle() {
    return Book.find(mBookId).getTitle();
  }

  public List<Author> getAllAuthors() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT authors.id AS mId, first_name AS mFirstName, last_name AS mLastName FROM authors " +
                   "JOIN authors_books ON (authors.id = authors_books.author_id) " +
                   "WHERE authors_books.book_id = :id " +
                   "ORDER BY authors.last_name, authors.first_name";
      return con.createQuery(sql)
        .addParameter("id", mBookId)
        .executeAndFetch(Author.class);
    }
  }

  public Copy(int bookId) {
    mBookId = bookId;
  }

  @Override
  public boolean equals(Object otherCopy) {
    if (!(otherCopy instanceof Copy)) {
      return false;
    } else {
      Copy copy = (Copy) otherCopy;
      return this.getId() == copy.getId() &&
             this.getBookId() == copy.getBookId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO copies (book_id) VALUES (:book_id)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("book_id", mBookId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Copy find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, book_id AS mBookId " +
                   "FROM copies WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Copy.class);
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String deleteCheckouts = "DELETE FROM checkouts WHERE copy_id = :id;";
      String deleteCopy = "DELETE FROM copies WHERE id = :id;";

      con.createQuery(deleteCheckouts + deleteCopy)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }
}
