DROP TABLE IF EXISTS activity;
DROP TABLE IF EXISTS activity_record;

CREATE TABLE `activity`
(
    `id`   IDENTITY NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `date` TIMESTAMP    NOT NULL,
    CONSTRAINT pk_activity PRIMARY KEY(id)
);

CREATE TABLE `activity_record`
(
    `activity_id`  BIGINT  NOT NULL,
    `time`       INT     NOT NULL,
    `timer_time` INT     NOT NULL,
    `moving`     BOOLEAN NOT NULL,
    `distance`   FLOAT   NOT NULL,
    `watts`      SMALLINT NOT NULL,
    `heart_rate` SMALLINT NOT NULL,
    `cadence`    SMALLINT NOT NULL,
    `velocity`   FLOAT   NOT NULL,
    `lat`        DOUBLE  NOT NULL,
    `lon`        DOUBLE  NOT NULL,
    `temp`       TINYINT NOT NULL,
    `altitude`   FLOAT   NOT NULL,
    `grade`      FLOAT   NOT NULL,
    CONSTRAINT pk_activity_record PRIMARY KEY(activity_id, timer_time),
    CONSTRAINT fk_activity_record_activity FOREIGN KEY (activity_id) REFERENCES activity(id)
);
