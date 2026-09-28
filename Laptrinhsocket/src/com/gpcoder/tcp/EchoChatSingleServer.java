package com.gpcoder.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoChatSingleServer {

    public final static int SERVER_PORT = 7;

    public static void main(String[] args) throws IOException {
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

                    OutputStream os = socket.getOutputStream();
                    InputStream is = socket.getInputStream();
                    int ch = 0;
                    while (true) {
                        ch = is.read();
                        if (ch == -1) {
                            break;
                        }
                        os.write(ch);
                    }
                    socket.close();
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
