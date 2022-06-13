package com.cesar.gurrola.pokemon.presentation.utils

import com.cesar.gurrola.pokemon.domain.models.PokemonListItem

interface onPokemonClickListener {
    fun onItemCLickListener(item: PokemonListItem)
}