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

  @Test
  public void isCheckedOut_returnsTrueIfCopyIsCheckedOut() {
    Patron patron = new Patron("Mark", "Twain", 5551212);
    patron.save();
    Book book = new Book("Huckleberry Finn");
    book.save();
    Copy copy = new Copy(book.getId());
    copy.save();
    patron.checkout(copy.getId(), "2012-01-01", "2015-03-03");
    assertTrue(copy.isCheckedOut());
  }

  @Test
  public void getAllOverdue_returnsAllOverdueCopies() {
    Patron patron = new Patron("Mark", "Twain", 5551212);
    patron.save();
    Book book = new Book("Huckleberry Finn");
    book.save();
    Copy firstCopy = new Copy(book.getId());
    firstCopy.save();
    Copy secondCopy = new Copy(book.getId());
    secondCopy.save();
    patron.checkout(firstCopy.getId(), "2016-01-01", "2016-01-20");
    patron.checkout(secondCopy.getId(), "2016-01-01", "2016-01-30");
    assertEquals(1, Copy.getAllOverdue().size());
  }

  @Test
  public void getAllCheckedOut_returnsAllCheckedOutCopies() {
    Patron patron = new Patron("Mark", "Twain", 5551212);
    patron.save();
    Book book = new Book("Huckleberry Finn");
    book.save();
    Copy firstCopy = new Copy(book.getId());
    firstCopy.save();
    Copy secondCopy = new Copy(book.getId());
    secondCopy.save();
    patron.checkout(firstCopy.getId(), "2016-01-01", "2016-01-20");
    patron.checkout(secondCopy.getId(), "2016-01-01", "2016-01-30");
    assertEquals(2, Copy.getAllCheckedOut().size());
  }
}
