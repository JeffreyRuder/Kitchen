import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void client_instantiatesWithName() {
    Client client = new Client("Jane", "Roe");
    assertEquals("Jane Roe", client.getFullName());
  }

  @Test
  public void equals_returnsTrueIfSameName() {
    Client firstClient = new Client("John", "Doe");
    Client secondClient = new Client("John", "Doe");
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_savesClientInDatabase() {
    Client client = new Client("John", "Doe");
    client.save();
    assertEquals(client.getFullName(), Client.find(
      client.getId()).getFullName());
  }

  @Test
  public void all_returnsAllClients() {
    Client firstClient = new Client("John", "Doe");
    Client secondClient = new Client("Jane", "Roe");
    firstClient.save();
    secondClient.save();
    Client[] clients = new Client[] {firstClient, secondClient};
    assertTrue(Client.all().containsAll(Arrays.asList(clients)));
  }

  @Test
  public void allWithTrue_returnsOrderedListOfAllClients() {
    Client firstClient = new Client("John", "Doe");
    Client secondClient = new Client("Jane", "Roe");
    Client thirdClient = new Client("Burke", "Boe");
    firstClient.save();
    secondClient.save();
    thirdClient.save();
    assertTrue(Client.all(true).get(0).equals(thirdClient));
  }

  @Test
  public void total_returnsTotalNumberOfClients_3() {
    Client firstClient = new Client("John", "Doe");
    Client secondClient = new Client("Jane", "Roe");
    Client thirdClient = new Client("Burke", "Boe");
    firstClient.save();
    secondClient.save();
    thirdClient.save();
    assertEquals((Integer)3, (Integer)Client.total());
  }

  @Test
  public void update_changesClientNameInObjectAndDatabase() {
    Client client = new Client("John", "Doe");
    client.save();
    client.update("Max", "Moe");
    assertEquals("Max Moe", client.getFullName());
    assertEquals("Max Moe", Client.find(
      client.getId()).getFullName());
  }

  @Test
  public void delete_removesClientFromDatabase() {
    Client firstClient = new Client("John", "Doe");
    Client secondClient = new Client("Jane", "Roe");
    firstClient.save();
    secondClient.save();
    firstClient.delete();
    assertTrue(!(Client.all().contains(firstClient)));
  }

  @Test
  public void getStylistId_returnsStylistId() {
    Client client = new Client("John", "Doe");
    client.save();
    Stylist stylist = new Stylist("Ada", "Lovelace");
    stylist.save();
    client.assignStylist(stylist.getId());
    assertEquals(stylist.getId(), client.getStylistId());
  }

}
