import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.sql2o.*;

public class Category {
  private int mId;
  private String mName;

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public Category(String name) {
    mName = name;
  }

  public static List<Category> all() {
    String sql = "SELECT id AS mId, name AS mName FROM categories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  @Override
  public boolean equals(Object otherCategory){
    if (!(otherCategory instanceof Category)) {
      return false;
    } else {
      Category newCategory = (Category) otherCategory;
      return this.getName().equals(newCategory.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories(name) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Category find(int searchId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id as mId, name as mName FROM categories where id=:id";
      Category Category = con.createQuery(sql)
        .addParameter("id", searchId)
        .executeAndFetchFirst(Category.class);
      return Category;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRelationships = "DELETE FROM categories_tasks WHERE category_id = :id";
      con.createQuery(deleteRelationships)
        .addParameter("id", mId)
        .executeUpdate();
    }

    try(Connection con = DB.sql2o.open()) {
    String deleteCategory = "DELETE FROM categories WHERE id = :id;";
    con.createQuery(deleteCategory)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

  public void addTask(Task task) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories_tasks(category_id, task_id) VALUES (:categoryid, :taskid)";
      con.createQuery(sql)
        .addParameter("categoryid", this.mId)
        .addParameter("taskid", task.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Task> getTasks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT task_id FROM categories_tasks INNER JOIN tasks ON categories_tasks.task_id = tasks.id WHERE category_id = :category_id ORDER BY tasks.is_done, tasks.due_date";
      List<Integer> taskIds = con.createQuery(sql)
        .addParameter("category_id", this.mId)
        .executeAndFetch(Integer.class);

      ArrayList<Task> associatedTasks = new ArrayList<Task>();

      for (Integer taskId : taskIds) {
        String taskQuery = "SELECT id AS mId, description as mDescription, is_done AS mIsDone, due_date AS mDueDate FROM tasks WHERE id = :taskid";
        Task task = con.createQuery(taskQuery)
          .addParameter("taskid", taskId)
          .executeAndFetchFirst(Task.class);
        associatedTasks.add(task);
      }

      return associatedTasks;
    }
  }
}
