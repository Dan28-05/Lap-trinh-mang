package com.gpcoder.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Ví dụ minh họa sử dụng lớp InetAddress để tra cứu thông tin IP máy cục bộ và tên miền DNS.
 * Nguồn tham khảo: https://gpcoder.com/3664-lap-trinh-mang-voi-java/
 */
public class InetAddressExample {

    public static void main(String[] args) {
        try {
            // 1. Lấy thông tin máy tính cục bộ (Localhost)
            System.out.println("========== THÔNG TIN LOCALHOST ==========");
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("Local Host Name : " + localHost.getHostName());
            System.out.println("Local IP Address: " + localHost.getHostAddress());

            // 2. Lấy thông tin IP của một tên miền cụ thể
            System.out.println("\n========== TRA CỨU TÊN MIỀN ==========");
            String hostDomain = "www.google.com";
            InetAddress remoteHost = InetAddress.getByName(hostDomain);
            System.out.println("Host Name       : " + remoteHost.getHostName());
            System.out.println("Canonical Name  : " + remoteHost.getCanonicalHostName());
            System.out.println("IP Address      : " + remoteHost.getHostAddress());

            // 3. Lấy tất cả địa chỉ IP liên kết với tên miền (Load Balancing / Multi-homed)
            System.out.println("\n========== TẤT CẢ ĐỊA CHỈ IP CỦA " + hostDomain + " ==========");
            InetAddress[] allIPs = InetAddress.getAllByName(hostDomain);
            for (int i = 0; i < allIPs.length; i++) {
                System.out.printf("[%d] %s (IPv%d)%n", 
                    (i + 1), 
                    allIPs[i].getHostAddress(), 
                    (allIPs[i].getAddress().length == 4 ? 4 : 6)
                );
            }

            // 4. Tra cứu thêm trang gpcoder.com
            System.out.println("\n========== TRA CỨU GPCoder.COM ==========");
            InetAddress gpCoderIp = InetAddress.getByName("gpcoder.com");
            System.out.println("Host Name  : " + gpCoderIp.getHostName());
            System.out.println("IP Address : " + gpCoderIp.getHostAddress());

        } catch (UnknownHostException e) {
            System.err.println("Không thể phân giải tên miền: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
