package menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;
import java.util.Map;

public class HashTableController {
    private MenuApplication menuApplication;
    @FXML
    private TableView<KeyValue> tableView;

    @FXML
    private TableColumn<KeyValue, String> keyColumn;

    @FXML
    private TableColumn<KeyValue, Integer> valueColumn;

    // A class to hold key-value pairs
    public static class KeyValue {
        private final String key;
        private final Integer value;

        public KeyValue(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }
    }

   @FXML
    public void initialize(HashMap<String, Integer> map) {
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        // Load sample data
        // HashMap<String, Integer> map = new HashMap<>();
        // map.put("Alice", 25);
        // map.put("Bob", 30);
        // map.put("Charlie", 35);

        ObservableList<KeyValue> data = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            data.add(new KeyValue(entry.getKey(), entry.getValue()));
        }

        // Set data in the TableView
        tableView.setItems(data);
    }
    public void setMenuApplication(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }
    @FXML
    public void backToSearch() {
        try {
            menuApplication.showSearchPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
