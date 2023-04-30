package com.keremkulac.movieapp.ui.movie

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.MovieAdapter
import com.keremkulac.movieapp.databinding.FragmentMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(),MovieAdapter.ClickListener {

    private lateinit var binding : FragmentMovieBinding
    private lateinit var trendAdapter : MovieAdapter
    private lateinit var popularAdapter : MovieAdapter
    private lateinit var upcomingAdapter : MovieAdapter
    private  val viewModel by viewModels<MovieViewModel>()
    private  var popularMovie : Movie? = null
    private var randomNumber : Int = 0
    private lateinit var navController : NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieBinding.inflate(inflater)
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        navController = navHostFragment.navController
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        popularPosterClick()
    }

    private fun observeLiveData(){
        viewModel.popularMovies.observe(viewLifecycleOwner) { popularList ->
            popularAdapter = MovieAdapter(this,popularList)
            binding.popularMovieRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.popularMovieRecyclerView.setHasFixedSize(true)
            binding.popularMovieRecyclerView.adapter = popularAdapter
            randomNumber = kotlin.random.Random.nextInt(0, popularList.size)
            popularMovie = popularList[randomNumber]
            popularMovie?.let {
                if (popularMovie!!.backdrop_path != null && !popularMovie!!.adult!!) {
                    popularMovie!!.backdrop_path?.let { it1 ->
                        binding.popularMoviePoster.downloadFromUrl(
                            it1,
                            placeHolderProgressBar(requireContext())
                        )
                    }
                    binding.popularMoviePoster.visibility = View.VISIBLE
                }
            }
        }
        viewModel.trendMovies.observe(viewLifecycleOwner){trendMovies->
            trendAdapter = MovieAdapter(this,trendMovies)
            binding.trendMovieRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.trendMovieRecyclerView.setHasFixedSize(true)
            binding.trendMovieRecyclerView.adapter = trendAdapter
        }

        viewModel.upcomingMovies.observe(viewLifecycleOwner){upcomingMovies->
            upcomingAdapter = MovieAdapter(this,upcomingMovies)
            binding.upcomingMovieRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.upcomingMovieRecyclerView.setHasFixedSize(true)
            binding.upcomingMovieRecyclerView.adapter = upcomingAdapter
        }
    }
    private fun popularPosterClick(){
        binding.popularMoviePoster.setOnClickListener {
            val bundle = bundleOf("movie" to popularMovie)
            navController.navigate(R.id.action_movieFragment_to_movieDetailFragment,bundle)
        }
    }

    override fun ClickedMovieItem(movie: Movie) {
           val bundle = bundleOf("movie" to movie)
        navController.navigate(R.id.action_movieFragment_to_movieDetailFragment,bundle)
    }
}