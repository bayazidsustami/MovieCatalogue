package com.the_b.moviecatalogue.api

import com.the_b.moviecatalogue.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*object ApiRepository {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "a81e9d88885bc612f8202ca5bf308441"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/original"

    fun create(): ApiService{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}*/

class ApiRepository{
    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "a81e9d88885bc612f8202ca5bf308441"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/original"

        private val client: OkHttpClient = buildClient()
        private val retrofit: Retrofit = buildRetrofit(client)

        private fun buildClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            var builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .addInterceptor {
                    val url = it.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build()

                    val request = it.request().newBuilder().url(url).build()

                    it.proceed(request)
                }
            if (BuildConfig.DEBUG){
                builder = builder.addInterceptor(loggingInterceptor)
            }

            return builder.build()
        }

        private fun buildRetrofit(httpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun <T> createService(service: Class<T>): T{
            return retrofit.create(service)
        }
    }
}