package clients;

import java.util.ArrayList;

import controller.ClientHomeController;
import controller.MarketPlaceController;
import dto.LoginDTO;
import javafx.application.Platform;
import menu.Club;
import menu.Player;
import util.SocketWrapper;

public class ClientThreadServer implements Runnable {
    private final Main main;
    private final Thread thr;
   
    public ClientThreadServer(Main main) {
        this.main = main;
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
                Object o = main.getSocketWrapper().read();
                System.out.println("instance of logindto: " + (o instanceof LoginDTO));
                System.out.println("instance of club: " + (o instanceof Club));
                System.out.println("instance of string: " + (o instanceof String));
                System.out.println("instance of arraylist: " + (o instanceof ArrayList));
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        System.out.println(loginDTO.getClubName());
                        System.out.println(loginDTO.isStatus());

                        if (loginDTO.isStatus()) {

                            try {
                                Object ob = main.getSocketWrapper().read();
                                // System.out.println(ob instanceof Club);
                                if (ob != null) {
                                    if (ob instanceof Club) {
                                        Club club = (Club) ob;

                                        // players = club.getPlayersList();
                                        // ClientHomeController.setPlayersList(players);
                                        ClientHomeController.setOwnClub(club);
                                        main.setMainClub(club);
                                        // System.out.println(players.size());
                                        Platform.runLater(() -> {
                                            try {
                                                main.showClientHome(club.getName());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        });

                                        // main.showClientHome(players, club.getName());
                                    }
                                } else {
                                    System.out.println("I got null ClubList from server");
                                }

                            } catch (Exception e) {
                                System.out.println(e);

                            }
                           
                        } else {
                            Platform.runLater(() -> {
                                main.showAlert();

                            });
                        }

                      
                    } else if (o instanceof String&&((String) o).equalsIgnoreCase("updateStockListAfterSell")) {
                        //String messageFromServer = (String) o;
                            System.out.println("I got update message");
                            Object stockUpdatedClub = main.getSocketWrapper().read();
                            System.out.println("instance of string: " + (stockUpdatedClub instanceof String));
                            System.out.println("instance of Club: " + (stockUpdatedClub instanceof Club));
                            // System.out.println((String)stockUpdatedClub);
                            if (stockUpdatedClub == null) {
                                System.out.println("Didn't get updated stock list from server");
                            } else if (stockUpdatedClub instanceof Club) {
                                Club newClub = (Club) stockUpdatedClub;
                                MarketPlaceController.refreshStockList(newClub);
                                //i have to also refresh the main's club as Market
                                //controller initially pointing to it
                                main.setMainClub(newClub);
                                // System.out.println(
                                // "size of the sending player list list: " + newClub.getPlayersList().size());
                                // System.out.println("Player's list: ");
                                // printList(newClub.getPlayersList());
                                // System.out.println(
                                // "size of the sending player stock list: " + newClub.getStockList().size());
                                // System.out.println("Stock list: ");
                                // printList(newClub.getStockList());
                            } 
                    }else if(o instanceof String&&((String)o).equalsIgnoreCase("updateStockListAfterBuy")){
                        Object updatedStockList=main.getSocketWrapper().read();
                        if(updatedStockList==null){
                            System.out.println("got null list after buying response");
                        }else{
                            Club updateClub=(Club)updatedStockList;
                            main.setMainClub(updateClub);
                            System.out.println("After buy stock list of "+updateClub.getName());
                            printList(updateClub.getStockList());
                            System.out.println("After buying player list of "+updateClub.getName());
                            printList(updateClub.getPlayersList());
                            MarketPlaceController.refreshStockList(updateClub);
                            ClientHomeController.refreshPlayerList(updateClub);
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
