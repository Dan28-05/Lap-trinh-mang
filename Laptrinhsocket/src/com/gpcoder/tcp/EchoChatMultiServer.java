package com.gpcoder.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoChatMultiServer {

    public static final int NUM_OF_THREAD = 4;
    public final static int SERVER_PORT = 7;

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREAD);
        ServerSocket serverSocket = null;
        try {
            System.out.println("Dang lang nghe tren cong " + SERVER_PORT + ", vui long cho...");
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server da khoi dong: " + serverSocket);
            System.out.println("Dang cho client ket noi...");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client da ket noi: " + socket);

                    WorkerThread handler = new WorkerThread(socket);
                    executor.execute(handler);
                } catch (IOException e) {
                    System.err.println("Loi ket noi: " + e.getMessage());
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
