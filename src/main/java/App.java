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

    //GETTING

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stores", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stores", Store.all());
      model.put("template", "templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/brands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("brands", Brand.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stores/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Store thisStore = Store.find(Integer.parseInt(request.params("id")));
      model.put("store", thisStore);
      model.put("brands", Brand.all());
      model.put("template", "templates/store.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //CHANGING

    post("stores/add-new-store", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Store newStore = new Store(request.queryParams("add-new-store"));
      boolean duplicateStoreRequested = Store.all().contains(newStore);
      if (!duplicateStoreRequested) { newStore.save(); }
      model.put("duplicateStoreRequested", duplicateStoreRequested);
      model.put("stores", Store.all());
      model.put("template", "templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stores/:id/add-brand", (request, response) -> {
      Store thisStore = Store.find(Integer.parseInt(request.params("id")));
      thisStore.addBrand(Integer.parseInt(request.queryParams("store-add-brand")));
      response.redirect("/stores/" + thisStore.getId());
      return null;
    });

    post("/stores/:id/remove-brand", (request, response) -> {
      Store thisStore = Store.find(Integer.parseInt(request.params("id")));
      thisStore.removeBrand(Integer.parseInt(request.queryParams("brand-to-remove")));
      response.redirect("/stores/" + thisStore.getId());
      return null;
    });

    post("/stores/:id/change-name", (request, response) -> {
      Store thisStore = Store.find(Integer.parseInt(request.params("id")));
      thisStore.update(request.queryParams("store-new-name"));
      response.redirect("/stores/" + thisStore.getId());
      return null;
    });

    //DELETING

    post("/stores/:id/delete-store", (request, response) -> {
      Store thisStore = Store.find(Integer.parseInt(request.params("id")));
      thisStore.delete();
      response.redirect("/stores");
      return null;
    });
  }
}
