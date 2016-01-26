import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void students_haveName() {
    Student student = new Student("Max Power");
    student.save();
    assertEquals("Max Power", Student.find(student.getId()).getName());
  }

  @Test
  public void students_haveEnrollmentDate() {
    Student student = new Student("Max Power");
    student.save();
    student.setEnrollmentDate("2016-01-26");
    assertEquals("2016-01-26", Student.find(student.getId()).getEnrollmentDate());
  }

  @Test
  public void students_haveAMajorDepartment() {
    Student student = new Student("Max Power");
    student.save();
    Department department = new Department("History", "HST");
    department.save();
    student.setMajor(department.getId());
    assertEquals(department.getId(), student.getDepartmentId());
  }

  @Test
  public void student_deleteWorksProperly() {
    Student student = new Student("Lil' Jimmy");
    student.save();
    student.delete();
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void student_updateWorksProperly() {
    Student student = new Student("Lil' Jimmy");
    student.save();
    student.update("Jim Everyman");
    assertEquals(student.getName(), "Jim Everyman");
    assertEquals(Student.find(student.getId()).getName(), "Jim Everyman");
  }

  @Test
  public void equals_returnsTrueIfSameNameAndEnrollmentDate() {
    Student firstStudent = new Student("Lil' Jimmy");
    firstStudent.save();
    Student secondStudent = new Student("Lil' Jimmy");
    secondStudent.save();
    firstStudent.setEnrollmentDate("2016-01-26");
    secondStudent.setEnrollmentDate("2016-01-26");
    assertTrue(firstStudent.equals(secondStudent));
  }
}
