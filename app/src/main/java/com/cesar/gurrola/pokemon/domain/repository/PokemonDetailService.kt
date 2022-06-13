package com.cesar.gurrola.pokemon.domain.repository

import com.cesar.gurrola.pokemon.domain.models.PokemonDetailModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonDetailService {


    @GET("pokemon/{name}")
    fun getPokemonDetails(
        @Path("name") name : String
    ) : Observable<PokemonDetailModel>

}