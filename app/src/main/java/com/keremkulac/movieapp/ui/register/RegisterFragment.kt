package com.keremkulac.movieapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.keremkulac.movieapp.databinding.FragmentRegisterBinding
import com.keremkulac.movieapp.repository.model.User


class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var viewModel : RegisterViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       binding = FragmentRegisterBinding.inflate(inflater)
        viewModel = RegisterViewModel()
        backToLogin()
        saveUser()

        return binding.root
    }


    private fun backToLogin(){
        binding.registerBackToLoginScreen.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }


    private fun saveUser(){

        binding.registerSignUp.setOnClickListener {
            val email: String? = binding.registerEmail.text?.trim().toString()
            val password : String? = binding.registerPassword.text?.trim().toString()
            if(email == null || email.equals("") || password.equals("") || password == null){
                Toast.makeText(requireContext(),"Please enter all information completely",Toast.LENGTH_SHORT).show()
            }else{
                val user = User(email,password)
                viewModel.saveUser(user,requireContext(),requireActivity().supportFragmentManager)
            }
        }
    }

}