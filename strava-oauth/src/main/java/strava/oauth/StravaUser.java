package strava.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StravaUser {

    private String username;
    private String firstname;
    private String lastname;
    private long id;

    public StravaUser(String username, String firstname, String lastname, long id) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}