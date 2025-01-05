package menu;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableViewController {
    private Stage stage;
    @FXML
    private TableView<Player> playerTable;
    @FXML
    private TableColumn<Player, ImageView> imageColumn;
    @FXML
    private TableColumn<Player, ImageView> countryFlagColumn;

    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> countryNameColumn;

    @FXML
    private TableColumn<Player, String> countryColumn;

    @FXML
    private TableColumn<Player, Integer> ageColumn;

    @FXML
    private TableColumn<Player, Double> heightColumn;

    @FXML
    private TableColumn<Player, String> clubColumn;

    @FXML
    private TableColumn<Player, String> positionColumn;

    @FXML
    private TableColumn<Player, Integer> numberColumn;

    @FXML
    private TableColumn<Player, Integer> weeklySalaryColumn;

    private final ObservableList<Player> playerObservableList = FXCollections.observableArrayList();

    public TableViewController() {
    }

    private List<Player> players;
    private MenuApplication menuApplication;

    @FXML
    public void initialize(List<Player> players) {
        // Bind columns to Player properties
        imageColumn.setCellValueFactory(param -> {
            Player player = param.getValue();
            String imagePath = player.getImagePath();
            ImageView imageView;
            try {
                imageView = new ImageView(new Image(imagePath));
                imageView.setFitWidth(60);
                imageView.setFitHeight(60);
            } catch (Exception e) {
                System.out.println("Error loading player image: " + imagePath);
                imageView = new ImageView(); // Fallback to empty view
            }
            return new SimpleObjectProperty<>(imageView);
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryFlagColumn.setCellValueFactory(param -> {
            Player player = param.getValue();
            String flagPath = player.getCountryFlagPath();
            ImageView flagView;
            try {
                flagView = new ImageView(new Image(flagPath));
                flagView.setFitWidth(55);
                flagView.setFitHeight(45);
            } catch (Exception e) {
                System.out.println("Error loading flag image: " + flagPath);

                flagView = new ImageView(); // Fallback to empty view
            }

            return new SimpleObjectProperty<>(flagView);
        });
        countryNameColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        weeklySalaryColumn.setCellValueFactory(new PropertyValueFactory<>("weeklySalary"));

        // Populate the TableView with data
        this.players = players;

    }

    public void populateTableView() {

        playerObservableList.clear();
        playerObservableList.addAll(players);
        // System.out.println("size: " + playerObservableList.size());
        // for (Player p : playerObservableList) {
        // System.out.println(p);
        // }
        playerTable.setItems(playerObservableList);
    }

    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }

    @FXML
    public void backToMain() {
        try {
            menuApplication.showSearchPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
