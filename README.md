# GUI-Sistem-Klinik-Unsika

Sistem Klinik UNSIKA adalah aplikasi berbasis Java GUI (Swing) yang dirancang untuk membantu pengelolaan data pasien dan dokter pada sebuah klinik sederhana. Aplikasi ini dibuat sebagai proyek akhir mata kuliah Pemrograman Berorientasi Objek (PBO) dengan tujuan agar mahasiswa memahami penerapan konsep OOP, GUI, serta koneksi database dalam satu proyek nyata.
Aplikasi ini menyediakan fitur utama untuk mengelola data pasien, memilih dokter tujuan, serta menentukan jadwal kunjungan sesuai hari praktik dokter.

# 🎯 Tujuan Sistem

1. Menerapkan konsep OOP dalam pengembangan aplikasi Java.
2. Menggunakan Java Swing untuk membuat tampilan antarmuka (GUI).
3. Mengimplementasikan proses CRUD (Create, Read, Update, Delete) dengan database MySQL.
4. Mensimulasikan sistem pengelolaan data klinik yang sederhana dan mudah digunakan.
5. Mengembangkan aplikasi dengan tampilan modern agar nyaman digunakan.

# ⚙️ Fitur Utama

1. Input Data Pasien
- Nama pasien
- Umur
- Keluhan
- Dokter tujuan
- Hari kunjungan
- Data yang dimasukkan akan disimpan dalam database MySQL.

2. Pemilihan Dokter
- Data dokter dimuat secara langsung dari database.
- Pasien harus memilih dokter yang tersedia.
- Dokter di-model menggunakan class tersendiri (class Dokter).

3. Pengelolaan Data Pasien (CRUD)
- Aplikasi menyediakan tombol:
- Tambah Pasien → Menyimpan data pasien baru ke database
- Edit Pasien → Mengubah data pasien yang dipilih
- Hapus Pasien → Menghapus data pasien dari database
- Clear Form → Mengosongkan form input
- Semua data ditampilkan dalam tabel agar memudahkan proses pengelolaan.

4. Tabel Data Pasien
- Menampilkan daftar seluruh pasien beserta dokter & hari kunjungan.
- Tabel otomatis refresh setelah CRUD.
- Menggunakan gaya tampilan yang modern (warna header, zebra stripes).

# 📚 Teknologi yang Digunakan
1. Java Swing → GUI aplikasi
2. Java OOP → Class Dokter, Pasien, dan struktur program
3. MySQL → Penyimpanan data
4. JDBC → Koneksi Java ↔ Database
5. Visual Studio Code → Editor pembuatan aplikasi

# 🏥 Alur Penggunaan Sistem

-User menjalankan aplikasi → GUI terbuka.
-Aplikasi otomatis memuat daftar dokter & pasien dari database.
-User mengisi data pasien pada form.
-User memilih dokter & hari kunjungan.
-User menekan:
-Tambah → jika ingin menyimpan data baru
-Edit → untuk memperbarui data pasien
-Hapus → untuk menghapus data
-Clear → mengosongkan form input
-Tabel di sisi kanan akan menampilkan seluruh data pasien yang telah tersimpan.

# 🔍 Konsep PBO yang Digunakan

1. Encapsulation → Penggunaan getter & setter pada class Dokter/Pasien
2. Class & Object → Dokter dan Pasien menjadi objek
3. Constructor → Untuk inisialisasi data dokter
4. Modularization → Kode dipisahkan berdasarkan package (model, database, view)
5. Instance object digunakan untuk memanipulasi data pasien
