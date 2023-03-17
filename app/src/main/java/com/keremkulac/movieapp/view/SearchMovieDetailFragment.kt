package com.keremkulac.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentSearchMovieDetailBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import com.keremkulac.movieapp.viewmodel.SearchMovieDetailViewModel


class SearchMovieDetailFragment : Fragment(){

    private lateinit var binding : FragmentSearchMovieDetailBinding
    private lateinit var viewModel : SearchMovieDetailViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchMovieDetailBinding.inflate(inflater)
        viewModel = SearchMovieDetailViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindMovieItem()
        back()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.searchFrameLayout,SearchFragment())
                fragmentTransaction.commit()

            }
        })
    }

    private fun bindMovieItem(){
        var click  = 0
        val  arg =  requireArguments().getSerializable("movie") as Movie
        observeLiveData(arg.genre_ids,binding.movieDetailGenre)
        binding.movieDetailPoster.downloadFromUrl(arg.poster_path, placeHolderProgressBar(requireContext()))
        binding.movieDetailBackdrop.downloadFromUrl(arg.backdrop_path, placeHolderProgressBar(requireContext()))
        binding.movieDetailOverview.text = arg.overview
        if(arg.release_date == null){
            binding.movieDetailReleaseDate.text = "Unknown"
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

    private fun back(){
        binding.movieDetailBackButton.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.searchFrameLayout,SearchFragment())
            fragmentTransaction.commit()
        }
    }

}