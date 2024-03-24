import Views.AuthenticationViews;
import Views.TheMainAppViews;
import controller.AuthenticationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;

public class App extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Color color = Color.web("#222f3e");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/LoginScene.fxml"));
        Parent root = loader.load();
        AuthenticationViews authController = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/LoginScene.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Gestion de Frais de scolarite");
        primaryStage.getIcons().add(new Image("file:./icon/icon.png"));
        primaryStage.show();
        authController.SetMainApp(primaryStage);

    }

    public void ClosePrimaryStage() {
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
