# MandiriTechnicalAssessment
Aplikasi Movie List App adalah aplikasi yang menampilkan daftar film dari TheMovieDatabase. Dalam aplikasi ini, pengguna dapat melihat daftar film beserta detail seperti poster, judul, trailer, sinopsis, hingga ke riview dari film tersebut.

## Tentang TheMovieDatabase API

TheMovieDatabase API adalah sebuah layanan yang menyediakan data tentang film dan serial TV dari seluruh dunia. Dengan menggunakan API ini, Anda dapat mengambil data seperti daftar film, sinopsis, poster, trailer, dan banyak lagi.

Untuk menggunakan TheMovieDatabase API, Anda harus mendaftar dan memperoleh API key. Anda dapat mendaftar di [sini](https://www.themoviedb.org/documentation/api) untuk mendapatkan API key.

Dalam aplikasi ini, kami menggunakan TheMovieDatabase API untuk mengambil daftar film dan detail tentang setiap film yang ditampilkan pada layar. Kami mengambil data dari API menggunakan Retrofit library dan menampilkan gambar menggunakan Picasso library.


## Teknologi dan Pattern yang Digunakan

- [Kotlin](https://kotlinlang.org/) - Bahasa pemrograman yang digunakan
- [MVVM](https://developer.android.com/jetpack/guide?gclid=Cj0KCQjw8vqGBhC_ARIsADMSd1DIXmX8xvZtGzykbjRiNSmI0mj8lDDwhe1cH_JPGeljKSKwbXgk-1YaAoz2EALw_wcB&gclsrc=aw.ds#overview) - Pattern arsitektur yang digunakan
- [Retrofit](https://square.github.io/retrofit/) - Library untuk HTTP networking
- [Picasso](https://square.github.io/picasso/) - Library untuk memuat dan menampilkan gambar secara efisien
- [Shimmer](https://facebook.github.io/shimmer-android/) - Library untuk membuat efek loading pada aplikasi

## Preview Aplikasi

Berikut adalah beberapa screenshot dari aplikasi ini:

<div align="center">
  <img src="https://raw.githubusercontent.com/RendhikaAditya/MandiriTechnicalAssessment/master/ss_home.png" width="190" alt="Preview Aplikasi">
  <img src="https://raw.githubusercontent.com/RendhikaAditya/MandiriTechnicalAssessment/master/ss_detail_mv.png" width="190" alt="Preview Aplikasi">  
  <img src="https://raw.githubusercontent.com/RendhikaAditya/MandiriTechnicalAssessment/master/ss_ulasan.png" width="190" alt="Preview Aplikasi"> 
  <img src="https://raw.githubusercontent.com/RendhikaAditya/MandiriTechnicalAssessment/master/ss_yt_view.png" width="190" alt="Preview Aplikasi">
  <img src="https://raw.githubusercontent.com/RendhikaAditya/MandiriTechnicalAssessment/master/ss_home_gendre.png" width="190" alt="Preview Aplikasi">  
</div>
