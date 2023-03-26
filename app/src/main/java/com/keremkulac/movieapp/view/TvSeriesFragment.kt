package com.keremkulac.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.adapter.TvSeriesAdapter
import com.keremkulac.movieapp.databinding.FragmentTvSeriesBinding
import com.keremkulac.movieapp.viewmodel.TvSeriesViewModel


class TvSeriesFragment : Fragment() {

    private lateinit var binding : FragmentTvSeriesBinding
    private lateinit var viewModel : TvSeriesViewModel
    private lateinit var popularAdapter : TvSeriesAdapter
    private lateinit var topRatedAdapter : TvSeriesAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTvSeriesBinding.inflate(inflater)
        viewModel = TvSeriesViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.popularTvSeries.observe(viewLifecycleOwner) { popularList ->
            popularAdapter = TvSeriesAdapter(requireActivity(), popularList)
            binding.popularTvSeriesRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.popularTvSeriesRecyclerView.setHasFixedSize(true)
            binding.popularTvSeriesRecyclerView.adapter = popularAdapter

        }

        viewModel.topRatedTvSeries.observe(viewLifecycleOwner){topRated->
            topRatedAdapter = TvSeriesAdapter(requireActivity(),topRated)
            binding.topRatedTvSeriesRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.topRatedTvSeriesRecyclerView.setHasFixedSize(true)
            binding.topRatedTvSeriesRecyclerView.adapter = topRatedAdapter
        }
    }

}