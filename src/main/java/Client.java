import java.util.List;
import org.sql2o.*;

public class Client {
  private String mFirstName;
  private String mLastName;
  private int mId;
  private int mStylistId;
  private boolean mDuplicate;

  //CONSTRUCTOR

  public Client(String first, String last) {
    mFirstName = first.trim();
    mLastName = last.trim();
    mDuplicate = false;
    for (Client client : Client.all()) {
      if (this.equals(client)) {
        mDuplicate = true;
      }
    }
  }

  //GETTERS

  public String getFirstName() {
    return mFirstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public String getFullName() {
    return mFirstName + " " + mLastName;
  }

  public int getId() {
    return mId;
  }

  public int getStylistId() {
    return mStylistId;
  }

  public boolean isDuplicate() {
    return mDuplicate;
  }

  //EQUALITY

  @Override
  public boolean equals(Object otherClient) {
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client client = (Client) otherClient;
      return this.getFullName().equals(client.getFullName());
    }
  }

  //CREATE

  public void save() {
    String sql = "INSERT INTO clients(first_name, last_name) VALUES (:first, :last)";
    try (Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("first", this.getFirstName())
        .addParameter("last", this.getLastName())
        .executeUpdate()
        .getKey();
    }
  }

  //READ

  public static List<Client> all() {
    String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName FROM clients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Client.class);
    }
  }

  public static List<Client> all(boolean alphabetical) {
    if (alphabetical) {
      List<Client> clients = Client.all();
      clients.sort((c1, c2) -> c1.mLastName.compareTo(c2.mLastName));
      return clients;
    } else {
      return Client.all();
    }
  }

  public static Client find(int search) {
    String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName, stylist_id AS mStylistId FROM clients WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      Client client = (Client) con.createQuery(sql)
        .addParameter("id", search)
        .executeAndFetchFirst(Client.class);
      return client;
    }
  }

  public static Integer total() {
    String sql = "SELECT count(id) FROM clients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeScalar(Integer.class);
    }
  }

  //UPDATE

  public void update(String first, String last) {
    String sql = "UPDATE clients SET first_name = :first, last_name = :last WHERE id = :id";
    mFirstName = first;
    mLastName = last;
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("first", first)
        .addParameter("last", last)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public void assignStylist(int stylistId) {
    String sql = "UPDATE clients SET stylist_id = :stylistid WHERE id = :id";
    mStylistId = stylistId;
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("stylistid", stylistId)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  //DELETE

  public void delete() {
    String sql = "DELETE FROM clients WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }
}
