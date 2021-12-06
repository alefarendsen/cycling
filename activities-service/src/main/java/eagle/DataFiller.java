package eagle;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.io.Readable;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.micronaut.runtime.exceptions.ApplicationStartupException;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Bean
@ConfigurationProperties("eagle")
@Context
public class DataFiller implements ApplicationEventListener<ApplicationStartupEvent> {

    @Inject ActivityDataRepository activityDataRepository;

    @Inject
    DataSource dataSource;

    private Readable createTableScript;

    public void setCreateTableScript(Readable createTableScript) {
        this.createTableScript = createTableScript;
    }

    @Override
    public void onApplicationEvent(ApplicationStartupEvent event) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            BufferedReader br = new BufferedReader(createTableScript.asReader());
            StringBuffer buffy = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                buffy.append(str);
            }
            jdbcTemplate.execute(buffy.toString());

            // create one activity and records
            String recordsFile = "test-activityrecords-short.csv";
            createActivityAndRecords("Windy Wednesday", "test-activityrecords-short.csv");
            createActivityAndRecords("Snowy Sunday", "test-activityrecords-shorter.csv");
            createActivityAndRecords("Moony Monday", "test-activityrecords-long.csv");
        } catch (IOException e) {
            throw new ApplicationStartupException("Couldn't start app", e);
        }
    }

    private Activity createActivityAndRecords(String activityName, String recordsFileName) throws IOException {
        List<ActivityRecord> records = getActivityRecordsFromFile(recordsFileName);
        Activity activity = new Activity(activityName, ZonedDateTime.now());
        activity = activityDataRepository.saveActivity(activity);
        activityDataRepository.saveActivityRecords(activity, records);
        return activity;
    }

    public List<ActivityRecord> getActivityRecordsFromFile(String fileName) throws IOException {
        List<ActivityRecord> activityRecords;
        InputStream testFile =
                this.getClass().getResourceAsStream(fileName);
        if (testFile == null) {
            throw new FileNotFoundException("Couldn't find file " + fileName);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(testFile));

        // skip the header of the csv
        try {
            activityRecords = br.lines()
                    .skip(1)
                    .map(mapToActivityRecord)
                    .collect(Collectors.toList());
        } finally {
            br.close();
        }
        return activityRecords;
    }

    private Function<String,ActivityRecord> mapToActivityRecord = (line) -> {

        String[] p = line.split(",");// a CSV has comma separated lines

        return new ActivityRecord.Builder()
                .atTime(Short.parseShort(p[0].trim()), Short.parseShort(p[1].trim()))
                .moving(Boolean.parseBoolean(p[2].trim()))
                .withDistance(Float.parseFloat(p[3].trim()))
                .withWatts(Short.parseShort(p[4].trim()))
                .withHeartRate(Short.parseShort(p[5].trim()))
                .withCadence(Short.parseShort(p[6].trim()))
                .withVelocity(Float.parseFloat(p[7].trim()))
                .atPosition(Double.parseDouble(p[8].trim()), Double.parseDouble(p[9].trim()))
                .withTemperature(Short.parseShort(p[10].trim()))
                .atAltitude(Float.parseFloat(p[11].trim()))
                .withGrade(Float.parseFloat(p[12].trim())).build();
    };
}
