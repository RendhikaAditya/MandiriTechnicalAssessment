package co.id.tesmandiritmdb.network


import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "92563a19d31b9b68a52b985ca0c6bcba"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_URL = "https://image.tmdb.org/t/p/w342"
const val THUMNAIL_YT = "https://img.youtube.com/vi/"
const val YT_URL = "https://www.youtube.com/watch?v="

object ApiService {
    fun getClient(): EndPoin {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val requestInterceptor = Interceptor{ chain ->
            val url = chain.request().url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder().serializeNulls().create()

        return Retrofit.Builder()
            .baseUrl( BASE_URL )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(EndPoin::class.java)
    }



}