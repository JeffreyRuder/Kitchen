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
    get("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("students", Student.all());
      model.put("departments", Department.all());
      model.put("department", Department.class);
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //
    post("/student/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String newName = request.queryParams("student-name");
      String newDate = request.queryParams("student-enrollment");
      Integer departmentId = Integer.parseInt(request.queryParams("student-department"));

      Student student = new Student(newName);
      student.save();
      student.setEnrollmentDate(newDate);
      student.setMajor(departmentId);

      response.redirect("/students");
      return null;
    });

    get("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("departments", Department.all());
      model.put("department", Department.class);
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/course/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String newName = request.queryParams("course-name");
      Integer departmentId = Integer.parseInt(request.queryParams("course-department"));
      Integer courseNumber = Integer.parseInt(request.queryParams("course-number"));

      Course course = new Course(departmentId, courseNumber, newName);
      course.save();

      response.redirect("/courses");
      return null;
    });

    get("/departments", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("departments", Department.all());
      model.put("department", Department.class);
      model.put("template", "templates/departments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/department/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String newName = request.queryParams("department-name");
      String newAbbreviation = request.queryParams("department-abbreviation");

      Department department = new Department(newName, newAbbreviation);
      department.save();

      response.redirect("/departments");
      return null;
    });

    get("/student/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Student thisStudent = Student.find(
        Integer.parseInt(request.params("id")));

      model.put("student", thisStudent);
      model.put("department", Department.class);
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/student/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String newName = request.queryParams("student-name");
      String newDate = request.queryParams("student-enrollment");
      Integer departmentId = Integer.parseInt(request.queryParams("student-department"));

      Student thisStudent = Student.find(
        Integer.parseInt(request.params("id")));

      response.redirect("/student/" + thisStudent.getId());
      return null;
    });

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
