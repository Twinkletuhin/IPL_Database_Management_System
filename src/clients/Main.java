package clients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.ClientHomeController;
import controller.LoginController;
import controller.MarketPlaceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.Region;
import menu.Club;
import menu.Player;
import util.SocketWrapper;

public class Main extends Application {
    private Stage stage;
    private SocketWrapper socketWrapper;
    private Club ownClub;
    private static Main currentMain;

    public Stage getStage() {
        return stage;
    }

    public static void setCurrentMain(Main main) {
        currentMain = main;
    }

    public void setOwnClub(Club club) {
        this.ownClub = club;
    }

    public Club getOwnClub() {
        return ownClub;
    }

    public SocketWrapper getSocketWrapper() {
        return socketWrapper;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Main.setCurrentMain(this);
        connectToServer();
        showLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        socketWrapper = new SocketWrapper(serverAddress, serverPort);
        new ClientThreadServer(this);
    }

    public void showLoginPage() throws IOException {
        System.out.println("login called");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/login-page.fxml"));
        Parent root = fxmlLoader.load();
        LoginController controller = fxmlLoader.getController();
        controller.setMain(this);
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void showClientHome(String clubName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/client-home.fxml"));
        Parent root = fxmlLoader.load();
        ClientHomeController controller = fxmlLoader.getController();
        controller.setMain(this);
        ClientHomeController.setOwnClub(ownClub);

        controller.initialize(clubName);
        stage.setTitle("Client Home");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setMainClub(Club club) {
        ownClub = club;
    }

    public void showMarketPlace(String clubName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/market-place.fxml"));
        Parent root = fxmlLoader.load();
        MarketPlaceController controller = fxmlLoader.getController();
        MarketPlaceController.setOwnClub(ownClub);
        controller.setMain(this);
        controller.initialize(clubName);
        stage.setTitle("Market Place");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText(null); // No default header
        alert.setContentText("The ClubName and password you provided is not correct.");

        alert.getDialogPane().setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #400485; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-radius: 10;");

        Text content = new Text(alert.getContentText());
        content.setStyle(
                "-fx-font-family: 'Poppins'; " +
                        "-fx-font-size: 14px; " +
                        "-fx-fill: grey;");
        content.setWrappingWidth(300);
        alert.getDialogPane().setContent(content);

        alert.getDialogPane().lookupButton(ButtonType.OK).setStyle(
                "-fx-background-color: #400485; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-family: 'Poppins'; " +
                        "-fx-background-radius: 20;");

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.showAndWait();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
