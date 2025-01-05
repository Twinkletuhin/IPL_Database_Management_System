package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import menu.Club;
import menu.Player;
import util.FileOperation;
import util.SocketWrapper;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Club> clubsList = new ArrayList<>();
    private ArrayList<Player>stockList=new ArrayList<>();
    private ConcurrentHashMap<String, String> clubCridentials = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, SocketWrapper> clubMap = new ConcurrentHashMap<>();
    private FileOperation fileOperation;
    private volatile boolean running = true;

    public Server(int port) {
        this.fileOperation = new FileOperation();
        loadDB();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    private void loadDB() {
        try {
            clubsList = fileOperation.loadClubsList();
            clubCridentials = fileOperation.loadClubCridentials();
            stockList=fileOperation.loadStockList(); 
           // printList(stockList);
            setStockList();
            System.out.println("Database loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading database: " + e.getMessage());
        }
    }
    private void setStockList(){
        
        for(Club clb:clubsList){
            for(Player p:stockList){
                if(clb.getName().equalsIgnoreCase(p.getClub())==false){
                    clb.getStockList().add(p);
                }

            }
        }

    }
    
    public void start() {
        try {
            while (running) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } finally {
            stopServer();
        }
    }

    private void serve(Socket clientSocket) throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        new ReadThreadServer(clubsList,stockList, clubMap, clubCridentials, socketWrapper);
    }
    private void takeStockBeforeClose(){
        for(Club clb:clubsList){
            ArrayList<Player>tempStock=clb.getStockList();
            if(tempStock!=null){
                for(Player p:tempStock){
                    if(exitsInStock(p.getName())==false){
                        stockList.add(p);
                    }
                }
            }
        }
    }
    private boolean exitsInStock(String name){
        for(Player p:stockList){
            if(p.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public void saveDB() {
        try {
            fileOperation.closeClubsList(clubsList);
            fileOperation.closeClubCridentials(clubCridentials);
            takeStockBeforeClose();
            fileOperation.closeStockList(stockList);
        } catch (IOException e) {
            System.out.println("Error saving database: " + e.getMessage());
        }
    }

    private void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println("Server stopped.");
        } catch (IOException e) {
            System.out.println("Error stopping server: " + e.getMessage());
        }
    }
    public void printList(ArrayList<Player>px) {
        for(Player p:px){
            System.out.println(p);
        }
    }
    public static void main(String[] args) {
        Server server = new Server(33333);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter 'Stop' to stop the server");
                String signal = scanner.nextLine();
                if (signal.strip().equalsIgnoreCase("Stop")) {
                    server.saveDB();
                    System.exit(0);
                }
            }
        }).start();
        server.start();
    }
}
