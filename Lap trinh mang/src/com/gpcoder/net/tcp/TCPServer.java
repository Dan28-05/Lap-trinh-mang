package com.gpcoder.net.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static final int PORT = 8088;

    public static void main(String[] args) {
        System.out.println("TCP Server dang khoi dong tren cong " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server san sang, dang cho client ket noi...");

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                System.out.println("Client da ket noi: " + clientSocket.getRemoteSocketAddress());
                out.println("Chao mung ban den voi TCP Server");

                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("Server nhan: " + msg);
                    if ("bye".equalsIgnoreCase(msg.trim()) || "exit".equalsIgnoreCase(msg.trim())) {
                        out.println("Tam biet client");
                        break;
                    }
                    out.println("Server echo: " + msg.toUpperCase());
                }
                System.out.println("Client da ngat ket noi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
