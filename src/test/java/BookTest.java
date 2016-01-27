import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Book.all().size());
  }

  @Test
  public void book_instantiateWithTitle() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    assertEquals("Huckleberry Finn", Book.find(book.getId()).getTitle());
  }

  @Test
  public void book_updateWorksProperly() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    book.update("Tom Sawyer");
    assertEquals(book.getTitle(), "Tom Sawyer");
    assertEquals("Tom Sawyer", Book.find(book.getId()).getTitle());
  }

  @Test
  public void book_deleteWorksProperly_0() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    book.delete();
    assertEquals(0, Book.all().size());
  }

  @Test
  public void assignAuthor_assignsAuthorToBook_true() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    Author author = new Author("Mark", "Twain");
    author.save();
    boolean successfulAssignment = book.assignAuthor(author.getId());
    assertTrue(Book.find(book.getId()).getAllAuthors().contains(author));
    assertTrue(successfulAssignment);
  }

  @Test
  public void removeAuthor_removesAuthorFromBook_0() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    Author author = new Author("Mark", "Twain");
    author.save();
    boolean successfulAssignment = book.assignAuthor(author.getId());
    book.removeAuthor(author.getId());
    assertEquals(0, Book.find(book.getId()).getAllAuthors().size());
  }

}
