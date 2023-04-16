package com.keremkulac.movieapp.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
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
    private  var popular : Movie? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        viewModel = MovieDetailViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // val  arg =  requireArguments().getSerializable("movie") as Movie
      getMovie()
        getPopular()
        getTvSeries()
        addMovie()
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
            changeIcon(movie!!.id.toString())
            observeLiveData(movie!!.genre_ids,binding.movieGenres)
            binding.movieOverview.movementMethod= ScrollingMovementMethod()
            if(movie!!.release_date == null){
                binding.movieReleaseDate.text = getString(R.string.unknown)
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
    private fun getPopular(){
        popular = requireArguments().getSerializable("popular") as Movie?
        if(popular != null){
            changeIcon(popular!!.id.toString())
            binding.movieOverview.movementMethod= ScrollingMovementMethod()
            if(popular!!.release_date == null){
                binding.movieReleaseDate.text = getString(R.string.unknown)
            }else{
                binding.movieReleaseDate.text = popular!!.release_date?.let { viewModel.splitDate(it) }
            }
            binding.movieOverview.text = popular!!.overview
            if(popular!!.title == null){
                binding.movieName.text = popular!!.original_title
            }else{
                binding.movieName.text = popular!!.title

            }

            binding.movieImage.downloadFromUrl(popular!!.poster_path, placeHolderProgressBar(requireContext()))
            binding.movieRate.text = viewModel.vote(popular!!.vote_average.toString())
        }
    }

    private fun getTvSeries(){
        tvSeries = requireArguments().getSerializable("tvSeries") as Movie?
        if(tvSeries != null){
            changeIcon(tvSeries!!.id.toString())
            observeTvSeriesGenres(tvSeries!!.genre_ids,binding.movieGenres)
            binding.movieOverview.movementMethod= ScrollingMovementMethod()
            if(tvSeries!!.first_air_date == null){
                binding.movieReleaseDate.text = getString(R.string.unknown)
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

    private fun addMovie(){
        binding.addMovie.setOnClickListener {


            if(tvSeries != null){
                if(binding.isChecked.text.equals("false")){
                    viewModel.add(tvSeries,requireContext())
                    binding.addMovie.setImageResource(R.drawable.ic_check)
                    binding.isChecked.text = getString(R.string.trueText)
                }else if(binding.isChecked.text.equals("true")){
                    viewModel.checkAndRemoveList(tvSeries!!.id.toInt(),requireContext(),binding.addMovie)
                    binding.isChecked.text = getString(R.string.falseText)
                }
            }

            if(movie != null){
                if(binding.isChecked.text == "false"){
                    viewModel.add(movie,requireContext())
                    binding.addMovie.setImageResource(R.drawable.ic_check)
                    binding.isChecked.text = getString(R.string.trueText)
                }else if(binding.isChecked.text == "true"){
                    viewModel.checkAndRemoveList(tvSeries!!.id.toInt(),requireContext(),binding.addMovie)
                    binding.isChecked.text = getString(R.string.falseText)
                }
            }
        }
    }

    private fun changeIcon(id : String){
        viewModel.idList.observe(viewLifecycleOwner){
            for(i in it){
                if(i == id){
                    binding.addMovie.setImageResource(R.drawable.ic_check)
                    binding.isChecked.text = getString(R.string.trueText)
                }
            }
        }
    }

}