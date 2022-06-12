package com.cesar.gurrola.pokemon.domain.repository

import com.cesar.gurrola.pokemon.domain.models.pokemonListResponse
import io.reactivex.Observable
import retrofit2.http.GET

private const val BASE_URL = "https://pokeapi.co/api/v2/"

interface ApiService {

    @GET("/")
    fun fetchPokemonData(): Observable<pokemonListResponse>

}