package menu;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Club implements Serializable {
    private String name;
    private String password;
    private ArrayList<Player> playersList = new ArrayList<>();
    private ArrayList<Player> stockList = new ArrayList<>();;

    public Club() {
       
    }

    public Club(String name, String password, ArrayList<Player> playersList, ArrayList<Player> stockList) {
        this.name = name;
        this.password = password;
        this.playersList = new ArrayList<>(playersList);
        this.stockList = new ArrayList<>(stockList);
    }

    public Club(String name, String password, ArrayList<Player> playersList) {
        this.name = name;
        this.password = password;
        this.playersList = playersList;
    }

    public Club(Club club) {
        this.name = club.name;
        this.password = club.password;
        this.playersList = new ArrayList<>(club.playersList);
        this.stockList = new ArrayList<>(club.stockList);
    }

    public Club(String name, ArrayList<Player> playersList) {
        this.name = name;
        this.playersList = playersList;

    }

    public Club(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlayersList(ArrayList<Player> playersList) {
        this.playersList = playersList;
    }

    public void addToClub(Player player) {
        playersList.add(player);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void addToStock(Player player) {
        this.stockList.add(player);
    }

    public ArrayList<Player> getPlayersList() {
        return playersList;
    }

    public void setStockList(ArrayList<Player> stokcList) {
        this.stockList = stokcList;
    }

    public ArrayList<Player> getStockList() {
        return stockList;
    }

    // @Override
    // public boolean equals(Object o) {
    // if (o == this) {
    // return true;
    // }
    // if (o instanceof Club) {
    // Club club = (Club) o;
    // return name.equalsIgnoreCase(club.getName());
    // }
    // return false;
    // }

    // @Override
    // public int hashCode() {
    // return Objects.hash(name);
    // }
}
