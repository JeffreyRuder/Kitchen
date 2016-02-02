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
}
