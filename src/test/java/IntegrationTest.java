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
  public void taskIsCreatedTest() {
      goTo("http://localhost:4567/");
      fill("#description").with("Mow the lawn");
      submit(".btn");
      assertThat(pageSource()).contains("Your task has been saved.");
  }

  @Test
  public void taskIsDisplayedTest() {
      goTo("http://localhost:4567/");
      fill("#description").with("Mow the lawn");
      submit(".btn");
      click("a", withText("Go Back"));
      assertThat(pageSource()).contains("Mow the lawn");
  }

  @Test
  public void multipleTasksAreDisplayedTest() {
      goTo("http://localhost:4567/");
      fill("#description").with("Mow the lawn");
      submit(".btn");
      click("a", withText("Go Back"));
      goTo("http://localhost:4567/");
      fill("#description").with("Wash the dishes");
      submit(".btn");
      click("a", withText("Go Back"));
      assertThat(pageSource()).contains("Mow the lawn");
      assertThat(pageSource()).contains("Wash the dishes");
  }
}
