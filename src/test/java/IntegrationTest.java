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
  public void coursesPage_displaysAllCourses() {
    Department newDepartment = new Department("Computer Science", "CS");
    newDepartment.save();
    Course newCourse = new Course(newDepartment.getId(), 101, "Intro to Programming");
    newCourse.save();
    goTo("http://localhost:4567/courses");
    assertThat(pageSource()).contains("Intro to Programming");
    assertThat(pageSource()).contains("101");
    assertThat(pageSource()).contains("CS");
  }

  @Test
  public void departmentsPage_displaysAllDepartments() {
    Department newDepartment = new Department("Computer Science", "CS");
    newDepartment.save();
    goTo("http://localhost:4567/departments");
    assertThat(pageSource()).contains("Computer Science");
  }

  @Test
  public void indvStudentPage_displaysAllCourses() {
    Department newDepartment = new Department("Computer Science", "CS");
    newDepartment.save();
    Course newCourse = new Course(newDepartment.getId(), 101, "Intro to Programming");
    newCourse.save();
    Student newStudent = new Student("Molly Dogooder");
    newStudent.save();
    newStudent.enrollIn(newCourse.getId());
    newStudent.setMajor(newDepartment.getId());
    goTo("http://localhost:4567/student/" + newStudent.getId());
    assertThat(pageSource()).contains("Computer Science");
    assertThat(pageSource()).contains("Molly Dogooder");
    assertThat(pageSource()).contains("Intro to Programming");
  }

  @Test
  public void indvCoursePage_displaysAllStudents() {
    Department newDepartment = new Department("Computer Science", "CS");
    newDepartment.save();
    Course newCourse = new Course(newDepartment.getId(), 101, "Intro to Programming");
    newCourse.save();
    Student newStudent = new Student("Molly Dogooder");
    newStudent.save();
    newStudent.enrollIn(newCourse.getId());
    newStudent.setMajor(newDepartment.getId());
    goTo("http://localhost:4567/course/" + newCourse.getId());
    assertThat(pageSource()).contains("Computer Science");
    assertThat(pageSource()).contains("Dogooder, Molly");
    assertThat(pageSource()).contains("Intro to Programming");
  }

}
