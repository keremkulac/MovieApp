package com.keremkulac.movieapp.ui.movie_detail

import android.content.DialogInterface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentMovieDetailBinding
import com.keremkulac.movieapp.repository.model.Genre
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMovieDetailBinding
    private val viewModel  by viewModels<MovieDetailViewModel>()
    private var genres = ArrayList<Genre>()
    private var movie : Movie? = null
    private var myList : String? = null
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        navController = navHostFragment.navController
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      getMovie()
        addMovie()
    }



    private fun observeLiveData(list : ArrayList<Int>,textView : TextView){
        viewModel.movieGenres.observe(viewLifecycleOwner) { movieGenres ->
            val builder = StringBuilder()
            if (movieGenres != null) {
                genres = movieGenres
            }
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
    private fun getMovie(){
        movie = arguments?.getSerializable("movie") as Movie?
        if(movie != null){
            changeIcon(movie!!.id.toString())
            movie!!.genre_ids?.let { observeLiveData(it,binding.movieGenres) }
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

            movie!!.poster_path?.let { binding.movieImage.downloadFromUrl(it, placeHolderProgressBar(requireContext())) }
            binding.movieRate.text = viewModel.vote(movie!!.vote_average.toString())
        }
    }

    private fun addMovie(){
        binding.addMovie.setOnClickListener {

            if(movie != null){
                if(binding.isChecked.text == "false"){
                    viewModel.add(movie,requireContext())
                    binding.addMovie.setImageResource(R.drawable.ic_check)
                    binding.isChecked.text = getString(R.string.trueText)
                }else if(binding.isChecked.text == "true"){
                    movie!!.id?.let {
                            it1 -> viewModel.checkAndRemoveList(it1.toInt(),requireContext(),binding.addMovie)

                    }
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



    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        myList = arguments?.getString("myList")
        myList?.let {
                navController.navigate(R.id.action_movieDetailFragment_to_myListFragment)
            }
        }
    }