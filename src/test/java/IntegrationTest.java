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
  public void addStylistForm_addsStylist() {
    goTo("http://localhost:4567/");
    fill("#firstname").with("Charles");
    fill("#lastname").with("Babbage");
    submit(".btn");
    assertThat(pageSource()).contains("Babbage, Charles");
  }

  @Test
  public void removeStylistForm_removesStylist() {
    Stylist stylist = new Stylist("Grace", "Hopper");
    stylist.save();
    goTo("http://localhost:4567/");
    click(".btn-link", withText("Remove Grace Hopper"));
    assertThat(!(pageSource()).contains("Hopper, Grace"));
  }

  @Test
  public void addClients_addsClientsToStylist() {
    Stylist stylist = new Stylist("Grace", "Hopper");
    stylist.save();
    goTo("http://localhost:4567/stylists/" +
      Integer.toString(stylist.getId()));
      fill("#firstname").with("Richard");
      fill("#lastname").with("Roe");
      submit(".btn", withText("Add New Client"));
      assertThat(pageSource()).contains("Roe, Richard");
  }

  @Test
  public void removeClientForm_removesClient() {
    Stylist stylist = new Stylist("Grace", "Hopper");
    stylist.save();
    Client client = new Client("Richard", "Roe");
    client.save();
    client.assignStylist(stylist.getId());
    goTo("http://localhost:4567/stylists/" +
      Integer.toString(stylist.getId()));
    click(".btn-link", withText("Remove Richard Roe"));
    assertThat(!(pageSource()).contains("Roe, Richard"));
  }

  @Test
  public void updateClientForm_ChangesName() {
    Stylist stylist = new Stylist("Grace", "Hopper");
    stylist.save();
    Client client = new Client("Richard", "Roe");
    client.save();
    client.assignStylist(stylist.getId());
    goTo("http://localhost:4567/clients/" +
      Integer.toString(client.getId()));
    fill("#newfirstname").with("Larry");
    fill("#newlastname").with("Loe");
    submit(".btn", withText("Update Name"));
    assertThat(!(pageSource()).contains("Richard Roe"));
    assertThat(pageSource()).contains("Larry Loe");
  }

  @Test
  public void assignStylistForm_assignsNewStylist() {
    Stylist stylist = new Stylist("Grace", "Hopper");
    stylist.save();
    Client client = new Client("Richard", "Roe");
    client.save();
    goTo("http://localhost:4567/clients/" +
      Integer.toString(client.getId()));
    fillSelect("#stylistselector").withText("Grace Hopper");
    submit(".btn", withText("Change Stylist"));
    assertThat(!(pageSource()).contains("Unassigned Clients"));
  }

  @Test
  public void createStylistForm_preventsDuplicateNames() {
    Stylist stylist = new Stylist("Grace", "Hopper");
    stylist.save();
    goTo("http://localhost:4567/");
    fill("#firstname").with("Grace");
    fill("#lastname").with("Hopper");
    submit(".btn");
    assertThat(pageSource()).contains("That stylist is already in the database");
  }

  @Test
  public void createClientForm_preventsDuplicateNames() {
    Stylist stylist = new Stylist("Grace", "Hopper");
    stylist.save();
    Client client = new Client("Richard", "Roe");
    client.save();
    client.assignStylist(stylist.getId());
    goTo("http://localhost:4567/stylists/" +
      Integer.toString(stylist.getId()));
    fill("#firstname").with("Richard");
    fill("#lastname").with("Roe");
    submit(".btn", withText("Add New Client"));
    assertThat(pageSource()).contains("That name is already in the database");
  }

  @Test
  public void unassignedClients_highlightedOnHomePage() {
    Client client = new Client("Richard", "Roe");
    client.save();
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Warning! There are clients not assigned to a stylist.");
  }

  @Test
  public void unassignedClients_getSpecialLinkInBreadcrumbs() {
    Client client = new Client("Richard", "Roe");
    client.save();
    goTo("http://localhost:4567/clients/" +
      Integer.toString(client.getId()));
    assertThat(pageSource()).contains("Unassigned Clients");
  }

}
