import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Patron {
  private int mId;
  private String mFirstName;
  private String mLastName;
  private String mPhone;
  private boolean mIsActive;

  //CONSTRUCTORS
  public Patron() {
    mIsActive = true;
  }

  public Patron(String first, String last) {
    mFirstName = first.trim();
    mLastName = last.trim();
    mIsActive = true;
  }

  //GETTERS
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

  public String getPhone() {
    return mPhone;
  }

  public boolean getIsActive() {
    return mIsActive;
  }

  //STATIC METHODS
  public static Patron find(int searchId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id as mId, first_name AS mFirstName, last_name as mLastName, phone as mPhone, is_active as mIsActive FROM patrons WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", searchId)
        .executeAndFetchFirst(Patron.class);
    }
  }

  public static List<Patron> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id as mId, first_name AS mFirstName, last_name as mLastName, phone as mPhone, is_active as mIsActive FROM patrons";
      return con.createQuery(sql)
        .executeAndFetch(Patron.class);
    }
  }

  //INSTANCE METHODS
  @Override
  public boolean equals(Object otherPatron) {
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron patron = (Patron) otherPatron;
      return this.getFullName().equals(patron.getFullName());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (first_name, last_name, phone, is_active) VALUES (:first, :last, :phone, :isactive)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("first", mFirstName)
        .addParameter("last", mLastName)
        .addParameter("phone", mPhone)
        .addParameter("isactive", mIsActive)
        .executeUpdate()
        .getKey();
    }
  }

  public void updateFirstName(String newFirst) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET first_name = :newfirst WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newfirst", newFirst)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void updateLastName(String newLast) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET last_name = :newlast WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newlas", newLast)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void updatePhone(String newPhone) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET phone = :newphone WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newphone", newPhone)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void setIsActive(boolean active) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET is_active = :active WHERE id = :id";
      con.createQuery(sql)
        .addParameter("active", active)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }
}
