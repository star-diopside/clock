package jp.gr.java_conf.stardiopside.clock;

import java.util.ResourceBundle;

import org.controlsfx.dialog.ExceptionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Jsr330ScopeMetadataResolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
@ComponentScan(scopeResolver = Jsr330ScopeMetadataResolver.class, excludeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public class App extends Application {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        applicationContext = SpringApplication.run(App.class, getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var messages = ResourceBundle.getBundle("messages");

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
            logger.error(e.getMessage(), e);
            var dialog = new ExceptionDialog(e);
            dialog.setHeaderText(messages.getString("message.uncaughtException"));
            dialog.show();
        });

        var loader = new FXMLLoader(applicationContext.getResource("classpath:/fxml/clock.fxml").getURL(), messages);
        loader.setControllerFactory(applicationContext::getBean);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
    }
}
