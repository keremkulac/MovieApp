package com.keremkulac.movieapp.view

import android.os.Bundle
import android.util.Log
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
import com.keremkulac.movieapp.databinding.FragmentPopularMovieBinding
import com.keremkulac.movieapp.viewmodel.PopularMovieViewModel

class PopularMovieFragment : Fragment() {

    private lateinit var binding : FragmentPopularMovieBinding
    private lateinit var adapter : PopularMovieAdapter
    private lateinit var viewModel : PopularMovieViewModel
    private lateinit var popularMovieList : ArrayList<Movie>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPopularMovieBinding.inflate(inflater)
      viewModel = PopularMovieViewModel()

        popularMovieList = ArrayList()
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
      //  setSearchMenu()
    }

    private fun observeLiveData(){
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { popularList->
            adapter = PopularMovieAdapter(requireActivity(),popularList)

            binding.popularMovieRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            binding.popularMovieRecyclerView.setHasFixedSize(true)
            binding.popularMovieRecyclerView.adapter = adapter
            popularMovieList.addAll(popularList)
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
                                viewModel.filter(newText,popularMovieList,adapter)
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