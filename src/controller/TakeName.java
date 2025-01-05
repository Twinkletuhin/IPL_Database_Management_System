package controller;

import menu.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class TakeName {
    private MenuApplication menuApplication;
    private DatabaseSystem data;
    private String name;
    @FXML
    private TextField nameText;

    @FXML
    public void submit(ActionEvent actionEvent) {
        this.name = nameText.getText();
        Player p = menuApplication.getDatabaseSystem().searchByName(name);
        if (p == null) {
            menuApplication.showAlert("No such payer with the name ");
        } else {
            ArrayList<Player> players = new ArrayList<>();
            players.add(p);
            // for(Player player:players){
            //     System.out.println(player);
            // }
            try {
                menuApplication.showTable(players);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // System.out.println(p);
        // System.out.println(name);
    }

    @FXML
    public void backToSearch(ActionEvent actionEvent) {
        try {
            menuApplication.showSearchPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }
}
