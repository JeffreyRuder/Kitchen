import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Order {
  private int mId;
  private int mPatronId;
  private int mTable;
  private int mSeat;
  private int mDishId;
  private String mCreationDate;
  private String mCreationTime;
  private String mCompletionDate;
  private String mCompletionTime;
  private String mComments;
  private boolean mPaid;

  //CONSTRUCTORS
  public Order(int table, int seat, int dish) {
    mTable = table;
    mSeat = seat;
    mDishId = dish;
    mPaid = false;
    mCreationDate = LocalDate.now().toString();
    mCreationTime = LocalTime.now().toString();
  }

  public Order(int table, int seat, int dish, int patronId) {
    mTable = table;
    mSeat = seat;
    mDishId = dish;
    mPatronId = patronId;
    mPaid = false;
    mCreationDate = LocalDate.now().toString();
    mCreationTime = LocalTime.now().toString();
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

  public String getDishName() {
    return Dish.find(mDishId).getName();
  }

  public boolean checkPaid() {
    return mPaid;
  }

  public String getCreationDate() {
    return mCreationDate;
  }

  public String getCreationTime() {
    return mCreationTime;
  }

  public String getCompletionDate() {
    return mCompletionDate;
  }

  public String getCompletionTime() {
    return mCompletionTime;
  }

  public String getComments() {
    return mComments;
  }

  //STATIC METHODS


  public static Order find(int searchId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id as mId, patron_id AS mPatronId, table_num AS mTable, seat_num AS mSeat, dish_id AS mDishId, comments AS mComments, creation_date AS mCreationDate, creation_time AS mCreationTime, completion_date AS mCompletionDate, completion_time AS mCompletionTime, is_paid AS mPaid FROM orders WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", searchId)
        .executeAndFetchFirst(Order.class);
    }
  }

  public static List<Order> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id as mId, patron_id AS mPatronId, table_num AS mTable, seat_num AS mSeat, dish_id AS mDishId, comments AS mComments, creation_date AS mCreationDate, creation_time AS mCreationTime, completion_date AS mCompletionDate, completion_time AS mCompletionTime, is_paid AS mPaid FROM orders ORDER BY creation_date, creation_time";
      return con.createQuery(sql)
        .executeAndFetch(Order.class);
    }
  }

  public static List<Order> getAllActive() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id as mId, patron_id AS mPatronId, table_num AS mTable, seat_num AS mSeat, dish_id AS mDishId, comments AS mComments, creation_date AS mCreationDate, creation_time AS mCreationTime, completion_date AS mCompletionDate, completion_time AS mCompletionTime, is_paid AS mPaid FROM orders WHERE completion_time IS NULL ORDER BY table_num, creation_date, creation_time";
      return con.createQuery(sql)
        .executeAndFetch(Order.class);
    }
  }

  public static List<Order> getAllActiveOrderByTime() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id as mId, patron_id AS mPatronId, table_num AS mTable, seat_num AS mSeat, dish_id AS mDishId, comments AS mComments, creation_date AS mCreationDate, creation_time AS mCreationTime, completion_date AS mCompletionDate, completion_time AS mCompletionTime, is_paid AS mPaid FROM orders WHERE completion_time IS NULL ORDER BY creation_date, creation_time";
      return con.createQuery(sql)
        .executeAndFetch(Order.class);
    }
  }

  //SETTERS

  public void addComments(String comments) {
    mComments = comments;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE orders SET comments = :comments WHERE id = :id";
      con.createQuery(sql)
        .addParameter("comments", mComments)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void pay() {
    mPaid = true;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE orders SET is_paid = true WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void changeDish(int newDishId) {
    mDishId = newDishId;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE orders SET dish_id = :newdish WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newdish", newDishId)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void complete() {
    mCompletionDate = LocalDate.now().toString();
    mCompletionTime = LocalTime.now().toString();
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE orders SET completion_date = to_date(:completiondate, 'YYYY-MM-DD'), completion_time = :completiontime WHERE id = :id";
      con.createQuery(sql)
        .addParameter("completiondate", mCompletionDate)
        .addParameter("completiontime", mCompletionTime)
        .addParameter("id", mId)
        .executeUpdate();
      }
    }

  public void completeAndStartDuplicate() {
    this.complete();
    if (this.getPatronId() > 0) {
      Order newOrder = new Order (this.getTable(), this.getSeat(), this.getDishId(), this.getPatronId());
      newOrder.save();
    } else {
      Order newOrder = new Order(this.getTable(), this.getSeat(), this.getDishId());
      newOrder.save();
    }
  }

  //OTHER INSTANCE METHODS
  @Override
  public boolean equals(Object otherOrder) {
    if (!(otherOrder instanceof Order)) {
      return false;
    } else {
      Order order = (Order) otherOrder;
      return mTable == order.getTable() &&
        mSeat == order.getSeat() &&
        mDishId == order.getDishId() &&
        mCreationDate.equals(order.getCreationDate()) &&
        mCreationTime.substring(0, 5).equals(order.getCreationTime().substring(0, 5));
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO orders (patron_id, table_num, seat_num, dish_id, creation_date, creation_time, comments) VALUES (:patronid, :table, :seat, :dish, to_date(:creationdate, 'YYYY-MM-DD'), :creationtime, :comments)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("patronid", mPatronId)
        .addParameter("table", mTable)
        .addParameter("seat", mSeat)
        .addParameter("dish", mDishId)
        .addParameter("creationdate", mCreationDate)
        .addParameter("creationtime", mCreationTime)
        .addParameter("comments", mComments)
        .executeUpdate()
        .getKey();
    }
  }

  public void make() {
    Dish thisDish = Dish.find(this.getDishId());
    List<Ingredient> theseIngredients = thisDish.getAllIngredients();
    for (Ingredient ingredient : theseIngredients) {
      try (Connection con = DB.sql2o.open()) {
        String sql = "SELECT ingredient_amount FROM dishes_ingredients WHERE dish_id = :dishid AND ingredient_id = :ingredientid";
        int amountNeeded = con.createQuery(sql)
          .addParameter("dishid", thisDish.getId())
          .addParameter("ingredientid", ingredient.getId())
          .executeScalar(Integer.class);
        ingredient.decrement(amountNeeded);
      }
    }
  }

}
