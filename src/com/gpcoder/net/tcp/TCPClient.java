package com.gpcoder.net.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8088;

    public static void main(String[] args) {
        System.out.println("Dang ket noi toi TCP Server " + HOST + ":" + PORT);
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Ket noi thanh cong toi Server");

            String welcome = in.readLine();
            System.out.println("Server gui: " + welcome);

            String[] messages = {
                "Xin chao server tu TCP Client",
                "Lap trinh mang java co ban",
                "Hoc phan OOSE 2026-2027",
                "bye"
            };

            for (String m : messages) {
                System.out.println("\nClient gui: " + m);
                out.println(m);

                String res = in.readLine();
                System.out.println("Client nhan phan hoi: " + res);
            }

            System.out.println("\nKet thuc phien giao tiep client");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
