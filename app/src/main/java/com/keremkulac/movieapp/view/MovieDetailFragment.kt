package com.keremkulac.movieapp.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.databinding.FragmentMovieDetailBinding
import com.keremkulac.movieapp.model.Genre
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import com.keremkulac.movieapp.viewmodel.MovieDetailViewModel


class MovieDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMovieDetailBinding
    private lateinit var viewModel : MovieDetailViewModel
    private var genres = ArrayList<Genre>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        viewModel = MovieDetailViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  arg =  requireArguments().getSerializable("movie") as Movie
        observeLiveData(arg.genre_ids,binding.movieGenres)
        binding.movieOverview.movementMethod= ScrollingMovementMethod()
        Log.d("TAG",arg.genre_ids.toString())
        if(arg.release_date == null){
            binding.movieReleaseDate.text = "Unknown"
        }else{
            binding.movieReleaseDate.text = viewModel.splitDate(arg.release_date)
        }
        binding.movieOverview.text = arg.overview
        if(arg.original_title == null){
            binding.movieName.text = arg.name
        }else{
            binding.movieName.text = arg.original_title

        }

        binding.movieImage.downloadFromUrl(arg.poster_path, placeHolderProgressBar(requireContext()))
        binding.movieRate.text = viewModel.vote(arg.vote_average.toString())
    }



    private fun observeLiveData(list : ArrayList<Int>,textView : TextView){
        viewModel.genres.observe(viewLifecycleOwner) { Genres ->
            val builder = StringBuilder()
            genres = Genres
            for (item in list) {
                for (genreItem in genres) {
                    if (genreItem.id == item) {
                        builder.append(genreItem.name)
                        builder.append("  ")
                    }
                }
            }
            textView.text = builder.toString()
        }
    }
}