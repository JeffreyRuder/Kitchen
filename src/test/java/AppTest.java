import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.*;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  //GETTING RESOURCES

  @Test
  public void activeOrdersPage_rendersActiveOrders() {
    Dish firstDish = new Dish("Tofu Dog", 2);
    Dish secondDish = new Dish("Penne Alfredo", 2);
    firstDish.save();
    secondDish.save();
    Order firstOrder = new Order(1, 1, firstDish.getId());
    firstOrder.save();
    Order secondOrder = new Order(1, 2, secondDish.getId());
    secondOrder.save();
    goTo("http://localhost:4567/servers/orders/active");
    assertThat(pageSource()).contains("Tofu Dog");
    assertThat(pageSource()).contains("Penne Alfredo");
  }

  @Test
  public void activeOrdersPageKitchen_rendersActiveOrders() {
    Dish firstDish = new Dish("Tofu Dog", 2);
    Dish secondDish = new Dish("Penne Alfredo", 2);
    firstDish.save();
    secondDish.save();
    Order firstOrder = new Order(1, 1, firstDish.getId());
    firstOrder.save();
    Order secondOrder = new Order(1, 2, secondDish.getId());
    secondOrder.save();
    goTo("http://localhost:4567/kitchen/orders/active");
    assertThat(pageSource()).contains("Tofu Dog");
    assertThat(pageSource()).contains("Penne Alfredo");
  }

  @Test
  public void newOrdersPage_rendersActiveOrders() {
    Dish firstDish = new Dish("Tofu Dog", 2);
    Dish secondDish = new Dish("Penne Alfredo", 2);
    firstDish.save();
    secondDish.save();
    Order firstOrder = new Order(1, 1, firstDish.getId());
    firstOrder.save();
    Order secondOrder = new Order(1, 2, secondDish.getId());
    secondOrder.save();
    goTo("http://localhost:4567/servers/orders/new");
    assertThat(pageSource()).contains("Tofu Dog");
    assertThat(pageSource()).contains("Penne Alfredo");
  }

  @Test
  public void individualOrderPage_rendersCorrectly() {
    Dish firstDish = new Dish("Tofu Dog", 2);
    firstDish.save();
    Order firstOrder = new Order(1, 1, firstDish.getId());
    firstOrder.save();
    goTo("http://localhost:4567/servers/orders/" + firstOrder.getId());
    assertThat(pageSource()).contains("Tofu Dog");
  }

  @Test
  public void payButton_marksOrderPaid() {
    Dish firstDish = new Dish("Tofu Dog", 2);
    firstDish.save();
    Order firstOrder = new Order(1, 1, firstDish.getId());
    firstOrder.save();
    goTo("http://localhost:4567/servers/orders/active");
    click(".btn-link", withText("Pay"));
    assertThat(pageSource()).contains("Yes");
  }

  @Test
  public void dishesByTimesOrderedToday_displaysCorrectly() {
    Dish dishOne = new Dish("Cheezeburger", 2);
    dishOne.save();
    Dish dishTwo = new Dish("Hamburg Burger", 2);
    dishTwo.save();
    Order orderOne = new Order(1, 1, dishOne.getId());
    orderOne.save();
    Order orderTwo = new Order(1, 2, dishOne.getId());
    orderTwo.save();
    Order orderThree = new Order(1, 3, dishTwo.getId());
    orderThree.save();
    goTo("http://localhost:4567/manager/orders/dishes");
    assertThat(pageSource()).contains("Hamburg");
    assertThat(pageSource()).contains("1");
    assertThat(pageSource()).contains("Cheezeburger");
    assertThat(pageSource()).contains("2");
  }

  @Test
  public void newDishesForm_createsANewDish() {
    goTo("http://localhost:4567/manager/orders/dishes");
    fill("#dish-name").with("Cheezeburger");
    submit(".btn");
    assertThat(pageSource()).contains("Cheezeburger");
  }

  @Test
  public void dish_updatesDishName() {
    Dish dish = new Dish("Cheezeburger", 2);
    dish.save();
    goTo("http://localhost:4567/manager/dishes/" + Integer.toString(dish.getId()));
    fill("#new-name").with("Hamburg Burger");
    submit(".new-name");
    assertThat(pageSource()).contains("Hamburg Burger");
  }

  @Test
  public void dish_addIngredientToList() {
    Dish dish = new Dish("Cheezeburger", 2);
    dish.save();
    Ingredient ingredient = new Ingredient("Bunz", "pair(s)", 300, 14);
    ingredient.save();
    goTo("http://localhost:4567/manager/dishes/" + Integer.toString(dish.getId()));
    fillSelect("#add-ingredient").withText("Bunz, pair(s)");
    fill("#amount").with("1");
    submit(".add-ingredient");
    assertThat(pageSource()).contains("Bunz");
  }

  @Test
  public void completeButton_completesOrder() {
    Dish firstDish = new Dish("Tofu Dog", 2);
    firstDish.save();
    Order firstOrder = new Order(1, 1, firstDish.getId());
    firstOrder.save();
    goTo("http://localhost:4567/servers/orders/active");
    click(".btn-link", withText("Complete"));
    assertThat(pageSource()).doesNotContain("Tofu Dog");
  }

  @Test
  public void managerIngredients_rendersCorrectly() {
    Ingredient ingredient = new Ingredient("Ground Beef", "Pounds", 100, 5);
    ingredient.save();
    goTo("http://localhost:4567/manager/inventory");
    assertThat(pageSource()).contains("Ground Beef");
  }

  @Test
  public void managerDeliveryPage_rendersCorrectly() {
    Ingredient ingredient = new Ingredient("Ground Beef", "Pounds", 100, 5);
    ingredient.save();
    goTo("http://localhost:4567/manager/delivery");
    assertThat(pageSource()).contains("Ground Beef");
  }

  @Test
  public void ingredientPage_rendersCorrectly() {
    Ingredient ingredient = new Ingredient("Ground Beef", "Pounds", 100, 5);
    ingredient.save();
    goTo("http://localhost:4567/manager/ingredients/" + ingredient.getId());
    assertThat(pageSource()).contains("Ground Beef");
  }

  @Test
  public void takeDeliveryForm_addsToInventory() {
    Ingredient ingredient = new Ingredient("Ground Beef", "Pounds", 100, 5);
    ingredient.save();
    goTo("http://localhost:4567/manager/delivery");
    fill("#" + ingredient.getId()).with("13");
    submit(".btn");
    assertThat(pageSource()).contains("13");
    assertThat(pageSource()).contains("Ground Beef");
  }

}
