package com.gpcoder.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressExample {

    public static void main(String[] args) {
        try {
            System.out.println("=== THONG TIN LOCALHOST ===");
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("Ten may local : " + localHost.getHostName());
            System.out.println("Dia chi IP    : " + localHost.getHostAddress());

            System.out.println("\n=== TRA CUU TEN MIEN ===");
            String hostDomain = "www.google.com";
            InetAddress remoteHost = InetAddress.getByName(hostDomain);
            System.out.println("Ten host       : " + remoteHost.getHostName());
            System.out.println("Canonical Host : " + remoteHost.getCanonicalHostName());
            System.out.println("Dia chi IP     : " + remoteHost.getHostAddress());

            System.out.println("\n=== DANH SACH IP CUA " + hostDomain + " ===");
            InetAddress[] allIPs = InetAddress.getAllByName(hostDomain);
            for (int i = 0; i < allIPs.length; i++) {
                System.out.println("[" + (i + 1) + "] " + allIPs[i].getHostAddress());
            }

            System.out.println("\n=== TRA CUU GPCODER.COM ===");
            InetAddress gpCoderIp = InetAddress.getByName("gpcoder.com");
            System.out.println("Host Name : " + gpCoderIp.getHostName());
            System.out.println("IP        : " + gpCoderIp.getHostAddress());

        } catch (UnknownHostException e) {
            System.err.println("Khong tim thay host: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
