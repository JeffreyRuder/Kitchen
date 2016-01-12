import org.junit.*;
import static org.junit.Assert.*;

public class TaskTest {

  @Test
  public void task_instantiatesCorrectly_true() {
      Task testTask = new Task("Mow the lawn");
      assertEquals(true, testTask instanceof Task);
  }

  @Test
  public void task_instantiatesWithDescription_true() {
      Task testTask = new Task("Mow the lawn");
      assertEquals("Mow the lawn", testTask.getDescription());
  }

}
