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


      //ROUTES: CHANGING RESOURCES

      post("/", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        if (request.queryParams("addnewclient").equals("true")) {
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

        model.put("client", Client.class);
        model.put("stylist", Stylist.class);
        model.put("stylists", Stylist.all(true));

        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());
    }
}
