import java.util.List;
import org.sql2o.*;

public class Stylist {
  private String mFirstName;
  private String mLastName;
  private int mId;

  public Stylist(String first, String last) {
    mFirstName = first.trim();
    mLastName = last.trim();
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

  public int getId() {
    return mId;
  }

}
