import java.util.ArrayList;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        get("/", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("tasks", request.session().attribute("tasks"));
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/tasks", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String, Object>();

            ArrayList<Task> taskList = request.session().attribute("tasks");

            if (taskList == null) {
                taskList = new ArrayList<Task>();
                request.session().attribute("tasks", taskList);
            }

            String description = request.queryParams("description");
            Task newTask = new Task(description);

            taskList.add(newTask);

            model.put("template", "templates/success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

    }
}
