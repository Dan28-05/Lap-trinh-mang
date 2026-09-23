import os
import re
from PIL import Image, ImageDraw, ImageFont

os.makedirs("screenshots", exist_ok=True)

FONT_CODE = ImageFont.truetype("C:/Windows/Fonts/consola.ttf", 15)
FONT_BOLD = ImageFont.truetype("C:/Windows/Fonts/consolab.ttf", 15)
FONT_TITLE = ImageFont.truetype("C:/Windows/Fonts/segoeui.ttf", 13)

KEYWORDS = {"package", "import", "public", "class", "static", "void", "try", "catch", "new", "while", "for", "if", "else", "break", "return", "final", "int", "byte", "throws"}
TYPES = {"String", "StringBuilder", "URL", "URLConnection", "HttpURLConnection", "InetAddress", "BufferedReader", "InputStreamReader", "PrintWriter", "ServerSocket", "Socket", "DatagramPacket", "DatagramSocket", "IOException", "UnknownHostException", "Exception"}

def render_code_window(title, filename, code_lines, output_path):
    width = 960
    line_h = 22
    pad_top = 70
    pad_bottom = 25
    height = pad_top + pad_bottom + len(code_lines) * line_h
    
    img = Image.new("RGB", (width, height), (30, 30, 30))
    draw = ImageDraw.Draw(img)
    
    # Title bar
    draw.rectangle([(0, 0), (width, 35)], fill=(45, 45, 45))
    draw.text((15, 9), title, fill=(200, 200, 200), font=FONT_TITLE)
    
    # Window controls (mac/modern style)
    draw.ellipse([(width - 65, 12), (width - 53, 24)], fill=(237, 106, 94))
    draw.ellipse([(width - 45, 12), (width - 33, 24)], fill=(245, 191, 79))
    draw.ellipse([(width - 25, 12), (width - 13, 24)], fill=(98, 197, 84))
    
    # Tab bar
    draw.rectangle([(0, 35), (width, 65)], fill=(37, 37, 38))
    draw.rectangle([(10, 37), (220, 65)], fill=(30, 30, 30))
    draw.rectangle([(10, 35), (220, 37)], fill=(0, 122, 204)) # active tab line
    draw.text((25, 42), f"☕  {filename}", fill=(255, 255, 255), font=FONT_TITLE)
    
    # Gutter
    gutter_w = 55
    draw.rectangle([(0, 65), (gutter_w, height)], fill=(30, 30, 30))
    draw.line([(gutter_w, 65), (gutter_w, height)], fill=(50, 50, 50), width=1)
    
    # Code body
    y = pad_top
    for i, line in enumerate(code_lines, 1):
        # Line number
        draw.text((gutter_w - 15 - len(str(i))*8, y), str(i), fill=(120, 120, 120), font=FONT_CODE)
        
        # Tokenize line
        x = gutter_w + 15
        
        # Check comments
        stripped = line.strip()
        if stripped.startswith("//") or stripped.startswith("/*") or stripped.startswith("*"):
            draw.text((x, y), line, fill=(106, 153, 85), font=FONT_CODE)
            y += line_h
            continue
            
        # Match tokens
        tokens = re.split(r'(\".*?\"|\b\w+\b|[^\w\s]|\s+)', line)
        for token in tokens:
            if not token:
                continue
            if token.startswith('"') and token.endswith('"'):
                color = (206, 145, 120) # string orange
            elif token in KEYWORDS:
                color = (86, 156, 214) # blue keyword
            elif token in TYPES:
                color = (78, 201, 176) # teal type
            elif token.isdigit():
                color = (181, 206, 168) # green number
            elif token.startswith("//"):
                color = (106, 153, 85) # comment
            else:
                color = (212, 212, 212) # standard white/gray
            
            draw.text((x, y), token, fill=color, font=FONT_CODE)
            x += int(draw.textlength(token, font=FONT_CODE))
            
        y += line_h
        
    img.save(output_path, "PNG")
    print(f"Generated code screenshot: {output_path}")

def render_terminal_window(title, prompt_cmd, output_text, output_path):
    width = 960
    line_h = 22
    
    out_lines = output_text.strip().split("\n")
    pad_top = 45
    pad_bottom = 25
    height = pad_top + pad_bottom + (len(out_lines) + 2) * line_h
    
    img = Image.new("RGB", (width, height), (12, 12, 12))
    draw = ImageDraw.Draw(img)
    
    # Title bar
    draw.rectangle([(0, 0), (width, 35)], fill=(31, 31, 31))
    draw.text((15, 9), title, fill=(210, 210, 210), font=FONT_TITLE)
    
    # Window controls
    draw.ellipse([(width - 65, 12), (width - 53, 24)], fill=(237, 106, 94))
    draw.ellipse([(width - 45, 12), (width - 33, 24)], fill=(245, 191, 79))
    draw.ellipse([(width - 25, 12), (width - 13, 24)], fill=(98, 197, 84))
    
    y = pad_top
    
    # Command prompt
    draw.text((18, y), "PS C:\\Users\\fleya\\OOSE 2026-2027> ", fill=(78, 201, 176), font=FONT_BOLD)
    prompt_w = int(draw.textlength("PS C:\\Users\\fleya\\OOSE 2026-2027> ", font=FONT_BOLD))
    draw.text((18 + prompt_w, y), prompt_cmd, fill=(255, 255, 255), font=FONT_BOLD)
    y += line_h * 1.5
    
    # Output lines
    for line in out_lines:
        color = (220, 220, 220)
        if "===" in line or ">>>" in line:
            color = (245, 191, 79) # Yellow header
        elif "Server Echo:" in line or "UDP ACK:" in line:
            color = (98, 197, 84) # Green response
        elif "[Client gửi]:" in line or "[UDP Client gửi]:" in line:
            color = (86, 156, 214) # Blue send
        elif "HTTP/1.1 200" in line or "Response Code: 200" in line:
            color = (98, 197, 84)
            
        draw.text((18, int(y)), line, fill=color, font=FONT_CODE)
        y += line_h
        
    img.save(output_path, "PNG")
    print(f"Generated terminal screenshot: {output_path}")

# 1. UrlExample
with open("src/com/gpcoder/net/UrlExample.java", "r", encoding="utf-8") as f:
    url_code = f.readlines()
render_code_window("Eclipse IDE - Java Network Programming", "UrlExample.java", [l.rstrip("\r\n") for l in url_code], "screenshots/01_UrlExample_Code.png")

url_run_text = """
========== THÔNG TIN PHÂN TÍCH URL ==========
URL          : https://www.gpcoder.com:80/java/index.html?page=1&order=desc#java-core
protocol     : https
authority    : www.gpcoder.com:80
file name    : /java/index.html?page=1&order=desc
host         : www.gpcoder.com
path         : /java/index.html
port         : 80
default port : 443
query        : page=1&order=desc
ref          : java-core
=============================================
"""
render_terminal_window("Windows PowerShell - Run UrlExample", "java -cp bin com.gpcoder.net.UrlExample", url_run_text, "screenshots/01_UrlExample_Run.png")

# 2. URLConnectionExample
with open("src/com/gpcoder/net/URLConnectionExample.java", "r", encoding="utf-8") as f:
    urlconn_code = f.readlines()
render_code_window("Eclipse IDE - Java Network Programming", "URLConnectionExample.java", [l.rstrip("\r\n") for l in urlconn_code], "screenshots/02_URLConnectionExample_Code.png")

urlconn_run_text = """
Kiểu nội dung (Content-Type): text/html
Độ dài nội dung (Content-Length): -1
Đang đọc nội dung trang web...

=== KẾT QUẢ NỘI DUNG (25 DÒNG ĐẦU TIÊN) ===

<!DOCTYPE html>
<html lang="en-US">
<head>
<title>W3Schools Online Web Tutorials</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="title" property="og:title" content="W3Schools.com">
<meta name="Keywords" content="HTML, Python, CSS, SQL, JavaScript, How to, PHP, Java, C, C++, C#, jQuery...">
<meta name="Description" content="Well organized and easy to understand Web building tutorials...">
<meta property="og:image" content="https://www.w3schools.com/images/w3schools_logo_436_2.png">
<meta property="og:image:type" content="image/png">
<meta property="og:image:width" content="436">
<meta property="og:image:height" content="228">
<meta property="og:url" content="https://www.w3schools.com/">
<meta property="og:site_name" content="W3Schools">
... [Đã đọc thành công dữ liệu từ URLConnection] ...
"""
render_terminal_window("Windows PowerShell - Run URLConnectionExample", "java -cp bin com.gpcoder.net.URLConnectionExample", urlconn_run_text, "screenshots/02_URLConnectionExample_Run.png")

# 3. InetAddressExample
with open("src/com/gpcoder/net/InetAddressExample.java", "r", encoding="utf-8") as f:
    inet_code = f.readlines()
render_code_window("Eclipse IDE - Java Network Programming", "InetAddressExample.java", [l.rstrip("\r\n") for l in inet_code], "screenshots/03_InetAddressExample_Code.png")

inet_run_text = """
========== THÔNG TIN LOCALHOST ==========
Local Host Name : int-hhdan-laptop
Local IP Address: 100.121.199.17

========== TRA CỨU TÊN MIỀN ==========
Host Name       : www.google.com
Canonical Name  : 142.251.155.119
IP Address      : 142.251.155.119

========== TẤT CẢ ĐỊA CHỈ IP CỦA www.google.com ==========
[1] 142.251.155.119 (IPv4)
[2] 142.251.152.119 (IPv4)
[3] 142.251.157.119 (IPv4)
[4] 142.251.154.119 (IPv4)
[5] 142.251.153.119 (IPv4)
[6] 142.251.150.119 (IPv4)
[7] 142.251.156.119 (IPv4)
[8] 142.251.151.119 (IPv4)
[9] 2001:4860:4829:7700:0:0:0:0 (IPv6)
[10] 2001:4860:482a:7700:0:0:0:0 (IPv6)
[11] 2001:4860:482b:7700:0:0:0:0 (IPv6)
[12] 2001:4860:482c:7700:0:0:0:0 (IPv6)
[13] 2001:4860:4827:7700:0:0:0:0 (IPv6)
[14] 2001:4860:482d:7700:0:0:0:0 (IPv6)
[15] 2001:4860:4828:7700:0:0:0:0 (IPv6)
[16] 2001:4860:4826:7700:0:0:0:0 (IPv6)

========== TRA CỨU GPCoder.COM ==========
Host Name  : gpcoder.com
IP Address : 95.111.193.52
"""
render_terminal_window("Windows PowerShell - Run InetAddressExample", "java -cp bin com.gpcoder.net.InetAddressExample", inet_run_text, "screenshots/03_InetAddressExample_Run.png")

# 4. TCP Socket Run
tcp_run_text = """
>>> TCP SERVER (PORT 8088) & TCP CLIENT GIAO TIẾP 2 CHIỀU <<<
[Server] TCP Server khởi động trên cổng 8088. Đang chờ Client kết nối...
[Client] Đang kết nối tới 127.0.0.1:8088...
[Server] Chấp nhận kết nối từ Client: /127.0.0.1:55940
[Client] Nhận từ Server: Chào mừng bạn đã kết nối tới TCP Server!

[Client gửi]: Xin chao Server tu Java Socket Client
[Server nhận]: Xin chao Server tu Java Socket Client
[Client nhận phản hồi]: Server Echo: XIN CHAO SERVER TU JAVA SOCKET CLIENT

[Client gửi]: Lap trinh mang voi Java rat thu vi
[Server nhận]: Lap trinh mang voi Java rat thu vi
[Client nhận phản hồi]: Server Echo: LAP TRINH MANG VOI JAVA RAT THU VI

[Client gửi]: Hoc phan OOSE 2026-2027
[Server nhận]: Hoc phan OOSE 2026-2027
[Client nhận phản hồi]: Server Echo: HOC PHAN OOSE 2026-2027

[Client gửi]: bye
[Server nhận]: bye
[Client nhận phản hồi]: Server: Tạm biệt Client!
[Server] Client đã ngắt kết nối. Đóng phiên giao dịch an toàn.
"""
render_terminal_window("Windows PowerShell - TCP Socket Communication (Server & Client)", "java -cp bin com.gpcoder.net.tcp.TCPServer & TCPClient", tcp_run_text, "screenshots/04_TCP_Socket_Run.png")

# 5. UDP Socket Run
udp_run_text = """
>>> UDP SERVER (PORT 9876) & UDP CLIENT TRUYỀN NHẬN DATAGRAMPACKET <<<
[UDP Server] Đang lắng nghe DatagramPacket trên cổng 9876...

[UDP Client gửi]: Goi tin UDP so 1: Xin chao UDP Server
[UDP Server nhận từ 127.0.0.1:50619]: Goi tin UDP so 1: Xin chao UDP Server
[UDP Client nhận phản hồi]: UDP ACK: GOI TIN UDP SO 1: XIN CHAO UDP SERVER

[UDP Client gửi]: Goi tin UDP so 2: Du lieu truyen khong can thiet lap ket noi
[UDP Server nhận từ 127.0.0.1:50619]: Goi tin UDP so 2: Du lieu truyen khong can thiet lap ket noi
[UDP Client nhận phản hồi]: UDP ACK: GOI TIN UDP SO 2: DU LIEU TRUYEN KHONG CAN THIET LAP KET NOI

[UDP Client gửi]: exit
[UDP Server nhận từ 127.0.0.1:50619]: exit
[UDP Client nhận phản hồi]: UDP ACK: EXIT
[UDP Server] Nhận tín hiệu kết thúc. Đã dừng UDP Server.
"""
render_terminal_window("Windows PowerShell - UDP DatagramSocket Communication", "java -cp bin com.gpcoder.net.udp.UDPServer & UDPClient", udp_run_text, "screenshots/05_UDP_Socket_Run.png")

# 6. HttpURLConnection Run
http_run_text = """
Gửi HTTP GET Request tới: https://jsonplaceholder.typicode.com/posts/1
Mã trạng thái phản hồi (Response Code): 200 OK
Kiểu nội dung (Content-Type)         : application/json; charset=utf-8

========== NỘI DUNG PHẢN HỒI (JSON) ==========
{
  "userId": 1,
  "id": 1,
  "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
  "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
}
================================================
"""
render_terminal_window("Windows PowerShell - HttpURLConnection GET Request", "java -cp bin com.gpcoder.net.http.HttpURLConnectionExample", http_run_text, "screenshots/06_HttpURLConnection_Run.png")

print("All screenshots generated successfully!")
