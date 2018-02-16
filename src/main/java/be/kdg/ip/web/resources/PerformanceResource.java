package be.kdg.ip.web.resources;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class PerformanceResource {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startdatetime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime enddatetime;

    private String beschrijving;

    public LocalDateTime getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(LocalDateTime startdatetime) {
        this.startdatetime = startdatetime;
    }

    public LocalDateTime getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(LocalDateTime enddatetime) {
        this.enddatetime = enddatetime;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
}
