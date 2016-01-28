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

    //GETTING RESOURCES

    get("/", (request, response) -> {
      response.redirect("/books");
      return null;
    });

    get("/patrons", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("copy", Copy.class);
      model.put("patrons", Patron.all());

      model.put("template", "templates/patrons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("copy", Copy.class);
      model.put("patrons", Patron.all());
      model.put("books", Book.all());

      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/authors", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("authors", Author.all());
      model.put("copy", Copy.class);
      model.put("patrons", Patron.all());

      model.put("template", "templates/authors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/acquisitions", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      boolean successfulAdd = true;
      boolean duplicateAuthor = false;
      boolean duplicateBook = false;

      model.put("duplicateBook", duplicateBook);
      model.put("duplicateAuthor", duplicateAuthor);
      model.put("authors", Author.all());
      model.put("books", Book.all());
      model.put("successfulAdd", successfulAdd);
      model.put("template", "templates/acquisitions.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("checkedOutCopies", Copy.getAllCheckedOut());
      model.put("allOverdueCopies", Copy.getAllOverdue());
      model.put("patron", Patron.class);
      model.put("checkout", Checkout.class);
      model.put("copy", Copy.class);


      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    //CHANGING RESOURCES - ADD

    post("/books/add-book", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      boolean successfulAdd = true;
      boolean duplicateBook = false;
      Book book = new Book(request.queryParams("new-book-title"));
      for (Book newBook : book.all()) {
        if (newBook.equals(book)) {
          duplicateBook = true;
        }
      }
      if (!duplicateBook) {
        book.save();
        book.assignAuthor(Integer.parseInt(request.queryParams("new-book-author")));
        for (Integer i = Integer.parseInt(request.queryParams("new-book-copies")); i > 0; i--) {
          Copy copy = new Copy(book.getId());
          copy.save();
        }
      }
      model.put("books", Book.all());
      model.put("authors", Author.all());
      model.put("duplicateBook", duplicateBook);
      model.put("successfulAdd", successfulAdd);
      model.put("template", "templates/acquisitions.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/authors/add-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      boolean successfulAdd = true;
      boolean duplicateAuthor = false;
      Author author = new Author(request.queryParams("new-author-first"), request.queryParams("new-author-last"));
      for (Author newAuthor : author.all()) {
        if (newAuthor.equals(author)) {
          duplicateAuthor = true;
        }
      }
      if (!duplicateAuthor) {
        author.save();
      }

      model.put("books", Book.all());
      model.put("authors", Author.all());
      model.put("duplicateAuthor", duplicateAuthor);
      model.put("successfulAdd", successfulAdd);
      model.put("template", "templates/acquisitions.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/additional-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.queryParams("existing-book")));
      Author author = Author.find(Integer.parseInt(request.queryParams("existing-author")));
      boolean successfulAdd = book.assignAuthor(author.getId());
      model.put("successfulAdd", successfulAdd);
      model.put("books", Book.all());
      model.put("authors", Author.all());
      model.put("template", "templates/acquisitions.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    //CHANGING RESOURCES - DELETE


    //CHANGING RESOURCES - CHECKOUT/RETURN COPIES


  }
}
