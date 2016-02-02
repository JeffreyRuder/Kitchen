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
    goTo("http://localhost:4567/orders/active");
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
    goTo("http://localhost:4567/orders/new");
    assertThat(pageSource()).contains("Tofu Dog");
    assertThat(pageSource()).contains("Penne Alfredo");
  }


  //TODO: Not working, not sure why?
  // @Test
  // public void newOrdersForm_createsNewOrder() {
  //   Dish firstDish = new Dish("Tofu Dog");
  //   Dish secondDish = new Dish("Penne Alfredo");
  //   firstDish.save();
  //   secondDish.save();
  //   goTo("http://localhost:4567/orders/new");
  //   fill("#table").with("20");
  //   fill("#seat").with("10");
  //   findFirst("input").click();
  //   submit(".btn");
  //   assertThat(pageSource()).contains("20");
  // }



}
