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

  @Test
  public void delete_inventoryIsDeleted() {
    Ingredient ingredient = new Ingredient("Flour", "ounce", 800, 180);
    ingredient.save();
    Inventory inventory = new Inventory(ingredient.getId(), 50);
    inventory.save();
    inventory.delete();
    assertEquals(0, Inventory.all().size());
  }

  @Test
  public void update_inventoryIsUpdated() {
    Ingredient ingredient = new Ingredient("Flour", "ounce", 800, 180);
    ingredient.save();
    Inventory inventory = new Inventory(ingredient.getId(), 50);
    inventory.save();
    inventory.update(100);
    assertEquals(100, Inventory.find(inventory.getId()).getCurrentOnHand());
  }

  @Test
  public void getIngredients_ingredientsAttachedToInventoryAreReturned() {
    Ingredient ingredient = new Ingredient("Flour", "ounce", 800, 180);
    ingredient.save();
    Inventory inventory = new Inventory(ingredient.getId(), 50);
    inventory.save();
    assertEquals(1, inventory.getIngredients().size());
  }

  @Test
  public void updateExpiration_correctlySetsNewExpiration() {
    Ingredient ingredient = new Ingredient("Flour", "ounce", 800, 180);
    ingredient.save();
    Inventory inventory = new Inventory(ingredient.getId(), 50);
    inventory.save();
    inventory.updateExpiration("2000-01-01");
    assertEquals("2000-01-01", Inventory.find(inventory.getId()).getExpirationDate());
  }
}
