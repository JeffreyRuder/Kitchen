import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class CheckoutTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void checkout_instantiatesCorrectly() {
    Patron patron = new Patron("Bill", "Borrower", 5551212);
    patron.save();
    Book book = new Book("Huckleberry Finn");
    book.save();
    Copy copy = new Copy(book.getId());
    copy.save();
    patron.checkout(copy.getId(), "2016-01-27", "2016-02-15");
    Checkout firstItemCheckedOut = Checkout.all().get(0);
    Copy copyCheckedOut = Copy.find(firstItemCheckedOut.getCopyId());
    assertEquals("Huckleberry Finn", copyCheckedOut.getBookTitle());
    assertTrue(copy.equals(copyCheckedOut));
  }

  
}
