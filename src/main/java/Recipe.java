import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Recipe {
// MEMBER VARIABLES

  private int mId;
  private int mDishId;
  private int mIngredientId;
  private int mIngredientAmount;

// GETTERS

  public int getId() {
    return mId;
  }

  public int getDishId() {
    return mDishId;
  }

  public String getDishName() {
    return Dish.find(mDishId).getName();
  }

  public int getIngredientId() {
    return mIngredientId;
  }

  public String getIngredientName() {
    return Ingredient.find(mIngredientId).getName();
  }

  public int getIngredientAmount() {
    return mIngredientAmount;
  }

// CONSTRUCTOR

  public Recipe(int dishId, int ingredientId, int ingredientAmount) {
    mDishId = dishId;
    mIngredientId = ingredientId;
    mIngredientAmount = ingredientAmount;
  }

// OVERRIDE equals

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

// SAVE

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

// SAVES ALL RECIPE OBJECTS INTO A LIST

  public static List<Recipe> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, dish_id AS mDishId, ingredient_id AS mIngredientId, ingredient_amount AS mIngredientAmount " +
                   "FROM dishes_ingredients";
      return con.createQuery(sql)
                .executeAndFetch(Recipe.class);
    }
  }

// FINDS AN INSTANCE OF A RECIPE IN THE LIST OF RECIPES

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
