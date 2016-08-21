package jp.gr.java_conf.star_diopside.clock.application;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jp.gr.java_conf.star_diopside.clock.config.AppConfig;
import jp.gr.java_conf.star_diopside.spark.commons.support.util.CharsetResourceBundleControl;

public class App extends Application {

    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(applicationContext.getResource("classpath:/fxml/clock.fxml").getURL(),
                ResourceBundle.getBundle("i18n", new CharsetResourceBundleControl(StandardCharsets.UTF_8)));
        loader.setControllerFactory(applicationContext::getBean);
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
    }
}
