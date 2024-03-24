package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TheMainAppController {
    @FXML
    private Label labelLogin;

    @FXML

    public void SetLabelLogin(String Login) {

        labelLogin.setText(Login);
    }

}
