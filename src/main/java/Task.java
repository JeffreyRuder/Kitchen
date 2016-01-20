import java.util.List;

import org.sql2o.*;

public class Task {
  private int id;
  private String description;
  private int categoryId;

  public Task(String description, int categoryId) {
    this.description = description;
    this.categoryId = categoryId;
  }

  public static List<Task> all() {
    String sql = "SELECT id, description, categoryId FROM Tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public static Task find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  @Override
  public boolean equals(Object otherTask) {
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId() &&
             this.getCategoryId() == newTask.getCategoryId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Tasks(description, categoryId) VALUES (:description, :categoryId)";
      this.id  = (int) con.createQuery(sql, true)
        .addParameter("description", description)
        .addParameter("categoryId", categoryId)
        .executeUpdate()
        .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tasks WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public int getCategoryId() {
    return categoryId;
  }
}
