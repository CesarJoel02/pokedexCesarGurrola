package com.cesar.gurrola.pokemon.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cesar.gurrola.pokemon.R
import com.cesar.gurrola.pokemon.domain.models.pokemonListResponse

class PokemonAdapter(private val pokemonList : pokemonListResponse):
    RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(
            layoutInflater.inflate(R.layout.pokemon_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = pokemonList.results[position]
        holder.bind(item)
        //interfaz para el click
    }

    override fun getItemCount(): Int = pokemonList.count
}