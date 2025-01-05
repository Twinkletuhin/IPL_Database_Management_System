package server;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import dto.BuyRequest;
import dto.LoginDTO;
import dto.SellRequest;
import menu.Club;
import menu.Player;
import util.*;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    private ArrayList<Club> clubsList;
    private ConcurrentHashMap<String, SocketWrapper> clubMap;
    private ConcurrentHashMap<String, String> clubCridentials;

    public ReadThreadServer(ArrayList<Club> clubsList, ConcurrentHashMap<String, SocketWrapper> clubMap,
            ConcurrentHashMap<String, String> clubCridentials, SocketWrapper socketWrapper) {
        this.clubsList = clubsList;
        // System.out.println("Clubs list size in readthread server: " +
        // clubsList.size());
        this.clubMap = clubMap;
        this.clubCridentials = clubCridentials;
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

    public static void printList(ArrayList<Player> p) {
        // System.out.println("Stock list of " + ownClub.getName());
        for (Player px : p) {
            System.out.println(px);
        }
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        String password = clubCridentials.get(loginDTO.getClubName());

                        if (password != null && loginDTO.getPassword().equals(password)) {
                            loginDTO.setStatus(true);
                            socketWrapper.write(loginDTO); // Send login confirmation
                            for (Club club : clubsList) {
                                if (club.getName().equals(loginDTO.getClubName())) {
                                    // System.out.println("size of the clublist in readthreadserver:
                                    // "+club.getPlayersList().size());
                                    socketWrapper.write(club);
                                    break;
                                }
                            }

                            // Add the club to the active map
                            synchronized (clubMap) {
                                System.out.println(loginDTO.getClubName() + " added to the clubmap " + socketWrapper);
                                clubMap.put(loginDTO.getClubName(), socketWrapper);
                            }
                        } else {
                            loginDTO.setStatus(false);
                            socketWrapper.write(loginDTO); // Send login failure
                        }
                    } else if (o instanceof SellRequest) {
                        SellRequest sellRequest = (SellRequest) o;
                        System.out.println("i got sell request from: " + sellRequest.getClubName());
                        System.out.println(sellRequest.getPlayer());
                        {
                            for (Club eachClub : clubsList) {
                                if (eachClub.getName().equalsIgnoreCase(sellRequest.getClubName())) {
                                    // Remove the player from the club's players list
                                    boolean removed = eachClub.getPlayersList().remove(sellRequest.getPlayer());
                                    if (removed) {
                                        System.out.println(sellRequest.getPlayer().getName() + " removed from "
                                                + eachClub.getName());
                                    } else {
                                        System.out.println("Failed to remove " + sellRequest.getPlayer().getName());
                                    }
                                    printList(eachClub.getPlayersList());
                                } else {
                                    // Add the player to the stock list of other clubs
                                    // System.out.println("Adding to stock list of " + eachClub.getName());
                                    // boolean added = eachClub.getStockList().add(sellRequest.getPlayer());
                                    eachClub.addToStock(sellRequest.getPlayer());

                                    // if (added) {
                                    // System.out.println("added successfully ");
                                    // } else {
                                    // System.out.println("can't add to the club's stock list");
                                    // }
                                    // printList(eachClub.getStockList());
                                }
                            }
                        }

                        // updating the stock list of the clients
                        for (Club club : clubsList) {
                            SocketWrapper sWrapper = clubMap.get(club.getName());
                            if (sWrapper != null && sWrapper != socketWrapper) {
                                sWrapper.write("updateStockListAfterSell");
                                System.out.println(
                                        "stock list writing in club: " + club.getName() + " socket wrapper: "
                                                + sWrapper);
                                sWrapper.write(new Club(club));

                            }
                        }

                    } else if (o instanceof BuyRequest) {
                        BuyRequest buyRequest = (BuyRequest) o;
                        String buyer = buyRequest.getBuyer();
                        System.out.println("buyer is: " + buyer);
                        // updating the database of the server

                        for (Club eachClub : clubsList) {
                            if (eachClub.getName().equalsIgnoreCase(buyer)) {
                                // Remove the player from the club's players list
                                Player p = new Player(buyRequest.getPlayer());
                                // p=buyRequest.getPlayer();
                                p.setClub(buyer);
                                // as we create new player without setting country flag path so i have to add
                                // this following line
                                URL flagUrl = FileOperation.class
                                        .getResource("/flags/" + p.getCountry().toLowerCase().trim() + ".png");
                                if (flagUrl != null) {
                                    p.setCountryFlagPath(flagUrl.toExternalForm());

                                } else {
                                    p.setCountryFlagPath(
                                            FileOperation.class.getResource("flags/bangladesh.png").toExternalForm());
                                }

                                boolean added = eachClub.getPlayersList().add(p);
                                if (added) {
                                    System.out.println(buyRequest.getPlayer().getName() + " added to "
                                            + eachClub.getName());
                                } else {
                                    System.out.println("Failed to add " + buyRequest.getPlayer().getName());
                                }
                                printList(eachClub.getPlayersList());
                            }
                            {

                                boolean rm = eachClub.getStockList().remove(buyRequest.getPlayer());
                                if (rm) {
                                    System.out.println(
                                            "removed successfully from the stock list of: " + eachClub.getName());
                                } else {
                                    System.out.println("can't remove  from the stock list of " + eachClub.getName());
                                }
                            }
                        }
                        // writing the updated club to the client
                        for (Club club : clubsList) {
                            System.out.println("after buy Stock list of " + club.getName());
                            printList(club.getStockList());
                            System.out.println("after buy players list of " + club.getName());
                            printList(club.getPlayersList());
                            SocketWrapper sWrapper = clubMap.get(club.getName());
                            if (sWrapper != null) {
                                sWrapper.write("updateStockListAfterBuy");
                                System.out.println(
                                        "stock list writing in club: " + club.getName() + " socket wrapper: "
                                                + sWrapper);
                                sWrapper.write(new Club(club));
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
