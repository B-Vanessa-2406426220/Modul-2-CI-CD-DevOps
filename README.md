## Deployment Link
Aplikasi telah dideploy secara otomatis menggunakan Koyeb dan dapat diakses melalui tautan berikut:
- **URL:** [https://vital-mirna-eshop-vanessa-d6b7e78e.koyeb.app](https://vital-mirna-eshop-vanessa-d6b7e78e.koyeb.app)

<details>
<summary>Module 2</summary>

# Module 2 

### 1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.

Issues yang dideteksi oleh SonarCloud dan perbaikannya:

* **Risiko Keamanan (Izin GitHub Actions)**
    * **Masalah:** File `scorecard.yml` menggunakan `permissions: read-all`, yang memberikan hak akses baca ke seluruh repositori secara berlebihan.
    * **Strategi:** Membatasi izin menjadi hanya `contents: read` untuk mengikuti prinsip hak akses minimum, sehingga mengurangi risiko keamanan.
* **Deklarasi Exception yang Redundan**
    * **Masalah:** Penggunaan `throws Exception` pada test method padahal tidak ada checked exception yang dilempar.
    * **Strategi:** Menghapus deklarasi tersebut agar kode lebih bersih.
* **Metode Kosong (Empty Method)**
    * **Masalah:** Adanya metode `setUp()` yang kosong di `ProductRepositoryTest` tanpa komentar penjelas.
    * **Strategi:** Menambahkan baris komentar karena inisialisasi sudah ditangani secara otomatis oleh anotasi `@InjectMocks`, sehingga menghilangkan kode yang tidak berfungsi (*dead code*).
* **Perbaikan Dependency Injection**
    * **Masalah:** Penggunaan Field Injection (`@Autowired` pada variabel langsung) yang dianggap kurang baik untuk pengujian unit.
    * **Strategi:** Mengubahnya menjadi Constructor Injection pada Controller agar dependensi menjadi transparan, mempermudah pengujian, dan mendukung prinsip imutabilitas.
* **Penggunaan Tag Anchor sebagai Tombol (Accessibility)**
    * **Masalah:** Penggunaan tag `<a>` untuk aksi fungsional (seperti tombol hapus/edit) yang seharusnya dilakukan oleh elemen `<button>`.
    * **Strategi:** Menambahkan atribut `role="button"` pada tag `<a>` untuk menginformasikan bahwa elemen ini berfungsi sebagai tombol aksi, bukan sekadar link biasa
* **Import yang Tidak Digunakan (Unused Import)**
    * **Masalah:** Adanya import `org.openqa.selenium.WebElement` yang tidak lagi digunakan dalam kode.
    * **Strategi:** Menghapus impor tersebut untuk merapikan bagian header file dan mengurangi polusi kode.
* **Kode yang Di-comment (Commented-out Code)**
    * **Masalah:** Terdapat blok kode yang dinonaktifkan dengan komentar di dalam file Java.
    * **Strategi:** Menghapus blok kode tersebut karena riwayat perubahan sudah terekam dengan aman di sistem kontrol versi (Git), sehingga kode utama tetap bersih dan mudah dibaca.

### 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!





---
</details>

<details>
<summary>Module 1
</summary>
# Module 1

## Reflection 1 

**You already implemented two new features using Spring Boot. Check again your source code 
and evaluate the coding standards that you have learned in this module. Write clean code 
principles and secure coding practices that have been applied to your code.  If you find any 
mistake in your source code, please explain how to improve your code.**

### Clean code principles applied:
- **Meaningful Names**  
  Saya menggunakan penamaan variabel, metode, dan kelas yang jelas serta deskriptif, seperti `ProductController`, `ProductRepository`, `create()`, dan `findAll()`.  
  Penamaan yang baik membantu meningkatkan keterbacaan kode dan memudahkan pengembang lain memahami tujuan dari setiap bagian kode tanpa perlu membaca implementasi secara mendalam.

- **Small Functions (Single Responsibility Principle)**  
  Setiap metode dalam `ProductController` dan `ProductRepository` hanya memiliki satu tanggung jawab utama.  
  Contohnya, metode `create()` hanya menangani proses penyimpanan data produk, sementara `findAll()` hanya bertugas mengambil daftar produk.  
  Penerapan prinsip ini membuat kode lebih mudah diuji, dipelihara, dan dikembangkan di masa depan.

- **Separation of Concerns**  
  Logika aplikasi dipisahkan berdasarkan perannya masing-masing.  
  `ProductController` bertanggung jawab dalam menangani HTTP request dan response, sedangkan `ProductRepository` bertanggung jawab dalam pengelolaan data produk.  
  Pemisahan ini mengurangi ketergantungan antar komponen dan meningkatkan modularitas kode.

- **Konsisten**  
  Struktur kode, penamaan metode, serta pola implementasi dibuat konsisten di seluruh kelas.  
  Konsistensi ini memudahkan proses pembacaan kode, terutama ketika proyek berkembang dan melibatkan lebih banyak fitur atau pengembang.

- **Tidak Hard-Coded Values**  
  Nilai penting seperti ID produk tidak ditentukan secara manual atau statis di dalam kode, melainkan dihasilkan secara dinamis.  
  Hal ini membuat kode lebih fleksibel dan mengurangi risiko kesalahan akibat penggunaan nilai yang tetap.

### Secure Coding Practices Applied

- **UUID Generation**  
  Saya menggunakan `java.util.UUID.randomUUID()` untuk menghasilkan ID produk.  
  Pendekatan ini mencegah ID yang bersifat prediktif, sehingga mengurangi risiko eksploitasi seperti enumerasi data oleh pihak yang tidak berwenang.

- **Redirect after POST (Post/Redirect/Get Pattern)**  
  Setelah operasi POST, controller mengembalikan response berupa redirect (`redirect:list`).  
  Pola ini mencegah duplikasi data akibat refresh halaman dan melindungi aplikasi dari pengiriman request yang tidak disengaja.

- **Minimizing Data Exposure**  
  Controller hanya memproses dan meneruskan data yang dibutuhkan, tanpa mengekspos struktur internal repository atau detail implementasi penyimpanan data.  
  Hal ini membantu mengurangi risiko kebocoran informasi internal aplikasi.

- **Controlled Data Manipulation**  
  Seluruh operasi pembuatan, pengubahan, dan penghapusan data produk dilakukan melalui repository, bukan langsung di controller.  
  Pendekatan ini memastikan akses data lebih terkontrol dan memudahkan penambahan mekanisme keamanan di kemudian hari.

### Improvement

Setelah melakukan evaluasi ulang terhadap kode yang telah dibuat, saya menemukan beberapa aspek yang masih dapat ditingkatkan:

- Pada `ProductRepository`, operasi `update` dan `delete` masih memanipulasi struktur data `List` secara langsung menggunakan `set()` dan `removeIf()`.  
  Ke depannya, perlu ditambahkan validasi untuk memastikan bahwa data produk yang akan diubah atau dihapus benar-benar ada.

- Validasi input masih dapat ditingkatkan, khususnya untuk memastikan bahwa:
  - Data produk tidak bernilai `null` atau kosong
  - Kuantitas produk hanya menerima bilangan bulat positif  

  Validasi ini akan meningkatkan ketahanan aplikasi terhadap input yang tidak valid atau berpotensi merusak data.

---

## Reflection 2

### 1.

**After writing the unit test, how do you feel? How many unit tests should be made in a  
class? How to make sure that our unit tests are enough to verify our program? It would be  
good if you learned about code coverage. Code coverage is a metric that can help you  
understand how much of your source is tested. If you have 100% code coverage, does  
that mean your code has no bugs or errors?**

- Setelah menulis unit test, saya merasa lebih aman dan percaya diri terhadap stabilitas dan kebenaran logika program, karena setiap fungsi utama telah diverifikasi melalui pengujian otomatis.

- Tidak terdapat jumlah pasti unit test yang harus dibuat dalam sebuah kelas. Namun, setiap perilaku dan logika penting dalam kode sebaiknya memiliki test yang mencakup skenario normal, skenario gagal, serta edge cases.

- Code coverage dapat digunakan sebagai indikator awal untuk menilai kecukupan pengujian. Metrik ini membantu mengidentifikasi bagian kode yang belum pernah dieksekusi oleh unit test.

- Meskipun 100% code coverage dapat dicapai, hal tersebut tidak menjamin bahwa aplikasi bebas dari bug atau error. Kesalahan logika, asumsi yang keliru, atau skenario yang tidak terduga tetap dapat terjadi jika test tidak dirancang dengan baik.

**Kesimpulan:**  
Code coverage merupakan alat ukur kuantitas pengujian, bukan kualitas. Unit test yang baik harus dirancang untuk menguji berbagai kondisi input, batas nilai, dan kemungkinan kesalahan penggunaan.

---

### 2.

**Suppose that after writing the CreateProductFunctionalTest.java along with the  
corresponding test case, you were asked to create another functional test suite that  
verifies the number of items in the product list. You decided to create a new Java class  
similar to the prior functional test suites with the same setup procedures and instance  
variables.  
What do you think about the cleanliness of the code of the new functional test suite? Will  
the new code reduce the code quality? Identify the potential clean code issues, explain  
the reasons, and suggest possible improvements to make the code cleaner!**

Jika saya membuat kelas functional test baru dengan prosedur setup dan variabel yang sama persis, hal tersebut akan berdampak negatif terhadap kebersihan dan kualitas kode.

**Potensi Masalah (Clean Code Issues):**
- **Code Duplication**  
  Pengulangan kode pada bagian `@BeforeEach`, seperti inisialisasi `baseUrl` dan `serverPort`, menyebabkan kode sulit dipelihara dan melanggar prinsip *Don't Repeat Yourself (DRY)*.

- **Low Maintainability**  
  Duplikasi setup yang tersebar di banyak kelas meningkatkan risiko inkonsistensi dan kesalahan ketika konfigurasi pengujian perlu diubah.

**Improvement Suggestion:**  
Pendekatan yang lebih bersih adalah dengan membuat sebuah *base functional test class* yang berisi:
- Variabel bersama (`serverPort`, `testBaseUrl`)
- Metode setup umum untuk inisialisasi environment testing

Kelas-kelas functional test lainnya cukup melakukan `extends` terhadap base class tersebut dan tiap file dapat fokus ke satu fungsi. Dengan cara ini, kode menjadi lebih terstruktur, mudah dirawat, dan skalabel.
</details>
