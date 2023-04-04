package com.keremkulac.movieapp.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keremkulac.movieapp.LatestMovie
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
    private var tvSeriesGenre = ArrayList<Genre>()
    private var movie : Movie? = null
    private var tvSeries : Movie? = null
    private  var latestMovie : LatestMovie? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        viewModel = MovieDetailViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // val  arg =  requireArguments().getSerializable("movie") as Movie
      getMovie()
        getLatestMovie()
        getTvSeries()
    }



    private fun observeLiveData(list : ArrayList<Int>,textView : TextView){
        viewModel.movieGenres.observe(viewLifecycleOwner) { movieGenres ->
            val builder = StringBuilder()
            genres = movieGenres
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
    private fun observeTvSeriesGenres(list : ArrayList<Int>,textView : TextView){
        viewModel.tvSeriesGenres.observe(viewLifecycleOwner){tvSeriesGenres->
            val builder = StringBuilder()
            tvSeriesGenre = tvSeriesGenres
            for (item in list) {
                for (genreItem in tvSeriesGenres) {
                    if (genreItem.id == item) {
                        builder.append(genreItem.name)
                        builder.append("  ")
                    }
                }
            }
            textView.text = builder.toString()
        }
    }
    private fun getMovie(){
        movie = requireArguments().getSerializable("movie") as Movie?
        if(movie != null){
            observeLiveData(movie!!.genre_ids,binding.movieGenres)
            binding.movieOverview.movementMethod= ScrollingMovementMethod()
            Log.d("TAG", movie!!.genre_ids.toString())
            if(movie!!.release_date == null){
                binding.movieReleaseDate.text = "Unknown"
            }else{
                binding.movieReleaseDate.text = movie!!.release_date?.let { viewModel.splitDate(it) }
            }
            binding.movieOverview.text = movie!!.overview
            if(movie!!.original_title == null){
                binding.movieName.text = movie!!.name
            }else{
                binding.movieName.text = movie!!.original_title

            }

            binding.movieImage.downloadFromUrl(movie!!.poster_path, placeHolderProgressBar(requireContext()))
            binding.movieRate.text = viewModel.vote(movie!!.vote_average.toString())
        }
    }
    private fun getLatestMovie(){
        latestMovie = requireArguments().getSerializable("latestMovie") as LatestMovie?
        if(latestMovie != null){
            binding.movieOverview.movementMethod= ScrollingMovementMethod()
            if(latestMovie!!.release_date == null){
                binding.movieReleaseDate.text = "Unknown"
            }else{
                binding.movieReleaseDate.text = latestMovie!!.release_date?.let { viewModel.splitDate(it) }
            }
            binding.movieOverview.text = latestMovie!!.overview
            if(latestMovie!!.title == null){
                binding.movieName.text = latestMovie!!.original_title
            }else{
                binding.movieName.text = latestMovie!!.title

            }

            binding.movieImage.downloadFromUrl(latestMovie!!.poster_path, placeHolderProgressBar(requireContext()))
            binding.movieRate.text = viewModel.vote(latestMovie!!.vote_average.toString())
        }
    }

    private fun getTvSeries(){
        tvSeries = requireArguments().getSerializable("tvSeries") as Movie?
        if(tvSeries != null){
            observeTvSeriesGenres(tvSeries!!.genre_ids,binding.movieGenres)
            binding.movieOverview.movementMethod= ScrollingMovementMethod()
            if(tvSeries!!.first_air_date == null){
                binding.movieReleaseDate.text = "Unknown"
            }else{
                binding.movieReleaseDate.text = tvSeries!!.first_air_date?.let { viewModel.splitDate(it) }
            }
            binding.movieOverview.text = tvSeries!!.overview
            if(tvSeries!!.name == null){
              //  binding.movieName.text = tvSeries!!.original_name
            }else{
                binding.movieName.text = tvSeries!!.name

            }

            binding.movieImage.downloadFromUrl(tvSeries!!.poster_path, placeHolderProgressBar(requireContext()))
            binding.movieRate.text = viewModel.vote(tvSeries!!.vote_average.toString())
        }
    }

}