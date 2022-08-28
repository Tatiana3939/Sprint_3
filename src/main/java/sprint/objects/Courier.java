package sprint.objects;

public class Courier {
    private String id;
    private String login;
    private String password;
    private String firstName;

    public Courier(String id, String login, String password, String firstName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Courier(String id) {
        this.id = id;
    }

    public Courier() {
    }

    public String getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }

    public String setId(String id) {
        this.id = id;
        return id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
