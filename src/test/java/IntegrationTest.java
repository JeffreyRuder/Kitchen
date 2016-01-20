import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
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

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void welcomePageIsCreatedTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Welcome to the To Do List");
  }

  @Test
  public void ableToSeeAllOfThelistsTest(){
    goTo("http://localhost:4567/");
    click("a", withText("See all tasks"));
    assertThat(pageSource()).contains("Your tasks for today:");
  }

  @Test
  public void allTasksPageIncludesTasks() {
    Category category = new Category("Household chores");
    category.save();
    Task task = new Task("Clean bathroom", category.getId());
    task.save();
    goTo("http://localhost:4567/");
    click("a", withText("See all tasks"));
    assertThat(pageSource()).contains("Clean bathroom");
  }

  @Test
  public void ableToSeeAllCategorysTest(){
    Category firstCategory = new Category("Household chores");
    Category secondCategory = new Category("Shops");
    firstCategory.save();
    secondCategory.save();
    goTo("http://localhost:4567/");
    click("a", withText("See all categories"));
    assertThat(pageSource()).contains("Household chores");
    assertThat(pageSource()).contains("Shops");
  }

  @Test
  public void selectCategoryToSeeTasks() {
    Category category = new Category("Household chores");
    category.save();
    Task task = new Task("Clean bathroom", category.getId());
    task.save();
    goTo("http://localhost:4567/category/" + Integer.toString(category.getId()));
    assertThat(pageSource()).contains("Clean bathroom");
  }

  @Test
  public void createNewCategoryTrue(){
    goTo("http://localhost:4567/allcategories");
    click("a", withText("Create new category"));
    assertThat(pageSource()).contains("Enter new category name");
  }

  @Test
  public void newCategorySuccessfullyCreated(){
    goTo("http://localhost:4567/newcategory");
    fill("#name").with("Car repairs");
    submit(".btn");
    assertThat(pageSource()).contains("Car repairs");
  }

  @Test
  public void newTaskSuccessfullyCreated(){
    Category category = new Category("Car repairs");
    category.save();
    Task task = new Task("Clean bathroom", category.getId());
    task.save();
    goTo("http://localhost:4567/category/" + Integer.toString(category.getId()));
    fill("#name").with("change oil");
    submit(".btn-success");
    assertThat(pageSource()).contains("change oil");
  }

  @Test
  public void taskCanBeFinished() {
    Category category = new Category("Car repairs");
    category.save();
    Task task = new Task("Change wiper blades", category.getId());
    task.save();
    goTo("http://localhost:4567/category/" + Integer.toString(category.getId()));
    submit(".btn-removal");
    assertThat(!(pageSource()).contains("Change wiper blades"));
  }

  @Test
  public void showFinishedTasksPage() {
    Category category = new Category("Car repairs");
    category.save();
    Task task = new Task("Change wiper blades", category.getId());
    task.save();
    task.finish();
    goTo("http://localhost:4567/");
    click("a", withText("See all finished tasks"));
    assertThat(pageSource()).contains("Change wiper blades");
  }

}
