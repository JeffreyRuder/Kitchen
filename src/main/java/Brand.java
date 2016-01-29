import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Brand {
  private int mId;
  private String mName;

  //CONSTRUCTOR
  public Brand(String name) {
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
      String sql = "SELECT id AS mId, name AS mName FROM brands";
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
        .addParameter("newname", newName)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM brands WHERE id = :id";
      con.createQuery(sql, true)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }
}
