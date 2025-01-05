package controller;

import menu.*;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class TakeClubName {
    private MenuApplication menuApplication;
    private String clubName;
    private String methodIdentifire;
    @FXML
    private TextField clubNameText;

    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }

    public void init(String methodIdentifire) {
        this.methodIdentifire = methodIdentifire;
    }
    @FXML
    public void backToSearch() {
        try {
            menuApplication.showSearchClubs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void submit(ActionEvent actionEvent) {
        this.clubName = clubNameText.getText();
        Player p;
        if (methodIdentifire.equals("maxSalary")) {
            p = menuApplication.getDatabaseSystem().maxSalaryPlayer(clubName);
            if (p == null) {
                menuApplication.showAlert("No such player with the club");
            } else {
                try {
                    menuApplication.showPlayer(p, "Player with the maximum salary of :" + clubName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (methodIdentifire.equals("maxAge")) {
            p = menuApplication.getDatabaseSystem().maxAgePlayer(clubName);
            if (p == null) {
                menuApplication.showAlert("No such player with the club");
            } else {
                try {
                    menuApplication.showPlayer(p, "Player with the maximum age of :" + clubName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (methodIdentifire.equals("maxHeight")) {
            p = menuApplication.getDatabaseSystem().maxHeightPlayer(clubName);
            if (p == null) {
                menuApplication.showAlert("No such player with the club");
            } else {
                try {
                    menuApplication.showPlayer(p, "Player with the maximum height of :" + clubName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (methodIdentifire.equals("totalYearlySalary")) {
            Long total = menuApplication.getDatabaseSystem().totalYearlySalary(clubName);
            if (total==null) {
                menuApplication.showAlert("No such player with the club");
            } else {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Total Yearly Salary");
                    alert.setHeaderText(null);
                    alert.setContentText("Total yearly salary of the " + clubName + " club is: " + total);

                    // Show the alert and wait for the user's response
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Call showSearchPlayersByClub if OK button is pressed
                        menuApplication.showSearchClubs();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
