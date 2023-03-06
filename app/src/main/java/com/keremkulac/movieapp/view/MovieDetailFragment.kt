package com.keremkulac.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.databinding.FragmentMovieDetailBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar


class MovieDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMovieDetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  arg =  requireArguments().getSerializable("movie") as Movie
      //  binding.movieReleaseDate.text = arg.release_date?.let { splitDate(it) }
        if(arg.release_date == null){
            binding.movieReleaseDate.text = "Unknown"
        }else{
            binding.movieReleaseDate.text = splitDate(arg.release_date)
        }
        binding.movieOverview.text = arg.overview
        if(arg.original_title == null){
            binding.movieName.text = arg.name
        }else{
            binding.movieName.text = arg.original_title

        }
        binding.movieImage.downloadFromUrl(arg.poster_path, placeHolderProgressBar(requireContext()))
        binding.movieRate.text = arg.vote_average.toString()
    }

    private fun splitDate(date : String): String{
        val list = date.split("-")
        return list.get(0)
    }
}