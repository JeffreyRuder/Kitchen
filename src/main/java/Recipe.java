import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Recipe {
  private int mId;
  private int mDishId;
  private int mIngredientId;
  private int mIngredientAmount;

  public int getId() {
    return mId;
  }

  public int getDishId() {
    return mDishId;
  }

  public int getIngredientId() {
    return mIngredientId;
  }

  public int getIngredientAmount() {
    return mIngredientAmount;
  }

  public Recipe(int dishId, int ingredientId, int ingredientAmount) {
    mDishId = dishId;
    mIngredientId = ingredientId;
    mIngredientAmount = ingredientAmount;
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe recipe = (Recipe) otherRecipe;
      return this.getDishId() == recipe.getDishId() &&
             this.getIngredientId() == recipe.getIngredientId() &&
             this.getIngredientAmount() == recipe.getIngredientAmount();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO dishes_ingredients (dish_id, ingredient_id, ingredient_amount) VALUES (:dishId, :ingredientId, :ingredientAmount)";
      mId = (int) con.createQuery(sql, true)
                .addParameter("dishId", mDishId)
                .addParameter("ingredientId", mIngredientId)
                .addParameter("ingredientAmount", mIngredientAmount)
                .executeUpdate()
                .getKey();
    }
  }

  public static List<Recipe> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, dish_id AS mDishId, ingredient_id AS mIngredientId, ingredient_amount AS mIngredientAmount " +
                   "FROM dishes_ingredients";
      return con.createQuery(sql)
                .executeAndFetch(Recipe.class);
    }
  }

  public static Recipe find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, dish_id AS mDishId, ingredient_id AS mIngredientId, ingredient_amount AS mIngredientAmount " +
                   "FROM dishes_ingredients WHERE id = :id";
      return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Recipe.class);
    }
  }

}
