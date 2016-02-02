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

    //GET

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Orders

    get("/orders/active", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("orders", Order.getAllActive());
      model.put("template", "templates/orders-active.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/orders/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("orders", Order.getAllActive());
      model.put("dishes", Dish.all());
      model.put("template", "templates/orders-new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //POST

    // Orders
    post("/orders/new", (request, response) -> {
      int table = Integer.parseInt(request.queryParams("table"));
      int seat = Integer.parseInt(request.queryParams("seat"));
      for (Dish dish : Dish.all()) {
        if (request.queryParams(dish.getName()) != null) {
          Integer dishId = Integer.parseInt(request.queryParams(dish.getName()));
          Order order = new Order(table, seat, dishId);
          order.save();
        }
      }
      response.redirect("/orders/active");
      return null;
    });


  }
}
