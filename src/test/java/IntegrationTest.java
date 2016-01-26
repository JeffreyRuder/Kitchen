import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void studentsPage_addStudentFormAddsStudent() {
    Department newDepartment = new Department("History", "HST");
    newDepartment.save();
    goTo("http://localhost:4567/students");
    fill("#student-name").with("Sally Sue");
    fillSelect("#student-department").withText("History");
    submit(".btn");
    assertThat(pageSource()).contains("Sue, Sally");
    assertThat(pageSource()).contains("History");
  }

  @Test
  public void studentsPage_displaysAllStudents() {
    Student firstStudent = new Student("John Doe");
    Student secondStudent = new Student("Jane Roe");
    firstStudent.save();
    secondStudent.save();
    firstStudent.setEnrollmentDate("2012-01-01");
    secondStudent.setEnrollmentDate("2012-01-01");
    goTo("http://localhost:4567/students");
    assertThat(pageSource()).contains("Doe, John");
    assertThat(pageSource()).contains("Roe, Jane");
    assertThat(pageSource()).contains("2012-01-01");
  }

  @Test
  public void coursesPage_displaysAllStudents() {
    Student firstStudent = new Student("John Doe");
    Student secondStudent = new Student("Jane Roe");
    firstStudent.save();
    secondStudent.save();
    firstStudent.setEnrollmentDate("2012-01-01");
    secondStudent.setEnrollmentDate("2012-01-01");
    goTo("http://localhost:4567/students");
    assertThat(pageSource()).contains("Doe, John");
    assertThat(pageSource()).contains("Roe, Jane");
    assertThat(pageSource()).contains("2012-01-01");
  }



}
