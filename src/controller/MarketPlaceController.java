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
import dto.BuyRequest;
import menu.Club;
import menu.Player;

import java.util.ArrayList;
import java.util.List;

public class MarketPlaceController {
    private Main main;
    @FXML
    private TableView<Player> playerTable;
    @FXML
    private TableColumn<Player, ImageView> imageColumn;
    @FXML
    private TableColumn<Player, ImageView> countryFlagColumn;
    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, String> countryColumn;
    @FXML
    private TableColumn<Player, String> countryNameColumn;

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

    @FXML
    private TableColumn<Player, Void> buyColumn;
    @FXML
    private Label clubPromot;
    @FXML
    private Button logoutButton;
    @FXML
    private Button backButton;
    private static ObservableList<Player> playerObservableList = FXCollections.observableArrayList();
    private String clubName;
    private static Club ownClub;
    private static MarketPlaceController currentController;

    public static void setCurrentController(MarketPlaceController controller) {
        currentController = controller;
    }

    public MarketPlaceController() {
        // Manually add some Player objects to the list
        // ArrayList<Player> players = new ArrayList<>();
        // players.add(new Player("Player1", "Country1", 25, 180.5, "Club1",
        // "Position1", 10, 50000));
        // players.add(new Player("Player2", "Country2", 28, 175.3, "Club1",
        // "Position2", 9, 60000));
        // players.add(new Player("Player3", "Country3", 22, 170.2, "Club1",
        // "Position3", 11, 45000));
        // playersStock = players;
    }

     @FXML
    public void initialize(String clubName) {
       
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
        this.clubName = clubName;
        clubPromot.setText("Showing market Place of " + clubName);
        // Add "Sell" buttons
        addBuyButtonToTable();
        MarketPlaceController.setCurrentController(this);


        playerObservableList.clear();
        // playerObservableList.addAll(playersStock);
        if (ownClub.getStockList() != null) {
            playerObservableList.addAll(ownClub.getStockList());

        }
        playerTable.setItems(playerObservableList);
    }

   

    public static void setOwnClub(Club club) {
        ownClub = club;
    }
  
    private void addBuyButtonToTable() {
        // Set cell factory for sellColumn
        Callback<TableColumn<Player, Void>, TableCell<Player, Void>> cellFactory = param -> new TableCell<>() {
            private final Button buyButton = new Button("Buy");

            {
                buyButton.setStyle("-fx-background-color: linear-gradient(to right, #603813 0%, #b29f94 51%, #603813 100%);" +
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
                buyButton.setOnAction(event -> {
                    Player player = getTableView().getItems().get(getIndex());
                    System.out.println("Buying player: " + player.getName());
                    BuyRequest newBuy=new BuyRequest(clubName,player);
                    ownClub.getStockList().remove(player);
                    System.out.println("stock list after buying player: ");
                    printList(ownClub.getStockList());
                    playerObservableList.remove(player);

                    try{
                        main.getSocketWrapper().write(newBuy);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    // Add your sell logic here

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buyButton);
                }
            }
        };

        buyColumn.setCellFactory(cellFactory);
    }

    public static void printList(ArrayList<Player> px) {
        System.out.println("Stock list of " + ownClub.getName() + "size: " + px.size());
        // for (Player p : ownClub.getStockList()) {
        // System.out.println(p);
        // }
        for (Player p : px) {
            System.out.println(p);
        }
    }

    // 
    public static void refreshStockList(Club refreshedClub) {
        ownClub = refreshedClub;
        System.out.println("Stock list updated");
        printList(ownClub.getStockList());
        
        if (currentController != null) {
            ObservableList<Player> updatedList = FXCollections.observableArrayList(ownClub.getStockList());
            Platform.runLater(() -> {
                currentController.playerTable.setItems(updatedList);
            });
        } else {
            System.out.println("Error: No active MarketPlaceController instance.updating ownPlayers list");
        }
    }
    
   

    @FXML
    public void logoutButton() {
        System.out.println("Logout button clicked!");
        // Add navigation logic here
        try{
            main.showLoginPage();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void backButton() {
        System.out.println("Markter button clicked!");
        try {
            main.showClientHome(clubName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
