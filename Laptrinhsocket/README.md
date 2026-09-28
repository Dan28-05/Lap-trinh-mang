# BÁO CÁO TÌM HIỂU & THỰC HÀNH LẬP TRÌNH CLIENT - SERVER BẰNG SOCKET TRONG JAVA

> **Học phần**: Phát triển phần mềm hướng đối tượng (OOSE 2026-2027)  
> **Chủ đề**: Lập trình mạng - Kiến trúc Client-Server sử dụng Socket (TCP, UDP, Multicast)  
> **Tài liệu tham khảo chính**:  
> 1. [GP Coder - Lập trình mạng với Java](https://gpcoder.com/3664-lap-trinh-mang-voi-java/)  
> 2. [GP Coder - Xây dựng ứng dụng Client-Server với Socket trong Java](https://gpcoder.com/3679-xay-dung-ung-dung-client-server-voi-socket-trong-java/)  
> **GitHub Repository**: [https://github.com/Dan28-05/Lap-trinh-mang.git](https://github.com/Dan28-05/Lap-trinh-mang.git)

---

## MỤC LỤC
1. [Tổng quan lý thuyết Socket & Kiến trúc Client-Server](#1-tổng-quan-lý-thuyết-socket--kiến-trúc-client-server)
   - [Khái niệm Socket](#11-khái-niệm-socket)
   - [Mô hình Client-Server ở chế độ có nối kết (TCP)](#12-mô-hình-client-server-ở-chế-độ-có-nối-kết-tcp)
   - [Mô hình Client-Server ở chế độ không nối kết (UDP)](#13-mô-hình-client-server-ở-chế-độ-không-nối-kết-udp)
   - [Mô hình truyền thông theo nhóm (Multicast Socket)](#14-mô-hình-truyền-thông-theo-nhóm-multicast-socket)
   - [Kiến trúc Server: Tuần tự (Single-thread) vs Song song (Multi-thread)](#15-kiến-trúc-server-tuần-tự-single-thread-vs-song-song-multi-thread)
2. [Cấu trúc mã nguồn dự án](#2-cấu-trúc-mã-nguồn-dự-án)
3. [Phân tích chi tiết các bài thực hành](#3-phân-tích-chi-tiết-các-bài-thực-hành)
   - [Chủ đề 1: Giao tiếp TCP Socket (Echo Chat)](#chủ-đề-1-giao-tiếp-tcp-socket-echo-chat)
   - [Chủ đề 2: Giao tiếp TCP Server Đa luồng (Multi-Thread Server)](#chủ-đề-2-giao-tiếp-tcp-server-đa-luồng-multi-thread-server)
   - [Chủ đề 3: Giao tiếp UDP Datagram Socket](#chủ-đề-3-giao-tiếp-udp-datagram-socket)
   - [Chủ đề 4: Giao tiếp Multicast Socket](#chủ-đề-4-giao-tiếp-multicast-socket)
   - [Chủ đề 5: Các lớp mạng cơ bản gói java.net](#chủ-đề-5-các-lớp-mạng-cơ-bản-gói-javanet)
4. [Hướng dẫn biên dịch và chạy chương trình](#4-hướng-dẫn-biên-dịch-và-chạy-chương-trình)
5. [Thư mục Screenshots minh chứng](#5-thư-mục-screenshots-minh-chứng)

---

## 1. Tổng quan lý thuyết Socket & Kiến trúc Client-Server

### 1.1. Khái niệm Socket
- **Socket** là một điểm cuối (endpoint) của một kênh truyền thông hai chiều giữa hai chương trình đang chạy trên mạng.
- Socket cung cấp giao diện lập trình ứng dụng (API) cho phép tầng ứng dụng (Application Layer) truy xuất đến các dịch vụ của tầng vận chuyển (Transport Layer) như TCP hoặc UDP.
- Một Socket được định danh duy nhất trên mạng bởi sự kết hợp của: **Địa chỉ IP** (xác định máy tính) và **Số hiệu cổng - Port** (xác định tiến trình cụ thể trong máy tính, dải từ `0` đến `65535`).

### 1.2. Mô hình Client-Server ở chế độ có nối kết (TCP)
TCP (Transmission Control Protocol) là giao thức hướng kết nối (connection-oriented), đảm bảo dữ liệu truyền đi đến nơi đầy đủ, chính xác, không mất gói và đúng thứ tự thông qua cơ chế bắt tay 3 bước (3-way handshake).

Quy trình giao tiếp gồm 4 giai đoạn chính:
1. **Giai đoạn 1 (Server khởi tạo)**: Server tạo `ServerSocket`, gắn (bind) với một số hiệu cổng cố định và lắng nghe yêu cầu kết nối từ các client (`listen`).
2. **Giai đoạn 2 (Client yêu cầu kết nối)**: Client tạo một `Socket`, chỉ định địa chỉ IP và Port của Server cần kết nối. Hệ điều hành tự động gán một cổng ngẫu nhiên rảnh (ephemeral port) cho Socket này.
3. **Giai đoạn 3 (Thiết lập kết nối & Trao đổi dữ liệu)**: Server chấp nhận kết nối qua lệnh `accept()`, sinh ra một đối tượng `Socket` độc lập phục vụ riêng cho Client này. Hai bên trao đổi dữ liệu thông qua các dòng vào/ra (`InputStream` và `OutputStream`).
4. **Giai đoạn 4 (Đóng kết nối)**: Một trong hai bên chủ động đóng kết nối bằng phương thức `close()`.

### 1.3. Mô hình Client-Server ở chế độ không nối kết (UDP)
UDP (User Datagram Protocol) là giao thức phi kết nối (connectionless), không thực hiện bắt tay, không đảm bảo thứ tự gói tin và không kiểm tra lỗi truyền lại. Đổi lại, UDP có overhead rất nhỏ, độ trễ cực thấp và tốc độ truyền nhanh.
- Dữ liệu được đóng gói thành các gói tin độc lập gọi là **DatagramPacket**.
- Mỗi gói tin phải chứa đầy đủ thông tin: mảng byte dữ liệu, độ dài, địa chỉ IP đích và cổng đích.
- Quá trình gửi và nhận được thực hiện thông qua **DatagramSocket**.

### 1.4. Mô hình truyền thông theo nhóm (Multicast Socket)
- Multicast là cơ chế gửi một gói tin từ một máy nguồn đến đồng thời một nhóm các máy nhận (One-to-Many).
- Sử dụng địa chỉ IP lớp D (từ `224.0.0.0` đến `239.255.255.255`).
- Các máy muốn nhận dữ liệu phải tham gia vào nhóm bằng cách gọi phương thức `joinGroup()` trên đối tượng `MulticastSocket`.

### 1.5. Kiến trúc Server: Tuần tự (Single-thread) vs Song song (Multi-thread)
- **Server phục vụ tuần tự (Single-threaded)**: Server xử lý tuần tự từng client trong vòng lặp `while(true)`. Nếu một client giữ kết nối lâu hoặc gửi dữ liệu chậm, tất cả các client khác phải chờ đợi (hiện tượng blocking/starvation).
- **Server phục vụ song song (Multi-threaded)**: Khi một client kết nối tới (`accept()`), Server sẽ tạo một luồng xử lý riêng (`WorkerThread`) hoặc giao nhiệm vụ cho một Thread Pool (`ExecutorService`) để phục vụ client đó. Nhờ vậy, tiến trình chính (Main Thread) ngay lập tức quay lại lắng nghe các kết nối tiếp theo, cho phép hàng nghìn client kết nối đồng thời.

---

## 2. Cấu trúc mã nguồn dự án

```text
Laptrinhsocket/
│── src/
│   └── com/
│       └── gpcoder/
│           ├── tcp/                               # Lập trình TCP Socket
│           │   ├── EchoChatSingleServer.java      # Server tuần tự (đơn luồng)
│           │   ├── EchoChatMultiServer.java       # Server song song (đa luồng với ExecutorService)
│           │   ├── WorkerThread.java              # Thread xử lý I/O độc lập cho từng Client
│           │   └── EchoChatClient.java            # Client TCP gửi tuần tự các ký tự '0' -> '9'
│           ├── udp/                               # Lập trình UDP Datagram Socket
│           │   ├── EchoServer.java                # Server UDP nhận Datagram và phản hồi
│           │   └── EchoClient.java                # Client UDP nhập tin từ bàn phím gửi tới Server
│           ├── multicast/                         # Lập trình Multicast Socket
│           │   ├── MulticastSender.java           # Máy phát tin nhắn định kỳ tới nhóm 224.0.0.1
│           │   └── MulticastReceiver.java         # Máy nhận tham gia nhóm (joinGroup) và lắng nghe
│           └── net/                               # Các lớp cơ bản gói java.net
│               ├── UrlExample.java                # Phân tích cấu trúc URL
│               ├── URLConnectionExample.java      # Đọc dữ liệu HTML qua kết nối URLConnection
│               └── InetAddressExample.java        # Tra cứu IP máy và phân giải DNS
│── screenshots/                                   # Thư mục ảnh chụp màn hình mã nguồn và kết quả chạy
│   ├── 01_UrlExample_Code.png
│   ├── 01_UrlExample_Run.png
│   ├── 02_URLConnectionExample_Code.png
│   ├── 02_URLConnectionExample_Run.png
│   ├── 03_InetAddressExample_Code.png
│   ├── 03_InetAddressExample_Run.png
│   ├── 04_TCP_EchoChatSingleServer_Code.png
│   ├── 04_TCP_EchoChatClient_Code.png
│   ├── 04_TCP_SingleServer_Run.png
│   ├── 05_TCP_EchoChatMultiServer_Code.png
│   ├── 05_TCP_WorkerThread_Code.png
│   ├── 05_TCP_MultiServer_Run.png
│   ├── 06_UDP_EchoServer_Code.png
│   ├── 06_UDP_EchoClient_Code.png
│   ├── 06_UDP_Echo_Run.png
│   ├── 07_Multicast_Sender_Code.png
│   ├── 07_Multicast_Receiver_Code.png
│   └── 07_Multicast_Run.png
│── bin/                                           # Thư mục chứa Bytecode (.class) sau khi biên dịch
│── .gitignore                                     # Quy định các file không đưa lên Git
└── README.md                                      # Báo cáo chi tiết đồ án
```

---

## 3. Phân tích chi tiết các bài thực hành

### Chủ đề 1: Giao tiếp TCP Socket (Echo Chat)
- **`EchoChatSingleServer.java`**:
  - Khởi tạo `ServerSocket(7)` lắng nghe trên cổng 7.
  - Chấp nhận kết nối từ Client qua `serverSocket.accept()`.
  - Sử dụng vòng lặp `while (true)` đọc từng byte dữ liệu từ `InputStream` và ghi ngược lại vào `OutputStream` (Echo).
  - Đóng kết nối khi Client gửi tín hiệu kết thúc (ký tự `-1`).
- **`EchoChatClient.java`**:
  - Kết nối tới `127.0.0.1:7` bằng lệnh `new Socket(SERVER_IP, SERVER_PORT)`.
  - Dùng vòng lặp gửi các ký tự từ `'0'` đến `'9'` sang Server, sau đó đọc lại ký tự phản hồi từ Server và in ra màn hình console.

#### Minh chứng Mã nguồn & Kết quả chạy:
| Mã nguồn EchoChatSingleServer | Mã nguồn EchoChatClient |
| :---: | :---: |
| ![EchoChatSingleServer Code](screenshots/04_TCP_EchoChatSingleServer_Code.png) | ![EchoChatClient Code](screenshots/04_TCP_EchoChatClient_Code.png) |

**Kết quả chạy TCP Single Server & Client:**
![TCP Single Server Run](screenshots/04_TCP_SingleServer_Run.png)

---

### Chủ đề 2: Giao tiếp TCP Server Đa luồng (Multi-Thread Server)
- **`EchoChatMultiServer.java`**:
  - Sử dụng `ExecutorService executor = Executors.newFixedThreadPool(4)` để quản lý một Thread Pool gồm 4 luồng xử lý.
  - Vòng lặp chính liên tục gọi `serverSocket.accept()`. Khi có một client mới kết nối, Server bọc `socket` đó vào một thể hiện của `WorkerThread` và chuyển cho `executor.execute(handler)` thực thi không đồng bộ.
  - Luồng chính không bao giờ bị nghẽn (non-blocking) và luôn sẵn sàng đón client tiếp theo.
- **`WorkerThread.java`**:
  - Kế thừa lớp `Thread`, nhận đối tượng `Socket` trong constructor.
  - Cài đặt phương thức `run()` đọc và ghi echo dữ liệu độc lập cho client tương ứng.

#### Minh chứng Mã nguồn & Kết quả chạy:
| Mã nguồn EchoChatMultiServer | Mã nguồn WorkerThread |
| :---: | :---: |
| ![EchoChatMultiServer Code](screenshots/05_TCP_EchoChatMultiServer_Code.png) | ![WorkerThread Code](screenshots/05_TCP_WorkerThread_Code.png) |

**Kết quả chạy TCP Multi-Thread Server phục vụ đồng thời nhiều Client:**
![TCP MultiServer Run](screenshots/05_TCP_MultiServer_Run.png)

---

### Chủ đề 3: Giao tiếp UDP Datagram Socket
- **`EchoServer.java`**:
  - Khởi tạo `DatagramSocket(7)` trên cổng 7.
  - Chuẩn bị bộ đệm `byte[] BUFFER = new byte[4096]`.
  - Nhận gói tin bằng `ds.receive(incoming)`.
  - Lấy nội dung, địa chỉ IP nguồn và cổng nguồn từ gói tin nhận, sau đó tạo một `DatagramPacket` mới để gửi phản hồi ngược lại cho Client.
- **`EchoClient.java`**:
  - Khởi tạo `DatagramSocket` (cổng ngẫu nhiên do hệ điều hành cấp phát).
  - Sử dụng `BufferedReader` để người dùng nhập chuỗi văn bản từ bàn phím.
  - Đóng gói dữ liệu chuỗi vào `DatagramPacket` gửi tới Server tại `127.0.0.1:7`.
  - Chờ nhận phản hồi qua `ds.receive(incoming)` và hiển thị ra màn hình.

#### Minh chứng Mã nguồn & Kết quả chạy:
| Mã nguồn EchoServer | Mã nguồn EchoClient |
| :---: | :---: |
| ![EchoServer Code](screenshots/06_UDP_EchoServer_Code.png) | ![EchoClient Code](screenshots/06_UDP_EchoClient_Code.png) |

**Kết quả chạy UDP Echo Server & Client:**
![UDP Echo Run](screenshots/06_UDP_Echo_Run.png)

---

### Chủ đề 4: Giao tiếp Multicast Socket
- **`MulticastSender.java`**:
  - Sử dụng địa chỉ nhóm IP Class D: `224.0.0.1` và cổng `8888`.
  - Khởi tạo `DatagramSocket` thông thường.
  - Sử dụng vòng lặp định kỳ (cách nhau 1 giây) phát các gói tin Datagram tới địa chỉ nhóm `224.0.0.1:8888`.
- **`MulticastReceiver.java`**:
  - Khởi tạo `MulticastSocket(8888)`.
  - Đăng ký gia nhập nhóm bằng `socket.joinGroup(address)`.
  - Lắng nghe và hiển thị các gói tin gửi tới nhóm từ bất kỳ thành viên nào.

#### Minh chứng Mã nguồn & Kết quả chạy:
| Mã nguồn MulticastSender | Mã nguồn MulticastReceiver |
| :---: | :---: |
| ![MulticastSender Code](screenshots/07_Multicast_Sender_Code.png) | ![MulticastReceiver Code](screenshots/07_Multicast_Receiver_Code.png) |

**Kết quả chạy Multicast Sender & Receiver:**
![Multicast Run](screenshots/07_Multicast_Run.png)

---

### Chủ đề 5: Các lớp mạng cơ bản gói java.net
- **`UrlExample.java`**: Tách và in các thành phần của một URL (Protocol, Authority, Host, Port, Path, File, Query, Ref).
- **`URLConnectionExample.java`**: Kết nối tới địa chỉ web qua HTTP/HTTPS và đọc nội dung HTML.
- **`InetAddressExample.java`**: Tra cứu tên máy local, địa chỉ IP cục bộ và phân giải tên miền DNS của `google.com` và `gpcoder.com`.

#### Minh chứng Mã nguồn & Kết quả chạy:
| Ví dụ | Ảnh Code IDE | Ảnh Kết quả chạy |
| :--- | :---: | :---: |
| **UrlExample** | ![UrlExample Code](screenshots/01_UrlExample_Code.png) | ![UrlExample Run](screenshots/01_UrlExample_Run.png) |
| **URLConnectionExample** | ![URLConnectionExample Code](screenshots/02_URLConnectionExample_Code.png) | ![URLConnectionExample Run](screenshots/02_URLConnectionExample_Run.png) |
| **InetAddressExample** | ![InetAddressExample Code](screenshots/03_InetAddressExample_Code.png) | ![InetAddressExample Run](screenshots/03_InetAddressExample_Run.png) |

---

## 4. Hướng dẫn biên dịch và chạy chương trình

### Biên dịch toàn bộ mã nguồn:
Mở Terminal hoặc PowerShell tại thư mục dự án:
```powershell
javac -d bin -sourcepath src src/com/gpcoder/net/*.java src/com/gpcoder/tcp/*.java src/com/gpcoder/udp/*.java src/com/gpcoder/multicast/*.java
```

### Chạy các chương trình:

#### 1. TCP Echo Chat (Single-threaded Server):
- **Cửa sổ 1 (Khởi động Server)**:
  ```powershell
  java -cp bin com.gpcoder.tcp.EchoChatSingleServer
  ```
- **Cửa sổ 2 (Khởi động Client kết nối)**:
  ```powershell
  java -cp bin com.gpcoder.tcp.EchoChatClient
  ```

#### 2. TCP Multi-Thread Server (Hỗ trợ nhiều Client cùng lúc):
- **Cửa sổ 1 (Khởi động Server đa luồng)**:
  ```powershell
  java -cp bin com.gpcoder.tcp.EchoChatMultiServer
  ```
- **Cửa sổ 2, 3, 4 (Khởi động đồng thời nhiều Client)**:
  ```powershell
  java -cp bin com.gpcoder.tcp.EchoChatClient
  ```

#### 3. UDP Echo Chat:
- **Cửa sổ 1 (Khởi động UDP Server)**:
  ```powershell
  java -cp bin com.gpcoder.udp.EchoServer
  ```
- **Cửa sổ 2 (Khởi động UDP Client & nhập tin nhắn)**:
  ```powershell
  java -cp bin com.gpcoder.udp.EchoClient
  ```

#### 4. Multicast Socket (Truyền thông theo nhóm):
- **Cửa sổ 1 (Khởi động Receiver tham gia nhóm lắng nghe)**:
  ```powershell
  java -cp bin com.gpcoder.multicast.MulticastReceiver
  ```
- **Cửa sổ 2 (Khởi động Sender phát tin nhắn tới nhóm)**:
  ```powershell
  java -cp bin com.gpcoder.multicast.MulticastSender
  ```

#### 5. Các ví dụ cơ bản gói java.net:
```powershell
java -cp bin com.gpcoder.net.UrlExample
java -cp bin com.gpcoder.net.URLConnectionExample
java -cp bin com.gpcoder.net.InetAddressExample
```

---

## 5. Thư mục Screenshots minh chứng

Toàn bộ ảnh chụp màn hình được tổ chức trong thư mục `screenshots/`:

| STT | Tên file hình ảnh | Mô tả nội dung |
| :---: | :--- | :--- |
| 1 | `01_UrlExample_Code.png` | Ảnh chụp mã nguồn `UrlExample.java` giao diện Eclipse IDE |
| 2 | `01_UrlExample_Run.png` | Ảnh chụp màn hình kết quả chạy phân tích URL trên PowerShell |
| 3 | `02_URLConnectionExample_Code.png` | Ảnh chụp mã nguồn `URLConnectionExample.java` giao diện Eclipse IDE |
| 4 | `02_URLConnectionExample_Run.png` | Ảnh chụp màn hình kết quả đọc dữ liệu HTML từ URLConnection |
| 5 | `03_InetAddressExample_Code.png` | Ảnh chụp mã nguồn `InetAddressExample.java` giao diện Eclipse IDE |
| 6 | `03_InetAddressExample_Run.png` | Ảnh chụp màn hình kết quả tra cứu IP và phân giải DNS |
| 7 | `04_TCP_EchoChatSingleServer_Code.png` | Ảnh chụp mã nguồn `EchoChatSingleServer.java` giao diện Eclipse IDE |
| 8 | `04_TCP_EchoChatClient_Code.png` | Ảnh chụp mã nguồn `EchoChatClient.java` giao diện Eclipse IDE |
| 9 | `04_TCP_SingleServer_Run.png` | Ảnh chụp màn hình chạy giao tiếp giữa TCP Single Server và Client |
| 10 | `05_TCP_EchoChatMultiServer_Code.png` | Ảnh chụp mã nguồn `EchoChatMultiServer.java` giao diện Eclipse IDE |
| 11 | `05_TCP_WorkerThread_Code.png` | Ảnh chụp mã nguồn `WorkerThread.java` giao diện Eclipse IDE |
| 12 | `05_TCP_MultiServer_Run.png` | Ảnh chụp màn hình chạy TCP Multi-Thread Server phục vụ đồng thời nhiều Client |
| 13 | `06_UDP_EchoServer_Code.png` | Ảnh chụp mã nguồn `EchoServer.java` giao diện Eclipse IDE |
| 14 | `06_UDP_EchoClient_Code.png` | Ảnh chụp mã nguồn `EchoClient.java` giao diện Eclipse IDE |
| 15 | `06_UDP_Echo_Run.png` | Ảnh chụp màn hình chạy giao tiếp giữa UDP Server và Client |
| 16 | `07_Multicast_Sender_Code.png` | Ảnh chụp mã nguồn `MulticastSender.java` giao diện Eclipse IDE |
| 17 | `07_Multicast_Receiver_Code.png` | Ảnh chụp mã nguồn `MulticastReceiver.java` giao diện Eclipse IDE |
| 18 | `07_Multicast_Run.png` | Ảnh chụp màn hình chạy Multicast Sender phát tin và Receiver nhận tin |
