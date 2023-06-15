package com.keremkulac.movieapp.ui.register

import android.os.Bundle
import android.text.InputType
import android.util.Log
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
import com.keremkulac.movieapp.util.FirebaseResource
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
        binding.registerEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        binding.registerSignUp.setOnClickListener {
            viewModel.createUser(
                binding.registerName.text?.trim().toString().capitalize(),
                binding.registerLastName.text?.trim().toString().capitalize(),
                binding.registerEmail.text?.toString()!!.trim(),
                binding.registerPassword.text?.trim().toString()
            )
        }
        observeRegistrationStatus()
    }

    private fun observeRegistrationStatus(){
        viewModel.userRegistrationStatus.observe(viewLifecycleOwner){
            when (it) {
                is FirebaseResource.Loading -> {
                    Log.d("TAG1","LOADING") }
                is FirebaseResource.Success -> {
                    findNavController().navigate(R.id.loginFragment)}
                is FirebaseResource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}