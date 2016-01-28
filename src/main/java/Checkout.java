import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Checkout {
  private int mId;
  private int mCopyId;
  private int mPatronId;
  private String mCheckoutDate;
  private String mDueDate;
  private boolean mIsReturned;

  public int getId() {
    return mId;
  }

  public int getCopyId() {
    return mCopyId;
  }

  public int getPatronId() {
    return mPatronId;
  }

  public String getCheckoutDate() {
    return mCheckoutDate;
  }

  public String getDueDate() {
    return mDueDate;
  }

  public boolean getIsReturned() {
    return mIsReturned;
  }

  public static List<Checkout> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, copy_id AS mCopyId, patron_id AS mPatronId, checkout_date AS mCheckoutDate, due_date AS mDueDate, is_returned AS mIsReturned FROM checkouts";
      return con.createQuery(sql)
        .executeAndFetch(Checkout.class);
    }
  }

  public static Checkout findCurrentCheckout(int copyId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, copy_id AS mCopyId, patron_id AS mPatronId, checkout_date AS mCheckoutDate, due_date AS mDueDate, is_returned AS mIsReturned FROM checkouts WHERE copy_id = :id AND is_returned = false";
      return con.createQuery(sql)
        .addParameter("id", copyId)
        .executeAndFetchFirst(Checkout.class);
    }
  }
}
