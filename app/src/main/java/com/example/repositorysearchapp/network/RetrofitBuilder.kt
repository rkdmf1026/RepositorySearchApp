package com.example.repositorysearchapp.network

import com.example.repositorysearchapp.network.api.GitApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://api.github.com/"

    private val interceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

    private val headerInterceptor = Interceptor {
        val request = it.request()
            .newBuilder()
            .addHeader("accept", "application/vnd.github.v3+jso")
            .build()
        return@Interceptor it.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val gitService: GitApi by lazy {
        retrofit.create(GitApi::class.java)
    }
}
