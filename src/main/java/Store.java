import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Store {
  private int mId;
  private String mName;

  //CONSTRUCTOR
  public Store(String name) {
    mName = name;
  }

  //GETTERS
  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  //STATIC METHODS
  public static Store find(int searchId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM stores WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", searchId)
        .executeAndFetchFirst(Store.class);
    }
  }

  public static List<Store> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM stores";
      return con.createQuery(sql)
        .executeAndFetch(Store.class);
    }
  }

  //INSTANCE METHODS
  @Override
  public boolean equals(Object otherStore) {
    if (!(otherStore instanceof Store)) {
      return false;
    } else {
      Store store = (Store) otherStore;
      return mName.equals(store.getName());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores (name) VALUES (:name)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("name", mName)
        .executeUpdate()
        .getKey();
    }
  }

  public void update(String newName) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stores SET name = :newname WHERE id = :id";
      con.createQuery(sql, true)
        .addParameter("newname", newName)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stores WHERE id = :id";
      con.createQuery(sql, true)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void addBrand(int brandId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO carries (store_id, brand_id) VALUES (:store_id, :brand_id)";
      con.createQuery(sql)
        .addParameter("store_id", mId)
        .addParameter("brand_id", brandId)
        .executeUpdate();
    }
  }

  public List<Brand> getAllBrands() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT brands.id AS mId, brands.name AS mName FROM carries INNER JOIN brands ON carries.brand_id = brands.id WHERE carries.store_id = :id";
      return con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Brand.class);
    }
  }

  public void removeBrand(int brandId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM carries WHERE store_id = :store_id AND brand_id = :brand_id";
      con.createQuery(sql)
        .addParameter("store_id", mId)
        .addParameter("brand_id", brandId)
        .executeUpdate();
    }
  }
}
