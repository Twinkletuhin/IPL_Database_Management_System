package controller;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import menu.*;
public class TakeSalaryRange {
 private MenuApplication menuApplication;
    private Integer min;
    private Integer max;
    @FXML
    private TextField minText;
    @FXML
    private TextField maxText;
    @FXML
    public void submit(){
        this.min = Integer.parseInt(minText.getText());
        this.max = Integer.parseInt(maxText.getText());
        List<Player> players = menuApplication.getDatabaseSystem().searchBySalaryRange(min, max);
        if(players.size()==0){
            menuApplication.showAlert("No such player with the salary range");
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
