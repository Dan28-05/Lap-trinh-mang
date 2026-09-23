package com.gpcoder.net.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Chương trình TCP Server minh họa cơ chế giao tiếp Socket hướng kết nối (TCP).
 * Tham khảo: viettuts.vn & gpcoder.com
 */
public class TCPServer {

    public static final int PORT = 8088;

    public static void main(String[] args) {
        System.out.println(">>> TCP SERVER ĐANG KHỞI ĐỘNG TRÊN CỔNG " + PORT + " <<<");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server đã sẵn sàng. Đang chờ kết nối từ Client...");

            // Lắng nghe và chấp nhận kết nối từ Client
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                System.out.println("Kết nối thành công từ Client: " + clientSocket.getRemoteSocketAddress());
                out.println("Chào mừng bạn đã kết nối tới TCP Server!");

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    System.out.println("[Server nhận từ Client]: " + clientMessage);
                    if ("bye".equalsIgnoreCase(clientMessage.trim()) || "exit".equalsIgnoreCase(clientMessage.trim())) {
                        out.println("Server: Tạm biệt Client!");
                        break;
                    }
                    // Phản hồi lại Client bằng chuỗi chữ hoa
                    String response = "Server Echo: " + clientMessage.toUpperCase();
                    out.println(response);
                }
                System.out.println("Client đã ngắt kết nối. Đóng phiên giao dịch.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
