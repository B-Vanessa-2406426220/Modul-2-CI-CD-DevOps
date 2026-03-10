## Deployment Link
Aplikasi telah dideploy secara otomatis menggunakan Koyeb dan dapat diakses melalui tautan berikut:
- **URL:** [https://vital-mirna-eshop-vanessa-d6b7e78e.koyeb.app](https://vital-mirna-eshop-vanessa-d6b7e78e.koyeb.app)

<details>
<summary>Module 4</summary>

# Module 4

### 1. Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.
Pada awalnya menerapkan alur *Test-Driven Development* (TDD) terasa sedikit tidak alami karena saya harus memikirkan cara menguji sesuatu yang kodenya bahkan belum ada. Namun, setelah saya mengerjakan fitur **Order**, saya mulai merasakan manfaatnya 

Dengan membuat test terlebih dahulu (fase **Red**), saya dipaksa memikirkan skenario-skenario kegagalan sejak awal. Misalnya, saat menentukan apa yang terjadi jika status order tidak valid. TDD memberikan confidence karena setiap kali saya mengubah kode (refactoring), saya tidak perlu takut merusak fitur yang sudah jalan, cukup jalankan test, dan jika tetap hijau, berarti semuanya aman.

Kelemahan saya saat ini adalah saya masih terlalu sering mengikuti skenario ideal (happy path). Kedepannya, hal yang harus saya lakukan adalah lebih proaktif memikirkan nilai batas (boundary values) dan corner cases sejak fase pembuatan test pertama, sehingga logika kode benar-benar kuat dan teruji dari segala sisi sebelum implementasinya ditulis.


### 2. You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.
Secara keseluruhan, rangkaian unit test yang saya susun untuk layer Model, Repository, dan Service pada fitur Order telah dirancang sedemikian rupa untuk memenuhi standar kualitas pengujian melalui prinsip **F.I.R.S.T.** (Fast, Independent, Repeatable, Self-Validating, Timely), dengan rincian sebagai berikut:

* **Fast:** Pengujian berjalan sangat cepat karena penggunaan *mocking* (Mockito) pada layer Service, sehingga tidak bergantung pada resource eksternal membantu mempercepat eksekusi test.
* **Independent:** Setiap test bersifat otonom dan tidak bergantung satu sama lain. Penggunaan `@BeforeEach` memastikan data antar-test tidak saling tumpang tindih, mencegah test leakage (data test yang bocor dan dapat merusak test lain).
* **Repeatable:** Hasil pengujian konsisten karena menggunakan data statis yang dapat diprediksi (seperti UUID dan timestamp yang ditentukan) pada mocking, sehingga bisa dijalankan kapan pun dengan hasil yang sama.
* **Self-Validating:** Test sudah menggunakan *assertion* otomatis (JUnit) yang langsung memberikan status lulus/gagal secara objektif. Tidak perlu lagi inspeksi visual atau pengecekan print console secara manual
* **Timely:** Test ditulis beriringan dengan pengerjaan fitur, menjaga setiap perubahan kode tetap terkendali sejak awal. Namun, ini adalah area yang masih perlu saya tingkatkan. Dalam TDD, test ditulis sebelum menulis kode produksi. Namun pada praktiknya, kadang saya masih menulis kerangka kode produksi dulu baru merapikan test-nya lagi. Ke depannya, saya harus lebih disiplin menjaga siklus Red-Green-Refactor secara berurutan.

</details>

<details>
<summary>Module 3</summary>

# Module 3

### 1. Jelaskan prinsip-prinsip apa saja yang kamu terapkan pada proyek ini!

Prinsip SOLID yang diterapkan pada proyek eshop ini:

* **Single Responsibility Principle (SRP)**
  * Setiap class hanya memiliki satu tanggung jawab. Controller hanya menangani HTTP request, service hanya mengelola logika bisnis, dan repository hanya menangani penyimpanan data.
  * **Contoh:** CarController sebelumnya berada di file yang sama dengan ProductController dan bahkan melakukan inheritance yang tidak perlu. Pisahkan CarController ke file sendiri. Pastikan CarServiceImpl hanya mengurusi logika bisnis, dan CarRepository hanya mengurusi data.


* **Open/Closed Principle (OCP)**
  * Kode harus terbuka untuk ekstensi tetapi tertutup untuk modifikasi. Hal ini diterapkan dengan membuat interface `ProductRepositoryInterface` dan `CarRepositoryInterface`.
  * **Contoh:** Jika ingin mengganti penyimpanan dari in-memory `ArrayList` ke database, cukup buat class baru yang mengimplementasi `ProductRepositoryInterface` tanpa mengubah kode `ProductServiceImpl` sama sekali.

* **Liskov Substitution Principle (LSP)**
  * Objek dari subclass harus bisa menggantikan objek superclass tanpa merusak program. Controller mendeklarasikan tipe interface, bukan implementasi konkret.
  * **Contoh:** `ProductController` menggunakan tipe `ProductService` (interface), sehingga `ProductServiceImpl` bisa diganti dengan implementasi lain tanpa mengubah controller.

* **Interface Segregation Principle (ISP)**
  * Interface harus spesifik dan fokus, client tidak boleh dipaksa bergantung pada method yang tidak mereka gunakan.
  * **Contoh:** `ProductService` dan `CarService` dipisahkan menjadi dua interface yang masing-masing hanya memiliki 5 method relevan, bukan satu interface besar. Selain itu, method `deleteCarById` pada `CarService` telah di-rename menjadi `delete` agar konsisten dan tidak memaksakan penamaan spesifik.

* **Dependency Inversion Principle (DIP)**
  * Modul tingkat tinggi tidak boleh bergantung pada modul tingkat rendah; keduanya harus bergantung pada abstraksi. Semua dependency menggunakan constructor injection dan bergantung pada interface.
  * **Contoh:** Sebelumnya `CarServiceImpl` menggunakan field injection (`@Autowired private CarRepository`) yang bergantung pada concrete class. Setelah refactoring, diubah menjadi constructor injection dengan tipe `CarRepositoryInterface` (interface). Hal yang sama diterapkan pada `CarController` yang kini menggunakan constructor injection untuk `CarService`.

### 2. Jelaskan keuntungan menerapkan prinsip SOLID pada proyek ini beserta contohnya.

* **Mudah di-extend tanpa memodifikasi kode yang ada (OCP)**
  * Dengan adanya `ProductRepositoryInterface`, jika ingin beralih dari in-memory ke database, cukup buat class baru seperti `JpaProductRepository implements ProductRepositoryInterface`. Tidak perlu mengubah satu baris pun di `ProductServiceImpl` atau `ProductController`, sehingga mengurangi risiko memperkenalkan bug.

* **Mudah di-test (DIP + SRP)**
  * Karena service bergantung pada interface (bukan concrete class), kita bisa dengan mudah membuat mock dalam unit test menggunakan `@Mock ProductRepositoryInterface`. Test berjalan cepat tanpa perlu koneksi database atau setup yang rumit.

* **Kode lebih mudah dipahami dan dikelola (SRP)**
  * Setiap class memiliki tanggung jawab yang jelas. Ketika ada bug pada penyimpanan data, developer langsung tahu harus melihat ke repository. Ketika ada masalah routing, langsung periksa controller. Tidak ada pencampuran tanggung jawab.

* **Perubahan terisolasi dan minim dampak (LSP + ISP)**
  * Karena controller bergantung pada interface, kita bisa mengganti implementasi kapan saja selama kontrak interface terpenuhi. Misalnya, bisa membuat `CachedProductServiceImpl` untuk menambahkan caching tanpa mengubah controller.

* **Fleksibilitas tinggi (DIP)**
  * Constructor injection membuat dependency eksplisit. Jika ingin mengganti implementasi service, cukup ubah konfigurasi Spring tanpa mengubah kode controller.

### 3. Jelaskan kerugian jika tidak menerapkan prinsip SOLID pada proyek ini beserta contohnya.

* **Kode sulit di-test (tanpa DIP)**
  * Sebelum refactoring, `CarServiceImpl` menggunakan field injection dengan concrete class `CarRepository`. Ini membuat unit testing sulit karena tidak bisa mengganti repository dengan mock tanpa reflection, dan test bergantung pada implementasi konkret yang rentan rusak jika repository berubah.

* **Perubahan berantai / ripple effect (tanpa OCP)**
  * Tanpa interface, jika mengubah nama method atau return type di `ProductRepository`, maka `ProductServiceImpl` juga harus diubah, bahkan bisa berdampak ke `ProductController`. Satu perubahan kecil memicu perubahan besar di banyak tempat.

* **Tanggung jawab campur aduk (tanpa SRP)**
  * Sebelum refactoring, `CarController` memiliki `System.out.println()` debug logging di dalam method `editCarPost()`. Controller menjadi tempat "dump" untuk berbagai kode yang tidak relevan, membuat debugging sulit dan kode tidak modular.

* **Interface yang terlalu gemuk (tanpa ISP)**
  * Jika `ProductService` dan `CarService` digabungkan menjadi satu interface besar `EshopService`, maka `ProductController` dipaksa bergantung pada method-method car yang tidak relevan. Ini membuat kode menjadi *tightly coupled* dan rentan terhadap perubahan yang tidak terkait.

* **Substitusi menjadi tidak aman (tanpa LSP)**
  * Jika subclass mengubah perilaku yang diharapkan (misalnya throw exception alih-alih return `null`), program bisa crash secara tidak terduga. Contohnya, jika implementasi `CarService` yang baru memiliki method `findById()` yang throw exception ketika car tidak ditemukan, maka `CarController` akan error karena mengasumsikan perilaku tertentu.

---
</details>

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

Menurut saya, implementasi saat ini sudah memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD).

**Continuous Integration** terpenuhi karena saya telah mengonfigurasi pipeline otomatis menggunakan GitHub Actions yang dipicu oleh setiap push ke repositori. Rangkaian ini mencakup eksekusi unit test secara otomatis dan integrasi dengan SonarCloud untuk melakukan analisis kualitas kode statis (static code analysis). Proses ini memastikan bahwa setiap integrasi kode baru tetap memenuhi standar kualitas dan tidak merusak fitur yang sudah ada sebelumnya.

**Continuous Deployment** juga telah tercapai melalui integrasi otomatis dengan platform Koyeb. Setelah kode berhasil melewati tahap CI dan di-merge ke branch main, Koyeb secara otomatis melakukan build ulang menggunakan Docker dan memperbarui aplikasi di lingkungan produksi. Proses ini memastikan aplikasi di lingkungan produksi selalu diperbarui ke versi stabil terbaru secara instan dan otomatis, tanpa memerlukan intervensi manual tambahan.

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
