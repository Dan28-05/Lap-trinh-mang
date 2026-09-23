package com.gpcoder.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Chương trình UDP Server sử dụng DatagramSocket và DatagramPacket.
 * Giao tiếp phi kết nối (Connectionless).
 * Tham khảo: viettuts.vn & gpcoder.com
 */
public class UDPServer {

    public static final int PORT = 9876;

    public static void main(String[] args) {
        System.out.println(">>> UDP SERVER ĐANG LẮNG NGHE TRÊN CỔNG " + PORT + " <<<");
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            byte[] receiveData = new byte[1024];

            while (true) {
                // Chuẩn bị packet để nhận dữ liệu
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("[UDP Server nhận từ " + clientAddress + ":" + clientPort + "]: " + sentence);

                // Chuẩn bị dữ liệu phản hồi
                String capitalizedSentence = "UDP ACK: " + sentence.toUpperCase();
                byte[] sendData = capitalizedSentence.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);

                if ("exit".equalsIgnoreCase(sentence.trim()) || "bye".equalsIgnoreCase(sentence.trim())) {
                    System.out.println("Nhận lệnh kết thúc từ Client. Đang dừng UDP Server.");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi UDP Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
