import java.util.List;
import org.sql2o.*;

public class Client {
  private String mFirstName;
  private String mLastName;
  private int mId;
  private int mStylistId;

  public Client(String first, String last) {
    mFirstName = first;
    mLastName = last;
  }

  public String getFullName() {
    return mFirstName + " " + mLastName;
  }

}
