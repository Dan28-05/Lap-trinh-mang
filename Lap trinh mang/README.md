# BAO CAO TIM HIEU VA THUC HANH LAP TRINH MANG VOI JAVA
> Hoc phan: Phat trien phan mem huong doi tuong (OOSE 2026-2027)  
> Tai lieu tham khao chinh: [GP Coder - Lap trinh mang voi Java](https://gpcoder.com/3664-lap-trinh-mang-voi-java/)  
> Tai lieu tham khao mo rong: [VietTuts - Lap trinh mang voi Java](https://viettuts.vn/lap-trinh-mang-voi-java)

---

## MUC LUC
1. [Tong quan ly thuyet Lap trinh mang trong Java](#1-tong-quan-ly-thuyet-lap-trinh-mang-trong-java)
2. [Cau truc ma nguon du an](#2-cau-truc-ma-nguon-du-an)
3. [Chi tiet cac bai thuc hanh](#3-chi-tiet-cac-bai-thuc-hanh)
4. [Huong dan bien dich va chay chuong trinh](#4-huong-dan-bien-dich-va-chay-chuong-trinh)
5. [Thu muc Screenshots minh chung](#5-thu-muc-screenshots-minh-chung)

---

## 1. Tong quan ly thuyet Lap trinh mang trong Java

Lap trinh mang la viec xay dung cac ung dung co kha nang truyen nhan du lieu giua cac may tinh thong qua moi truong mang (LAN, Internet,...).

### Cac khai niem can nam:
- **Socket**: Giao dien lap trinh ung dung (API), la diem cuoi (endpoint) cua kenh truyen thong hai chieu giua hai chuong trinh tren mang.
- **Port Number**: So hieu cong (0 - 65535) de dinh danh ung dung/tien trinh tren mot may tinh.
- **IP Address**: Dia chi logic dinh danh mot thiet bi trong mang TCP/IP (IPv4 va IPv6).
- **MAC Address**: Dia chi vat ly duy nhat gan voi card mang (NIC).
- **Protocol**: Bo quy tac truyen thong ma cac ben phai tuan thu (TCP, UDP, HTTP, FTP,...).

### So sanh TCP va UDP:

| Dac diem | TCP | UDP |
| :--- | :--- | :--- |
| **Co che** | Huong ket noi (Connection-oriented) | Phi ket noi (Connectionless) |
| **Do tin cay** | Cao, dam bao dung thu tu va khong mat du lieu | Khong dam bao, co the mat goi tin |
| **Toc do** | Cham hon do phai kiem tra bat tay | Rat nhanh, chi phi thap |
| **Lop Java** | `ServerSocket`, `Socket` | `DatagramSocket`, `DatagramPacket` |
| **Ung dung** | Web (HTTP), Mail (SMTP), File (FTP) | Streaming video, Game online, DNS |

---

## 2. Cau truc ma nguon du an

```text
OOSE 2026-2027/
│── src/
│   └── com/
│       └── gpcoder/
│           └── net/
│               ├── UrlExample.java                     # Phan tich URL
│               ├── URLConnectionExample.java            # Doc Web qua URLConnection
│               ├── InetAddressExample.java              # Tra cuu IP & DNS
│               ├── tcp/
│               │   ├── TCPServer.java                   # TCP Server Socket
│               │   └── TCPClient.java                   # TCP Client Socket
│               ├── udp/
│               │   ├── UDPServer.java                   # UDP Datagram Server
│               │   └── UDPClient.java                   # UDP Datagram Client
│               └── http/
│                   └── HttpURLConnectionExample.java   # HTTP REST request
│── screenshots/                                         # Anh chup man hinh code va chay
│   ├── 01_UrlExample_Code.png
│   ├── 01_UrlExample_Run.png
│   ├── 02_URLConnectionExample_Code.png
│   ├── 02_URLConnectionExample_Run.png
│   ├── 03_InetAddressExample_Code.png
│   ├── 03_InetAddressExample_Run.png
│   ├── 04_TCP_Socket_Run.png
│   ├── 05_UDP_Socket_Run.png
│   └── 06_HttpURLConnection_Run.png
│── generate_screenshots.py
│── .gitignore
└── README.md
```

---

## 3. Chi tiet cac bai thuc hanh

- **UrlExample.java**: Su dung `java.net.URL` tach cac thanh phan: Protocol, Authority, Host, Port, Path, File, Query, Ref.
- **URLConnectionExample.java**: Ket noi HTTP toi trang web, thiet lap User-Agent, doc du lieu HTML qua `BufferedReader`.
- **InetAddressExample.java**: Tra cuu ten may va IP local, tra cuu phan giai DNS IPv4/IPv6 cho `google.com` va `gpcoder.com`.
- **TCPServer & TCPClient**: Giao tiep 2 chieu su dung TCP Socket tren cong 8088. Client gui thong diep, Server nhan va phan hoi Echo chu hoa.
- **UDPServer & UDPClient**: Truyen nhan goi tin DatagramPacket tren cong 9876 qua UDP phi ket noi.
- **HttpURLConnectionExample**: Gui HTTP GET request toi REST API `jsonplaceholder`, kiem tra ma trang thai 200 OK va doc du lieu JSON.

---

## 4. Huong dan bien dich va chay chuong trinh

### Bien dich toan bo:
```powershell
javac -d bin -sourcepath src src/com/gpcoder/net/*.java src/com/gpcoder/net/tcp/*.java src/com/gpcoder/net/udp/*.java src/com/gpcoder/net/http/*.java
```

### Chay tung bai:
- **UrlExample**:
  ```powershell
  java -cp bin com.gpcoder.net.UrlExample
  ```
- **URLConnectionExample**:
  ```powershell
  java -cp bin com.gpcoder.net.URLConnectionExample
  ```
- **InetAddressExample**:
  ```powershell
  java -cp bin com.gpcoder.net.InetAddressExample
  ```
- **TCP Socket**:
  - Terminal 1: `java -cp bin com.gpcoder.net.tcp.TCPServer`
  - Terminal 2: `java -cp bin com.gpcoder.net.tcp.TCPClient`
- **UDP Socket**:
  - Terminal 1: `java -cp bin com.gpcoder.net.udp.UDPServer`
  - Terminal 2: `java -cp bin com.gpcoder.net.udp.UDPClient`
- **HttpURLConnectionExample**:
  ```powershell
  java -cp bin com.gpcoder.net.http.HttpURLConnectionExample
  ```

---

## 5. Thu muc Screenshots minh chung

| Ten File | Mo ta noi dung |
| :--- | :--- |
| `01_UrlExample_Code.png` | Code UrlExample.java |
| `01_UrlExample_Run.png` | Ket qua chay UrlExample |
| `02_URLConnectionExample_Code.png` | Code URLConnectionExample.java |
| `02_URLConnectionExample_Run.png` | Ket qua chay URLConnectionExample |
| `03_InetAddressExample_Code.png` | Code InetAddressExample.java |
| `03_InetAddressExample_Run.png` | Ket qua chay InetAddressExample |
| `04_TCP_Socket_Run.png` | Man hinh giao tiep TCP Server & Client |
| `05_UDP_Socket_Run.png` | Man hinh truyen nhan UDP Datagram |
| `06_HttpURLConnection_Run.png` | Man hinh goi HTTP GET Request |
