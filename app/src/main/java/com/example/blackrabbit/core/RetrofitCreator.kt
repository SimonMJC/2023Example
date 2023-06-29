package com.example.blackrabbit.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitCreator {


    private fun cocktailRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(UrlUtils.COCKTAIL_MAIN_API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    private fun toiletRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(UrlUtils.FIND_PUBLIC_TOILET_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().build())
            }
            .build()
    }

    fun <T> createV1(service: Class<T>): T {
        return cocktailRetrofit().create(service)
    }

    fun <T> createToiletRetrofit(service: Class<T>): T {
        return toiletRetrofit().create(service)
    }

}