package com.gpcoder.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EchoClient {

    public final static String SERVER_IP = "127.0.0.1";
    public final static int SERVER_PORT = 7;
    public final static byte[] BUFFER = new byte[4096];

    public static void main(String[] args) {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            System.out.println("Client da khoi dong");

            InetAddress server = InetAddress.getByName(SERVER_IP);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Nhap tin nhan: ");
                String theString = br.readLine();
                if (theString == null) {
                    break;
                }

                byte[] data = theString.getBytes();
                DatagramPacket dp = new DatagramPacket(data, data.length, server, SERVER_PORT);
                ds.send(dp);

                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                ds.receive(incoming);

                String response = new String(incoming.getData(), 0, incoming.getLength());
                System.out.println("Nhan tu server: " + response);

                if ("exit".equalsIgnoreCase(theString.trim()) || "bye".equalsIgnoreCase(theString.trim())) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Loi: " + e.getMessage());
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
}
