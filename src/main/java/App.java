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

    get("/brands/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Brand thisBrand = Brand.find(Integer.parseInt(request.params("id")));
      model.put("brand", thisBrand);
      model.put("stores", Store.all());
      model.put("template", "templates/brand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //MODIFY

    //Stores
    post("/stores/add-new-store", (request, response) -> {
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

    //Brands
    post("/brands/add-new-brand", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Brand newBrand = new Brand(request.queryParams("add-new-brand"));
      boolean duplicateBrandRequested = Brand.all().contains(newBrand);
      if (!duplicateBrandRequested) { newBrand.save(); }
      model.put("duplicateBrandRequested", duplicateBrandRequested);
      model.put("brands", Brand.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/brands/:id/add-store", (request, response) -> {
      Brand thisBrand = Brand.find(Integer.parseInt(request.params("id")));
      thisBrand.addStore(Integer.parseInt(request.queryParams("brand-add-store")));
      response.redirect("/brands/" + thisBrand.getId());
      return null;
    });

    post("/brands/:id/remove-store", (request, response) -> {
      Brand thisBrand = Brand.find(Integer.parseInt(request.params("id")));
      thisBrand.removeStore(Integer.parseInt(request.queryParams("store-to-remove")));
      response.redirect("/brands/" + thisBrand.getId());
      return null;
    });

    post("/brands/:id/change-name", (request, response) -> {
      Brand thisBrand = Brand.find(Integer.parseInt(request.params("id")));
      thisBrand.update(request.queryParams("brand-new-name"));
      response.redirect("/brands/" + thisBrand.getId());
      return null;
    });

    //DELETE

    post("/stores/:id/delete-store", (request, response) -> {
      Store thisStore = Store.find(Integer.parseInt(request.params("id")));
      thisStore.delete();
      response.redirect("/stores");
      return null;
    });

    post("/brands/:id/delete-brand", (request, response) -> {
      Brand thisBrand = Brand.find(Integer.parseInt(request.params("id")));
      thisBrand.delete();
      response.redirect("/brands");
      return null;
    });
  }
}
