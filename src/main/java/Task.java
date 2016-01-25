import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Task {
  private int mId;
  private String mDescription;
  private Boolean mIsDone;

  public int getId() {
    return mId;
  }

  public String getDescription() {
    return mDescription;
  }

  public Boolean isDone() {
    return mIsDone;
  }

  //CONSTRUCTOR
  public Task(String description) {
    mDescription = description;
    mIsDone = false;
  }

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription())
        && this.getId() == newTask.getId();
    }
  }


  public static List<Task> all() {
    String sql = "SELECT id AS mId, description AS mDescription, is_done AS mIsDone FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(description, is_done) VALUES (:description, :isdone)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("description", mDescription)
        .addParameter("isdone", mIsDone)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int number) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, description AS mDescription, is_done AS mIsDone FROM tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", number)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void update(String newDescription) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", newDescription)
        .addParameter("id", mId)
        .executeUpdate();
    }
    mDescription = newDescription;
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRelationships = "DELETE FROM categories_tasks WHERE task_id = :id";
      con.createQuery(deleteRelationships)
        .addParameter("id", mId)
        .executeUpdate();
    }

    try(Connection con = DB.sql2o.open()) {
    String deleteTask = "DELETE FROM tasks WHERE id = :id;";
    con.createQuery(deleteTask)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

  public void addCategory(Category category) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories_tasks(category_id, task_id) VALUES (:categoryid, :taskid)";
      con.createQuery(sql)
        .addParameter("taskid", this.mId)
        .addParameter("categoryid", category.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Category> getCategories() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT category_id FROM categories_tasks WHERE task_id = :task_id";
      List<Integer> categoryIds = con.createQuery(sql)
        .addParameter("task_id", this.mId)
        .executeAndFetch(Integer.class);

     ArrayList<Category> associatedCategories = new ArrayList<Category>();

     for (Integer categoryId : categoryIds) {
       String categoryQuery = "SELECT id AS mId, name AS mName FROM categories WHERE id = :categoryid";
       Category category = con.createQuery(categoryQuery)
        .addParameter("categoryid", categoryId)
        .executeAndFetchFirst(Category.class);
       associatedCategories.add(category);
     }
     return associatedCategories;
   }
 }

 public void finish() {
   mIsDone = true;
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE tasks SET is_done = :isdone WHERE id = :id";
     con.createQuery(sql)
       .addParameter("isdone", mIsDone)
       .addParameter("id", mId)
       .executeUpdate();
   }
 }
}
