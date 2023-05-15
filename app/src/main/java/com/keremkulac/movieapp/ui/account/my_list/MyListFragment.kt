package com.keremkulac.movieapp.ui.account.my_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.MyListAdapter
import com.keremkulac.movieapp.databinding.FragmentMyListBinding
import com.keremkulac.movieapp.repository.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : Fragment(),MyListAdapter.ClickListener {

    private lateinit var binding : FragmentMyListBinding
    private val viewModel by viewModels<MyListViewModel>()
    private lateinit var adapter : MyListAdapter

    private var user : User? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        backToAccount()
        user = arguments?.getSerializable("user") as User?
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
            val bundle = bundleOf("user" to user)
            findNavController().navigate(R.id.action_myListFragment_to_accountFragment,bundle)
        }
    }

    override fun ClickedMyListItem(movie: Movie) {
        val bundle = bundleOf("movie" to movie,"myList" to "myList","user" to user)
        findNavController().navigate(R.id.action_myListFragment_to_movieDetailFragment,bundle)
    }
}