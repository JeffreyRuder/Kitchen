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
        model.put("stylists", Stylist.all());

        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());


      //ROUTES: GETTING RESOURCES


      //ROUTES: CHANGING RESOURCES
    }
}
