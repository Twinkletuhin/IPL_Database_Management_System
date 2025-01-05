package controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

import menu.*;
public class TakeClubAndCountry {
   private MenuApplication menuApplication;
    private DatabaseSystem data;
    private String club;
    private String country;
    @FXML
    private TextField clubText;
    @FXML
    private TextField countryText;
    @FXML
    public void submit(){
        this.club = clubText.getText();
        this.country = countryText.getText();
        List<Player> players = menuApplication.getDatabaseSystem().searchByClubAndCountry(club, country);
        if(players.size()==0){
            menuApplication.showAlert("No such player with the club and country");
        }else{
            try {
                menuApplication.showTable(players);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } 
    @FXML
    public void backToSearch(){
        try {
            menuApplication.showSearchPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }
}
