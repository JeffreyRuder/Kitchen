import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_catalog_test", null, null);

  }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM * CASCADE").executeUpdate();

      // String deleteBooksQuery = "DELETE FROM books CASCADE;";
      // String deleteAuthorsQuery = "DELETE FROM authors CASCADE;";
      // String deleteGenresQuery = "DELETE FROM genres CASCADE;";
      // String deletePatronsQuery = "DELETE FROM patrons CASCADE;";
      // String deleteCopiesQuery = "DELETE FROM copies CASCADE;";
      // String deleteCheckoutsQuery = "DELETE FROM checkouts CASCADE;";
      // String deleteAuthorsBooksQuery = "DELETE FROM authors_books CASCADE;";
      // String deleteBooksGenresQuery = "DELETE FROM books_genres CASCADE;";
      //
      // con.createQuery(deleteBooksQuery).executeUpdate();
      // con.createQuery(deleteAuthorsQuery).executeUpdate();
      // con.createQuery(deleteGenresQuery).executeUpdate();
      // con.createQuery(deletePatronsQuery).executeUpdate();
      // con.createQuery(deleteCopiesQuery).executeUpdate();
      // con.createQuery(deleteCheckoutsQuery).executeUpdate();
      // con.createQuery(deleteAuthorsBooksQuery).executeUpdate();
      // con.createQuery(deleteBooksGenresQuery).executeUpdate();
    }
  }
}
