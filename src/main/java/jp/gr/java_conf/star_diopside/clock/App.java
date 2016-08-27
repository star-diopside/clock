package jp.gr.java_conf.star_diopside.clock;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Jsr330ScopeMetadataResolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jp.gr.java_conf.star_diopside.spark.commons.support.util.CharsetResourceBundleControl;

@SpringBootApplication
@ComponentScan(scopeResolver = Jsr330ScopeMetadataResolver.class)
public class App extends Application {

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
