package com.keremkulac.movieapp.ui.search_movie_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentSearchMovieDetailBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieDetailFragment : Fragment(){

    private lateinit var binding : FragmentSearchMovieDetailBinding
    private  val viewModel by viewModels<SearchMovieDetailViewModel>()
    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchMovieDetailBinding.inflate(inflater)
        val navHostFragment =requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment3) as NavHostFragment
        navController = navHostFragment.navController
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindMovieItem()
        backToSearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.commit()

            }
        })
    }

    private fun bindMovieItem(){
        var click  = 0
        val arg = arguments?.getSerializable("movie") as Movie?
        arg?.let {
        arg.genre_ids?.let { observeLiveData(it,binding.movieDetailGenre) }
        arg.poster_path?.let { binding.movieDetailPoster.downloadFromUrl(it, placeHolderProgressBar(requireContext())) }
            arg.backdrop_path?.let { it1 -> binding.movieDetailBackdrop.downloadFromUrl(it1, placeHolderProgressBar(requireContext())) }
        binding.movieDetailOverview.text = arg.overview
        if(arg.release_date == null){
            binding.movieDetailReleaseDate.text = getString(R.string.unknown)
            binding.movieDetailReleaseDate.text = arg.first_air_date?.let { it1 ->
                viewModel.splitDate(
                    it1
                )
            }

        }else{
            binding.movieDetailReleaseDate.text = viewModel.splitDate(arg.release_date)
        }
        if(arg.original_title == null){
            binding.movieDetailName.text = arg.name
        }else{
            binding.movieDetailName.text = arg.original_title
        }
        binding.movieDetailIMDB.text = viewModel.vote(arg.vote_average.toString())
        binding.movieDetailExpand.setOnClickListener {
        click++
            if(click % 2 == 1){
                binding.movieDetailOverview.visibility = View.VISIBLE
            }else{
                binding.movieDetailOverview.visibility = View.GONE
            }
        }
        }
    }

    private fun observeLiveData(list : ArrayList<Int>,textView : TextView){
        viewModel.genres.observe(viewLifecycleOwner) { Genres ->
            val builder = StringBuilder()
            for (item in list) {
                for (genreItem in Genres) {
                    if (genreItem.id == item) {
                        builder.append(genreItem.name)
                        builder.append("  ")
                    }
                }
            }
            textView.text = builder.toString()
        }
    }

    private fun backToSearchFragment(){
        binding.movieDetailBackButton.setOnClickListener {
            navController.navigate(R.id.action_searchMovieDetailFragment_to_searchFragment)
        }
    }

}