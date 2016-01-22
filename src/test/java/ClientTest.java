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

}
