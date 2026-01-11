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
   - Catatan: Jika username/password salah, akan muncul pesan peringatan.

Logout : 
1. Klik halaman logout pada dashboard.

### Dashboard Utama
1. Pilih menu Registrasi Pengunjung untuk mengelola data member.
2. Pilih menu Sesi untuk mengatur jadwal kelas.
3. Pilih menu Check-In untuk melakukan presensi harian.
4. Tekan tombol ikon Keluar di pojok kanan bawah untuk logout.

### Manajemen Pengunjung
Jika memilih card 1
1. Melihat Daftar Pengunjung
   - Pilih menu Daftar Pengunjung.
   - Akan muncul daftar nama pengunjung beserta statusnya (Member atau Guest).
   - Gunakan Kolom Pencarian di bagian atas untuk mencari nama tertentu dengan cepat.
3. Menambah Pengunjung Baru
   - Di halaman Daftar Pengunjung, tekan tombol Tambah (+) di pojok kanan bawah.
   - Anda akan masuk ke form Tambah Pengunjung.
   - Isi data berikut:
        - Nama Lengkap: (Wajib diisi)
        - Nomor HP: (Wajib diisi)
        - Tipe Pengunjung: Pilih "Member" atau "Guest".
   - Tekan tombol "Simpan".
        - Validasi: Jika ada kolom yang kosong, sistem akan menolak dan meminta Anda melengkapinya.
   - Jika berhasil, akan muncul notifikasi "Data Berhasil Disimpan" dan Anda kembali ke daftar pengunjung.
5. Edit Pengunjung
   - Di halaman Daftar Pengunjung, pilih atau klik salah satu card pengunjung yang ingin di edit.
   - Anda akan masuk ke halaman Detail Pengunjung.
   - Pilih tombol "edit", kemudian akan masuk ke form Edit Pengunjung.
   - Edit data yang diinginkan (nama/nomor hp/tipe pengunjung).
   - Tekan tombol "Simpan".
   - Kemudian akan kembali ke halaman Detail Pengunjung disertai notifikasi "data berhasil diupdate".
7. Hapus Pengunjung
   - Di halaman Daftar Pengunjung, pilih atau klik salah satu card pengunjung yang ingin di edit.
   - Anda akan masuk ke form Detail Pengunjung.
   - Klik tombol "Hapus".
        - Validasi: Apakah yakin akan menghapus pengunjung, jika ya, maka terhapus, dan jika tidak maka batal.
   - Pilih "Ya" untuk menghapus pengunjung, dan akan tampil notif "Data berhasil dihapus".

### Manajemen Sesi
Jika memilih card 2
1. Terdapat 3 card tetap sesi.
2. klik salah satu sesi yang ingin diedit (jam/instruktur).
   - Jika tidak diisi maka muncul notifikasi "Jam dan Instruktur wajib diisi".
3. Klik simpan untuk menyimpan perubahan, dan akan tampil notifikasi "Sesi berhasil diupdate".
   
### Manajemen Checkin
Fitur ini digunakan saat pengunjung datang untuk mengikuti kelas pilates (sebelum masuk ruangan pilates).
1. Pilih menu Check-In dari Dashboard.
2. Terdapat Halaman Checkin dengan pilihan card sesi, fitur tanggal, dan tombol + dikanan bawah.
3. Untuk Tambah Checkin.
   - Klik +.
   - akan masuk ke halaman form checkin.
   - Pilih Pengunjung: Cari nama pengunjung dari daftar dropdown (dapat diketik untuk meminimalkan waktu pencarian dengan scroll).
   - Pilih Sesi saat itu juga (karena kita input checkin sebelum memasuki ruangan jadi sesi sudah pasti).
   - Klik tombol "Check-in Sekarang".
   - Akan kembali ke halaman Checkin disertai notifikasi "checkin berhasil", dan dapat di check pada detail sesi sesuai tanggal hari ini.
4. Untuk Lihat Detail Data Check-in Pada Hari Itu.
   - Pada halaman checkin, Pilih tanggal yang diinginkan.
   - Pilih dan klik card yang ingin dilihat.
   -  Akan tampil data pengunjung yang checkin pada sesi tersebut sesuai dengan tanggal yang diatur. 

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
    git clone [https://github.com/mauraaninditakirana/KiranaPilates.git]
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












