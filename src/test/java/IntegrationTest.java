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
  public void homePageIsCreated() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Add New Stylist");
  }

  @Test
  public void addClientFormWorks() {
    goTo("http://localhost:4567/");
    fill("#firstname").with("Charles");
    fill("#lastname").with("Babbage");
    submit(".btn");
    assertThat(pageSource()).contains("Babbage, Charles");
  }


}
