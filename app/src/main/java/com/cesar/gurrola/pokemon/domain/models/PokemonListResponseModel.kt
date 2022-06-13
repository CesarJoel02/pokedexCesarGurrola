package com.cesar.gurrola.pokemon.domain.models

data class PokemonListResponseModel(
    val count: Int,
    val next: String,
    val previous: String,
    val results: MutableList<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String
)