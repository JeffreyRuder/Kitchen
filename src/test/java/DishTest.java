import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class DishTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void equals_returnsTrueIfSameName() {
    Dish firstDish = new Dish("Cheeseburger");
    firstDish.save();
    Dish secondDish = new Dish("Cheeseburger");
    secondDish.save();
    assertTrue(firstDish.equals(secondDish));
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Dish.all().size(), 0);
  }

  @Test
  public void dish_instantiatesNameAndFindsItInListOfDishes() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    assertEquals("Cheeseburger", Dish.find(dish.getId()).getName());
  }

  @Test
  public void delete_deletesADish() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    Dish anotherDish = new Dish("Turkey Burger");
    anotherDish.save();
    dish.delete();
    assertEquals(1, Dish.all().size());
  }

  @Test
  public void update_updateWorksProperly() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    dish.update("Turkey Burger");
    assertEquals(dish.getName(), "Turkey Burger");
  }

  @Test
  public void addIngredient_addsIngredientToDishesIngredientsTable() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    Ingredient ingredient = new Ingredient("Ground Beef", "Ounce", 20, 5);
    ingredient.save();
    dish.addIngredient(ingredient.getId(), 8);
    Ingredient[] ingredients = new Ingredient[] {ingredient};
    assertTrue(dish.getAllIngredients().containsAll(Arrays.asList(ingredients)));
  }

  @Test
  public void removeIngredient_removesAnIngredientFromDishesIngredientsTable() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    Ingredient ingredient = new Ingredient("Ground Beef", "Ounce", 20, 5);
    ingredient.save();
    dish.addIngredient(ingredient.getId(), 8);
    Ingredient ingredientTwo = new Ingredient("Ground Beef", "Ounce", 20, 5);
    ingredientTwo.save();
    dish.addIngredient(ingredientTwo.getId(), 8);
    dish.removeIngredient(ingredient.getId());
    Ingredient[] ingredients = new Ingredient[] {ingredientTwo};
    assertTrue(dish.getAllIngredients().containsAll(Arrays.asList(ingredients)));
    assertEquals(dish.getAllIngredients().size(), 1);
  }

  @Test
  public void getTimesOrderedToday_getsACountOfDishOrderedToday_2() {
    Dish dishOne = new Dish("Cheeseburger");
    dishOne.save();
    Dish dishTwo = new Dish("Hamburger");
    dishTwo.save();
    Order orderOne = new Order(1, 1, dishOne.getId());
    orderOne.save();
    Order orderTwo = new Order(1, 2, dishOne.getId());
    orderTwo.save();
    Order orderThree = new Order(1, 3, dishTwo.getId());
    orderThree.save();
    assertEquals(dishOne.getTimesOrderedToday(), 2);
  }

  @Test
  public void hasMissingIngredient_indicatesDishMissingIngredient_true() {
    Dish dish = new Dish("Cheeseburger");
    dish.save();
    Ingredient ingredient = new Ingredient("Ground Beef", "Ounce", 20, 5);
    ingredient.save();
    dish.addIngredient(ingredient.getId(), 8);
    Inventory inventory = new Inventory(ingredient.getId(), 0);
    inventory.save();
    assertEquals(true, Dish.find(dish.getId()).hasMissingIngredient());
  }

  @Test
  public void getAllRecipes_getsAllRecipesForDish() {
    Dish dish = new Dish("Hot Dog");
    dish.save();
    Ingredient ingredient = new Ingredient("Sausage", "Unit", 20, 5);
    ingredient.save();
    Ingredient secondIngredient = new Ingredient("Buns", "Pair", 20, 10);
    secondIngredient.save();
    dish.addIngredient(ingredient.getId(), 1);
    dish.addIngredient(secondIngredient.getId(), 1);
    assertEquals(2, dish.getAllRecipes().size());
    assertTrue(dish.getAllRecipes().get(1) instanceof Recipe);
    assertEquals(1, dish.getAllRecipes().get(1).getIngredientAmount());
  }

}
