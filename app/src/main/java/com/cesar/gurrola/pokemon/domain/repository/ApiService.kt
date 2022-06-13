package com.cesar.gurrola.pokemon.domain.repository

import com.cesar.gurrola.pokemon.domain.models.PokemonListResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon/")
    fun fetchPokemonData(
       @Query("limit") limit: Int = 0,
       @Query("offset") offset: Int = 0,
    ): Observable<PokemonListResponseModel>
}