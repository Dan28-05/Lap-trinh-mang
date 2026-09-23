package com.gpcoder.net.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpURLConnectionExample {

    public static void main(String[] args) {
        try {
            String targetUrl = "https://jsonplaceholder.typicode.com/posts/1";
            System.out.println("Gui HTTP GET Request toi: " + targetUrl);

            URL url = URI.create(targetUrl).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode + " " + conn.getResponseMessage());
            System.out.println("Content-Type : " + conn.getContentType());

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                in.close();

                System.out.println("\n=== NOI DUNG PHAN HOI (JSON) ===");
                System.out.println(response.toString().trim());
                System.out.println("================================");
            } else {
                System.out.println("Yeu cau that bai, code: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
