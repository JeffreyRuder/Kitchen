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

  //STORES
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
  public void deleteStoreForm_redirectsCorrectly() {
    Store store = new Store("Nike Factory Store");
    store.save();
    goTo("http://localhost:4567/stores/" + store.getId());
    submit(".btn-link", withText("Delete Store"));
    assertThat(pageSource()).contains("All Stores");
  }

  //BRANDS
  @Test
  public void newBrandFormAddsNewBrand() {
    goTo("http://localhost:4567/brands");
    fill("#add-new-brand").with("Nike");
    submit(".btn");
    assertThat(pageSource()).contains("Nike");
  }

  @Test
  public void newBrandFrom_preventsDuplicates() {
    Brand brand = new Brand("Nike");
    brand.save();
    goTo("http://localhost:4567/brands");
    fill("#add-new-brand").with("Nike");
    submit(".btn");
    assertThat(pageSource()).contains("That brand is already in the database");
  }

  @Test
  public void addStoreForm_addsStoreToBrand() {
    Brand brand = new Brand("Nike");
    brand.save();
    Store store = new Store("Nike Factory Store");
    store.save();
    goTo("http://localhost:4567/brands/" + brand.getId());
    fillSelect("#brand-add-store").withText("Nike Factory Store");
    submit(".btn", withText("Add Store"));
    goTo("http://localhost:4567/brands");
    assertThat(pageSource()).contains("Nike Factory Store");
  }

  @Test
  public void updateForm_changesBrandName() {
    Brand brand = new Brand("OopsWrong");
    brand.save();
    goTo("http://localhost:4567/brands/" + brand.getId());
    fill("#brand-new-name").with("Nike");
    submit(".btn", withText("Change Name"));
    assertThat(pageSource()).contains("Nike");
  }

  @Test
  public void deleteBrandForm_redirectsCorrectly() {
    Brand brand = new Brand("Nike");
    brand.save();
    goTo("http://localhost:4567/brands/" + brand.getId());
    submit(".btn-link", withText("Delete Brand"));
    assertThat(pageSource()).contains("All Brands");
  }
}
