import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }


  @Test
  public void equals_returnsTrueIfSameObject() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    Ingredient ingredient = new Ingredient("Ground Beef", "Ounce", 20, 5);
    ingredient.save();
    Recipe firstRecipe = new Recipe(dish.getId(), ingredient.getId(), 1);
    firstRecipe.save();
    Recipe secondRecipe = new Recipe(dish.getId(), ingredient.getId(), 1);
    secondRecipe.save();
    assertTrue(firstRecipe.equals(secondRecipe));
  }

  @Test
  public void recipe_instantiatesIdsAndFindsDishIdInListOfRecipes() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    Ingredient ingredient = new Ingredient("Ground Beef", "Ounce", 20, 5);
    ingredient.save();
    Recipe recipe = new Recipe(dish.getId(), ingredient.getId(), 1);
    recipe.save();
    assertEquals(Recipe.find(recipe.getId()).getDishId(), dish.getId());
    assertEquals(Recipe.find(recipe.getId()).getIngredientId(), ingredient.getId());
  }

  @Test
  public void getDishName_returnsDishName() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    Ingredient ingredient = new Ingredient("Ground Beef", "Ounce", 20, 5);
    ingredient.save();
    Recipe recipe = new Recipe(dish.getId(), ingredient.getId(), 1);
    recipe.save();
    assertEquals("Cheeseburger", recipe.getDishName());
  }

  @Test
  public void getIngredientName_returnsIngredientName() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    Ingredient ingredient = new Ingredient("Ground Beef", "Ounce", 20, 5);
    ingredient.save();
    Recipe recipe = new Recipe(dish.getId(), ingredient.getId(), 1);
    recipe.save();
    assertEquals("Ground Beef", recipe.getIngredientName());
  }

}
