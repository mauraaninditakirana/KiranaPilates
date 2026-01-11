# Aplikasi Kirana Pilates Manager
Aplikasi Android berbasis Kotlin yang digunakan untuk membantu staf admin Kirana Pilates dalam mengelola data pengunjung, keanggotaan (Member & Guest), sesi latihan, serta pencatatan check-in harian.
Aplikasi ini menggantikan proses manual (buku catatan / spreadsheet) menjadi sistem yang lebih baik dan aman.

##

## Fitur Aplikasi
- Login & Logout Admin  
- Manajemen Pengunjung (Member & Guest)  (CRUD)
- Tambah paket kuota Member (+10)  
- Manajemen sesi (Pagi, Sore, Malam) (Update) 
- Check-in harian dengan validasi kuota  (Create)
- Pencarian pengunjung  
- Riwayat kunjungan  
- Keamanan data dengan autentikasi & hashing password

## Panduan Pengguna
### 1. Login
Login :
1. Buka aplikasi.
2. Login dengan username dan password.
3. Klik tombol Login untuk masuk ke dashboard.

Logout : 
1. Klik halaman logout pada dashboard.

### Dashboard Utama
1. Pilih menu Registrasi Pengunjung untuk mengelola data member.
2. Pilih menu Sesi untuk mengatur jadwal kelas.
3. Pilih menu Check-In untuk melakukan presensi harian.
4. Tekan tombol ikon Keluar di pojok kanan bawah untuk logout.


## Technical Documentation
### Entity Relationship Diagram (ERD)

<img width="865" height="306" alt="image" src="https://github.com/user-attachments/assets/2cb20a45-6769-411b-9ca1-2387dd31f3a6" />

<img width="864" height="493" alt="image" src="https://github.com/user-attachments/assets/abded820-91e7-4b6a-acaf-92ab283a4b05" />

### RELASI ANTAR TABEL (RAT)

<img width="866" height="447" alt="image" src="https://github.com/user-attachments/assets/b1436430-e1a0-4250-9670-14c9bfd4a8b6" />

### USECASE 

<img width="866" height="789" alt="image" src="https://github.com/user-attachments/assets/86823072-3aba-46cf-a68a-b2f3400af704" />

### FLOWCHART 

<img width="801" height="680" alt="image" src="https://github.com/user-attachments/assets/3501340b-05e2-4b45-a23d-8416fe5fb475" />

### ACTIVITY DIAGRAM

<img width="617" height="570" alt="image" src="https://github.com/user-attachments/assets/5f9d9c4a-4577-414e-b4ce-e7e9b6934410" />
<img width="579" height="680" alt="image" src="https://github.com/user-attachments/assets/d39c3620-7448-428c-b372-351ff934247d" />
<img width="597" height="635" alt="image" src="https://github.com/user-attachments/assets/78bd999e-ad53-4478-a5e6-93e9812ede36" />
<img width="618" height="534" alt="image" src="https://github.com/user-attachments/assets/800b7264-c216-433c-b806-40223f4e812f" />
<img width="627" height="577" alt="image" src="https://github.com/user-attachments/assets/db9f7133-8d11-432f-991a-b276a9279040" />
<img width="687" height="806" alt="image" src="https://github.com/user-attachments/assets/cbf37e14-65a1-4d2f-8e22-1d9312f43092" />

## KAMUS DATA
### TABEL ADMIN

<img width="428" height="176" alt="image" src="https://github.com/user-attachments/assets/aae1821b-a055-41b8-8fb9-99019a8ed534" />

### TABEL TOKEN

<img width="425" height="153" alt="image" src="https://github.com/user-attachments/assets/78d0455e-91c7-4e9d-925d-d31ae77125e9" />

### TABEL PENGUNJUNG

<img width="425" height="242" alt="image" src="https://github.com/user-attachments/assets/8981e86b-3d4b-4f8e-ac47-638d5f814571" />

### TABEL SESI

<img width="425" height="154" alt="image" src="https://github.com/user-attachments/assets/9b266724-9510-41b7-88c3-428679a6295d" />

### TABEL CHECKIN

<img width="424" height="155" alt="image" src="https://github.com/user-attachments/assets/55dc4379-bfff-45a6-a36d-5c3dca7c0854" />

## CARA INSTALASI
### Prasyarat
1.  Android Studio versi terbaru.
2.  XAMPP/LARAGON (untuk menjalankan server lokal PHP & MySQL).
3.  JDK 17 atau lebih baru.

### Langkah-langkah
1.  **Clone Repository**
    ```bash
    git clone [https://github.com/username-kamu/KiranaPilates.git](https://github.com/username-kamu/KiranaPilates.git)
    ```
2.  Setup Backend
    - Copy folder backend ke dalam htdocs di XAMPP atau di www jika menggunakan Laragon.
    - Import database kiranapilates_db.sql ke phpMyAdmin.
    - Sesuaikan konfigurasi database di file config/database.php.
3.  Setup Android
    - Buka project di Android Studio.
    - Buka file KiranaApiService.kt atau ContainerApp.kt.
    - Jika pakai real device, ubah BASE_URL sesuai IP Address laptop kamu (contoh: http://192.168.1.5/kiranapilates-api/).
    - Build dan Run aplikasi di Emulator atau Device fisik.
4. Login
   - Username: admin
   - Password: admin123
  
## 👤 Author
Nama: Maura Anindita Kirana
NIM: 20230140090
Teknologi Informasi Universitas Muhammadiyah Yogyakarta












