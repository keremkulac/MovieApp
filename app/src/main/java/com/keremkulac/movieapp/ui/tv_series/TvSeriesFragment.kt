package com.keremkulac.movieapp.ui.tv_series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


@AndroidEntryPoint
class TvSeriesFragment : Fragment(), TvSeriesAdapter.ClickListener {
    private val viewModel  by viewModels<TvSeriesViewModel>()
    private lateinit var binding : FragmentTvSeriesBinding
    private lateinit var popularAdapter : TvSeriesAdapter
    private lateinit var topRatedAdapter : TvSeriesAdapter
    private  var popularTvSeries : Movie? = null
    private var randomNumber : Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTvSeriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        popularPosterClick()
    }

    private fun observeLiveData(){
        viewModel.popularTvSeries.observe(viewLifecycleOwner) { popularList ->
            popularAdapter = TvSeriesAdapter(this,popularList)
            binding.popularTvSeriesRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.popularTvSeriesRecyclerView.setHasFixedSize(true)
            binding.popularTvSeriesRecyclerView.adapter = popularAdapter
            randomNumber = kotlin.random.Random.nextInt(0, popularList.size)
            popularTvSeries = popularList[randomNumber]
            popularTvSeries?.let {
                it.backdrop_path.let {
                    popularTvSeries!!.backdrop_path?.let { it1 ->
                        binding.popularTvSeriesPoster.downloadFromUrl(
                            it1,
                            placeHolderProgressBar(requireContext())
                        )
                    }
                }
            }

        }

        viewModel.topRatedTvSeries.observe(viewLifecycleOwner){topRated->
            topRatedAdapter = TvSeriesAdapter(this,topRated)
            binding.topRatedTvSeriesRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.topRatedTvSeriesRecyclerView.setHasFixedSize(true)
            binding.topRatedTvSeriesRecyclerView.adapter = topRatedAdapter
        }
    }

    private fun popularPosterClick(){
            binding.popularTvSeriesPoster.setOnClickListener {
                val bundle = bundleOf("movie" to popularTvSeries)
                findNavController().navigate(R.id.action_tvSeriesFragment_to_movieDetailFragment2,bundle)
                }
            }

    override fun ClickedTvSeriesItem(movie: Movie) {
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_tvSeriesFragment_to_movieDetailFragment2,bundle)
    }
}
