package com.keremkulac.movieapp.ui.movie

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.MovieAdapter
import com.keremkulac.movieapp.databinding.FragmentMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment @Inject constructor(
    private val popularAdapter: MovieAdapter,
    private val trendAdapter: MovieAdapter,
    private val upcomingAdapter: MovieAdapter): Fragment(R.layout.fragment_movie) {

    private lateinit  var binding : FragmentMovieBinding
    private  val viewModel by viewModels<MovieViewModel>()
    private  var popularMovie : Movie? = null
    private var randomNumber : Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        observeLiveData()
        popularPosterClick()
    }

    private fun setPopularMoviePoster(popularList : ArrayList<Movie>){
        randomNumber = kotlin.random.Random.nextInt(0, popularList.size)
        popularMovie = popularList[randomNumber]
        popularMovie?.let {
            if (popularMovie!!.backdrop_path != null && !popularMovie!!.adult!!) {
                popularMovie!!.backdrop_path?.let { it1 ->
                    binding.popularMoviePoster.downloadFromUrl(it1, placeHolderProgressBar(requireContext()))
                }
                binding.popularMoviePoster.visibility = View.VISIBLE
            }
        }
    }


    private fun observeLiveData(){
        viewModel.popularMoviesList.observe(viewLifecycleOwner){popularList->
            popularList.data?.movies.let {
                popularAdapter.movies = it!!.toList()
                setPopularMoviePoster(it)
            }
            binding.popularMovieRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.popularMovieRecyclerView.setHasFixedSize(true)
            binding.popularMovieRecyclerView.adapter = popularAdapter
            popularAdapter.onItemClick = {movie->
                navigateToMovieDetailFragment(movie)
            }
        }

        viewModel.trendMovieList.observe(viewLifecycleOwner){trendMovies->
            trendMovies.data?.movies.let {
                trendAdapter.movies = it!!.toList()
            }
            binding.trendMovieRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.trendMovieRecyclerView.setHasFixedSize(true)
            binding.trendMovieRecyclerView.adapter = trendAdapter
            trendAdapter.onItemClick = {movie->
                navigateToMovieDetailFragment(movie)
            }
        }

        viewModel.upcomingMovieList.observe(viewLifecycleOwner){upcomingMovies->
            upcomingMovies.data?.movies.let {
                upcomingAdapter.movies = it!!.toList()
            }
            binding.upcomingMovieRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.upcomingMovieRecyclerView.setHasFixedSize(true)
            binding.upcomingMovieRecyclerView.adapter = upcomingAdapter
            upcomingAdapter.onItemClick = {movie->
                navigateToMovieDetailFragment(movie)
            }
        }
    }
    private fun popularPosterClick(){
        binding.popularMoviePoster.setOnClickListener {
            val bundle = bundleOf("movie" to popularMovie)
            findNavController().navigate(R.id.action_movieFragment_to_movieDetailFragment,bundle)
        }
    }

    private fun navigateToMovieDetailFragment(movie: Movie){
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_movieFragment_to_movieDetailFragment,bundle)
    }
}