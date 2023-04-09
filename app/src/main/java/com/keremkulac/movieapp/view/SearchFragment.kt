package com.keremkulac.movieapp.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.SearchGenreAdapter
import com.keremkulac.movieapp.adapter.SearchAdapter
import com.keremkulac.movieapp.databinding.FragmentSearchBinding
import com.keremkulac.movieapp.viewmodel.SearchViewModel


class SearchFragment : Fragment(),SearchGenreAdapter.ClickListener,SearchAdapter.ClickListener {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var viewModel : SearchViewModel
    private lateinit var searchAdapter : SearchAdapter
    private lateinit var genreAdapter : SearchGenreAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentSearchBinding.inflate(inflater)
     //  (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        viewModel = SearchViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchMenu()
    }

   private fun createMovieRecyclerView(list : ArrayList<Movie>){
       searchAdapter = SearchAdapter(this,list)
       binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
       binding.searchRecyclerView.adapter = searchAdapter

   }
    private fun createGenreRecyclerView(list : ArrayList<String>, genreWithSizeList : ArrayList<String>){
        genreAdapter = SearchGenreAdapter(this,list,genreWithSizeList)
        binding.movieGenresRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.movieGenresRecyclerView.adapter = genreAdapter
    }


    private fun setSearchMenu(){
        binding.searchView.setOnSearchClickListener {
            binding.movieGenresRecyclerView.visibility = View.VISIBLE
            viewModel.combineMovies()
            val list = viewModel.getNames()
            viewModel.genreWithSize.observe(viewLifecycleOwner) {
                createGenreRecyclerView(list, it)
            }
            createMovieRecyclerView(viewModel.allMovieListHm["All"]!!)
        }
        binding.searchView.setOnCloseListener {
            viewModel.clearList()
            binding.movieGenresRecyclerView.visibility = View.INVISIBLE
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

    override fun ClickedItem(genre: String) {
        val list = viewModel.getGenre()[genre]!!
        createMovieRecyclerView(list)
        if(viewModel.getGenre()[genre]!!.size == 0){
            binding.movieNotFound.visibility = View.VISIBLE
        }else{
            binding.movieNotFound.visibility = View.INVISIBLE
        }
    }

    override fun ClickedMovieItem(movie: Movie) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val args = Bundle()
        val searchMovieDetailFragment = SearchMovieDetailFragment()
        args.putSerializable("movie",movie)
        searchMovieDetailFragment.arguments = args
        fragmentTransaction.replace(R.id.searchFrameLayout,searchMovieDetailFragment)
        fragmentTransaction.commit()
    }
}