import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void patron_instantiatesWithoutName() {
    Patron patron = new Patron();
    patron.save();
    assertEquals(null, Patron.find(patron.getId()).getFirstName());
    assertEquals(true, Patron.find(patron.getId()).getIsActive());
  }

  @Test
  public void patron_instantiatesWithName() {
    Patron patron = new Patron("Jane", "Roe");
    patron.save();
    assertEquals("Jane Roe", Patron.find(patron.getId()).getFullName());
  }

  @Test
  public void equals_returnsTrueIfSameName_true() {
    Patron firstPatron = new Patron("Jane", "Roe");
    Patron secondPatron = new Patron("Jane", "Roe");
    assertTrue(firstPatron.equals(secondPatron));
  }

  @Test
  public void all_returnsCompleteListOfPatrons() {
    Patron firstPatron = new Patron("John", "Doe");
    Patron secondPatron = new Patron("Jane", "Roe");
    firstPatron.save();
    secondPatron.save();
    Patron[] patrons = new Patron[] {firstPatron, secondPatron};
    assertTrue(Patron.all().containsAll(Arrays.asList(patrons)));
  }

  @Test
  public void updateFirstName_changesNameOfPatronInDatabase() {
    Patron patron = new Patron("John", "Doe");
    patron.save();
    patron.updateFirstName("Larry");
    assertEquals("Larry Doe", Patron.find(patron.getId()).getFullName());
  }

  @Test
  public void updateLastName_changesNameOfPatronInDatabase() {
    Patron patron = new Patron("John", "Doe");
    patron.save();
    patron.updateFirstName("Larry");
    assertEquals("Larry Doe", Patron.find(patron.getId()).getFullName());
  }

  @Test public void updatePhone_updatesPhoneInDatabase() {
    Patron patron = new Patron();
    patron.save();
    patron.updatePhone("5551122");
    assertEquals("5551122", Patron.find(patron.getId()).getPhone());
  }

  @Test
  public void setIsActive_setsPatronActivityInDatabase() {
    Patron patron = new Patron("John", "Doe");
    patron.save();
    patron.setIsActive(false);
    assertEquals(false, Patron.find(patron.getId()).getIsActive());
  }
}
