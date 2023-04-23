package com.keremkulac.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var navController : NavController
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAccountBinding.inflate(inflater)
        auth = Firebase.auth
        backToHome()
       changePassword()
        logout()
        myList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun backToHome(){
        binding.accountToBackHome.setOnClickListener {
            navController.navigate(R.id.mainActivity)
        }
    }

    private fun changePassword(){
        binding.changePassword.setOnClickListener{
            navController.navigate(R.id.action_accountFragment_to_changePasswordFragment)
        }

    }

    private fun logout(){
        binding.logout.setOnClickListener{
            auth.signOut()
            navController.navigate(R.id.loginActivity)
        }
    }

    private fun myList(){
        binding.myList.setOnClickListener {
            navController.navigate(R.id.action_accountFragment_to_myListFragment)
        }
    }
}