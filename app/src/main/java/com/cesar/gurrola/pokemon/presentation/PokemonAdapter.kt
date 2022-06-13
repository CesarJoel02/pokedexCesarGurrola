package com.cesar.gurrola.pokemon.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cesar.gurrola.pokemon.R
import com.cesar.gurrola.pokemon.domain.models.PokemonListItem
import com.cesar.gurrola.pokemon.domain.models.PokemonListResponseModel
import com.cesar.gurrola.pokemon.presentation.utils.onPokemonClickListener

class PokemonAdapter(
    private val pokemonListModel: PokemonListResponseModel,
    private val onPokemonClickListener: onPokemonClickListener,
    private val isList: Boolean = true
): RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layout = if (isList) R.layout.pokemon_item_list else R.layout.pokemon_item_grid
        return PokemonViewHolder(layoutInflater.inflate(layout, parent, false), isList)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = pokemonListModel.results[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onPokemonClickListener.onItemCLickListener(item)
        }
    }

    override fun getItemCount(): Int = pokemonListModel.results.size

    fun addPokemon(pokemon: PokemonListItem){
        pokemonListModel.results.add(pokemon)
        notifyItemInserted(pokemonListModel.results.size -1 )
    }

    fun addAll(pokemonList : MutableList<PokemonListItem>){
        pokemonListModel.results.addAll(pokemonList)
        notifyDataSetChanged()
    }
}