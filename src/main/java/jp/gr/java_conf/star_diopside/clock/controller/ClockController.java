package jp.gr.java_conf.star_diopside.clock.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.util.converter.LocalDateTimeStringConverter;
import jp.gr.java_conf.star_diopside.clock.converter.ObjectStringConverter;
import jp.gr.java_conf.star_diopside.clock.model.Clock;

@Named
public class ClockController implements Initializable {

    @Inject
    private Clock clock;

    @FXML
    private Label now;

    @FXML
    private Label stopwatch;

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        now.textProperty().bindBidirectional(clock.nowProperty(),
                new LocalDateTimeStringConverter(DATE_TIME_FORMATTER, null));
        stopwatch.textProperty().bindBidirectional(clock.stopwatchProperty(),
                new ObjectStringConverter<>(
                        duration -> String.format("%02d:%02d:%02d.%03d", duration.toHours(), duration.toMinutes() % 60,
                                duration.getSeconds() % 60, duration.get(ChronoUnit.NANOS) / 1_000_000),
                        null));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            clock.update();
            now.getScene().getWindow().sizeToScene();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
