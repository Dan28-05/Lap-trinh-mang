package com.gpcoder.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

    public static final int PORT = 9876;

    public static void main(String[] args) {
        System.out.println("UDP Server dang lang nghe tren cong " + PORT);
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String text = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("Server nhan tu " + clientAddress + ":" + clientPort + " : " + text);

                String response = "UDP ACK: " + text.toUpperCase();
                byte[] sendData = response.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);

                if ("exit".equalsIgnoreCase(text.trim()) || "bye".equalsIgnoreCase(text.trim())) {
                    System.out.println("Nhan tin hieu dung tu client, ket thuc server");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
