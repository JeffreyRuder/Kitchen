import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class BrandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void brand_instantiatesWithName() {
    Brand brand = new Brand("Nike");
    brand.save();
    assertEquals("Nike", Brand.find(brand.getId()).getName());
  }

  @Test
  public void equals_returnsTrueIfSameName_true() {
    Brand firstBrand = new Brand("Nike");
    Brand secondBrand = new Brand("Nike");
    assertTrue(firstBrand.equals(secondBrand));
  }

  @Test
  public void all_returnsCompleteListOfBrands() {
    Brand firstBrand = new Brand("Reebok");
    Brand secondBrand = new Brand("Nike");
    firstBrand.save();
    secondBrand.save();
    Brand[] brands = new Brand[] {firstBrand, secondBrand};
    assertTrue(Brand.all().containsAll(Arrays.asList(brands)));
  }

  @Test
  public void update_changesNameOfBrandInDatabase() {
    Brand brand = new Brand("Reebok");
    brand.save();
    brand.update("Adidas");
    assertEquals("Adidas", Brand.find(brand.getId()).getName());
  }

  @Test
  public void delete_deletesBrandFromDatabase_0() {
    Brand brand = new Brand("Reebok");
    brand.save();
    brand.delete();
    assertEquals(0, Brand.all().size());
  }

  @Test
  public void addStore_assignsStoreToBrandInDatabase() {
    Store store = new Store("Nike Store");
    store.save();
    Brand brand = new Brand("Nike");
    brand.save();
    brand.addStore(store.getId());
    assertTrue(brand.getAllStores().contains(store));
  }
}
