import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class DepartmentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Department.all().size(), 0);
  }

  @Test
  public void departments_instantiateWithNameAndAbbreviation() {
    Department department = new Department("History", "HST");
    department.save();
    assertEquals("History", Department.find(department.getId()).getName());
    assertEquals("HST", Department.find(department.getId()).getAbbreviation());
  }

  @Test
  public void department_deleteWorksProperly_0() {
    Department department = new Department("History", "HST");
    department.save();
    department.delete();
    assertEquals(0, Department.all().size());
  }

  @Test
  public void department_updateWorksProperly() {
    Department department = new Department("History", "HST");
    department.save();
    department.update("History and Philosophy", "HSPH");
    assertEquals(department.getName(), "History and Philosophy");
    assertEquals(Department.find(department.getId()).getAbbreviation(), "HSPH");
  }

  @Test
  public void equals_returnsTrueIfSameNameAndAbbreviation() {
    Department firstDepartment = new Department("History", "HST");
    firstDepartment.save();
    Department secondDepartment = new Department("History", "HST");
    secondDepartment.save();
    assertTrue(firstDepartment.equals(secondDepartment));
  }

  @Test
  public void allDepartmentCourses_returnsListOfAllCoursesInDepartment() {
    Department department = new Department("Computer Science", "CMP");
    department.save();
    Course course = new Course(department.getId(), 101, "Programming and You");
    course.save();
    assertTrue(department.getAllCourses().contains(course));
  }

  @Test
  public void getStudentsWhoHaveNotTakenCourses_returnsCorrectList() {
    Department department = new Department("Computer Science", "CMP");
    department.save();
    Course course = new Course(department.getId(), 101, "Programming and You");
    course.save();
    Student firstStudent = new Student("Jane Roe");
    Student secondStudent = new Student("John Doe");
    firstStudent.save();
    firstStudent.setEnrollmentDate("1900-01-01");
    secondStudent.save();
    secondStudent.setEnrollmentDate("1979-04-04");
    firstStudent.enrollIn(course.getId());
    assertTrue(department.getStudentsWhoHaveNotTakenCourses()
      .contains(secondStudent));
    assertEquals(1, department.getStudentsWhoHaveNotTakenCourses().size());
  }
}
