import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class StoreTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void store_instantiatesWithName() {
    Store store = new Store("Payless");
    store.save();
    assertEquals("Payless", Store.find(store.getId()).getName());
  }

  @Test
  public void equals_returnsTrueIfSameName_true() {
    Store firstStore = new Store("Payless");
    Store secondStore = new Store("Payless");
    assertTrue(firstStore.equals(secondStore));
  }

  @Test
  public void all_returnsCompleteListOfStores() {
    Store firstStore = new Store("Nike Store");
    Store secondStore = new Store("Payless");
    firstStore.save();
    secondStore.save();
    Store[] stores = new Store[] {firstStore, secondStore};
    assertTrue(Store.all().containsAll(Arrays.asList(stores)));
  }

  @Test
  public void update_changesNameOfStoreInDatabase() {
    Store store = new Store("Nike Store");
    store.save();
    store.update("Nike Factory Store");
    assertEquals("Nike Factory Store", Store.find(store.getId()).getName());
  }

  @Test
  public void delete_deletesStoreFromDatabase_0() {
    Store store = new Store("Nike Store");
    store.save();
    store.delete();
    assertEquals(0, Store.all().size());
  }

  @Test
  public void addBrand_assignsBrandToStoreInDatabase() {
    Store store = new Store("Nike Store");
    store.save();
    Brand brand = new Brand("Nike");
    brand.save();
    store.addBrand(brand.getId());
    assertTrue(store.getAllBrands().contains(brand));
  }
}
