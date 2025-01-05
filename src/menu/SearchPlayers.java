package menu;

import javafx.fxml.FXML;

import java.util.HashMap;

import javafx.event.ActionEvent;

public class SearchPlayers {
    private MenuApplication menuApplication;

    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }

    @FXML
    public void searchByName(ActionEvent actionEvent) {
        // System.out.println("searchByName");
        try {
            menuApplication.takeName();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void searchByCulbAndCountry(ActionEvent actionEvent) {
       // System.out.println("searchByCulbAndCountry");
       try {
        menuApplication.takeClubAndCountry();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    @FXML
    public void searchByPosition(ActionEvent actionEvent) {
       // System.out.println("searchByPosition");
       try {
        menuApplication.takePosition();
       } catch (Exception e) {
        e.printStackTrace();
       }
    }

    @FXML
    public void bySalaryRange(ActionEvent actionEvent) {
        //System.out.println("bySalaryRange");
        try {
            menuApplication.takeSalaryRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void countryWisePlayersCount(ActionEvent actionEvent) {
        // System.out.println("countryWisePlayersCount"); countryWisePlayerCount
        HashMap<String, Integer> countryWisePlayersCount = menuApplication.getDatabaseSystem().countryWisePlayerCount();
        if (countryWisePlayersCount.size() == 0) {
            menuApplication.showAlert("No such player with the country");
        } else {
            try {
                menuApplication.showCountryWisePlayers(countryWisePlayersCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
