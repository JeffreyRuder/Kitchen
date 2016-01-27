import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Patron {
  private int mId;
  private String mFirstName;
  private String mLastName;
  private int mPhone;

  public int getId() {
    return mId;
  }

  public String getFirstName() {
    return mFirstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public String getFullName() {
    return mFirstName + " " + mLastName;
  }

  public int getPhone() {
    return mPhone;
  }

  public Patron(String firstName, String lastName, int phone) {
    mFirstName = firstName;
    mLastName = lastName;
    mPhone = phone;
  }

  @Override
  public boolean equals(Object otherPatron) {
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getFirstName().equals(newPatron.getFirstName()) &&
             this.getLastName().equals(newPatron.getLastName()) &&
             this.getId() == newPatron.getId() &&
             this.getPhone() == newPatron.getPhone();
    }
  }

  public static List<Patron> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName, phone AS mPhone " +
                   "FROM patrons ORDER BY last_name, first_name";
      return con.createQuery(sql).executeAndFetch(Patron.class);
    }
  }

  public static Patron find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName, phone AS mPhone " +
                   "FROM patrons WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patron.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (first_name, last_name, phone) VALUES (:first_name, :last_name, :phone)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("first_name", mFirstName)
        .addParameter("last_name", mLastName)
        .addParameter("phone", mPhone)
        .executeUpdate()
        .getKey();
    }
  }

  public void updateName(String firstName, String lastName) {
    mFirstName = firstName;
    mLastName = lastName;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET first_name = :first_name, last_name = :last_name " +
                   "WHERE id = :id";
      con.createQuery(sql)
        .addParameter("first_name", firstName)
        .addParameter("last_name", lastName)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void updatePhone(int phone) {
    mPhone = phone;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET phone = :phone WHERE id = :id";
      con.createQuery(sql)
        .addParameter("phone", phone)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

//TODO: prevent deletions while books are checked out.
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String deletePatron = "DELETE FROM patrons WHERE id = :id;";
      con.createQuery(deletePatron)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }


}
