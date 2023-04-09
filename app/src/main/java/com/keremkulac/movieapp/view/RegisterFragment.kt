package com.keremkulac.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentRegisterBinding
import com.keremkulac.movieapp.model.User
import com.keremkulac.movieapp.viewmodel.RegisterViewModel


class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var viewModel : RegisterViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       binding = FragmentRegisterBinding.inflate(inflater)
      //  (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        viewModel = RegisterViewModel()
        backToLogin()
        saveUser()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun backToLogin(){
        binding.registerBackToLoginScreen.setOnClickListener {
            replaceFragment(LoginFragment(),requireActivity().supportFragmentManager,R.id.loginFrameLayout)
        }
    }

    private fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager, layout :Int){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layout,fragment)
        fragmentTransaction.commit()

    }

    private fun saveUser(){

        binding.registerSignUp.setOnClickListener {
            val email: String = binding.registerEmail.text?.trim().toString()
            val password : String = binding.registerPassword.text?.trim().toString()
            if(email == null || password == null){
                Toast.makeText(requireContext(),"Please enter all information completely",Toast.LENGTH_SHORT).show()
            }else{
                val user = User(email,password)
                viewModel.saveUserFromFirebase(user,requireActivity().supportFragmentManager,requireContext())
            }
        }
    }

}