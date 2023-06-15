package com.keremkulac.movieapp.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.SearchGenreAdapter
import com.keremkulac.movieapp.adapter.SearchAdapter
import com.keremkulac.movieapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment @Inject constructor(
    private val searchAdapter: SearchAdapter,
    private val genreAdapter : SearchGenreAdapter) : Fragment(R.layout.fragment_search){

    private lateinit var binding : FragmentSearchBinding
    private  val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
       setSearchMenu()
        viewModel.getData()

    }


   private fun createMovieRecyclerView(list : ArrayList<Movie>){
       searchAdapter.movies = list
       binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
       binding.searchRecyclerView.adapter = searchAdapter
       searchAdapter.onItemClick = {
           movieClick(it)
       }
   }

    private fun createGenreRecyclerView(genreWithSizeList : ArrayList<String>){
        genreAdapter.list = genreWithSizeList
        binding.movieGenresRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.movieGenresRecyclerView.adapter = genreAdapter
        genreAdapter.onItemClick = {
            genreItemClick(it)
        }
    }

    private fun setSearchMenu(){
        binding.searchView.setOnSearchClickListener {
            binding.movieGenresRecyclerView.visibility = View.VISIBLE
            viewModel.combineMovies()
            viewModel.getNames()
            viewModel.genreWithSize.observe(viewLifecycleOwner) {
                createGenreRecyclerView(it)
            }
            createMovieRecyclerView(viewModel.allMovieListHm["All"]!!)
        }
        binding.searchView.setOnCloseListener {
            viewModel.clearList()
            binding.movieGenresRecyclerView.visibility = View.INVISIBLE
            binding.movieNotFound.visibility = View.INVISIBLE
            false
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filter(newText,searchAdapter)
                viewModel.movieFoundError.observe(viewLifecycleOwner) {
                    if (it) {
                        binding.movieNotFound.visibility = View.VISIBLE
                        binding.searchRecyclerView.visibility = View.INVISIBLE
                    } else {
                        binding.movieNotFound.visibility = View.INVISIBLE
                        binding.searchRecyclerView.visibility = View.VISIBLE
                    }
                }
                return true
            }
        })
    }

   private fun genreItemClick(genre : String){
        val list = viewModel.getGenre()[genre]!!
        createMovieRecyclerView(list)
        if(viewModel.getGenre()[genre]!!.size == 0){
            binding.movieNotFound.visibility = View.VISIBLE
        }else{
            binding.movieNotFound.visibility = View.INVISIBLE
        }
    }

    private fun movieClick(movie : Movie){
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_searchFragment_to_searchMovieDetailFragment,bundle)
    }
}