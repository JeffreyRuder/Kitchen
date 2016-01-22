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

      //ROUTES: GETTING HOME PAGE

      get("/", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        model.put("client", Client.class);
        model.put("stylist", Stylist.class);
        model.put("stylists", Stylist.all(true));

        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      //ROUTES: GETTING RESOURCES

      get("/stylists/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Stylist thisStylist = Stylist.find(
          Integer.parseInt(request.params("id")));

        model.put("stylist", thisStylist);
        model.put("clients", thisStylist.getAllClients());

        model.put("template", "templates/stylist.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/clients/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Stylist thisStylist = Stylist.find(
          Integer.parseInt(request.params("id")));

        model.put("stylist", thisStylist);
        model.put("clients", thisStylist.getAllClients());

        model.put("template", "templates/client.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      //ROUTES: CHANGING RESOURCES

      post("/", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        //Add a stylist
        if (request.queryParams("addnewstylist") != null) {
          String requestedFirst = request.queryParams("firstname");
          String requestedLast = request.queryParams("lastname");
          Stylist requestedStylist = new Stylist(requestedFirst, requestedLast);
          boolean duplicateStylistRequested = requestedStylist.isDuplicate();
          if (!(duplicateStylistRequested)) {
            requestedStylist.save();
          } else {
            model.put("duplicatestylistrequested", duplicateStylistRequested);
          }
        }

        //Remove a stylist
        if (request.queryParams("removestylist") != null) {
          Stylist removalRequest = Stylist.find(Integer.parseInt(
            request.queryParams("removestylist")));
          removalRequest.delete();
        }

        model.put("client", Client.class);
        model.put("stylist", Stylist.class);
        model.put("stylists", Stylist.all(true));

        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/stylists/:id", (request, response))
    }
}
