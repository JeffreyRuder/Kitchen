import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class StoreTest {

  @Test
  public void store_instantiatesWithName() {
    Store store = new Store("Payless");
    store.save();
    assertEquals("Payless", Store.find(store.getId()).getName());
  }

  @Test
  public void equals_returnsTrueIfSameName_true() {
    Store firstStore = new Store("Payless");
    Store secondStore = new Store("Payless");
    assertTrue(firstStore.equals(secondStore));
  }

}
