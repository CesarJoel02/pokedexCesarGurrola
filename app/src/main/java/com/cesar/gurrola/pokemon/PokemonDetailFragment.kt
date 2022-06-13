package com.cesar.gurrola.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.MenuInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cesar.gurrola.pokemon.databinding.PokemonDetailFragmentBinding
import com.cesar.gurrola.pokemon.domain.models.PokemonDetailModel
import com.cesar.gurrola.pokemon.domain.repository.ApiClient
import com.cesar.gurrola.pokemon.presentation.utils.Capitalize
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PokemonDetailFragment : Fragment() {

    private var myCompositeDisposable: CompositeDisposable? = null

    private var _binding: PokemonDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private var pokemonUrl = ""
    private var pokemonName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PokemonDetailFragmentBinding.inflate(inflater, container, false)
        if (arguments != null) {
            pokemonUrl = arguments!!.getString("url")!!
            pokemonName = arguments!!.getString("name")!!
        }
        initToolbar()
        fetchData()
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initToolbar() {
        (activity as MainActivity).getToolbar().title = pokemonName.replaceFirstChar {
            it.uppercase()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        myCompositeDisposable?.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.setGroupVisible(View.INVISIBLE, true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_settings)
        if (item != null){
            item.isVisible = false
        }
    }

    fun fetchData() {
        val req = ApiClient().providePokemonDetailService().getPokemonDetails(pokemonName)
        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(
            req
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { handleData(it) }
        )
    }

    private fun handleData(pokemonData: PokemonDetailModel) {
        binding.pokemonData = pokemonData
        getPokemonImage(pokemonData)
        val types = pokemonData.types.joinToString { types ->
            types.type.name.Capitalize()
        }
        val abilities = pokemonData.abilities.joinToString { abilities ->
            abilities.ability.name.Capitalize()
        }
        if (pokemonData.types.size >= 2) {
            binding.labelType.text = "Types :"
        }
        binding.textTypes.text = types
        binding.textAbilities.text = abilities
        binding.pokemonId = String.format("%03d", pokemonData.id)
    }

    private fun getPokemonImage(id: PokemonDetailModel) {
        val url = "https://cdn.traction.one/pokedex/pokemon/${id.id}.png"
        Picasso.get().load(url).into(binding.pokemonSprite)
    }


}