package com.gpcoder.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastSender {

    public static final String GROUP_ADDRESS = "224.0.0.1";
    public static final int PORT = 8888;

    public static void main(String[] args) throws InterruptedException {
        DatagramSocket socket = null;
        try {
            InetAddress address = InetAddress.getByName(GROUP_ADDRESS);
            socket = new DatagramSocket();

            DatagramPacket outPacket = null;
            long counter = 0;
            while (counter < 5) {
                String msg = "Goi tin so " + counter;
                counter++;
                outPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, PORT);
                socket.send(outPacket);
                System.out.println("Server da gui goi tin: " + msg);
                Thread.sleep(1000);
            }
            System.out.println("Hoan thanh gui 5 goi tin multicast");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
