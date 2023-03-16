package com.keremkulac.movieapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.adapter.PopularMovieAdapter
import com.keremkulac.movieapp.databinding.FragmentPopularMovieBinding
import com.keremkulac.movieapp.viewmodel.PopularMovieViewModel

class PopularMovieFragment : Fragment() {

    private lateinit var binding : FragmentPopularMovieBinding
    private lateinit var adapter : PopularMovieAdapter
    private lateinit var viewModel : PopularMovieViewModel
    private lateinit var popularMovieList : ArrayList<Movie>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPopularMovieBinding.inflate(inflater)
      viewModel = PopularMovieViewModel()

        popularMovieList = ArrayList()
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.popularMovies.observe(viewLifecycleOwner) { popularList ->
            adapter = PopularMovieAdapter(requireActivity(), popularList)

            binding.popularMovieRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.popularMovieRecyclerView.setHasFixedSize(true)
            binding.popularMovieRecyclerView.adapter = adapter
            popularMovieList.addAll(popularList)
        }
    }
}