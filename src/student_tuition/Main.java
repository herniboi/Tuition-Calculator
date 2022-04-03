package student_tuition;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main used for launching the GUI
 * @author Henry Lin, Andy Li
 */
public class Main extends Application {
    /**
     * Starts the JavaFX Tuition Manager GUI
     * @param primaryStage
     */
    //@Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource( "MainView.fxml"));
            primaryStage.setTitle("Tuition Manager");
            primaryStage.setScene(new Scene(root, 550, 450));
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls launch method for the GUI
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
