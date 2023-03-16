package com.keremkulac.movieapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.adapter.TrendMovieAdapter
import com.keremkulac.movieapp.databinding.FragmentTrendMovieBinding
import com.keremkulac.movieapp.viewmodel.TrendMovieViewModel

class TrendMovieFragment : Fragment() {
    private lateinit var binding: FragmentTrendMovieBinding
    private lateinit var viewModel : TrendMovieViewModel
    private lateinit var adapter : TrendMovieAdapter
    private lateinit var trendMovieList : ArrayList<Movie>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrendMovieBinding.inflate(inflater)
        viewModel = TrendMovieViewModel()
        trendMovieList = ArrayList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.trendMovies.observe(viewLifecycleOwner) { trendList ->
            adapter = TrendMovieAdapter(requireActivity(), trendList)
            binding.trendMovieRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.trendMovieRecyclerView.setHasFixedSize(true)
            binding.trendMovieRecyclerView.adapter = adapter
            trendMovieList.addAll(trendList)
        }
    }

}