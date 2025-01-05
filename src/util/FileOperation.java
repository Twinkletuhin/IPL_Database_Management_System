package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import java.util.List;

import menu.Club;
import menu.DatabaseSystem;
import menu.Player;

public class FileOperation {
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Club> clubsList = new ArrayList<>();
    private HashMap<String, String> countryShortName = new HashMap<>();
    private ArrayList<Player>stockList=new ArrayList<>();
    private ConcurrentHashMap<String, String> clubCridentials = new ConcurrentHashMap<>();

    public FileOperation() {
        try {
            getDatabase("players.txt");

        } catch (IOException e) {
            System.out.println("Error in reading player data");
        }
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

    public ArrayList<Player> getDatabase(String fileName) throws IOException {
        BufferedReader fReader = file(fileName);
        String line;
        try {

            while ((line = fReader.readLine()) != null) {
                String[] fields = line.split(",");
                // Name,Country,Age,Height,Club,Position,Number,WeeklySalary
                Player px = new Player(fields[0], fields[1], Integer.parseInt(fields[2]), Double.parseDouble(fields[3]),
                        fields[4], fields[5], fields[6].isEmpty() ? null : Integer.parseInt(fields[6]),
                        Integer.parseInt(fields[7]));
                URL flagUrl = FileOperation.class
                        .getResource("/flags/" + px.getCountry().toLowerCase().trim() + ".png");
                if (flagUrl != null) {
                    px.setCountryFlagPath(flagUrl.toExternalForm());

                } else {
                    px.setCountryFlagPath(FileOperation.class.getResource("flags/bangladesh.png").toExternalForm());
                }

                players.add(px);

            }
        } finally {
            fReader.close();
        }

        return (ArrayList<Player>) players;
    }
    public ArrayList<Player> loadStockList() throws IOException {
        BufferedReader fReader = file("stockList.txt");
        //ArrayList<Player>tempStockList=new ArrayList<>();
        String line;
        try {

            while ((line = fReader.readLine()) != null) {
                String[] fields = line.split(",");
                // Name,Country,Age,Height,Club,Position,Number,WeeklySalary
                Player px = new Player(fields[0], fields[1], Integer.parseInt(fields[2]), Double.parseDouble(fields[3]),
                        fields[4], fields[5], fields[6].isEmpty() ? null : Integer.parseInt(fields[6]),
                        Integer.parseInt(fields[7]));
                URL flagUrl = FileOperation.class
                        .getResource("/flags/" + px.getCountry().toLowerCase().trim() + ".png");
                if (flagUrl != null) {
                    px.setCountryFlagPath(flagUrl.toExternalForm());

                } else {
                    px.setCountryFlagPath(FileOperation.class.getResource("flags/bangladesh.png").toExternalForm());
                }

                //tempStockList.add(px);
                stockList.add(px);

            }
        } finally {
            fReader.close();
        }

        return (ArrayList<Player>)stockList;
    }

    public ArrayList<Club> loadClubsList() throws IOException {
        for (Player p : players) {
            Club foundClub = searchClubList(p.getClub());
            if (foundClub != null) {
                clubsList.get(clubsList.indexOf(foundClub)).addToClub(p);
                // clubsList.get(clubsList.indexOf(foundClub)).addToStock(null);;

            } else {
                Club club = new Club(p.getClub());
                club.setName(p.getClub());
                club.addToClub(p);
                // club.addToStock(null);
                clubsList.add(club);
            }

        }
        return clubsList;
    }

    public Club searchClubList(String clubName) {
        for (Club club : clubsList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                return club;
            }
        }
        return null;
    }

    public void printList() {
        for (Club club : clubsList) {
            System.out.println(club.getName());
            for (Player player : club.getPlayersList()) {
                System.out.println(player);
            }
        }
    }

    public ConcurrentHashMap<String, String> loadClubCridentials() throws FileNotFoundException, IOException {
        String line;
        BufferedReader fReader = file("clubCridentials.txt");
        try {
            while ((line = fReader.readLine()) != null) {
                String[] fields = line.split(",");
                clubCridentials.put(fields[0], fields[1]);

            }
        } catch (IOException e) {
            System.out.println("Error in reading club credentials");
        } finally {
            fReader.close();
        }

        return clubCridentials;
    }

    public void closeClubsList(ArrayList<Club> clubsList) throws IOException {
        // System.out.println("i am here");
        BufferedWriter fWriter = file("players.txt", false);
        try {

            for (Club club : clubsList) {
                for (Player player : club.getPlayersList()) {
                    fWriter.write(
                            player.getName() + "," + player.getCountry() + "," + player.getAge() + ","
                                    + player.getHeight()
                                    + "," + player.getClub() + "," + player.getPosition() + ","
                                    + (player.getNumber() != null ? player.getNumber() : "") + ","
                                    + player.getWeeklySalary() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error in writing player data");
        } finally {
            fWriter.close();
        }

    }
    public void printList(ArrayList<Player>px) {
        for(Player p:px){
            System.out.println(p);
        }
    }
    public void closeStockList(ArrayList<Player>finalStock) throws IOException {
         System.out.println("stock list closed");
       // printList(finalStock);
        BufferedWriter fWriter = file("stockList.txt", false);
        try {

           // for (Club club : clubsList) {
               // if(club.getStockList()!=null)
                for (Player player : finalStock) {
                    fWriter.write(
                            player.getName() + "," + player.getCountry() + "," + player.getAge() + ","
                                    + player.getHeight()
                                    + "," + player.getClub() + "," + player.getPosition() + ","
                                    + (player.getNumber() != null ? player.getNumber() : "") + ","
                                    + player.getWeeklySalary() + "\n");
              //  }
            }
        } catch (IOException e) {
            System.out.println("Error in writing player data in stock.txt");
        } finally {
            fWriter.close();
        }

    }

    public void closeClubCridentials(ConcurrentHashMap<String, String> clubPass) throws IOException {
        BufferedWriter fWriter = file("clubCridentials.txt", false);
        try {
            for (String club : clubPass.keySet()) {
                if (findClub(club) == null) {// if the club is new the set default password
                    fWriter.write(club + "," + "123456" + "\n");
                } else {
                    fWriter.write(club + "," + clubPass.get(club) + "\n");

                }
            }
        } finally {
            fWriter.close();
        }
    }

    private String findClub(String club) {
        for (String key : clubCridentials.keySet()) {
            if (key.equalsIgnoreCase(club)) {
                return key;
            }
        }
        return null;
    }

    public void printDB() {
        System.out.println("Players Database");
        System.out.println("=".repeat(120));
        for (Player p : players) {
            System.out.println(p);
        }
    }
}
