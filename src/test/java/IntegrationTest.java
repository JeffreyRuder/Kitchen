import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.*;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567");
    assertThat(pageSource()).contains("Local Shoes");
  }

  @Test
  public void allStoresPage_rendersCorrectly() {
    Store store = new Store("Nike Store");
    store.save();
    goTo("http://localhost:4567");
    click("a", withText("Manage Stores"));
    assertThat(pageSource()).contains("Nike Store");
  }

  @Test
  public void allBrandsPage_rendersCorrectly() {
    Brand brand = new Brand("Nike");
    brand.save();
    goTo("http://localhost:4567");
    click("a", withText("Manage Brands"));
    assertThat(pageSource()).contains("Nike");
  }

  @Test
  public void newStoreFormAddsNewStore() {
    goTo("http://localhost:4567/stores");
    fill("#add-new-store").with("Payless");
    submit(".btn");
    assertThat(pageSource()).contains("Payless");
  }

  @Test
  public void newStoreFrom_preventsDuplicates() {
    Store store = new Store("Payless");
    store.save();
    goTo("http://localhost:4567/stores");
    fill("#add-new-store").with("Payless");
    submit(".btn");
    assertThat(pageSource()).contains("That store is already in the database");
  }

  @Test
  public void addBrandForm_addsBrandToStore() {
    Store store = new Store("Nike Factory Store");
    store.save();
    Brand brand = new Brand("Nike");
    brand.save();
    goTo("http://localhost:4567/stores/" + store.getId());
    fillSelect("#store-add-brand").withText("Nike");
    submit(".btn", withText("Add Brand"));
    goTo("http://localhost:4567/stores");
    assertThat(pageSource()).contains("Nike");
  }

  @Test
  public void updateForm_changesStoreName() {
    Store store = new Store("Nike Factory Store");
    store.save();
    Brand brand = new Brand("Nike");
    brand.save();
    goTo("http://localhost:4567/stores/" + store.getId());
    fill("#store-new-name").with("Nike Store");
    submit(".btn", withText("Change Name"));
    assertThat(pageSource()).contains("Nike Store");
  }

  @Test
  public void deleteForm_redirectsCorrectly() {
    Store store = new Store("Nike Factory Store");
    store.save();
    goTo("http://localhost:4567/stores/" + store.getId());
    submit(".btn", withText("Delete Store"));
    assertThat(pageSource().contains("All Stores"));
  }
}
