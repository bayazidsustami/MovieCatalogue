package com.the_b.moviecatalogue.api

import com.the_b.moviecatalogue.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {

    const val IMAGE_URL = "https://image.tmdb.org/t/p/w154"

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
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
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
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(service: Class<T>): T{
        return retrofit.create(service)
    }
}