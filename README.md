# 🧩 Microservice System - User & Report Service (Spring Boot + API Gateway)

Proyek ini merupakan contoh implementasi **arsitektur microservice** sederhana menggunakan **Spring Boot 3**, **Spring Cloud Gateway**, dan **Docker Compose**.  
Sistem terdiri dari dua service utama:

- **user-service** → mengelola data user
- **report-service** → mengelola laporan yang dibuat oleh user
- **api-gateway** → menjadi satu pintu akses utama ke seluruh service

---

## ⚙️ Teknologi yang Digunakan

| Komponen | Teknologi                   |
|-----------|-----------------------------|
| Bahasa Pemrograman | Java 17                     |
| Framework Utama | Spring Boot 3               |
| API Gateway | Spring Cloud Gateway        |
| ORM / Database | Spring Data JPA, PostgreSQL |
| Build Tool | Maven                       |
| Containerization | Docker, Docker Compose      |
| Dokumentasi API | Swagger / OpenAPI / Postman |

---

## 🚀 Instalasi & Menjalankan Aplikasi

### 1️⃣ Clone repository
```bash
git clone https://github.com/<username>/<repository-name>.git
cd <repository-name>
```

### 2️⃣ Build setiap service
```
mvn clean package
```

### 3️⃣ Jalankan semua service dengan Docker Compose
```
docker compose up -d
```

## 📡 Akses Aplikasi

| Service                       | URL                   |
|-------------------------------|-----------------------|
| API Gateway                   | http://localhost:8080 |
| User Service                  | http://localhost:8081 |
| API Gateway                   | http://localhost:8082 |
| Swagger User & Report Service | {URL}/swagger-ui.html |

## 🚀 List APIs
1. Get All Users

GET /api/users <br>
Menampilkan seluruh data user yang terdaftar.
```json
[
  {
    "id": 8,
    "name": "Dewi Lestari",
    "email": "user2@example.com",
    "active": true,
    "createdAt": "2025-10-17T12:55:32.579714"
  },
  {
    "id": 9,
    "name": "Budi Santoso",
    "email": "user3@example.com",
    "active": true,
    "createdAt": "2025-10-18T12:55:32.579714"
  }
]
```
---
2. Get Grouped Users by Email Domain

GET /api/users/grouped <br>
Mengelompokkan user berdasarkan domain email menggunakan Java Stream.
```json
{
    "groupedCount": {
        "mail.com": 1,
        "example.com": 20
    }
}
```
---
3. Register User

POST /api/users/register <br>
Menambahkan user baru ke sistem.

Request:
```json
{
    "name": "John",
    "email": "john@mail.com"
}
```

Response:
```json
{
    "name": "John",
    "email": "john@mail.com"
}
```
---
4. Get All Reports

GET /api/reports <br>
Mengambil semua laporan yang tersimpan di sistem.

```json
[
    {
        "id": 1,
        "title": "Kerusakan Jalan",
        "description": "Laporan kerusakan jalan utama",
        "location": "Jakarta",
        "status": "OPEN",
        "createdAt": "2025-09-06T12:56:10.755034"
    },
    {
        "id": 2,
        "title": "Lampu Lalu Lintas Mati",
        "description": "Lampu lalu lintas mati di simpang",
        "location": "Bandung",
        "status": "OPEN",
        "createdAt": "2025-09-08T12:56:10.755034"
    },
    {
        "id": 3,
        "title": "Pohon Tumbang",
        "description": "Pohon tumbang di depan sekolah",
        "location": "Surabaya",
        "status": "IN_PROGRESS",
        "createdAt": "2025-09-09T12:56:10.755034"
    }
]
```
---
5. Get Report by ID

GET /api/reports/{id}<br>
Mengambil detail laporan berdasarkan ID.
```json
{
    "id": 1,
    "title": "Kerusakan Jalan",
    "description": "Laporan kerusakan jalan utama",
    "location": "Jakarta",
    "status": "OPEN",
    "createdAt": "2025-09-06T12:56:10.755034",
    "user_email": "user1@example.com"
}
```
---
6. Create Report

POST /api/reports <br>
Membuat laporan baru dari user.

Request:
```json
{
    "title": "Jalan rusak",
    "description": "Penuh lubang dan genangan air saat hujan",
    "location": "Jl bantul km 12, Bantul.",
    "user_email": "john@mail.com"
}
```

Response:
```json
{
    "id": 23,
    "title": "Jalan rusak",
    "description": "Penuh lubang dan genangan air saat hujan",
    "location": "Jl bantul km 12, Bantul.",
    "status": "PENDING",
    "createdAt": "2025-10-26T07:15:42.932497117",
    "user_email": "john@mail.com"
}
```
---
7. Update Report Status

PUT /api/reports/{id} <br>
Mengubah status laporan berdasarkan ID.

Request:
```json
{
  "status": "CLOSED"
}
```

Response:
```json
{
  "message": "Status updated successfully"
}
```
---
