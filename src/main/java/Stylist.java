import java.util.List;
import org.sql2o.*;

public class Stylist {
  private String mFirstName;
  private String mLastName;
  private int mId;

  //CONSTRUCTOR

  public Stylist(String first, String last) {
    mFirstName = first.trim();
    mLastName = last.trim();
  }

  //GETTERS

  public String getFirstName() {
    return mFirstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public String getFullName() {
    return mFirstName + " " + mLastName;
  }

  public int getId() {
    return mId;
  }

  //EQUALITY

  @Override
  public boolean equals(Object otherStylist) {
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist stylist = (Stylist) otherStylist;
      return this.getFullName().equals(stylist.getFullName());
    }
  }

  //CREATE

  public void save() {
    String sql = "INSERT INTO stylists(first_name, last_name) VALUES (:first, :last)";
    try (Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("first", this.getFirstName())
        .addParameter("last", this.getLastName())
        .executeUpdate()
        .getKey();
    }
  }

    //READ

  public static Stylist find(int searchId) {
    String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName FROM stylists WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      Stylist stylist = (Stylist) con.createQuery(sql)
        .addParameter("id", searchId)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }
}
