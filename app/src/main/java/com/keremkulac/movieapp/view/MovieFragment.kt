package com.keremkulac.movieapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.service.MovieApiImp
import com.keremkulac.movieapp.PopularMovies
import com.keremkulac.movieapp.adapter.PopularMovieAdapter
import com.keremkulac.movieapp.databinding.FragmentMovieBinding
import com.keremkulac.movieapp.viewmodel.MovieViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieFragment : Fragment() {

    private lateinit var binding : FragmentMovieBinding
    private lateinit var adapter : PopularMovieAdapter
    private lateinit var viewModel : MovieViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentMovieBinding.inflate(inflater)
      viewModel = MovieViewModel()

      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { popularList->
            adapter = PopularMovieAdapter(requireActivity(),popularList)
            binding.popularMovieRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            binding.popularMovieRecyclerView.adapter = adapter
        })
    }

}