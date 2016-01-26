import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void courses_instantiateWithNameAndAbbreviation() {
    Course course = new Course(1, 101, "Programming and You");
    course.save();
    assertEquals("Programming and You", Course.find(course.getId()).getName());
    assertEquals(101, Course.find(course.getId()).getNumber());
    assertEquals(1, Course.find(course.getId()).getDepartmentId());
  }

  @Test
  public void course_deleteWorksProperly_0() {
    Course course = new Course(1, 101, "Programming and You");
    course.save();
    course.delete();
    assertEquals(0, Course.all().size());
  }

  @Test
  public void course_updateWorksProperly() {
    Course course = new Course(1, 101, "Programming and You");
    course.save();
    course.update(2, 201, "World War I");
    assertEquals(course.getName(), "World War I");
    assertEquals(course.getNumber(), 201);
    assertEquals(course.getDepartmentId(), 2);
  }

  @Test
  public void equals_returnsTrueIfSameNameAndAbbreviation() {
    Course firstCourse = new Course(1, 101, "Programming and You");
    firstCourse.save();
    Course secondCourse = new Course(1, 101, "Programming and You");
    secondCourse.save();
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void allStudents_returnsListOfAllStudentsInCourse() {
    Course course = new Course(1, 101, "Programming and You");
    course.save();
    Student student = new Student("Jim Everyman");
    student.save();
    student.setEnrollmentDate("2012-01-01");
    student.enrollIn(course.getId());
    assertTrue(course.getAllStudents().contains(student));
  }

  @Test
  public void getAllStudentsWhoPassed_returnsCorrectStudents() {
    Course course = new Course(1, 101, "Programming and You");
    course.save();
    Student student = new Student("Jim Everyman");
    student.save();
    student.setEnrollmentDate("1900-01-01");
    student.enrollIn(course.getId());
    Student secondStudent = new Student("Jim Allpeople");
    secondStudent.save();
    secondStudent.setEnrollmentDate("1900-01-01");
    secondStudent.enrollIn(course.getId());
    student.passCourse(course.getId());
    assertTrue(course.getAllStudentsWhoPassed().contains(student));
    assertEquals(1, course.getAllStudentsWhoPassed().size());
  }
}
