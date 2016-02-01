import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Dish {
  private int mId;
  private String mName;

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public Dish(String name) {
    this.mName = name;
  }

  @Override
  public boolean equals(Object otherDish) {
    if(!(otherDish instanceof Dish)) {
      return false;
    } else {
      Dish newDish = (Dish) otherDish;
      return this.getName().equals(newDish.getName());
    }
  }

  public static List<Dish> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName from dishes";
      return con.createQuery(sql)
                .executeAndFetch(Dish.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO dishes(name) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
                          .addParameter("name", this.mName)
                          .executeUpdate()
                          .getKey();
    }
  }


}
