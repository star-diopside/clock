package jp.gr.java_conf.stardiopside.clock.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import jp.gr.java_conf.stardiopside.clock.model.Clock;

@Named
public class ClockController implements Initializable {

    @Inject
    private Clock clock;

    @FXML
    private Label now;

    @FXML
    private Label stopwatch;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var nowProperty = clock.nowProperty();
        var stopwatchProperty = clock.stopwatchProperty();
        now.textProperty()
                .bind(Bindings.createStringBinding(() -> nowProperty.get().format(DATE_TIME_FORMATTER), nowProperty));
        stopwatch.textProperty().bind(
                Bindings.createStringBinding(() -> convertDurationString(stopwatchProperty.get()), stopwatchProperty));

        var timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            clock.update();
            now.getScene().getWindow().sizeToScene();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static String convertDurationString(java.time.Duration duration) {
        return String.format("%02d:%02d:%02d.%03d", duration.toHours(), duration.toMinutesPart(),
                duration.toSecondsPart(), duration.toNanosPart() / 1_000_000);
    }
}
