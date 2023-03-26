package com.keremkulac.movieapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.adapter.MovieAdapter
import com.keremkulac.movieapp.databinding.FragmentMovieBinding
import com.keremkulac.movieapp.viewmodel.MovieViewModel

class MovieFragment : Fragment() {

    private lateinit var binding : FragmentMovieBinding
    private lateinit var trendAdapter : MovieAdapter
    private lateinit var popularAdapter : MovieAdapter
    private lateinit var upcomingAdapter : MovieAdapter
    private lateinit var viewModel : MovieViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieBinding.inflate(inflater)
      viewModel = MovieViewModel()
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.popularMovies.observe(viewLifecycleOwner) { popularList ->
            popularAdapter = MovieAdapter(requireActivity(), popularList)
            binding.popularMovieRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.popularMovieRecyclerView.setHasFixedSize(true)
            binding.popularMovieRecyclerView.adapter = popularAdapter

        }
        viewModel.trendMovies.observe(viewLifecycleOwner){trendMovies->
            trendAdapter = MovieAdapter(requireActivity(),trendMovies)
            binding.trendMovieRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.trendMovieRecyclerView.setHasFixedSize(true)
            binding.trendMovieRecyclerView.adapter = trendAdapter
        }

        viewModel.upcomingMovies.observe(viewLifecycleOwner){upcomingMovies->
            upcomingAdapter = MovieAdapter(requireActivity(),upcomingMovies)
            binding.upcomingMovieRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.upcomingMovieRecyclerView.setHasFixedSize(true)
            binding.upcomingMovieRecyclerView.adapter = upcomingAdapter
        }
    }
}