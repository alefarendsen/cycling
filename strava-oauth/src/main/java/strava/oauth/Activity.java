package strava.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;

/**
 * Activity object being mapped from the JSON response from Strava. Just a simple name of the
 * activity is mapped here, the remainder of the object is ommitted.
 */
@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Activity {

    private String name;

    public Activity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
