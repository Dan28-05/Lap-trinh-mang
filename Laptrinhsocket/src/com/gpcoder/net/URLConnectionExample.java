package com.gpcoder.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionExample {

    public static void main(String[] args) {
        try {
            URL url = URI.create("https://www.w3schools.com/").toURL();
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            System.out.println("Content-Type: " + urlConnection.getContentType());
            System.out.println("Content-Length: " + urlConnection.getContentLength());
            System.out.println("Dang doc noi dung web...\n");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            int count = 0;

            while ((line = br.readLine()) != null && count < 25) {
                sb.append(line).append("\n");
                count++;
            }
            br.close();

            System.out.println("=== KET QUA 25 DONG DAU TIEN ===");
            System.out.println(sb.toString());
            System.out.println("... Doc du lieu thanh cong ...");
        } catch (IOException e) {
            System.err.println("Loi ket noi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
