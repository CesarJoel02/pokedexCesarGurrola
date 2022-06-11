package com.cesar.gurrola.pokemon.domain.models

data class pokemonListResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)

data class Result(
    val name: String,
    val url: String
)