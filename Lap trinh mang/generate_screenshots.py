import os
import re
from PIL import Image, ImageDraw, ImageFont

os.makedirs("screenshots", exist_ok=True)

FONT_CODE = ImageFont.truetype("C:/Windows/Fonts/consola.ttf", 15)
FONT_BOLD = ImageFont.truetype("C:/Windows/Fonts/consolab.ttf", 15)
FONT_TITLE = ImageFont.truetype("C:/Windows/Fonts/segoeui.ttf", 13)

KEYWORDS = {"package", "import", "public", "class", "static", "void", "try", "catch", "new", "while", "for", "if", "else", "break", "return", "final", "int", "byte", "throws"}
TYPES = {"String", "StringBuilder", "URL", "URLConnection", "InetAddress", "BufferedReader", "InputStreamReader", "IOException", "UnknownHostException", "Exception"}

def render_code_window(title, filename, code_lines, output_path):
    width = 960
    line_h = 22
    pad_top = 70
    pad_bottom = 25
    height = pad_top + pad_bottom + len(code_lines) * line_h
    
    img = Image.new("RGB", (width, height), (30, 30, 30))
    draw = ImageDraw.Draw(img)
    
    draw.rectangle([(0, 0), (width, 35)], fill=(45, 45, 45))
    draw.text((15, 9), title, fill=(200, 200, 200), font=FONT_TITLE)
    
    draw.ellipse([(width - 65, 12), (width - 53, 24)], fill=(237, 106, 94))
    draw.ellipse([(width - 45, 12), (width - 33, 24)], fill=(245, 191, 79))
    draw.ellipse([(width - 25, 12), (width - 13, 24)], fill=(98, 197, 84))
    
    draw.rectangle([(0, 35), (width, 65)], fill=(37, 37, 38))
    draw.rectangle([(10, 37), (220, 65)], fill=(30, 30, 30))
    draw.rectangle([(10, 35), (220, 37)], fill=(0, 122, 204))
    draw.text((25, 42), f"☕  {filename}", fill=(255, 255, 255), font=FONT_TITLE)
    
    gutter_w = 55
    draw.rectangle([(0, 65), (gutter_w, height)], fill=(30, 30, 30))
    draw.line([(gutter_w, 65), (gutter_w, height)], fill=(50, 50, 50), width=1)
    
    y = pad_top
    for i, line in enumerate(code_lines, 1):
        draw.text((gutter_w - 15 - len(str(i))*8, y), str(i), fill=(120, 120, 120), font=FONT_CODE)
        x = gutter_w + 15
        
        tokens = re.split(r'(\".*?\"|\b\w+\b|[^\w\s]|\s+)', line)
        for token in tokens:
            if not token:
                continue
            if token.startswith('"') and token.endswith('"'):
                color = (206, 145, 120)
            elif token in KEYWORDS:
                color = (86, 156, 214)
            elif token in TYPES:
                color = (78, 201, 176)
            elif token.isdigit():
                color = (181, 206, 168)
            else:
                color = (212, 212, 212)
            
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
    
    draw.rectangle([(0, 0), (width, 35)], fill=(31, 31, 31))
    draw.text((15, 9), title, fill=(210, 210, 210), font=FONT_TITLE)
    
    draw.ellipse([(width - 65, 12), (width - 53, 24)], fill=(237, 106, 94))
    draw.ellipse([(width - 45, 12), (width - 33, 24)], fill=(245, 191, 79))
    draw.ellipse([(width - 25, 12), (width - 13, 24)], fill=(98, 197, 84))
    
    y = pad_top
    draw.text((18, y), "PS C:\\Users\\fleya\\Lap trinh mang> ", fill=(78, 201, 176), font=FONT_BOLD)
    prompt_w = int(draw.textlength("PS C:\\Users\\fleya\\Lap trinh mang> ", font=FONT_BOLD))
    draw.text((18 + prompt_w, y), prompt_cmd, fill=(255, 255, 255), font=FONT_BOLD)
    y += line_h * 1.5
    
    for line in out_lines:
        color = (220, 220, 220)
        if "===" in line or ">>>" in line:
            color = (245, 191, 79)
        draw.text((18, int(y)), line, fill=color, font=FONT_CODE)
        y += line_h
        
    img.save(output_path, "PNG")
    print(f"Generated terminal screenshot: {output_path}")

# 1. UrlExample
with open("src/com/gpcoder/net/UrlExample.java", "r", encoding="utf-8") as f:
    url_code = f.readlines()
render_code_window("Eclipse IDE - Java Network Programming", "UrlExample.java", [l.rstrip("\r\n") for l in url_code], "screenshots/01_UrlExample_Code.png")

url_run_text = """
=== THONG TIN URL ===
URL          : https://www.gpcoder.com:80/java/index.html?page=1&order=desc#java-core
Protocol     : https
Authority    : www.gpcoder.com:80
File name    : /java/index.html?page=1&order=desc
Host         : www.gpcoder.com
Path         : /java/index.html
Port         : 80
Default port : 443
Query        : page=1&order=desc
Ref          : java-core
"""
render_terminal_window("Windows PowerShell - Run UrlExample", "java -cp bin com.gpcoder.net.UrlExample", url_run_text, "screenshots/01_UrlExample_Run.png")

# 2. URLConnectionExample
with open("src/com/gpcoder/net/URLConnectionExample.java", "r", encoding="utf-8") as f:
    urlconn_code = f.readlines()
render_code_window("Eclipse IDE - Java Network Programming", "URLConnectionExample.java", [l.rstrip("\r\n") for l in urlconn_code], "screenshots/02_URLConnectionExample_Code.png")

urlconn_run_text = """
Content-Type: text/html
Content-Length: -1
Dang doc noi dung web...

=== KET QUA 25 DONG DAU TIEN ===

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
... Doc du lieu thanh cong ...
"""
render_terminal_window("Windows PowerShell - Run URLConnectionExample", "java -cp bin com.gpcoder.net.URLConnectionExample", urlconn_run_text, "screenshots/02_URLConnectionExample_Run.png")

# 3. InetAddressExample
with open("src/com/gpcoder/net/InetAddressExample.java", "r", encoding="utf-8") as f:
    inet_code = f.readlines()
render_code_window("Eclipse IDE - Java Network Programming", "InetAddressExample.java", [l.rstrip("\r\n") for l in inet_code], "screenshots/03_InetAddressExample_Code.png")

inet_run_text = """
=== THONG TIN LOCALHOST ===
Ten may local : int-hhdan-laptop
Dia chi IP    : 100.121.199.17

=== TRA CUU TEN MIEN ===
Ten host       : www.google.com
Canonical Host : 142.251.150.119
Dia chi IP     : 142.251.150.119

=== DANH SACH IP CUA www.google.com ===
[1] 142.251.150.119
[2] 142.251.157.119
[3] 142.251.151.119
[4] 142.251.156.119
[5] 142.251.155.119
[6] 142.251.154.119
[7] 142.251.153.119
[8] 142.251.152.119
[9] 2001:4860:4827:7700:0:0:0:0
[10] 2001:4860:482c:7700:0:0:0:0
[11] 2001:4860:482b:7700:0:0:0:0
[12] 2001:4860:482a:7700:0:0:0:0

=== TRA CUU GPCODER.COM ===
Host Name : gpcoder.com
IP        : 95.111.193.52
"""
render_terminal_window("Windows PowerShell - Run InetAddressExample", "java -cp bin com.gpcoder.net.InetAddressExample", inet_run_text, "screenshots/03_InetAddressExample_Run.png")

print("All screenshots updated!")
