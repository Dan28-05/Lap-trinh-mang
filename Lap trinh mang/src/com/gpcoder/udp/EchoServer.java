package com.gpcoder.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class EchoServer {

    public final static int SERVER_PORT = 7;
    public final static byte[] BUFFER = new byte[4096];

    public static void main(String[] args) {
        DatagramSocket ds = null;
        try {
            System.out.println("Dang lang nghe tren cong " + SERVER_PORT + ", vui long cho...");
            ds = new DatagramSocket(SERVER_PORT);
            System.out.println("Server da khoi dong");
            System.out.println("Dang cho tin nhan tu Client...");

            while (true) {
                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                ds.receive(incoming);

                String message = new String(incoming.getData(), 0, incoming.getLength());
                System.out.println("Server nhan: " + message);

                DatagramPacket outsending = new DatagramPacket(
                        message.getBytes(), 
                        incoming.getLength(),
                        incoming.getAddress(), 
                        incoming.getPort()
                );
                ds.send(outsending);

                if ("exit".equalsIgnoreCase(message.trim()) || "bye".equalsIgnoreCase(message.trim())) {
                    System.out.println("Nhan tin hieu thoat tu client");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
}
