package com.gpcoder.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {

    public static final byte[] BUFFER = new byte[4096];

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        MulticastSocket socket = null;
        DatagramPacket inPacket = null;
        try {
            InetAddress address = InetAddress.getByName(MulticastSender.GROUP_ADDRESS);
            socket = new MulticastSocket(MulticastSender.PORT);
            socket.joinGroup(address);

            System.out.println("Receiver da gia nhap nhom " + MulticastSender.GROUP_ADDRESS + ":" + MulticastSender.PORT);
            System.out.println("Dang cho nhan tin nhan tu Sender...");

            int receivedCount = 0;
            while (receivedCount < 5) {
                inPacket = new DatagramPacket(BUFFER, BUFFER.length);
                socket.receive(inPacket);
                String msg = new String(BUFFER, 0, inPacket.getLength());
                System.out.println("Nhan tu " + inPacket.getAddress() + " : " + msg);
                receivedCount++;
            }
            System.out.println("Da nhan du 5 goi tin multicast, dung receiver");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
