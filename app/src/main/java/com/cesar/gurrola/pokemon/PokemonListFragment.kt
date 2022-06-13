package com.cesar.gurrola.pokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cesar.gurrola.pokemon.databinding.PokemonListFragmentBinding
import com.cesar.gurrola.pokemon.domain.models.PokemonListItem
import com.cesar.gurrola.pokemon.domain.models.PokemonListResponseModel
import com.cesar.gurrola.pokemon.domain.repository.ApiClient
import com.cesar.gurrola.pokemon.presentation.PokemonAdapter
import com.cesar.gurrola.pokemon.presentation.utils.onPokemonClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PokemonListFragment : Fragment(), onPokemonClickListener {

    private lateinit var pokemonList: PokemonListResponseModel

    private val binding get() = _binding!!

    private var pokemonAdapter: PokemonAdapter? = null
    private var _binding: PokemonListFragmentBinding? = null
    private var myCompositeDisposable: CompositeDisposable? = null
    private var isList = true
    private var paginationLimit = 0
    private var pokemonOffset = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PokemonListFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        fetchData()
        (activity as MainActivity).supportActionBar?.title = "Pokedex"

        binding.recyclerPokemonList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    fetchData()
                }
            }
        })
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        if (pokemonAdapter != null) {
            initRecyclerView()
        }
    }

    fun initRecyclerView() {
        binding.recyclerPokemonList.adapter = pokemonAdapter
        if (isList)
            binding.recyclerPokemonList.layoutManager = LinearLayoutManager(requireContext())
        else
            binding.recyclerPokemonList.layoutManager = GridLayoutManager(requireContext(), 2)
    }


    fun changeLayout() {
        isList = !isList
        setLayoutManager()
        binding.recyclerPokemonList.adapter = pokemonAdapter
    }

    fun fetchData() {

        val req = ApiClient().provideService().fetchPokemonData(paginationLimit, pokemonOffset)
        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(
            req
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("Error", "${it.localizedMessage}") }
                .subscribe(this::handleData)
                { t: Throwable -> Log.e("ListFragment", "error : ${t.localizedMessage}") })

    }

    fun handleData(pokemonData: PokemonListResponseModel) {
        if (pokemonOffset == 0) {
            pokemonList = pokemonData
            pokemonAdapter = PokemonAdapter(pokemonList, this, isList)
            binding.recyclerPokemonList.adapter = pokemonAdapter
            binding.recyclerPokemonList.layoutManager = LinearLayoutManager(requireContext())
        } else {
            pokemonList.results.addAll(pokemonData.results)
            pokemonAdapter?.addAll(pokemonData.results)
        }
        pokemonOffset += 20
    }

    fun setLayoutManager() {
        val linearLayout = LinearLayoutManager(requireContext())
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        val gridLayout = GridLayoutManager(requireContext(), 2)
        if (isList)
            binding.recyclerPokemonList.layoutManager = linearLayout
        else
            binding.recyclerPokemonList.layoutManager = gridLayout
    }

    override fun onItemCLickListener(item: PokemonListItem) {
        val bundle = Bundle()
        bundle.putString("url", item.url)
        bundle.putString("name", item.name)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.dispose()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                changeLayout()
                if (isList)
                    item.icon = activity!!.getDrawable(R.drawable.ic_list_layout)
                else
                    item.icon = activity!!.getDrawable(R.drawable.ic_grid_layout)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}