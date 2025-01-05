package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import clients.Main;
import dto.SellRequest;
import menu.Club;
import menu.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ClientHomeController {
    private Main main;
    @FXML
    private TableView<Player> playerTable;
    @FXML
    private TableColumn<Player, ImageView> imageColumn;

    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, ImageView> countryFlagColumn;

    @FXML
    private TableColumn<Player, String> countryColumn;

    @FXML
    private TableColumn<Player, Integer> ageColumn;

    @FXML
    private TableColumn<Player, Double> heightColumn;

    @FXML
    private TableColumn<Player, String> clubColumn;
    @FXML
    private TableColumn<Player, String> countryNameColumn;

    @FXML
    private TableColumn<Player, String> positionColumn;

    @FXML
    private TableColumn<Player, Integer> numberColumn;

    @FXML
    private TableColumn<Player, Integer> weeklySalaryColumn;

    @FXML
    private TableColumn<Player, Void> sellColumn;
    @FXML
    private Label clubPromot;
    @FXML
    private Button logoutButton;
    @FXML
    private Button marketButton;
    private ObservableList<Player> playerObservableList = FXCollections.observableArrayList();
    private String clubName;
    private static Club ownClub;
    private static ClientHomeController currentController;

    public static void setCurrentController(ClientHomeController controller) {
        currentController = controller;
    }

    public ClientHomeController() {
    }

    public void initialize(String clubName) {
        this.clubName = clubName;

        TableColumn<?, ?> firstColumn = playerTable.getColumns().get(0); // First column
        TableColumn<?, ?> lastColumn = playerTable.getColumns().get(playerTable.getColumns().size() - 1); // Last column

        // Add style classes
        firstColumn.getStyleClass().add("first-column-header");
        lastColumn.getStyleClass().add("last-column-header");

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
        clubPromot.setText("Showing players for " + clubName);
        ClientHomeController.setCurrentController(currentController);
        // Add "Sell" buttons
        addSellButtonToTable();

        playerObservableList.clear();
        playerObservableList.addAll(ownClub.getPlayersList());// featched players list form the club
        playerTable.setItems(playerObservableList);
    }

    public static void setOwnClub(Club club) {
        ownClub = club;
    }

    private void addSellButtonToTable() {
        Callback<TableColumn<Player, Void>, TableCell<Player, Void>> cellFactory = param -> new TableCell<>() {
            private final Button sellButton = new Button("Sell");

            {
                sellButton.setStyle(
                        "-fx-background-color: linear-gradient(to right, #603813 0%, #b29f94 51%, #603813 100%);" +
                                "-fx-background-size: 200% auto;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 6 16;" +
                                "-fx-font-family: 'Poppins';" +
                                "-fx-border-radius: 10;" +
                                "-fx-border-color: rgb(3, 244, 248);" +
                                "-fx-border-width: 2;" +
                                "-fx-font-size: 12;" +
                                "-fx-effect: dropshadow(gaussian, #eee, 20, 0.5, 0, 0);" +
                                "-fx-transition: all 0.5s ease;" +
                                "-fx-cursor: hand;");
                sellButton.setOnAction(event -> {
                    Player player = getTableView().getItems().get(getIndex());
                    System.out.println("Selling player: " + player.getName());
                    SellRequest newSell = new SellRequest(clubName, player);

                    ownClub.getPlayersList().remove(player);
                    playerObservableList.remove(player);
                    try {
                        main.getSocketWrapper().write(newSell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(sellButton);
                }
            }
        };

        sellColumn.setCellFactory(cellFactory);
    }

    @FXML
    public void logoutButton() {
        System.out.println("Logout button clicked!");
        try {
            main.showLoginPage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void marketButton() {
        // System.out.println("Markter button clicked!");
        try {
            main.showMarketPlace(clubName);
            MarketPlaceController.setOwnClub(ownClub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshPlayerList(Club refreshedClub) {
        ownClub = refreshedClub;
        System.out.println("Players list updated");
        if (currentController != null) {
            ObservableList<Player> updatedList = FXCollections.observableArrayList(ownClub.getPlayersList());
            Platform.runLater(() -> {
                currentController.playerTable.setItems(updatedList);
            });

        }
    }

    public static void printList(ArrayList<Player> px) {
        System.out.println("Player list of " + ownClub.getName() + "size: " + px.size());
        // for (Player p : ownClub.getStockList()) {
        // System.out.println(p);
        // }
        for (Player p : px) {
            System.out.println(p);
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
