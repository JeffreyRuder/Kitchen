import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Brand {
  private int mId;
  private String mName;

  //CONSTRUCTOR
  public Brand(String name) {
    mName = name.trim();
  }

  //GETTERS
  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  //STATIC METHODS
  public static Brand find(int searchId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM brands WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", searchId)
        .executeAndFetchFirst(Brand.class);
    }
  }

  public static List<Brand> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM brands ORDER BY name";
      return con.createQuery(sql)
        .executeAndFetch(Brand.class);
    }
  }

  //INSTANCE METHODS
  @Override
  public boolean equals(Object otherBrand) {
    if (!(otherBrand instanceof Brand)) {
      return false;
    } else {
      Brand store = (Brand) otherBrand;
      return mName.equals(store.getName());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO brands (name) VALUES (:name)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("name", mName)
        .executeUpdate()
        .getKey();
    }
  }

  public void update(String newName) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE brands SET name = :newname WHERE id = :id";
      con.createQuery(sql, true)
        .addParameter("newname", newName.trim())
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String deleteCarries = "DELETE FROM carries WHERE brand_id = :id;";
      String deleteBrands = "DELETE FROM brands WHERE id = :id;";
      con.createQuery(deleteCarries + deleteBrands)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void addStore(int storeId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO carries (store_id, brand_id) VALUES (:store_id, :brand_id)";
      con.createQuery(sql)
        .addParameter("store_id", storeId)
        .addParameter("brand_id", mId)
        .executeUpdate();
    }
  }

  public List<Store> getAllStores() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT stores.id AS mId, stores.name AS mName FROM carries INNER JOIN stores ON carries.store_id = stores.id WHERE carries.brand_id = :id ORDER BY stores.name";
      return con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Store.class);
    }
  }

  public void removeStore(int storeId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM carries WHERE store_id = :store_id AND brand_id = :brand_id";
      con.createQuery(sql)
        .addParameter("store_id", storeId)
        .addParameter("brand_id", mId)
        .executeUpdate();
    }
  }
}
