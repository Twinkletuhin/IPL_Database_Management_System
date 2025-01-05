package menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseSystem {
    private String fileNam;
    private static List<Player> players = new ArrayList<>();
    private HashMap<String, List<Player>> clubs = new HashMap<>();

    public DatabaseSystem(String fileName) throws IOException {
        loadDatabase(fileName);
        loadClubs();
        fileNam = fileName;
    }

    // Function to search player by name
    public Player searchByName(String name) {
        for (Player x : players) {
            if (x.getName().toLowerCase().contains(name.toLowerCase())) {
                return x;
            }
        }
        return null;
    }

    public Player searchByNameA(String name) {
        for (Player x : players) {
            if (x.getName().equalsIgnoreCase(name)) {
                return x;
            }
        }
        return null;
    }

    // Function to search player by club and country
    public List<Player> searchByClubAndCountry(String club, String country) {
        List<Player> result = new ArrayList<>();
        for (Player x : players) {
            if (club.equalsIgnoreCase("ANY") && x.getCountry().equalsIgnoreCase(country)) {
                result.add(x);
            }

            else if (x.getClub().equalsIgnoreCase(club) && x.getCountry().equalsIgnoreCase(country)) {
                result.add(x);
            }
        }
        return result;
    }

    public List<Player> searchByPosition(String position) {
        List<Player> result = new ArrayList<>();
        for (Player x : players) {
            if (x.getPosition().equalsIgnoreCase(position)) {
                result.add(x);
            }
        }
        return result;
    }

    // Function to search player by salary range
    public List<Player> searchBySalaryRange(int min, int max) {
        List<Player> result = new ArrayList<>();
        for (Player x : players) {
            if (x.getWeeklySalary() >= min && x.getWeeklySalary() <= max) {
                result.add(x);
            }
        }
        return result;
    }

    // Function to count the number of players in each country
    public HashMap<String, Integer> countryWisePlayerCount() {
        HashMap<String, Integer> result = new HashMap<>();
        for (Player x : players) {
            String country = x.getCountry();
            if (result.containsKey(country)) {
                result.put(country, result.get(country) + 1);
            } else {
                result.put(country, 1);
            }
        }
        return result;
    }

    // Function to find the player with the maximum salary in a club
    public Player maxSalaryPlayer(String club) {

        List<Player> foundClub = new ArrayList<>();

        for (String key : clubs.keySet()) {
            if (key.equalsIgnoreCase(club)) {
                foundClub = clubs.get(key);
                break;
            }
        }
        if (foundClub.isEmpty()) {
            return null;
        }
        Player max = foundClub.get(0);
        for (Player p : foundClub) {
            if (p.getWeeklySalary() > max.getWeeklySalary()) {
                max = p;
            }
        }
        return max;

    }

    // Function to find the player with the maximum age in a club
    public Player maxAgePlayer(String club) {

        List<Player> foundClub = new ArrayList<>();
        for (String key : clubs.keySet()) {
            if (key.equalsIgnoreCase(club)) {
                foundClub = clubs.get(key);
                break;
            }
        }
        if (foundClub.isEmpty()) {
            return null;
        }
        Player max = foundClub.get(0);
        for (Player p : foundClub) {
            if (p.getAge() > max.getAge()) {
                max = p;
            }
        }
        return max;

    }

    // Function to find the player with the maximum height in a club
    public Player maxHeightPlayer(String club) {
        List<Player> foundList = new ArrayList<>();
        for (String key : clubs.keySet()) {
            if (key.equalsIgnoreCase(club)) {
                foundList = clubs.get(key);
                break;
            }
        }
        if (foundList.isEmpty()) {
            return null;
        }
        Player max = foundList.get(0);
        for (Player p : foundList) {
            if (p.getHeight() > max.getHeight()) {
                max = p;
            }
        }
        return max;
    }
    // Function to find the total yearly salary of a club

    public Long totalYearlySalary(String club) {

        List<Player> foundClubs = new ArrayList<>();
        for (String key : clubs.keySet()) {
            if (key.equalsIgnoreCase(club)) {
                foundClubs = clubs.get(key);
                break;
            }
        }
        if (foundClubs.isEmpty()) {
            return null;
        }
        Long total = 0L;
        for (Player p : foundClubs) {
            total += p.getWeeklySalary() * 52;
        }
        return total;

    }

    public static void addPlayer(Player player) throws IOException {
        players.add(player);
    }

    public void exitSystem() throws IOException {
        BufferedWriter wFile = file(fileNam, false);// false for open w mode
        try {
            for (Player player : players) {
                // Name,Country,Age,Height,Club,Position,Number,WeeklySalary
                wFile.write(
                        player.getName() + "," + player.getCountry() + "," + player.getAge() + "," + player.getHeight()
                                + "," + player.getClub() + "," + player.getPosition() + ","
                                + (player.getNumber() != null ? player.getNumber() : "") + ","
                                + player.getWeeklySalary() + "\n");
            }

        } finally {
            wFile.close();
        }

    }

    // Function to display the player details
    public static void displayList(List<Player> playersList) {
        System.out.println(Player.getHeader());
        System.out.println("=".repeat(120));
        for (Player p : playersList) {
            System.out.println(p);
        }
    }

    List<Player> getPlayersList() {
        return players;
    }

    public static BufferedReader file(String fileName) throws FileNotFoundException {

        String currentDir = System.getProperty("user.dir");
        return new BufferedReader(new FileReader(currentDir + "/" + fileName));
    }

    public static BufferedWriter file(String fileName, boolean noOverrite) throws IOException {
        String currentDir = System.getProperty("user.dir");
        return new BufferedWriter(
                new FileWriter(currentDir + "/" + fileName, noOverrite));
    }

    public static void loadDatabase(String fileName) throws IOException {
        BufferedReader fReader = file(fileName);
        String line;
        try {

            while ((line = fReader.readLine()) != null) {
                String[] fields = line.split(",");
                // Name,Country,Age,Height,Club,Position,Number,WeeklySalary
                Player p= new Player(fields[0], fields[1], Integer.parseInt(fields[2]), Double.parseDouble(fields[3]),
                fields[4], fields[5], fields[6].isEmpty() ? null : Integer.parseInt(fields[6]),
                Integer.parseInt(fields[7]));
               
                //System.out.println(path);
                URL flagUrl=DatabaseSystem.class.getResource("/flags/"+p.getCountry().toLowerCase().trim()+".png");
                if(flagUrl!=null){
                    p.setCountryFlagPath(flagUrl.toExternalForm());

                }
                else{
                    p.setCountryFlagPath(DatabaseSystem.class.getResource("flags/bangladesh.png").toExternalForm());
                }
                addPlayer(p);

            }
        } finally {
            fReader.close();
        }
    }

    public void loadClubs() throws IOException {
        for (Player p : players) {
            if (clubs.containsKey(p.getClub())) {
                clubs.get(p.getClub()).add(p);
            } else {
                List<Player> list = new ArrayList<>();
                list.add(p);
                clubs.put(p.getClub(), list);
            }
        }
    }
}
