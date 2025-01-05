package menu;

import javax.swing.Action;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenu {
    private MenuApplication menuApplication;

    public MainMenu() {

    }

    @FXML
    public void searchPlayers(ActionEvent actionEvent) {
        // System.out.println("searchPlayers");
        try {
            menuApplication.showSearchPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchClubs(ActionEvent actionEvent) {
        // System.out.println("searchClubs");
        try {
            menuApplication.showSearchClubs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addPlayer(ActionEvent event) {
        // System.out.println("addPlayer");
        try {
            menuApplication.showAddPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exitSystem(ActionEvent actionEvent) {
        try {

            menuApplication.getDatabaseSystem().exitSystem();
            Platform.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }

}