package Views;

import java.io.IOException;

import Model.User;
import controller.AuthenticationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AuthenticationViews {
    private Stage mainApp;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorPassword, errorLogin;

    @FXML
    private TextField tfLogin, tfPassword;

    @FXML

    public void login(ActionEvent e) throws IOException {
        String pwd;
        String username;
        AuthenticationController AuthController = new AuthenticationController();

        username = tfLogin.getText();
        pwd = tfPassword.getText();

        System.out.println(username);

        if (username.isEmpty()) {
            errorLogin.setVisible(true);
            errorLogin.setText("Veuillez remplir ce champ");
            tfLogin.setStyle("-fx-border-color : red");
            if (!pwd.isEmpty()) {
                tfPassword.setStyle("-fx-border-color : gray");
                errorPassword.setVisible(false);
            }

        }
        if (pwd.isEmpty()) {
            errorPassword.setVisible(true);
            tfPassword.setStyle("-fx-border-color : red");
            errorPassword.setText("Veuillez remplir ce champ");
            if (!username.isEmpty()) {
                tfLogin.setStyle("-fx-border-color : gray");
                errorLogin.setVisible(false);
            }
        }
        if (!pwd.isEmpty() && !pwd.isEmpty()) {

            User User = AuthController.GetAdminUsername(username);
            if (User != null) {
                String passwordFromDb = User.Getpassword();
                System.out.println(pwd);
                System.out.println(passwordFromDb);
                if (passwordFromDb.matches(pwd)) {

                    Stage secondStage = new Stage();

                    Color color = Color.web("#222f3e");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/TheMainApp.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    secondStage.setScene(scene);
                    TheMainAppViews appView = loader.getController();
                    appView.SetLabelLogin(username);
                    secondStage.setResizable(false);
                    secondStage.setTitle(username + " [Administrateur]");
                    secondStage.getIcons().add(new Image("file:./icon/icon.png"));
                    secondStage.show();

                    mainApp.close();
                } else {
                    tfLogin.setStyle("-fx-border-color : red");
                    tfPassword.setStyle("-fx-border-color : red");
                    errorPassword.setVisible(true);
                    errorPassword.setText("Mot de passe ou nom d'utilisateur inexactes");
                    errorLogin.setVisible(false);

                }
            } else {
                errorLogin.setVisible(false);
                errorPassword.setVisible(true);
                tfLogin.setStyle("-fx-border-color : red");
                errorPassword.setText("Cet Utilisateur n'existe pas");
                tfPassword.setText("");

            }
        }
    }

    public void SetMainApp(Stage mainApp) {
        this.mainApp = mainApp;

    }
}
