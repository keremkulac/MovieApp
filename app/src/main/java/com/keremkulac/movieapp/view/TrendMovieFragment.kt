package com.keremkulac.movieapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.PopularMovieAdapter
import com.keremkulac.movieapp.adapter.TrendMovieAdapter
import com.keremkulac.movieapp.databinding.FragmentTrendMovieBinding
import com.keremkulac.movieapp.viewmodel.TrendMovieViewModel

class TrendMovieFragment : Fragment() {
    private lateinit var binding: FragmentTrendMovieBinding
    private lateinit var viewModel : TrendMovieViewModel
    private lateinit var adapter : TrendMovieAdapter
    private lateinit var trendMovieList : ArrayList<Movie>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrendMovieBinding.inflate(inflater)
        viewModel = TrendMovieViewModel()
        trendMovieList = ArrayList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
       // setSearchMenu()
    }

    private fun observeLiveData(){
        viewModel.trendMovies.observe(viewLifecycleOwner, Observer { trendList->
            adapter = TrendMovieAdapter(requireActivity(),trendList)
            binding.trendMovieRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            binding.trendMovieRecyclerView.setHasFixedSize(true)
            binding.trendMovieRecyclerView.adapter = adapter
            trendMovieList.addAll(trendList)
        })
    }

    private fun setSearchMenu(){
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.actionSearch -> {
                        val searchView = menuItem.actionView as androidx.appcompat.widget.SearchView
                        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return false
                            }
                            override fun onQueryTextChange(newText: String): Boolean {
                                viewModel.filter(newText,trendMovieList,adapter)
                                return true
                            }
                        })
                        true
                    }
                    else -> false
                }
            }


        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}