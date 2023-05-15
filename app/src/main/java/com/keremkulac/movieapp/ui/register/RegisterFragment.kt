package com.keremkulac.movieapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentRegisterBinding
import com.keremkulac.movieapp.repository.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private  val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       binding = FragmentRegisterBinding.inflate(inflater)
        backToLogin()
        saveUser()
        return binding.root
    }


    private fun backToLogin(){
        binding.registerBackToLoginScreen.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
    private fun saveUser(){
        binding.registerSignUp.setOnClickListener {
            val email: String = binding.registerEmail.text?.trim().toString()
            val password : String = binding.registerPassword.text?.trim().toString()
            val name : String = binding.registerName.text?.trim().toString().capitalize()
            val lastname : String = binding.registerLastName.text?.trim().toString().capitalize()

            if( email == "" || password == "" || name == "" || lastname == ""){
                Toast.makeText(requireContext(),"Please enter all information completely",Toast.LENGTH_SHORT).show()
            }else{
                val user = User(email,name,lastname)
                viewModel.saveUser(user,password,requireContext(),findNavController())
            }
        }
    }

}