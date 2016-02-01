import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class InventoryTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Inventory.all().size());
  }

  @Test
  public void inventoryInitiatesCorrectly() {
    Ingredient ingredient = new Ingredient("Flour", "ounce", 800, 180);
    ingredient.save();
    Inventory inventory = new Inventory(ingredient.getId(), 50);
    inventory.save();
    assertEquals(1, inventory.all().size());
  }
}
