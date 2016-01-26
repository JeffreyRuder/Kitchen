import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/registrar_test", null, null);

  }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      // String deleteJoinQuery = "DELETE FROM students_courses_departments;";
      String deleteStudentsQuery = "DELETE FROM students;";
      String deleteCoursesQuery = "DELETE FROM courses;";
      String deleteDepartmentsQuery = "DELETE FROM departments;";
      // con.createQuery(deleteJoinQuery).executeUpdate();
      con.createQuery(deleteStudentsQuery).executeUpdate();
      con.createQuery(deleteCoursesQuery).executeUpdate();
      con.createQuery(deleteDepartmentsQuery).executeUpdate();
    }
  }
}
