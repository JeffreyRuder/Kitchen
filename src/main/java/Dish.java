import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.sql2o.*;

public class Dish {
  private int mId;
  private String mName;

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public Dish(String name) {
    this.mName = name;
  }

  @Override
  public boolean equals(Object otherDish) {
    if(!(otherDish instanceof Dish)) {
      return false;
    } else {
      Dish newDish = (Dish) otherDish;
      return this.getName().equals(newDish.getName());
    }
  }

  public static List<Dish> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName from dishes";
      return con.createQuery(sql)
                .executeAndFetch(Dish.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO dishes(name) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
                          .addParameter("name", this.mName)
                          .executeUpdate()
                          .getKey();
    }
  }

  public static Dish find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM dishes WHERE id = :id";
      Dish dish = con.createQuery(sql)
                     .addParameter("id", id)
                     .executeAndFetchFirst(Dish.class);
      return dish;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sqlDeleteDishConnection = "DELETE FROM dishes_ingredients WHERE dishes_ingredients.dish_id = :id;";
      String sqlDeleteDish = "DELETE FROM dishes WHERE id = :id;";
      con.createQuery(sqlDeleteDishConnection + sqlDeleteDish)
         .addParameter("id", mId)
         .executeUpdate();
    }
  }

  public void update(String newName) {
    mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE dishes SET name = :name WHERE id = :id";
      con.createQuery(sql)
         .addParameter("name", newName)
         .addParameter("id", mId)
         .executeUpdate();
    }
  }

  //TODO: make this work with fractional ingredient amounts
  public void addIngredient(int ingredientId, int ingredientAmount) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO dishes_ingredients (dish_id, ingredient_id, ingredient_amount) VALUES (:dishId, :ingredientId, :ingredientAmount)";
      con.createQuery(sql)
         .addParameter("dishId", this.getId())
         .addParameter("ingredientId", ingredientId)
         .addParameter("ingredientAmount", ingredientAmount)
         .executeUpdate();
    }
  }

  public void removeIngredient(int ingredientId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM dishes_ingredients WHERE dishes_ingredients.ingredient_id = :id AND dishes_ingredients.dish_id = :dishId";
      con.createQuery(sql)
         .addParameter("id", ingredientId)
         .addParameter("dishId", mId)
         .executeUpdate();
    }
  }

  public List<Ingredient> getAllIngredients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT ingredients.id AS mId, ingredients.name AS mName, ingredients.unit AS mUnit, ingredients.desired_on_hand as mDesiredOnHand, ingredients.shelf_life_days AS mShelfLifeDays FROM dishes_ingredients INNER JOIN ingredients ON dishes_ingredients.ingredient_id = ingredients.id WHERE dishes_ingredients.dish_id = :dishid";
      return con.createQuery(sql)
         .addParameter("dishid", mId)
         .executeAndFetch(Ingredient.class);
    }
  }

  public int getTimesOrderedToday() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT COUNT(orders.id) FROM orders WHERE dish_id = :dishid AND orders.creation_date = to_date(:creationdate, 'YYYY-MM-DD')";
      return con.createQuery(sql)
        .addParameter("dishid", mId)
        .addParameter("creationdate", LocalDate.now().toString())
        .executeScalar(Integer.class);
    }
  }

  public boolean hasMissingIngredient() {
    boolean hasMissingIngredient = false;
    for (Ingredient ingredient : this.getAllIngredients()) {
      if (ingredient.getTotalOnHand() <= 0) {
        hasMissingIngredient = true;
      }
    }
    return hasMissingIngredient;
  }

  public boolean hasEnoughIngredients() {
    boolean hasEnoughIngredients = false;
    for (Ingredient ingredient : this.getAllIngredients()) {
      if (ingredient.getIngredientAmountForDish(this.getId()) <= ingredient.getTotalOnHand()) {
        hasEnoughIngredients = true;
      }
    }
    return hasEnoughIngredients;
  }


}
