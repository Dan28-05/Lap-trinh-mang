package com.gpcoder.net.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Chương trình TCP Client kết nối tới TCPServer qua Socket.
 * Tham khảo: viettuts.vn & gpcoder.com
 */
public class TCPClient {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8088;

    public static void main(String[] args) {
        System.out.println(">>> KHỞI ĐỘNG TCP CLIENT KẾT NỐI TỚI " + HOST + ":" + PORT + " <<<");
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Đã kết nối thành công tới Server!");

            // Đọc lời chào từ Server
            String welcomeMessage = in.readLine();
            System.out.println("[Nhận từ Server]: " + welcomeMessage);

            // Gửi các gói tin thử nghiệm
            String[] testMessages = {
                "Xin chao Server tu Java Socket Client",
                "Lap trinh mang voi Java rat thu vi",
                "Hoc phan OOSE 2026-2027",
                "bye"
            };

            for (String msg : testMessages) {
                System.out.println("\n[Client gửi]: " + msg);
                out.println(msg);

                String response = in.readLine();
                System.out.println("[Client nhận phản hồi]: " + response);
            }

            System.out.println("\nĐã hoàn thành phiên giao dịch TCP Client.");

        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
