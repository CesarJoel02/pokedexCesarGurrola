package com.cesar.gurrola.pokemon.domain.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val apiClient by lazy {
        Retrofit.Builder()
            .baseUrl("https://img.medscapestatic.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideService(): ApiService {
        return apiClient.create(ApiService::class.java)
    }
}