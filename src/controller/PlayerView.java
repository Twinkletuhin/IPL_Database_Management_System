package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import menu.*;
public class PlayerView {
    private MenuApplication menuApplication;
    @FXML
    private Label nameLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Label clubLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label weeklySalaryLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;
   @FXML
   private Label promot;
    private Player player; // A custom class to hold player details

    
    public void setPlayer(Player player,String promot) {
        this.player = player;

        // Populate labels with player data
        nameLabel.setText(player.getName());
        countryLabel.setText(player.getCountry());
        ageLabel.setText(String.valueOf(player.getAge()));
        heightLabel.setText(String.format("%.1f", player.getHeight()));
        clubLabel.setText(player.getClub());
        positionLabel.setText(player.getPosition());
        numberLabel.setText(player.getNumber() != null ? player.getNumber().toString() : "N/A");
        weeklySalaryLabel.setText(String.format("%d", player.getWeeklySalary()));
       
        this.promot.setText(promot);
    }

   
    @FXML
    public void backToSearch() {
        try{
            menuApplication.showSearchClubs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }

   
    
}
