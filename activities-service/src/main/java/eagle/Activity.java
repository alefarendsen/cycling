package eagle;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * A cycling activity, with its associated {@link ActivityRecord}s.
 */
@MappedEntity
public class Activity {

    @Id @AutoPopulated
    private Long id;
    @Size(min=1, max=20)
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private ZonedDateTime date;

    public Activity(@NotNull String name, @NotNull ZonedDateTime date) {
        this(null, name, date);
    }

    public Activity(Long id, @NotNull String name, @NotNull ZonedDateTime date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public Activity() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getDate() {
        return date;
    }
}
