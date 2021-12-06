package eagle;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@JdbcRepository(dialect = Dialect.H2)
public abstract class ActivityDataRepository {

    private static final String SQL_INSERT_ACTIVITY =
            "insert into activity (`name`, `date`) values (?,?)";
    private static final String SQL_INSERT_ACTIVITY_RECORD =
            "insert into activity_record (activity_id, time, timer_time, " +
                    "moving, distance, watts, heart_rate, cadence, velocity, lat, " +
                    "lon, temp, altitude, grade) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    @Inject public ActivityDataRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Find an {@link Activity} by its id
     */
    @Query("select id, name, date from activity  where id=:id")
    public abstract Activity findActivity(long id);

    /**
     * Insert an activity
     * @return a new activity with its newly created ID filled in
     */
    public Activity saveActivity(@Valid Activity activity) {
        KeyHolder generatedKey = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ACTIVITY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, activity.getName());
            ps.setTimestamp(2, Timestamp.valueOf(activity.getDate().toLocalDateTime()));
            return ps;
        }, generatedKey);

        long id = (long)generatedKey.getKey();
        return new Activity(id, activity.getName(), activity.getDate());
    }

    public void saveActivityRecords(final Activity activity, final List<ActivityRecord> activityRecords) {

        jdbcTemplate.batchUpdate(SQL_INSERT_ACTIVITY_RECORD,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ActivityRecord r = activityRecords.get(i);
                        ps.setLong(1, activity.getId());
                        ps.setInt(2, r.getTime());
                        ps.setInt(3, r.getTimerTime());
                        ps.setBoolean(4, r.isMoving());
                        ps.setFloat(5, r.getDistance());
                        ps.setShort(6, r.getWatts());
                        ps.setShort(7, r.getHeartRate());
                        ps.setShort(8, r.getCadence());
                        ps.setFloat(9, r.getVelocity());
                        ps.setDouble(10, r.getLat());
                        ps.setDouble(11, r.getLon());
                        ps.setShort(12, r.getTemp());
                        ps.setFloat(13, r.getAltitude());
                        ps.setFloat(14, r.getAltitude());
                    }

                    @Override
                    public int getBatchSize() {
                        return activityRecords.size();
                    }
                });
    }

    public long count() {
        return jdbcTemplate.queryForObject("select count(id) from activity", Long.class);
    }

    @Query("select * from activity_record where activity_id = :activityId")
    public abstract List<ActivityRecord> findActivityRecords(Long activityId);

    @Query("select * from activity")
    public abstract List<Activity> findAllActivities();
}
