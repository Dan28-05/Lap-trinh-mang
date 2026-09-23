package com.gpcoder.net.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Ví dụ minh họa sử dụng HttpURLConnection để gửi HTTP GET request và kiểm tra mã phản hồi (Response Code).
 * Tham khảo: viettuts.vn & gpcoder.com
 */
public class HttpURLConnectionExample {

    public static void main(String[] args) {
        try {
            String targetUrl = "https://jsonplaceholder.typicode.com/posts/1";
            System.out.println("Gửi HTTP GET Request tới: " + targetUrl);

            URL url = URI.create(targetUrl).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Cấu hình Request
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            System.out.println("Mã trạng thái phản hồi (Response Code): " + responseCode + " " + conn.getResponseMessage());
            System.out.println("Kiểu nội dung (Content-Type)         : " + conn.getContentType());

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                in.close();

                System.out.println("\n========== NỘI DUNG PHẢN HỒI (JSON) ==========");
                System.out.println(response.toString().trim());
                System.out.println("================================================");
            } else {
                System.out.println("Yêu cầu không thành công. HTTP Status: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            System.err.println("Lỗi HttpURLConnection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
