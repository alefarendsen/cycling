package strava.oauth;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple controller just indicating that the main request to localhost:8080 should render a view called 'home'.
 * In this case, that will render a Thymeleaf page, in /src/main/resources/views/home.html
 */
@Controller
public class HomeController {

    @Secured(SecurityRule.IS_ANONYMOUS)
    @View("home")
    @Get
    public Map<String, Object> index() {
        return new HashMap<>();
    }
}
