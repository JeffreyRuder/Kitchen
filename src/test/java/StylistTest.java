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

}
