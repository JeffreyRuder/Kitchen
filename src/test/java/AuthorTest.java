import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Author.all().size());
  }

  @Test
  public void author_instantiateWithName() {
    Author author = new Author("Mark", "Twain");
    author.save();
    assertEquals("Mark Twain", Author.find(author.getId()).getFullName());
  }

  @Test
  public void author_updateWorksProperly() {
    Author author = new Author("Mark", "Twain");
    author.save();
    author.update("Samuel", "Clemens");
    assertEquals(author.getFullName(), "Samuel Clemens");
    assertEquals("Samuel Clemens", Author.find(author.getId()).getFullName());
  }

  @Test
  public void author_deleteWorksProperly_0() {
    Author author = new Author("Mark", "Twain");
    author.save();
    author.delete();
    assertEquals(0, Author.all().size());
  }

  @Test
  public void assignBook_assignsAuthorToBook_true() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    Author author = new Author("Mark", "Twain");
    author.save();
    boolean successfulAssignment = author.assignBook(book.getId());
    assertTrue(Author.find(author.getId()).getAllBooks().contains(book));
    assertTrue(successfulAssignment);
  }

  @Test
  public void removeBook_removesBookFromAuthor_0() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    Author author = new Author("Mark", "Twain");
    author.save();
    boolean successfulAssignment = author.assignBook(book.getId());
    author.removeBook(book.getId());
    assertEquals(0, Author.find(author.getId()).getAllBooks().size());
  }

}
