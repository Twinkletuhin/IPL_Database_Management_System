package menu;

import controller.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MenuApplication extends Application {
    private Stage stage;
    private static DatabaseSystem databaseSystem;

    public MenuApplication() {
        try {
            this.databaseSystem = new DatabaseSystem("players.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DatabaseSystem getDatabaseSystem() {
        return databaseSystem;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        showMainMenu();
    }

    public void showMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/main-menu.fxml"));
        Parent root = fxmlLoader.load();

        MainMenu controller = fxmlLoader.getController();
        controller.setMenuApplication(this);
        stage.setTitle("Home Menu");
        stage.setScene(new Scene(root));

        stage.show();
    }

    public void showSearchPlayers() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/search-players.fxml"));
        Parent root = fxmlLoader.load();

        SearchPlayers controller = fxmlLoader.getController();
        controller.setMenuApplication(this);

        stage.setTitle("Search Players");
        stage.setScene(new Scene(root));

        stage.show();
    }

    private Scene searchClubScene;

    public void showSearchClubs() throws IOException {
        if (searchClubScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/search-clubs.fxml"));
            Parent root = fxmlLoader.load();

            SearchClubs controller = fxmlLoader.getController();
            controller.setMenuApplication(this);
            searchClubScene = new Scene(root);
        }
        stage.setTitle("Search Clubs");
        stage.setScene(searchClubScene);

        stage.show();
    }

    public void showAddPlayer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/add-player.fxml"));
        Parent root = fxmlLoader.load();

        AddPlayerController controller = fxmlLoader.getController();
        controller.setMenuApplication(this);
        controller.initialize();
        stage.setTitle("Add Player");
        stage.setScene(new Scene(root));

        stage.show();
    }

    public void takeName() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/take-name.fxml"));
        Parent root = fxmlLoader.load();

        TakeName controller = fxmlLoader.getController();
        controller.setMenuApplication(this);

        stage.setTitle("Take Name");
        stage.setScene(new Scene(root));

        stage.show();

    }

    public void takeClubAndCountry() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/take-club-and-country.fxml"));
        Parent root = fxmlLoader.load();

        TakeClubAndCountry controller = fxmlLoader.getController();
        controller.setMenuApplication(this);

        stage.setTitle("Take Club And Country");
        stage.setScene(new Scene(root));

        stage.show();

    }

    public void takePosition() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/take-position.fxml"));
        Parent root = fxmlLoader.load();

        TakePosition controller = fxmlLoader.getController();
        controller.setMenuApplication(this);

        stage.setTitle("Take Position");
        stage.setScene(new Scene(root));

        stage.show();

    }

    public void takeSalaryRange() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/take-salary-range.fxml"));
        Parent root = fxmlLoader.load();

        TakeSalaryRange controller = fxmlLoader.getController();
        controller.setMenuApplication(this);

        stage.setTitle("Take Salary Range");
        stage.setScene(new Scene(root));

        stage.show();

    }

    public void takeClubName(String identifire) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/take-club-name.fxml"));
        Parent root = fxmlLoader.load();

        TakeClubName controller = fxmlLoader.getController();
        controller.setMenuApplication(this);
        controller.init(identifire);
        stage.setTitle("Take Club Name");
        stage.setScene(new Scene(root));

        stage.show();

    }

    public void showCountryWisePlayers(HashMap<String, Integer> map) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/hash-table.fxml"));
        Parent root = fxmlLoader.load();

        HashTableController controller = fxmlLoader.getController();
        controller.setMenuApplication(this);
        controller.initialize(map);
        stage.setTitle("Country Wise Players");
        stage.setScene(new Scene(root));

        stage.show();
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showPlayer(Player p, String promot) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/single-player.fxml"));
        Parent root = fxmlLoader.load();

        PlayerView controller = fxmlLoader.getController();
        controller.setMenuApplication(this);
        controller.setPlayer(p, promot);
        stage.setTitle("Player Details");
        stage.setScene(new Scene(root));

        stage.show();
    }

    public void showTable(List<Player> p) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/player-table.fxml"));
        Parent root = fxmlLoader.load();

        TableViewController controller = fxmlLoader.getController();
        // for(Player player:p){
        // System.out.println(player);
        // }
        controller.setMenuApplication(this);
        controller.initialize(p);
        controller.populateTableView();
        stage.setTitle("Players Table");
        stage.setScene(new Scene(root));

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
