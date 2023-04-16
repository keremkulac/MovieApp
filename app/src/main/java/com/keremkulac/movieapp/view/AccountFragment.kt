package com.keremkulac.movieapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentAccountBinding
import com.keremkulac.movieapp.util.replaceFragment


class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAccountBinding.inflate(inflater)
        auth = Firebase.auth
        backToHome()
       changePassword()
        logout()
        myList()
        return binding.root
    }


    private fun backToHome(){
        binding.accountToBackHome.setOnClickListener {
            val mainActivityIntent = Intent(requireContext(), MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
    }

    private fun changePassword(){
        Log.d("TAG11","POPUP")
        binding.changePassword.setOnClickListener{
            replaceFragment(ChangePasswordFragment(),requireActivity().supportFragmentManager,R.id.accountFrameLayout)
        }

    }

    private fun logout(){
        binding.logout.setOnClickListener{
            auth.signOut()
            val loginActivity = Intent(requireContext(), LoginActivity::class.java)
            startActivity(loginActivity)
        }
    }

    private fun myList(){
        binding.myList.setOnClickListener {
            replaceFragment(MyListFragment(),requireActivity().supportFragmentManager,R.id.accountFrameLayout)
        }
    }



}