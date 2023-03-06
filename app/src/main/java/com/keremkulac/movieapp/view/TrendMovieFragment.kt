package com.keremkulac.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.PopularMovieAdapter
import com.keremkulac.movieapp.adapter.TrendMovieAdapter
import com.keremkulac.movieapp.databinding.FragmentTrendMovieBinding
import com.keremkulac.movieapp.viewmodel.TrendMovieViewModel

class TrendMovieFragment : Fragment() {
    private lateinit var binding: FragmentTrendMovieBinding
    private lateinit var viewModel : TrendMovieViewModel
    private lateinit var adapter : TrendMovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrendMovieBinding.inflate(inflater)
        viewModel = TrendMovieViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.trendMovies.observe(viewLifecycleOwner, Observer { trendList->
            adapter = TrendMovieAdapter(requireActivity(),trendList)
            binding.trendMovieRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            binding.trendMovieRecyclerView.adapter = adapter
        })
    }

}