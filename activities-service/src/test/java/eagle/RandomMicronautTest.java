package eagle;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class RandomMicronautTest {

    @Inject ActivityDataRepository dataRepository;

    @Inject private DataFiller dataFiller;

    @Test public void testInsertActivityAndRecords() throws IOException {

        long oldCount = dataRepository.count();

        Activity activity = new Activity("Super Tuesday", ZonedDateTime.now());

        activity = dataRepository.saveActivity(activity);

        assertEquals(oldCount+1, dataRepository.count());

        Activity newActivity = dataRepository.findActivity(activity.getId());

        assertEquals(activity.getName(), newActivity.getName());
        assertEquals(activity.getDate(), newActivity.getDate());
    }

    @Test public void testInsertActivityRecords() throws IOException {

        // read the file and map to activity records
        String fileName = "test-activityrecords-short.csv";
        List<ActivityRecord> activityRecords = dataFiller.getActivityRecordsFromFile(fileName);

        Activity activity = new Activity("Activity Test", ZonedDateTime.now());
        activity = dataRepository.saveActivity(activity);
        dataRepository.saveActivityRecords(activity, activityRecords);

        List<ActivityRecord> savedRecords = dataRepository.findActivityRecords(activity.getId());

        assertEquals(activityRecords.size(), savedRecords.size());
    }

    @Test public void testFindRecordsById() throws IOException {

        Activity activityOne = new Activity("One", ZonedDateTime.now());

        String fileName = "test-activityrecords-short.csv";
        List<ActivityRecord> recordsForOne = dataFiller.getActivityRecordsFromFile(fileName);

        Activity activityTwo = new Activity("Two", ZonedDateTime.now());

        fileName = "test-activityrecords-shorter.csv";
        List<ActivityRecord> recordsForTwo = dataFiller.getActivityRecordsFromFile(fileName);

        activityOne = dataRepository.saveActivity(activityOne);
        activityTwo = dataRepository.saveActivity(activityTwo);

        dataRepository.saveActivityRecords(activityOne, recordsForOne);
        dataRepository.saveActivityRecords(activityTwo, recordsForTwo);

        assertEquals(recordsForOne.size(), dataRepository.findActivityRecords(activityOne.getId()).size());
        assertEquals(recordsForTwo.size(), dataRepository.findActivityRecords(activityTwo.getId()).size());
    }



}
