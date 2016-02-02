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

  // @Test
  // public void rootTest() {
  //   goTo("http://localhost:4567");
  //   assertThat(pageSource()).contains("Kitchen");
  // }

  //GETTING RESOURCES

  @Test
  public void activeOrdersPage_rendersActiveOrders() {
    Dish firstDish = new Dish("Tofu Dog");
    Dish secondDish = new Dish("Penne Alfredo");
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
    Dish firstDish = new Dish("Tofu Dog");
    Dish secondDish = new Dish("Penne Alfredo");
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
    Dish firstDish = new Dish("Tofu Dog");
    Dish secondDish = new Dish("Penne Alfredo");
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
    Dish firstDish = new Dish("Tofu Dog");
    firstDish.save();
    Order firstOrder = new Order(1, 1, firstDish.getId());
    firstOrder.save();
    goTo("http://localhost:4567/servers/orders/" + firstOrder.getId());
    assertThat(pageSource()).contains("Tofu Dog");
  }

  @Test
  public void payButton_marksOrderPaid() {
    Dish firstDish = new Dish("Tofu Dog");
    firstDish.save();
    Order firstOrder = new Order(1, 1, firstDish.getId());
    firstOrder.save();
    goTo("http://localhost:4567/servers/orders/active");
    click(".btn-link", withText("Pay"));
    assertThat(pageSource()).contains("Yes");
  }

  @Test
  public void completeButton_completesOrder() {
    Dish firstDish = new Dish("Tofu Dog");
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
    goTo("http://localhost:4567/manager/ingredients/inventory");
    assertThat(pageSource()).contains("Ground Beef");
  }

  @Test
  public void managerDeliveryPage_rendersCorrectly() {
    Ingredient ingredient = new Ingredient("Ground Beef", "Pounds", 100, 5);
    ingredient.save();
    goTo("http://localhost:4567/manager/ingredients/delivery");
    assertThat(pageSource()).contains("Ground Beef");
  }
}
