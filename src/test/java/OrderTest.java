import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class OrderTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void order_instantiatesAndSavesCorrectly() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Order order = new Order(1, 1, dish.getId());
    order.save();
    Order savedOrder = Order.find(order.getId());
    assertEquals("Hamburger", savedOrder.getDishName());
    assertEquals(order.getCreationDate(), savedOrder.getCreationDate());
    assertEquals(order.getCreationTime(), savedOrder.getCreationTime());
  }

  @Test
  public void order_instantiatesAndSavesCorrectlyWithPatron() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Patron patron = new Patron("Jon", "Snow");
    patron.save();
    Order order = new Order(1, 1, dish.getId(), patron.getId());
    order.save();
    Order savedOrder = Order.find(order.getId());
    assertEquals("Hamburger", savedOrder.getDishName());
    assertEquals(order.getCreationDate(), savedOrder.getCreationDate());
    assertEquals(order.getCreationTime(), savedOrder.getCreationTime());
    assertEquals(order.getPatronId(), savedOrder.getPatronId());
  }

  @Test
  public void complete_completesOrderCorrectly() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Order order = new Order(1, 1, dish.getId());
    order.save();
    order.pay();
    order.complete();
    Order savedOrder = Order.find(order.getId());
    assertTrue(order.checkPaid());
    assertEquals(order.getCompletionDate(), savedOrder.getCompletionDate());
    assertEquals(order.getCompletionTime(), savedOrder.getCompletionTime());
  }

  @Test
  public void equals_returnsTrueIfEquivalent() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Order firstOrder = new Order(1, 1, dish.getId());
    firstOrder.save();
    Order secondOrder = new Order(1, 1, dish.getId());
    secondOrder.save();
    assertTrue(firstOrder.equals(secondOrder));
  }

  @Test
  public void addComments_addsCommentsToOrder() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Order order = new Order(1, 1, dish.getId());
    order.save();
    order.addComments("Well done");
    assertEquals("Well done", Order.find(order.getId()).getComments());
  }

  @Test
  public void order_changeDish_changesDishOfOrder() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Dish secondDish = new Dish("Cheeseburger");
    secondDish.save();
    Order order = new Order(1, 1, dish.getId());
    order.save();
    order.changeDish(secondDish.getId());
    assertEquals("Cheeseburger", Order.find(order.getId()).getDishName());
  }

  @Test
  public void all_returnsAllOrders_true() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Order firstOrder = new Order(1, 1, dish.getId());
    firstOrder.save();
    Order secondOrder = new Order(1, 1, dish.getId());
    secondOrder.save();
    Order[] orders = new Order[] {firstOrder, secondOrder};
    assertTrue(Order.all().containsAll(Arrays.asList(orders)));
  }

  @Test
  public void getAllActive_returnsAllActiveOrders() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Order firstOrder = new Order(1, 1, dish.getId());
    firstOrder.save();
    Order secondOrder = new Order(1, 1, dish.getId());
    secondOrder.save();
    Order thirdOrder = new Order(1, 1, dish.getId());
    thirdOrder.save();
    firstOrder.pay();
    firstOrder.complete();
    Order check = Order.find(thirdOrder.getId());
    assertTrue(Order.getAllActive().contains(secondOrder));
    assertTrue(Order.getAllActive().contains(firstOrder));
    assertEquals(2, Order.getAllActive().size());
  }

  @Test
  public void completeAndStartDuplicate_endsAndRestartsOrder() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Order order = new Order(1, 1, dish.getId());
    order.save();
    order.completeAndStartDuplicate();
    assertEquals(1, Order.getAllActive().size());
    assertEquals(2, Order.all().size());
  }

  @Test
  public void make_appropriatelyDecrementsIngredientsOnHand() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Ingredient ingredient = new Ingredient("Ground Beef", "Pounds", 100, 5);
    ingredient.save();
    dish.addIngredient(ingredient.getId(), 1);
    Inventory inventory = new Inventory(ingredient.getId(), 1);
    inventory.save();
    Order order = new Order(1, 1, dish.getId());
    order.save();
    order.make();
    assertEquals(0, ingredient.getTotalOnHand());
  }

  @Test
  public void setIsUp_setsOrderStatusToUp_true() {
    Dish dish = new Dish("Hamburger");
    dish.save();
    Ingredient ingredient = new Ingredient("Ground Beef", "Pounds", 100, 5);
    ingredient.save();
    dish.addIngredient(ingredient.getId(), 1);
    Inventory inventory = new Inventory(ingredient.getId(), 1);
    inventory.save();
    Order order = new Order(1, 1, dish.getId());
    order.save();
    order.make();
    order.setIsUp();
    assertTrue(order.isUp());
  }
}
