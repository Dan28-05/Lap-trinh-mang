package com.gpcoder.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 9876;

    public static void main(String[] args) {
        System.out.println("Khoi dong UDP Client gui toi " + SERVER_HOST + ":" + SERVER_PORT);
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress ipAddress = InetAddress.getByName(SERVER_HOST);

            String[] messages = {
                "Goi tin UDP 1: Xin chao UDP Server",
                "Goi tin UDP 2: Du lieu gui phi ket noi",
                "exit"
            };

            for (String m : messages) {
                byte[] sendData = m.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, SERVER_PORT);
                System.out.println("\nClient gui: " + m);
                clientSocket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                String res = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client nhan phan hoi: " + res);
            }

            System.out.println("\nGui nhan xong tat ca goi tin UDP");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
