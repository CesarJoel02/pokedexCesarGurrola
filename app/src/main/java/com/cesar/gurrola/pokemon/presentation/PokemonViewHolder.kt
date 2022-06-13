package com.cesar.gurrola.pokemon.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cesar.gurrola.pokemon.databinding.PokemonItemGridBinding
import com.cesar.gurrola.pokemon.databinding.PokemonItemListBinding
import com.cesar.gurrola.pokemon.domain.models.PokemonListItem

class PokemonViewHolder(view: View, private val isList: Boolean) : RecyclerView.ViewHolder(view) {

    private val binding = if (isList)
        PokemonItemListBinding.bind(view)
    else
        PokemonItemGridBinding.bind(view)

    fun bind(item: PokemonListItem) {
        if (isList)
            (binding as PokemonItemListBinding).textPokemonName.text = item.name
        else
            (binding as PokemonItemGridBinding).textPokemonName.text = item.name
    }
}