package com.keremkulac.movieapp.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.adapter.SearchGenreAdapter
import com.keremkulac.movieapp.adapter.SearchAdapter
import com.keremkulac.movieapp.databinding.FragmentSearchBinding
import com.keremkulac.movieapp.viewmodel.SearchViewModel


class SearchFragment(var movieList : ArrayList<Movie>) : Fragment(),SearchGenreAdapter.ClickListener {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var viewModel : SearchViewModel
    private lateinit var list : ArrayList<Movie>
    private lateinit var adapter : SearchAdapter
    private lateinit var adapter2 : SearchGenreAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentSearchBinding.inflate(inflater)
       (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        list = ArrayList()
        viewModel = SearchViewModel()
       // viewModel.combineMovies()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchMenu()

    }



   private fun createMovieRecyclerView(list : ArrayList<Movie>){

       adapter = SearchAdapter(requireActivity(),list)
       binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
       binding.searchRecyclerView.adapter = adapter

   }
    private fun createGenreRecyclerView(list : ArrayList<String>){
        adapter2 = SearchGenreAdapter(this,list)
        binding.movieGenresRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.movieGenresRecyclerView.adapter = adapter2

    }



    private fun setSearchMenu(){
        binding.searchView.setOnCloseListener {
          //  Log.d("tag12", "kapatıldı")
         //   viewModel.setViewPager(binding.viewPager,binding.tabs,requireActivity().supportFragmentManager,viewLifecycleOwner, arrayListOf())

            false
        }

        binding.searchView.setOnSearchClickListener {
            viewModel.combineMovies()
            createGenreRecyclerView(viewModel.getNames())
        }
        binding.searchView.setOnClickListener{
         //  Log.d("TAGG","ACILDI")
         //  viewModel.setViewPager(binding.viewPager,binding.tabs,requireActivity().supportFragmentManager,viewLifecycleOwner)
       }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
             //  viewModel.setViewPager(binding.viewPager,binding.tabs,requireActivity().supportFragmentManager,viewLifecycleOwner)
                return true
            }

        })
        /*
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
                              //  binding.searchRecyclerView.visibility =  View.VISIBLE
                                viewModel.combineMovies(binding.viewPager,binding.tabs,requireActivity().supportFragmentManager,viewLifecycleOwner)


                                return true
                            }

                            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                             //   binding.searchRecyclerView.visibility = View.GONE
                                return true
                            }
                        })

                        val searchView = menuItem.actionView as androidx.appcompat.widget.SearchView
                        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return false
                            }
                            override fun onQueryTextChange(newText: String): Boolean {
                             //   binding.searchRecyclerView.visibility =  View.VISIBLE
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

         */

    }

    override fun ClickedItem(genre: String) {
        Log.d("W4343",genre)

        val list = viewModel.getGenre()[genre]!!
        createMovieRecyclerView(list)
        Log.d("4334534",viewModel.getGenre()[genre]!!.size.toString())
    }
}