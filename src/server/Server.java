package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import menu.Club;
import util.FileOperation;
import util.SocketWrapper;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Club> clubsList = new ArrayList<>();
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
            System.out.println("Database loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading database: " + e.getMessage());
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
        new ReadThreadServer(clubsList, clubMap, clubCridentials, socketWrapper);
    }

    public void saveDB() {
        try {
            fileOperation.closeClubsList(clubsList);
            fileOperation.closeClubCridentials(clubCridentials);
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
