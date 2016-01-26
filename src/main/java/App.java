import java.util.ArrayList;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    //IDENTIFYING RESOURCES
    // get("/", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/index.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/tasks", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("tasks", Task.all());
    //   model.put("template", "templates/tasks.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/categories", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("categories", Category.all());
    //   model.put("template", "templates/categories.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/tasks/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Task thisTask = Task.find(
    //     Integer.parseInt(
    //     request.params("id")));
    //
    //   model.put("task", thisTask);
    //   model.put("allCategories", Category.all());
    //   model.put("template", "templates/task.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/tasks/:id/edit", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Task thisTask = Task.find(
    //     Integer.parseInt(
    //     request.params("id")));
    //
    //   model.put("task", thisTask);
    //   model.put("allCategories", Category.all());
    //   model.put("template", "templates/edit-task.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/categories/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Category thisCategory = Category.find(
    //     Integer.parseInt(
    //     request.params("id")));
    //
    //   model.put("category", thisCategory);
    //   model.put("allTasks", Task.all());
    //   model.put("template", "templates/category.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/categories/:id/edit", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Category thisCategory = Category.find(
    //     Integer.parseInt(
    //     request.params("id")));
    //
    //   model.put("category", thisCategory);
    //   model.put("allTasks", Task.all());
    //   model.put("template", "templates/edit-category.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // //CHANGING RESOURCES
    // post("/tasks", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   String description = request.queryParams("description");
    //   String dueDate = request.queryParams("due_date");
    //   Task newTask = new Task(description);
    //   newTask.save();
    //   newTask.setDueDate(dueDate);
    //   response.redirect("/tasks");
    //   return null;
    // });
    //
    // post("/categories", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   String name = request.queryParams("name");
    //   Category newCategory = new Category(name);
    //   newCategory.save();
    //   response.redirect("/categories");
    //   return null;
    // });
    //
    // post("/add_categories", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Category newCategory = Category.find(
    //     Integer.parseInt(
    //     request.queryParams("category_id")));
    //
    //   Task thisTask = Task.find(
    //     Integer.parseInt(
    //     request.queryParams("task_id")));
    //
    //   thisTask.addCategory(newCategory);
    //
    //   response.redirect("/tasks/" + thisTask.getId());
    //   return null;
    // });
    //
    // post("/add_tasks", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Task newTask = Task.find(
    //     Integer.parseInt(
    //     request.queryParams("task_id")));
    //
    //   Category thisCategory = Category.find(
    //     Integer.parseInt(
    //     request.queryParams("category_id")));
    //
    //   thisCategory.addTask(newTask);
    //
    //   response.redirect("/categories/" + thisCategory.getId());
    //   return null;
    // });
    //
    // post("/tasks/:id/finish", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Task thisTask = Task.find(
    //     Integer.parseInt(
    //     request.params("id")));
    //
    //   thisTask.finish();
    //
    //   response.redirect("/tasks/" + thisTask.getId());
    //   return null;
    // });
    //
    // post("/tasks/:id/change-description", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Task thisTask = Task.find(
    //     Integer.parseInt(
    //     request.params("id")));
    //
    //   thisTask.update(request.queryParams("newdescription"));
    //
    //   response.redirect("/tasks/" + thisTask.getId());
    //   return null;
    //   });
    //
    // post("/categories/:id/change-name", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Category thisCategory = Category.find(
    //     Integer.parseInt(
    //     request.params("id")));
    //
    //   thisCategory.update(request.queryParams("newname"));
    //
    //   response.redirect("/categories/" + thisCategory.getId());
    //   return null;
    //   });
    //
    // //REMOVING RESOURCES
    // post("/tasks/delete", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Task task = Task.find(Integer.parseInt(request.queryParams("task_id")));
    //   task.delete();
    //   response.redirect("/tasks");
    //   return null;
    // });
  }
}
