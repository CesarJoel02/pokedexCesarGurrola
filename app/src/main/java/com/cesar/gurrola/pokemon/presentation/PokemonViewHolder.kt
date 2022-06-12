package com.cesar.gurrola.pokemon.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cesar.gurrola.pokemon.databinding.PokemonItemBinding
import com.cesar.gurrola.pokemon.domain.models.PokemonListItem

class PokemonViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding =  PokemonItemBinding.bind(view)

    fun bind(item : PokemonListItem){
        binding.textPokemonName.text = item.name
    }
}