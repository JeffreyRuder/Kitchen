import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void stylist_instantiatesWithName() {
    Stylist stylist = new Stylist("Charles", "Babbage");
    assertEquals("Charles Babbage", stylist.getFullName());
  }

  @Test
  public void equals_returnsTrueIfSameName() {
    Stylist firstStylist = new Stylist("Charles", "Babbage");
    Stylist secondStylist = new Stylist("Charles", "Babbage");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_savesStylistInDatabase() {
    Stylist stylist = new Stylist("Charles", "Babbage");
    stylist.save();
    assertEquals(stylist.getFullName(), Stylist.find(stylist.getId()).getFullName());
  }



}
