package com.keremkulac.movieapp.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.MyListAdapter
import com.keremkulac.movieapp.databinding.FragmentMyListBinding

class MyListFragment : Fragment(),MyListAdapter.ClickListener {

    private lateinit var binding : FragmentMyListBinding
    private lateinit var viewModel : MyListViewModel
    private lateinit var adapter : MyListAdapter
    private lateinit var navController: NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyListBinding.inflate(inflater)
        viewModel = MyListViewModel()
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        navController = navHostFragment.navController
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        backToAccount()
    }

    private fun setRecyclerView(list : ArrayList<Movie>){
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.myListRecyclerView.layoutManager = layoutManager
        adapter = MyListAdapter(this,list)
       binding.myListRecyclerView.adapter = adapter
    }
    private fun observeLiveData(){
            viewModel.myList.observe(viewLifecycleOwner){
                setRecyclerView(it)
            }
        }

    private fun backToAccount(){
        binding.myListToAccount.setOnClickListener {
            navController.navigate(R.id.action_myListFragment_to_accountFragment)
        }
    }

    override fun ClickedMyListItem(movie: Movie) {
        val bundle = bundleOf("movie" to movie)
         binding.root.findNavController().navigate(R.id.action_myListFragment_to_movieDetailFragment,bundle)
    }
}