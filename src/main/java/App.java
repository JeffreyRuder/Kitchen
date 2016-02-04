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

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //ORDERS

    get("/servers/orders/active", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("orders", Order.getAllActive());
      model.put("dishes", Dish.all());
      model.put("template", "templates/orders-active.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Order - take a new order
    post("/orders/new", (request, response) -> {
      int table = Integer.parseInt(request.queryParams("table"));
      int seat = Integer.parseInt(request.queryParams("seat"));
      for (Dish dish : Dish.all()) {
        if (dish.hasEnoughIngredients() && dish.getNumberPossibleDishes() > 0) {
          Integer dishQuantity = Integer.parseInt(request.queryParams(dish.getName()));
          if (dishQuantity > 0) {
            for (Integer i = dishQuantity; i > 0; i--) {
              Order order = new Order (table, seat, dish.getId());
              if (Dish.find(order.getDishId()).hasEnoughIngredients()) {
                order.save();
                order.make();
              }
            }
          }
        }
      }
      response.redirect("/servers/orders/active");
      return null;
    });

    //Order - pay for an order
    post("/servers/orders/:id/pay", (request, response) -> {
      Order thisOrder = Order.find(Integer.parseInt(request.params("id")));
      thisOrder.pay();
      response.redirect("/servers/orders/" + Integer.parseInt(request.params("id")));
      return null;
    });

    //Order - complete an order and make it no longer active
    post("/servers/orders/:id/complete", (request, response) -> {
      Order thisOrder = Order.find(Integer.parseInt(request.params("id")));
      thisOrder.complete();
      response.redirect("/servers/orders/active");
      return null;
    });

    //Order - cancel and lost ingredients i.e diner walked out
    post("/servers/orders/active/remove", (request, response) -> {
      Order thisOrder = Order.find(
        Integer.parseInt(request.queryParams("order-remove")));
      thisOrder.complete();
      response.redirect("/servers/orders/active");
      return null;
    });

    //Order - chef routing
    get("/kitchen/orders/active", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("orders", Order.getAllActiveKitchenSort());
      model.put("dishes", Dish.all());
      model.put("template", "templates/orders-kitchen.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("kitchen/orders/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("order", Order.find(Integer.parseInt(request.params("id"))));
      model.put("dishes", Dish.all());
      model.put("template", "templates/order-kitchen.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/kitchen/orders/:id/up", (request, response) -> {
      Order thisOrder = Order.find(Integer.parseInt(request.params("id")));
      thisOrder.setIsUp();
      response.redirect("/kitchen/orders/" + Integer.parseInt(request.params("id")));
      return null;
    });

    //Order - server routing
    get("/servers/orders/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("orders", Order.getAllActive());
      model.put("dishes", Dish.all());
      model.put("template", "templates/orders-new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/servers/orders/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("order", Order.find(Integer.parseInt(request.params("id"))));
      model.put("dishes", Dish.all());
      model.put("template", "templates/order.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Order - lost ingredients, cancel and restart i.e. diner sent it back
    post("/servers/orders/active/restart", (request, response) -> {
      Order thisOrder = Order.find(
        Integer.parseInt(request.queryParams("order-restart")));
      thisOrder.complete();
      Order newOrder = new Order(thisOrder.getTable(), thisOrder.getSeat(), thisOrder.getDishId());
      newOrder.save();
      if (Dish.find(newOrder.getDishId()).hasEnoughIngredients()) {
        newOrder.make();
      }
      response.redirect("/servers/orders/" + newOrder.getId());
      return null;
    });

    //INGREDIENTS
    get("/manager/ingredients/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("ingredient", Ingredient.find(Integer.parseInt(request.params("id"))));
      model.put("template", "templates/ingredient.vtl");
      return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    post("/manager/new-ingredient", (request, response) -> {
      Ingredient newIngredient = new Ingredient(
        request.queryParams("new-name"),
        request.queryParams("new-unit"),
        Integer.parseInt(request.queryParams("new-desired")),
        Integer.parseInt(request.queryParams("shelf-life-days")));
      newIngredient.save();
      response.redirect("/manager/ingredients/" + newIngredient.getId());
      return null;
    });

    //UPDATE ingredients

    get("/manager/new-ingredient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/ingredient-new.vtl");
      return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    //INVENTORY
    get("/manager/inventory", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("ingredients", Ingredient.all());
      model.put("dishes", Dish.all());
      model.put("eightysixes", Dish.getEightySixes());
      model.put("template", "templates/ingredients-inventory.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Take a delivery
    post("/manager/ingredients/inventory", (request, response) -> {
      for (Ingredient ingredient : Ingredient.all()) {
        int amount = Integer.parseInt(request.queryParams(ingredient.getName()));
        if (amount > 0) {
          Inventory delivery = new Inventory(ingredient.getId(), amount);
          delivery.save();
        }
      }
      response.redirect("/manager/inventory");
      return null;
    });

    get("/manager/delivery", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("ingredients", Ingredient.all());
      model.put("template", "templates/ingredients-delivery.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// GET DISHES

    //DISHES

    get("/manager/orders/dishes", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("dishes", Dish.all());
      model.put("template", "templates/dishes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// POST NEW DISH
    post("/manager/orders/dishes", (request, response) -> {
      Dish dish = new Dish(request.queryParams("dish-name"), Integer.parseInt(request.queryParams("category-id")));
      dish.save();
      response.redirect("/manager/orders/dishes");
      return null;
    });

// GET DISH

    get("/manager/dishes/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("dish", Dish.find(Integer.parseInt(request.params(":id"))));
      model.put("recipes", Recipe.all());
      model.put("ingredients", Ingredient.all());
      model.put("template", "templates/dish.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// UPDATE DISH

    post("/manager/dishes/:id/update", (request, response) -> {
      Dish dish = Dish.find(Integer.parseInt(request.params("id")));
      String newName = request.queryParams("new-name");
      dish.update(newName, dish.getCategory());
      response.redirect("/manager/dishes/" + dish.getId());
      return null;
    });

    post("/manager/dishes/:id/add-ingredient", (request, response) -> {
      Dish dish = Dish.find(Integer.parseInt(request.queryParams("dish-id")));
      dish.addIngredient(Integer.parseInt(request.queryParams("add-ingredient")), Integer.parseInt(request.queryParams("amount")));
      response.redirect("/manager/dishes/" + dish.getId());
      return null;
    });

// TODO: POST DISH:DELETE INGREDIENT FROM LIST

  }
}
