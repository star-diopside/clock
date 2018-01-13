package jp.gr.java_conf.stardiopside.clock.model;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.inject.Named;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@Named
public class Clock {

    private ObjectProperty<LocalDateTime> start = new SimpleObjectProperty<>(LocalDateTime.now());
    private ObjectProperty<LocalDateTime> now = new SimpleObjectProperty<>(LocalDateTime.now());
    private ObjectProperty<Duration> stopwatch = new SimpleObjectProperty<>();

    public Clock() {
        stopwatch.bind(new ObjectBinding<Duration>() {
            {
                bind(start, now);
            }

            @Override
            protected Duration computeValue() {
                return Duration.between(start.get(), now.get());
            }
        });
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
