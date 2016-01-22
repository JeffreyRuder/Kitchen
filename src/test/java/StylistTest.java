import java.util.Arrays;

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
    assertEquals(stylist.getFullName(), Stylist.find(
      stylist.getId()).getFullName());
  }

  @Test
  public void all_returnsAllStylists() {
    Stylist firstStylist = new Stylist("Charles", "Babbage");
    Stylist secondStylist = new Stylist("Ada", "Lovelace");
    firstStylist.save();
    secondStylist.save();
    Stylist[] stylists = new Stylist[] {firstStylist, secondStylist};
    assertTrue(Stylist.all().containsAll(Arrays.asList(stylists)));
  }

  @Test
  public void allWithTrue_returnsOrderedListOfAllStylists() {
    Stylist firstStylist = new Stylist("Charles", "Babbage");
    Stylist secondStylist = new Stylist("Ada", "Lovelace");
    Stylist thirdStylist = new Stylist("Frances", "Allen");
    firstStylist.save();
    secondStylist.save();
    thirdStylist.save();
    assertTrue(Stylist.all(true).get(0).equals(thirdStylist));
  }

  @Test
  public void total_returnsTotalNumberOfStylists_3() {
    Stylist firstStylist = new Stylist("Charles", "Babbage");
    Stylist secondStylist = new Stylist("Ada", "Lovelace");
    Stylist thirdStylist = new Stylist("Frances", "Allen");
    firstStylist.save();
    secondStylist.save();
    thirdStylist.save();
    assertEquals((Integer)3, (Integer)Stylist.total());
  }

  @Test
  public void ratio_returnsCorrectRatio() {
    Client firstClient = new Client("John", "Doe");
    Client secondClient = new Client("Jane", "Roe");
    Client thirdClient = new Client("Burke", "Boe");
    firstClient.save();
    secondClient.save();
    thirdClient.save();
    Stylist firstStylist = new Stylist("Charles", "Babbage");
    Stylist secondStylist = new Stylist("Ada", "Lovelace");
    firstStylist.save();
    secondStylist.save();
    System.out.println(Stylist.ratio());
    assertEquals("1.5 : 1", Stylist.ratio());
  }

  @Test
  public void update_changesStylistNameInObjectAndDatabase() {
    Stylist stylist = new Stylist("Charles", "Babbage");
    stylist.save();
    stylist.update("Grace", "Hopper");
    assertEquals("Grace Hopper", stylist.getFullName());
    assertEquals("Grace Hopper", Stylist.find(
      stylist.getId()).getFullName());
  }

  @Test
  public void delete_removesStylistFromDatabase() {
    Stylist firstStylist = new Stylist("Charles", "Babbage");
    Stylist secondStylist = new Stylist("Ada", "Lovelace");
    firstStylist.save();
    secondStylist.save();
    firstStylist.delete();
    assertTrue(!(Stylist.all().contains(firstStylist)));
  }

}
