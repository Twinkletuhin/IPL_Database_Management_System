package controller;

import clients.Main;
import dto.LoginDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class LoginController {
    private Main main;
    @FXML
    private TextField clubNamTextField;
    @FXML
    private TextField passworField;
    @FXML
    private Button loginButton;
    @FXML
    private Button resetButton;

    @FXML
    public void initialize() {
        // Add key listener to the password field
        passworField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                requestLogin();
            }
        });
    }

    @FXML
    public void requestLogin() {
        String clubName = clubNamTextField.getText().strip();
        String password = passworField.getText();
        if (clubName.isEmpty() || password.isEmpty()) {
            main.showAlert();
            return;
        }
        LoginDTO loginDTO = new LoginDTO(clubName, password);
        try {
            main.getSocketWrapper().write(loginDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void reset() {
        clubNamTextField.setText("");
        passworField.setText("");
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
