package eagle;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.micronaut.views.View;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@Controller("/activity")
@Validated
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Inject ActivityDataRepository repository;

    @Inject DataFiller dataFiller;

    @Get("/home")
    @View("home")
    public void home() {
        logger.info("sending to home page");
    }

    @Post
    public HttpResponse<?> savePerson(@Body @Valid Activity activity) {
        repository.saveActivity(activity);
        return HttpResponse.status(HttpStatus.CREATED);
    }

    @Get
    public Iterable<Activity> list() {
        return repository.findAllActivities();
    }
    @Get("/{id}/records")
    public Iterable<ActivityRecord> recordsForActivity(Long id) {
        return repository.findActivityRecords(id);

    }

    @Get("/activities")
    public Iterable<Activity> list2() { return null; }
}