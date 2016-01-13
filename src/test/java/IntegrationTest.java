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
  public void rootTest() {
      goTo("http://localhost:4567/");
      assertThat(pageSource()).contains("Task List");
  }

  @Test
  public void taskIsDisplayedTest() {
      goTo("http://localhost:4567/");
      click("a", withText("Add a New Task"));
      fill("#description").with("Mow the lawn");
      submit(".btn");
      assertThat(pageSource()).contains("Mow the lawn");
  }

  @Test
  public void multipleTasksAreDisplayedTest() {
      goTo("http://localhost:4567/tasks/new");
      fill("#description").with("Mow the lawn");
      submit(".btn");
      goTo("http://localhost:4567/tasks/new");
      fill("#description").with("Wash the dishes");
      submit(".btn");
      assertThat(pageSource()).contains("Mow the lawn");
      assertThat(pageSource()).contains("Wash the dishes");
  }

  @Test
  public void taskShowPageDisplaysDescription() {
      goTo("http://localhost:4567/tasks/new");
      fill("#description").with("Cook dinner");
      submit(".btn");
      click("a", withText("Cook dinner"));
      assertThat(pageSource()).contains("Cook dinner");
  }

  @Test
  public void taskNotFoundMessageShown() {
      goTo("http://localhost:45567/tasks/999");
      assertThat(pageSource().contains("Task Not Found"));
  }
}
