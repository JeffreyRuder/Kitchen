import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class CopyTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void copy_instantiateWithBookId() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    Copy copy = new Copy(book.getId());
    copy.save();
    assertEquals(Copy.find(copy.getId()).getBookId(), book.getId());
  }

  @Test
  public void getBookTitle_returnsBookTitle() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    Copy copy = new Copy(book.getId());
    copy.save();
    assertEquals("Huckleberry Finn", copy.getBookTitle());
  }

  @Test public void getAllAuthors_returnsAllAuthors() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    Author author = new Author("Mark", "Twain");
    author.save();
    author.assignBook(book.getId());
    Copy copy = new Copy(book.getId());
    copy.save();
    assertTrue(copy.getAllAuthors().contains(author));
    assertEquals(1, copy.getAllAuthors().size());
  }

  @Test
  public void delete_deletesCopyFromDatabase_0() {
    Book book = new Book("Huckleberry Finn");
    book.save();
    Copy copy = new Copy(book.getId());
    copy.save();
    Copy.find(copy.getId()).delete();
    assertEquals(0, book.getAllCopies().size());
  }
}
