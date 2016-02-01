import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class DishTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void equals_returnsTrueIfSameName() {
    Dish firstDish = new Dish("Cheeseburger");
    firstDish.save();
    Dish secondDish = new Dish("Cheeseburger");
    secondDish.save();
    assertTrue(firstDish.equals(secondDish));
  }

}
