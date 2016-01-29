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

    get("/patrons/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Patron thisPatron = Patron.find(Integer.parseInt(request.params("id")));

      model.put("patron", thisPatron);
      model.put("copy", Copy.class);
      model.put("template", "/templates/patron.vtl");
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

    get("/books/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Book thisBook = Book.find(Integer.parseInt(request.params("id")));

      model.put("book", thisBook);
      model.put("patron", Patron.class);
      model.put("checkout", Checkout.class);
      model.put("template", "templates/book.vtl");
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

    get("/authors/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author thisAuthor = Author.find(Integer.parseInt(request.params(":id")));

      model.put("author", thisAuthor);
      model.put("template", "templates/author.vtl");

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
      model.put("patrons", Patron.all());
      model.put("copy", Copy.class);
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
      model.put("patrons", Patron.all());
      model.put("copy", Copy.class);
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
      model.put("patrons", Patron.all());
      model.put("copy", Copy.class);
      model.put("template", "templates/acquisitions.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patrons/add-patron", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      boolean successfulAdd = true;
      boolean duplicatePatron = false;
      Patron patron = new Patron(request.queryParams("new-patron-first"), request.queryParams("new-patron-last"), Integer.parseInt(request.queryParams("new-patron-phone")));
      for (Patron newPatron : patron.all()) {
        if (newPatron.equals(patron)) {
          duplicatePatron = true;
        }
      }
      if (!duplicatePatron) {
        patron.save();
      }

      model.put("books", Book.all());
      model.put("authors", Author.all());
      model.put("duplicatePatron", duplicatePatron);
      model.put("successfulAdd", successfulAdd);
      model.put("patrons", Patron.all());
      model.put("copy", Copy.class);
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
      model.put("patrons", Patron.all());
      model.put("copy", Copy.class);
      model.put("template", "templates/acquisitions.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/additional-copies", (request, response) -> {
      Book book = Book.find(Integer.parseInt(request.queryParams("existing-book")));

      for (int i = Integer.parseInt(request.queryParams("new-book-copies")); i > 0; i--) {
        Copy copy = new Copy(book.getId());
        copy.save();
      }

      response.redirect("/books/" + book.getId());
      return null;
    });

    post("/books/:id/update_book_title", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Book thisBook = Book.find(Integer.parseInt(request.params("id")));

      boolean duplicateBook = false;
      Book duplicateCheck = new Book(request.queryParams("book-title"));
      for (Book book : Book.all()) {
        if (book.equals(duplicateCheck)) {
          duplicateBook = true;
        }
      }
      if (!duplicateBook) {
        thisBook.update(request.queryParams("book-title"));
      }

      model.put("book", thisBook);
      model.put("patron", Patron.class);
      model.put("checkout", Checkout.class);
      model.put("duplicateBook", duplicateBook);
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/authors/:id/update_author_name", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Author thisAuthor = Author.find(Integer.parseInt(request.params(":id")));

      boolean duplicateAuthor = false;
      Author duplicateCheck = new Author(request.queryParams("author-first-name"), request.queryParams("author-last-name"));
      for (Author author : Author.all()) {
        if (author.equals(duplicateCheck)) {
          duplicateAuthor = true;
        }
      }
      if (!duplicateAuthor) {
        thisAuthor.update(request.queryParams("author-first-name"), request.queryParams("author-last-name"));
      }

      model.put("author", thisAuthor);
      model.put("duplicateAuthor", duplicateAuthor);
      model.put("template", "templates/author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patrons/:id/update_patron", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Patron thisPatron = Patron.find(Integer.parseInt(request.params(":id")));

      boolean duplicatePatron = false;
      Patron duplicateCheck = new Patron(request.queryParams("patron-first-name"), request.queryParams("patron-last-name"), Integer.parseInt(request.queryParams("patron-phone")));
      for (Patron patron : Patron.all()) {
        if (patron.equals(duplicateCheck)) {
          duplicatePatron = true;
        }
      }
      if (!duplicatePatron) {
        thisPatron.updateName(request.queryParams("patron-first-name"), request.queryParams("patron-last-name"));
        thisPatron.updatePhone(Integer.parseInt(request.queryParams("patron-phone")));
      }

      model.put("patron", thisPatron);
      model.put("copy", Copy.class);
      model.put("duplicatePatron", duplicatePatron);
      model.put("template", "templates/patron.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //CHANGING RESOURCES - DELETE
    post("/patrons/remove-patron", (request, response) -> {

      Patron patron = Patron.find(Integer.parseInt(request.queryParams("existing-patron")));
      patron.delete();

      response.redirect("/patrons");
      return null;
    });

    post("/books/remove-copy", (request, response) -> {

      Copy copy = Copy.find(Integer.parseInt(request.queryParams("delete-copy")));
      int bookId = copy.getBookId();
      copy.delete();

      response.redirect("/books/" + bookId);
      return null;
    });

    //CHANGING RESOURCES - CHECKOUT/RETURN COPIES

    post("/patrons/checkout", (request, response) -> {
      Patron patron = Patron.find(Integer.parseInt(request.queryParams("patrons-patron-checkout")));
      patron.checkout(Integer.parseInt(request.queryParams("patrons-book-checkout")));
      response.redirect("/patrons/" + patron.getId());
      return null;
    });

    post("/patrons/return", (request, response) -> {
      Copy copy = Copy.find(Integer.parseInt(request.queryParams("patrons-book-return")));
      copy.returnCopy();
      response.redirect("/books/" + copy.getBookId());
      return null;
    });


  }
}
