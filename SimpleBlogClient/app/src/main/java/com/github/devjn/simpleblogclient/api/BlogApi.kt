package com.github.devjn.simpleblogclient.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object BlogApi {

    private const val BASE_URL = "http://192.168.1.100:8080/"

    private val okHttp: OkHttpClient = OkHttpClient.Builder().build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    @JvmStatic
    fun changeApiBaseUrl(newApiBaseUrl: String) {
        builder.baseUrl(newApiBaseUrl)
    }

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.build()
        return retrofit.create(serviceClass)
    }

}