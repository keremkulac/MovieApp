package com.keremkulac.movieapp.ui.account.my_list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.MyListAdapter
import com.keremkulac.movieapp.databinding.FragmentMyListBinding
import com.keremkulac.movieapp.repository.model.User
import com.keremkulac.movieapp.util.FirebaseResource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyListFragment @Inject constructor(private val myListAdapter: MyListAdapter) : Fragment(R.layout.fragment_my_list) {

    private lateinit var binding : FragmentMyListBinding
    private val viewModel by viewModels<MyListViewModel>()
    private var user : User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyListBinding.bind(view)
        observeMyList()
        backToAccount()
        user = arguments?.getSerializable("user") as User?
        setupRecyclerAdapter()
    }

    private fun setupRecyclerAdapter(){
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.myListRecyclerView.layoutManager = layoutManager
        binding.myListRecyclerView.adapter = myListAdapter
        myListAdapter.onItemClick = {
            val bundle = bundleOf("movie" to it,"myList" to "myList","user" to user)
            findNavController().navigate(R.id.action_myListFragment_to_movieDetailFragment,bundle)
        }
    }


    private fun observeMyList() {
        viewModel.myList.observe(viewLifecycleOwner) {
            when (it) {
                is FirebaseResource.Loading -> {
                    Log.d("TAG1", "LOADING")
                }
                is FirebaseResource.Success -> {
                    myListAdapter.movies = it.data!!
                }
                is FirebaseResource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun backToAccount(){
        binding.myListToAccount.setOnClickListener {
            val bundle = bundleOf("user" to user)
            findNavController().navigate(R.id.action_myListFragment_to_accountFragment,bundle)
        }
    }

}