import java.time.LocalDate;
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

  public void checkout(int copyId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO checkouts (copy_id, patron_id, checkout_date, due_date, is_returned) VALUES (:copy_id, :patron_id, TO_DATE (:checkout_date, 'yyyy-mm-dd'), TO_DATE (:due_date, 'yyyy-mm-dd'), false)";
      con.createQuery(sql)
        .addParameter("copy_id", copyId)
        .addParameter("patron_id", mId)
        .addParameter("checkout_date", LocalDate.now().toString())
        .addParameter("due_date", LocalDate.now().plusWeeks(2).toString())
        .executeUpdate();
    }
  }

  public void returnCopy(int copyId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE checkouts SET is_returned = true WHERE copy_id = :copy_id AND patron_id = :patron_id";
    con.createQuery(sql)
      .addParameter("copy_id", copyId)
      .addParameter("patron_id", mId)
      .executeUpdate();
    }
  }

  public List<Checkout> getCurrentCheckouts() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, copy_id AS mCopyId, patron_id AS mPatronId, checkout_date AS mCheckoutDate, due_date AS mDueDate, is_returned AS mIsReturned FROM checkouts WHERE patron_id = :patron_id AND is_returned = false";
      return con.createQuery(sql)
        .addParameter("patron_id", mId)
        .executeAndFetch(Checkout.class);
    }
  }

  public List<Checkout> getCheckoutHistory() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, copy_id AS mCopyId, patron_id AS mPatronId, checkout_date AS mCheckoutDate, due_date AS mDueDate, is_returned AS mIsReturned FROM checkouts WHERE patron_id = :patron_id";
      return con.createQuery(sql)
        .addParameter("patron_id", mId)
        .executeAndFetch(Checkout.class);
    }
  }
}
