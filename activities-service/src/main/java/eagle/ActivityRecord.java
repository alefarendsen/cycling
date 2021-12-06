package eagle;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;

@Introspected
@MappedEntity
public class ActivityRecord {

    private int time;
    private int timerTime;

    private boolean moving;
    private float distance;

    private short watts;
    private short heartRate;
    private short cadence;

    private float velocity;

    private double lat;
    private double lon;

    private short temp;
    private float altitude;

    private float grade;

    /**
     * It's discouraged to use this constructor, it's there for data-access purposes only.
     * Please go through the builder
     */
    public ActivityRecord(
            int time,
            int timerTime,
            boolean moving,
            float distance,
            short watts,
            short heartRate,
            short cadence,
            float velocity,
            double lat,
            double lon,
            short temp,
            float altitude,
            float grade) {
        this.time = time;
        this.timerTime = timerTime;
        this.moving = moving;
        this.distance = distance;
        this.watts = watts;
        this.heartRate = heartRate;
        this.cadence = cadence;
        this.velocity = velocity;
        this.lat = lat;
        this.lon = lon;
        this.temp = temp;
        this.altitude = altitude;
        this.grade = grade;
    }

    public int getTime() {
        return time;
    }

    public int getTimerTime() {
        return timerTime;
    }

    public boolean isMoving() {
        return moving;
    }

    public float getDistance() {
        return distance;
    }

    public short getWatts() {
        return watts;
    }

    public short getHeartRate() {
        return heartRate;
    }

    public short getCadence() {
        return cadence;
    }

    public float getVelocity() {
        return velocity;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public short getTemp() {
        return temp;
    }

    public float getAltitude() {
        return altitude;
    }

    public float getGrade() {
        return grade;
    }

    public static class Builder {

        private int time;
        private int timerTime;

        private boolean moving;
        private float distance;

        private short watts;
        private short heartRate;
        private short cadence;

        private float velocity;

        private double lat;
        private double lon;

        private short temp;
        private float altitude;

        private float grade;

        public Builder atPosition(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
            return this;
        }

        public Builder withTemperature(short temp) {
            this.temp = temp;
            return this;
        }

        public Builder atTime(int time, int timerTime) {
            this.time = time;
            this.timerTime = timerTime;
            return this;
        }

        public Builder moving(boolean moving) {
            this.moving = moving;
            return this;
        }

        public Builder withDistance(float distance) {
            this.distance = distance;
            return this;
        }

        public Builder withWatts(short watts) {
            this.watts = watts;
            return this;
        }

        public Builder withHeartRate(short heartRate) {
            this.heartRate = heartRate;
            return this;
        }

        public Builder withCadence(short cadence) {
            this.cadence = cadence;
            return this;
        }

        public Builder withVelocity(float velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder atAltitude(float altitude) {
            this.altitude = altitude;
            return this;
        }

        public Builder withGrade(float grade) {
            this.grade = grade;
            return this;
        }

        public ActivityRecord build() {
            return new ActivityRecord(time, timerTime, moving, distance, watts, heartRate, cadence, velocity, lat, lon, temp, altitude, grade);
        }
    }
}
