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
        model.put("unassignedclients", Client.unassignedClientsExist());

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
        Client thisClient = Client.find(
          Integer.parseInt(request.params("id")));
        Stylist currentStylist = Stylist.find(thisClient.getStylistId());

        model.put("stylist", Stylist.class);
        model.put("currentstylist", currentStylist);
        model.put("client", thisClient);

        model.put("template", "templates/client.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      //ROUTES: CHANGING RESOURCES

      post("/", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        //Create a stylist
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

        //Delete a stylist
        if (request.queryParams("removestylist") != null) {
          Stylist removalRequest = Stylist.find(Integer.parseInt(
            request.queryParams("removestylist")));
          removalRequest.delete();
        }

        model.put("client", Client.class);
        model.put("stylist", Stylist.class);
        model.put("stylists", Stylist.all(true));
        model.put("unassignedclients", Client.unassignedClientsExist());

        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/stylists/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Stylist thisStylist = Stylist.find(
          Integer.parseInt(request.params("id")));

        //Update a stylist

        if (request.queryParams("changestylist") != null) {
          String requestedFirst = request.queryParams("newfirstname");
          String requestedLast = request.queryParams("newlastname");
          Stylist duplicateChecker = new Stylist(requestedFirst, requestedLast);
          boolean duplicateStylistRequested = duplicateChecker.isDuplicate();
          if (!(duplicateStylistRequested)) {
            thisStylist.update(requestedFirst, requestedLast);
          } else {
            model.put("duplicatestylistrequested", duplicateStylistRequested);
          }
        }

        //Create a client
        if (request.queryParams("addnewclient") != null) {
          String requestedFirst = request.queryParams("firstname");
          String requestedLast = request.queryParams("lastname");
          Client requestedClient = new Client(requestedFirst, requestedLast);
          boolean duplicateClientRequested = requestedClient.isDuplicate();
          if (!(duplicateClientRequested)) {
            requestedClient.save();
            requestedClient.assignStylist(thisStylist.getId());
          } else {
            model.put("duplicateclientrequested", duplicateClientRequested);
          }
        }

        //Delete a client
        if (request.queryParams("removeclient") != null) {
          Client removalRequest = Client.find(Integer.parseInt(
            request.queryParams("removeclient")));
          removalRequest.delete();
        }

        model.put("stylist", thisStylist);
        model.put("clients", thisStylist.getAllClients());

        model.put("template", "templates/stylist.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/clients/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Client thisClient = Client.find(
          Integer.parseInt(request.params("id")));
        Stylist currentStylist = Stylist.find(thisClient.getStylistId());

        //Update a client - change name
        if (request.queryParams("changeclientname") != null) {
          String requestedFirst = request.queryParams("newfirstname");
          String requestedLast = request.queryParams("newlastname");
          Client duplicateChecker = new Client(requestedFirst, requestedLast);
          boolean duplicateClientRequested = duplicateChecker.isDuplicate();
          if (!(duplicateClientRequested)) {
            thisClient.update(requestedFirst, requestedLast);
          } else {
            model.put("duplicateclientrequested", duplicateClientRequested);
          }
        }

       //Update a client - change stylist
        if (request.queryParams("changestylist") != null) {
          thisClient.assignStylist(Integer.parseInt(
            request.queryParams("newstylistid")));
          currentStylist = Stylist.find(thisClient.getStylistId());
        }

        model.put("stylist", Stylist.class);
        model.put("currentstylist", currentStylist);
        model.put("client", thisClient);

        model.put("template", "templates/client.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    }
}
