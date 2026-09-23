# BAO CAO TIM HIEU VA THUC HANH LAP TRINH MANG VOI JAVA
> Hoc phan: Phat trien phan mem huong doi tuong (OOSE 2026-2027)  
> Tai lieu tham khao: [GP Coder - Lap trinh mang voi Java](https://gpcoder.com/3664-lap-trinh-mang-voi-java/)  

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

---

## 2. Cau truc ma nguon du an

```text
Lap trinh mang/
│── src/
│   └── com/
│       └── gpcoder/
│           └── net/
│               ├── UrlExample.java             # Phan tich cac thanh phan URL
│               ├── URLConnectionExample.java    # Doc du lieu trang web qua URLConnection
│               └── InetAddressExample.java      # Tra cuu IP may va phan giai DNS
│── screenshots/                                 # Anh chup man hinh code va chay
│   ├── 01_UrlExample_Code.png
│   ├── 01_UrlExample_Run.png
│   ├── 02_URLConnectionExample_Code.png
│   ├── 02_URLConnectionExample_Run.png
│   ├── 03_InetAddressExample_Code.png
│   └── 03_InetAddressExample_Run.png
│── bin/                                         # File class sau khi bien dich
│── .gitignore
└── README.md
```

---

## 3. Chi tiet cac bai thuc hanh

- **UrlExample.java**: Su dung `java.net.URL` tach cac thanh phan: Protocol, Authority, Host, Port, Path, File, Query, Ref.
- **URLConnectionExample.java**: Ket noi HTTP toi trang web, thiet lap User-Agent, doc du lieu HTML qua `BufferedReader`.
- **InetAddressExample.java**: Tra cuu ten may va IP local, tra cuu phan giai DNS IPv4/IPv6 cho `google.com` va `gpcoder.com`.

---

## 4. Huong dan bien dich va chay chuong trinh

### Bien dich:
```powershell
javac -d bin -sourcepath src src/com/gpcoder/net/*.java
```

### Chay chuong trinh:
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
