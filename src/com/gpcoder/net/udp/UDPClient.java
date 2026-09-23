package com.gpcoder.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Chương trình UDP Client gửi và nhận DatagramPacket tới UDPServer.
 * Tham khảo: viettuts.vn & gpcoder.com
 */
public class UDPClient {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 9876;

    public static void main(String[] args) {
        System.out.println(">>> KHỞI ĐỘNG UDP CLIENT GỬI TỚI " + SERVER_HOST + ":" + SERVER_PORT + " <<<");
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress ipAddress = InetAddress.getByName(SERVER_HOST);

            String[] messages = {
                "Goi tin UDP so 1: Xin chao UDP Server",
                "Goi tin UDP so 2: Du lieu truyen khong can thiet lap ket noi",
                "exit"
            };

            for (String msg : messages) {
                byte[] sendData = msg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, SERVER_PORT);
                System.out.println("\n[UDP Client gửi]: " + msg);
                clientSocket.send(sendPacket);

                // Nhận phản hồi từ Server
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("[UDP Client nhận phản hồi]: " + modifiedSentence);
            }

            System.out.println("\nĐã gửi nhận xong tất cả gói tin UDP.");
        } catch (Exception e) {
            System.err.println("Lỗi UDP Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
