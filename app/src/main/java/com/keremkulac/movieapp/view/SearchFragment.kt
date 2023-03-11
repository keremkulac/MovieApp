package com.keremkulac.movieapp.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.SearchAdapter
import com.keremkulac.movieapp.databinding.FragmentSearchBinding
import com.keremkulac.movieapp.viewmodel.SearchViewModel


class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var viewModel : SearchViewModel
    private lateinit var list : ArrayList<Movie>
    private lateinit var adapter : SearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentSearchBinding.inflate(inflater)
        list = ArrayList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = SearchViewModel()
        adapter = SearchAdapter(requireActivity(),list)
        setSearchMenu()
        createRecyclerView()
    }

   private fun createRecyclerView(){
       binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
       binding.searchRecyclerView.adapter = adapter
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
                        menuItem.setOnActionExpandListener(object :
                            MenuItem.OnActionExpandListener {
                            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                                binding.searchRecyclerView.visibility =  View.VISIBLE
                                viewModel.combineMovies()
                                return true
                            }

                            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                                binding.searchRecyclerView.visibility = View.GONE
                                return true
                            }
                        })

                        val searchView = menuItem.actionView as androidx.appcompat.widget.SearchView
                        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return false
                            }
                            override fun onQueryTextChange(newText: String): Boolean {
                                binding.searchRecyclerView.visibility =  View.VISIBLE
                                viewModel.filter(newText,adapter)
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