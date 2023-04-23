package com.keremkulac.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.TvSeriesAdapter
import com.keremkulac.movieapp.databinding.FragmentTvSeriesBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import com.keremkulac.movieapp.viewmodel.TvSeriesViewModel


class TvSeriesFragment : Fragment() {

    private lateinit var binding : FragmentTvSeriesBinding
    private lateinit var viewModel : TvSeriesViewModel
    private lateinit var popularAdapter : TvSeriesAdapter
    private lateinit var topRatedAdapter : TvSeriesAdapter
    private  var popularTvSeries : Movie? = null
    private var randomNumber : Int = 0
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTvSeriesBinding.inflate(inflater)
        viewModel = TvSeriesViewModel()
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
        viewModel.popularTvSeries.observe(viewLifecycleOwner) { popularList ->
            popularAdapter = TvSeriesAdapter(popularList)
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
            topRatedAdapter = TvSeriesAdapter(topRated)
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
                it.findNavController().navigate(R.id.action_tvSeriesFragment_to_movieDetailFragment2,bundle)
                }
            }
    }
