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
}
