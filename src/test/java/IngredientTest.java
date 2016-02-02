import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class IngredientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Ingredient.all().size());
  }

  @Test
  public void ingredientInitiatesCorrectly() {
    Ingredient ingredient = new Ingredient("Flour", "ounce", 800, 180);
    ingredient.save();
    Ingredient savedIngredient = Ingredient.find(ingredient.getId());
    assertEquals("Flour", ingredient.getName());
    assert(ingredient.equals(savedIngredient));
  }

  @Test
  public void delete_ingredientIsDeleted() {
    Ingredient ingredient = new Ingredient("Flour", "ounce", 800, 180);
    ingredient.save();
    ingredient.delete();
    assertEquals(0, Ingredient.all().size());
  }

  @Test
  public void update_ingredientIsUpdated() {
    Ingredient ingredient = new Ingredient("Flour", "ounce", 800, 180);
    ingredient.save();
    ingredient.update("Apple", "apple", 50, 21);
    assertEquals("Apple", Ingredient.find(ingredient.getId()).getName());
  }

}
