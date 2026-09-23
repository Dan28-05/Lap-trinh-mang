package com.gpcoder.net;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Ví dụ minh họa phân tích các thành phần của một URL trong Java.
 * Nguồn tham khảo: https://gpcoder.com/3664-lap-trinh-mang-voi-java/
 */
public class UrlExample {

    public static void main(String[] args) {
        try {
            // Khởi tạo đối tượng URL (sử dụng URI.create().toURL() theo chuẩn Java hiện đại)
            String urlString = "https://www.gpcoder.com:80/java/index.html?page=1&order=desc#java-core";
            URL url = URI.create(urlString).toURL();

            System.out.println("========== THÔNG TIN PHÂN TÍCH URL ==========");
            System.out.println("URL          : " + url.toString());
            System.out.println("protocol     : " + url.getProtocol());
            System.out.println("authority    : " + url.getAuthority());
            System.out.println("file name    : " + url.getFile());
            System.out.println("host         : " + url.getHost());
            System.out.println("path         : " + url.getPath());
            System.out.println("port         : " + url.getPort());
            System.out.println("default port : " + url.getDefaultPort());
            System.out.println("query        : " + url.getQuery());
            System.out.println("ref          : " + url.getRef());
            System.out.println("=============================================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
