package strava.oauth;

import io.micronaut.http.HttpHeaderValues;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper.ACCESS_TOKEN_KEY;

/**
 * Simple Micronaut @Controller mapped to /activities. Uses the {@link StravaApiClient} internally to
 * query Strava for activities.
 */
@Controller("/activities")
public class ActivitiesController {

    private StravaApiClient apiClient;

    /**
     * DI happening by Micronaut here
     */
    ActivitiesController(StravaApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Only available for authenticated users
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @View("activities")
    @Get
    Map<String, Object> list(Authentication authentication) {
        // the API client get passed the Bearer token, generated from the Authentication object. This is
        // further down the line passed on as an HttpHeader (see the StravaApiClient interface for more info)
        List<Activity> activities = apiClient.getActivities(authorizationValue(authentication));
        // activities returned are put straight in the model map, to be rendered by Thymeleaf
        Map<String, Object> model = new HashMap<>();
        model.put("activities", activities);
        return model;
    }

    private String authorizationValue(Authentication authentication) {
        Object claim = authentication.getAttributes().get(ACCESS_TOKEN_KEY);
        if (claim instanceof String) {
            return HttpHeaderValues.AUTHORIZATION_PREFIX_BEARER + ' ' + claim;
        }
        return null;
    }
}
