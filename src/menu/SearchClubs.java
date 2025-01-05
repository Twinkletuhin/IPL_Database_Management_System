package menu;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

public class SearchClubs {
    private MenuApplication menuApplication;

    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }

    /*
     * 1) Player(s) with the maximum salary of a club
     * (2) Player(s) with the maximum age of a club
     * (3) Player(s) with the maximum height of a club
     * (4) Total yearly salary of a club
     * (5) Back to Main Menu
     */
    @FXML
    public void maxSalary(ActionEvent actionEvent) {
        // System.out.println("maxSalary");
        try {
            menuApplication.takeClubName("maxSalary");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void maxAge(ActionEvent actionEvent) {
        // System.out.println("maxAge");
        try {
            menuApplication.takeClubName("maxAge");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void maxHeight(ActionEvent actionEvent) {
        // System.out.println("maxHeight");
        try {
            menuApplication.takeClubName("maxHeight");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void totalYearlySalary(ActionEvent actionEvent) {
        // System.out.println("totalYearlySalary");
        try {
            menuApplication.takeClubName("totalYearlySalary");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backToMain(ActionEvent actionEvent) {
        // System.out.println("backToMain");
        try {
            menuApplication.showMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
