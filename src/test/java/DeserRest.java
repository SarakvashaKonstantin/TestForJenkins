import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeserRest {

    public int getId() {
        return id;
    }

    public int id;
    public String name;
    public String username;
    public String email;
    public String phone;

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String website;


    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
