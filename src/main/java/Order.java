import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Order {
  private int mId;
  private int mPatronId;
  private int mTable;
  private int mSeat;
  private int mDishId;
  private LocalDateTime mCreation;
  private LocalDateTime mCompletion;
  private String mComments;

  //CONSTRUCTORS
  public Order(int table, int seat, int dish) {
    mTable = table;
    mSeat = seat;
    mDish = dish;
    mCreation = LocalDateTime.now();
  }

  public Order(int table, int seat, int dish, int patronId) {
    mTable = table;
    mSeat = seat;
    mDish = dish;
    mPatronId = patronId;
    mCreation = LocalDateTime.now();
  }

  //GETTERS
  public int getId() {
    return mId;
  }

  public int getPatronId() {
    return mPatronId;
  }

  public int getTable() {
    return mTable;
  }

  public int getSeat() {
    return mSeat;
  }

  public int getDishId() {
    return mDishId;
  }

  public LocalDateTime getCreationDatetime() {
    return mCreation;
  }

  public LocalDateTime getCompletionDatetime() {
    return mCompletion;
  }

  //SETTERS
  public void addComments(String comments) {
    mComments = comments;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE orders SET comments = :comments WHERE id = :id";
      con.createQuery(sql)
        .addParameter("comments" = mComments)
        .addParameter("id" = mId)
        .executeUpdate();
    }
  }


}
