package com.keremkulac.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.MyListAdapter
import com.keremkulac.movieapp.databinding.FragmentMyListBinding
import com.keremkulac.movieapp.util.replaceFragment
import com.keremkulac.movieapp.viewmodel.MyListViewModel

class MyListFragment : Fragment() {

    private lateinit var binding : FragmentMyListBinding
    private lateinit var viewModel : MyListViewModel
    private lateinit var adapter : MyListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyListBinding.inflate(inflater)
        viewModel = MyListViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        backToAccount()
    }

    private fun setRecyclerView(list : ArrayList<QueryDocumentSnapshot>){
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.myListRecyclerView.layoutManager = layoutManager
        adapter = MyListAdapter(list)
       binding.myListRecyclerView.adapter = adapter
    }
    private fun observeLiveData(){
            viewModel.myList.observe(viewLifecycleOwner){
                setRecyclerView(it)
            }
        }

    private fun backToAccount(){
        binding.myListToAccount.setOnClickListener {
            replaceFragment(AccountFragment(),requireActivity().supportFragmentManager,R.id.accountFrameLayout)
        }
    }




}