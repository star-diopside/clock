package jp.gr.java_conf.stardiopside.clock.model;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.inject.Named;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@Named
public class Clock {

    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> now;
    private ObjectProperty<Duration> stopwatch = new SimpleObjectProperty<>();

    public Clock() {
        var current = LocalDateTime.now();
        start = new SimpleObjectProperty<>(current);
        now = new SimpleObjectProperty<>(current);
        stopwatch.bind(Bindings.createObjectBinding(() -> Duration.between(start.get(), now.get()), start, now));
    }

    public ObjectProperty<LocalDateTime> nowProperty() {
        return now;
    }

    public LocalDateTime getNow() {
        return now.get();
    }

    public void setNow(LocalDateTime now) {
        this.now.set(now);
    }

    public ObjectProperty<Duration> stopwatchProperty() {
        return stopwatch;
    }

    public Duration getStopwatch() {
        return stopwatch.get();
    }

    public void setStopwatch(Duration stopwatch) {
        this.stopwatch.set(stopwatch);
    }

    public void update() {
        now.set(LocalDateTime.now());
    }
}
