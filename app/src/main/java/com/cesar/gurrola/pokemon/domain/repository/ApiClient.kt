package com.cesar.gurrola.pokemon.domain.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://pokeapi.co/api/v2/"


class ApiClient {

    /*private val logging = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder().also {
        it.addInterceptor(logging)
    }*/


    private val apiClient by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun provideService(): ApiService {
        return apiClient.create(ApiService::class.java)
    }

    fun providePokemonDetailService() : PokemonDetailService {
        return apiClient.create(PokemonDetailService::class.java)
    }
}