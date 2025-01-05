package menu;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AddPlayerController {
    private MenuApplication menuApplication;
    @FXML
    private TextField nameField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField clubField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField weeklySalaryField;

    @FXML
    private RadioButton yesButton;

    @FXML
    private RadioButton noButton;

    private ToggleGroup numberToggleGroup;

    @FXML
    public void initialize() {
        // Initialize the toggle group for Yes/No radio buttons
        numberToggleGroup = new ToggleGroup();
        yesButton.setToggleGroup(numberToggleGroup);
        noButton.setToggleGroup(numberToggleGroup);

        // Set default to "No"
        noButton.setSelected(true);
        toggleNumberField(); // Ensure the number field is hidden on initialization
    }

    /* 
      Toggles the visibility of the Number TextField based on the Yes/No radio
      button selection.
     */
    @FXML
    public void toggleNumberField() {
        boolean isYesSelected = yesButton.isSelected();
        numberField.setVisible(isYesSelected);
        if (!isYesSelected) {
            numberField.clear(); // Clear the field if it's hidden
        }
    }

    /*
     Called when the "Add Player" button is clicked.
     */
    @FXML
    public void addPlayer() {
        try {
            String name = nameField.getText();
            String country = countryField.getText();
            int age = Integer.parseInt(ageField.getText());
            double height = Double.parseDouble(heightField.getText());
            String club = clubField.getText();
            String position = positionField.getText();
            Integer number = yesButton.isSelected() ? Integer.parseInt(numberField.getText()) : null;
            int weeklySalary = Integer.parseInt(weeklySalaryField.getText());

           
            Player player = new Player(name, country, age, height, club, position, number, weeklySalary);
            Player findPlayer = menuApplication.getDatabaseSystem().searchByName(name);
            if (findPlayer != null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Player already exists!");
                alert.showAndWait();
                // return;
                clearFields();
            } else {

                try {

                    menuApplication.getDatabaseSystem().addPlayer(player);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Player added successfully!");
                alert.showAndWait();

                // Clear fields
                // clearFields();
                try {
                    menuApplication.showMainMenu();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Please ensure all numeric fields (Age, Height, Number, Weekly Salary) are valid numbers.");
            alert.showAndWait();
        }
    }

    /*
      Clears all input fields and resets to default state.
     */
    @FXML
    public void clearFields() {
        nameField.clear();
        countryField.clear();
        ageField.clear();
        heightField.clear();
        clubField.clear();
        positionField.clear();
        numberField.clear();
        weeklySalaryField.clear();

        // Reset radio buttons
        noButton.setSelected(true);
        toggleNumberField(); // Ensure the number field is hidden
    }
    @FXML
    public void backToMain(){
        try {
            menuApplication.showMainMenu();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }
}
