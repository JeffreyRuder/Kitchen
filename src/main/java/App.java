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
    get("/servers/orders/active", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("orders", Order.getAllActive());
      model.put("template", "templates/orders-active.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("servers/orders/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("orders", Order.getAllActive());
      model.put("dishes", Dish.all());
      model.put("template", "templates/orders-new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("kitchen/orders/active", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("orders", Order.getAllActiveOrderByTime());
      model.put("dishes", Dish.all());
      model.put("template", "templates/orders-active.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //POST

    // Orders
    post("/orders/new", (request, response) -> {
      int table = Integer.parseInt(request.queryParams("table"));
      int seat = Integer.parseInt(request.queryParams("seat"));
      for (Dish dish : Dish.all()) {
        Integer dishQuantity = Integer.parseInt(request.queryParams(dish.getName()));
        System.out.println(dishQuantity);
        if (dishQuantity > 0) {
          for (Integer i = dishQuantity; i > 0; i--) {
            Order order = new Order (table, seat, dish.getId());
            order.save();
          }
        }
      }
      response.redirect("/servers/orders/active");
      return null;
    });

// TO-DO: GET DISHES
    get("/manager/orders/dishes", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("dishes", Dish.all());
      model.put("template", "templates/dishes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// TO-DO: GET DISH

    get("/manager/dishes/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("dish", Dish.find(Integer.parseInt(request.params(":id"))));
      model.put("recipes", Recipe.all());
      model.put("template", "templates/dish.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// TO-DO: POST NEW DISH
    post("/manager/dishes/:id", (request, response) -> {
      Dish dish = new Dish(request.queryParams("dish-name"));
      dish.save();
      response.redirect("/manager/orders/dishes");
      return null;
    });
    
// TO-DO: POST DISH:UPDATE DISH

// TO-DO: POST DISH:ADD INGREDIENT TO LIST

// TO-DO: POST DISH:DELETE INGREDIENT FROM LIST

  }
}
