package controller;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import menu.*;
public class TakePosition {
    private MenuApplication menuApplication;
    private DatabaseSystem data;
    private String position;
    @FXML
    private TextField positionText;
    @FXML
    public void submit(){
        this.position = positionText.getText();
        List<Player> players = menuApplication.getDatabaseSystem().searchByPosition(position);
        if(players.size()==0){
            menuApplication.showAlert("No such player with the position");
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
