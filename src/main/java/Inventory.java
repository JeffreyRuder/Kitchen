import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.sql2o.*;

public class Inventory {
  private int mId;
  private int mIngredientId;
  private int mCurrentOnHand;
  private LocalDate mDeliveryDate;
  private LocalDate mExpirationDate;

  public Inventory(int ingredientId, int currentOnHand) {
    mIngredientId = ingredientId;
    mCurrentOnHand = currentOnHand;
    mDeliveryDate = LocalDate.now();
    mExpirationDate = mDeliveryDate.plusDays(Ingredient.find(ingredientId).getShelfLifeDays());
  }

  public int getId() {
    return mId;
  }

  public int getIngredientId() {
    return mIngredientId;
  }

  public int getCurrentOnHand() {
    return mCurrentOnHand;
  }

  public LocalDate getDeliveryDate() {
    return mDeliveryDate;
  }

  public LocalDate getExpirationDate() {
    return mExpirationDate;
  }

  public int getShelfLifeDays() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT ingredients.shelf_life_days AS mShelfLifeDays FROM ingredients WHERE ingredients.id = :id";
      return con.createQuery(sql)
        .addParameter("id", mIngredientId)
        .executeScalar(Integer.class);
    }
  }

  @Override
  public boolean equals(Object otherInventory) {
    if(!(otherInventory instanceof Inventory)) {
      return false;
    } else {
      Inventory newInventory = (Inventory) otherInventory;
      return this.getId() == newInventory.getId() &&
             this.getIngredientId() == newInventory.getIngredientId() &&
             this.getCurrentOnHand() == newInventory.getCurrentOnHand() &&
             this.getDeliveryDate().equals(newInventory.getDeliveryDate()) &&
             this.getExpirationDate() == newInventory.getExpirationDate();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO inventories (ingredient_id, current_on_hand, TO_DATE (delivery_date, 'YYYY-MM-DD'), TO_DATE(expiration_date, 'YYYY-MM-DD')) VALUES (:ingredientId, :currentOnHand, :deliveryDate, :expirationDate)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("ingredientId", mIngredientId)
        .addParameter("currentOnHand", mCurrentOnHand)
        .addParameter("deliveryDate", mDeliveryDate)
        .addParameter("expirationDate", mExpirationDate)
        .executeUpdate()
        .getKey();
    }
  }

  public static Inventory find(int inventoryId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, ingredient_id AS mIngredientId, current_on_hand AS mCurrentOnHand, delivery_date AS mDeliveryDate, expiration_date AS mExpirationDate FROM inventories WHERE id = :id";
      Inventory inventory = con.createQuery(sql)
        .addParameter("id", inventoryId)
        .executeAndFetchFirst(Inventory.class);
    }
  }

  public static List<Inventory> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, ingredient_id AS mIngredientId, current_on_hand AS mCurrentOnHand, delivery_date AS mDeliveryDate, expiration_date AS mExpirationDate FROM inventories";
      return con.createQuery(sql).executeAndFetch(Inventory.class);
    }
  }

  public List<Ingredient> getIngredients() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT ingredients.id AS mId, ingredients.name AS mName, ingredients.unit AS mUnit, ingredients.desired_on_hand AS mDesiredOnHand, ingredients.shelf_life_days AS mShelfLifeDays FROM ingredients INNER JOIN inventories ON (ingredients.id = inventories.ingredient_id) ORDER BY ingredients.name.";
    return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }
}
