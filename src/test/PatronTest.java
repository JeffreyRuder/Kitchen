import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void patron_instantiatesCorrectly() {
    Patron patron = new Patron("Joe", "Borrower", 5551212);
    patron.save();
    assertEquals("Joe Borrower", Patron.find(patron.getId()).getFullName());
    assertEquals(5551212, Patron.find(patron.getId()).getPhone());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Patron.all().size());
  }

  @Test
  public void patron_updateNameWorksProperly() {
    Patron patron = new Patron("Mark", "Twain", 5551212);
    patron.save();
    patron.updateName("Samuel", "Clemens");
    assertEquals(patron.getFullName(), "Samuel Clemens");
    assertEquals("Samuel Clemens", Patron.find(patron.getId()).getFullName());
  }

  @Test
  public void patron_updatePhoneWorksProperly() {
    Patron patron = new Patron("Mark", "Twain", 5551212);
    patron.save();
    patron.updatePhone(4442121);
    assertEquals(patron.getPhone(), 4442121);
    assertEquals(4442121, Patron.find(patron.getId()).getPhone());
  }

  @Test
  public void patron_deleteWorksProperly_0() {
    Patron patron = new Patron("Mark", "Twain");
    patron.save();
    patron.delete();
    assertEquals(0, Patron.all().size());
  }
}
