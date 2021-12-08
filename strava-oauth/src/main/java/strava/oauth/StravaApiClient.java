package strava.oauth;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Head;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

import java.util.List;

/**
 * Declarative HttpClient implemented by Micronaut. For more information, have a look at the Micronaut guides.
 */
@Client(id = "strava")
public interface StravaApiClient {

    @Get("athlete")
    Publisher<StravaUser> getUser(
            @Header(HttpHeaders.AUTHORIZATION) String authorization);

    @Get("athlete/activities")
    List<Activity> getActivities(@Header(HttpHeaders.AUTHORIZATION) String authorization);
}