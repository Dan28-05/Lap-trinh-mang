package com.gpcoder.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Ví dụ minh họa sử dụng URLConnection để đọc dữ liệu trang web qua HTTP/HTTPS.
 * Nguồn tham khảo: https://gpcoder.com/3664-lap-trinh-mang-voi-java/
 */
public class URLConnectionExample {

    public static void main(String[] args) {
        try {
            // URL trang web cần kết nối
            URL url = URI.create("https://www.w3schools.com/").toURL();
            URLConnection urlConnection = url.openConnection();

            // Thiết lập User-Agent để mô phỏng trình duyệt, tránh bị chặn bởi Cloudflare/Bot filter
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            System.out.println("Kiểu nội dung (Content-Type): " + urlConnection.getContentType());
            System.out.println("Độ dài nội dung (Content-Length): " + urlConnection.getContentLength());
            System.out.println("Đang đọc nội dung trang web...\n");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            int lineCount = 0;

            // Đọc 25 dòng đầu tiên để hiển thị minh họa trực quan
            while ((line = br.readLine()) != null && lineCount < 25) {
                sb.append(line).append("\n");
                lineCount++;
            }
            br.close();

            System.out.println("=== KẾT QUẢ NỘI DUNG (25 DÒNG ĐẦU TIÊN) ===");
            System.out.println(sb.toString());
            System.out.println("... [Đã đọc thành công dữ liệu từ URLConnection] ...");
        } catch (IOException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
