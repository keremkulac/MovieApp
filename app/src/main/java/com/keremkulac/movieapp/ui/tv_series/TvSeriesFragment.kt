package com.keremkulac.movieapp.ui.tv_series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.TvSeriesAdapter
import com.keremkulac.movieapp.databinding.FragmentTvSeriesBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TvSeriesFragment @Inject constructor(
    private val popularAdapter: TvSeriesAdapter,
    private val topRatedAdapter: TvSeriesAdapter) : Fragment(R.layout.fragment_tv_series) {

    private val viewModel  by viewModels<TvSeriesViewModel>()
    private  lateinit var binding : FragmentTvSeriesBinding
    private  var popularTvSeries : Movie? = null
    private var randomNumber : Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTvSeriesBinding.bind(view)
        observeLiveData()
        popularPosterClick()
    }

    private fun setupPopularTvSeriesPoster(popularList : ArrayList<Movie>){
        randomNumber = kotlin.random.Random.nextInt(0, popularList.size)
        popularTvSeries = popularList[randomNumber]
        popularTvSeries?.let {
            it.backdrop_path.let {
                popularTvSeries!!.backdrop_path?.let { it1 ->
                    binding.popularTvSeriesPoster.downloadFromUrl(it1, placeHolderProgressBar(requireContext()))
                }
            }
        }
    }
    private fun observeLiveData(){
        viewModel.popularTvSeriesList.observe(viewLifecycleOwner) { popularList ->
            popularList.data?.movies.let {
                popularAdapter.tvSeriesList = it!!.toList()
                setupPopularTvSeriesPoster(it)
            }
            binding.popularTvSeriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.popularTvSeriesRecyclerView.setHasFixedSize(true)
            binding.popularTvSeriesRecyclerView.adapter = popularAdapter
            popularAdapter.onItemClick={
                navigateToMovieDetailFragment(it)
            }
        }

        viewModel.topRatedTvSeriesList.observe(viewLifecycleOwner){topRated->
            topRated.data?.movies.let {
                topRatedAdapter.tvSeriesList = it!!.toList()
            }
            binding.topRatedTvSeriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.topRatedTvSeriesRecyclerView.setHasFixedSize(true)
            binding.topRatedTvSeriesRecyclerView.adapter = topRatedAdapter
            topRatedAdapter.onItemClick={
                navigateToMovieDetailFragment(it)
            }
        }
    }

    private fun popularPosterClick(){
            binding.popularTvSeriesPoster.setOnClickListener {
                val bundle = bundleOf("movie" to popularTvSeries)
                findNavController().navigate(R.id.action_tvSeriesFragment_to_movieDetailFragment2,bundle)
                }
    }
    private fun navigateToMovieDetailFragment(movie: Movie){
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_tvSeriesFragment_to_movieDetailFragment2,bundle)
    }

}
