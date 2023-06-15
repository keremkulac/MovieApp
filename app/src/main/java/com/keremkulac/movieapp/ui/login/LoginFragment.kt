package com.keremkulac.movieapp.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentLoginBinding
import com.keremkulac.movieapp.util.FirebaseResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater)
        signIn()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchRegisterFragment()
        isUserLogged()
    }

    private fun isUserLogged(){
        viewModel.isUserLogged.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.mainActivity)
            }
        }
    }

    private fun switchRegisterFragment(){
        binding.loginSignUpNow.setOnClickListener {
            binding.loginSignUpNow.setBackgroundResource(R.color.white2)
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


    private fun signIn(){
        binding.loginSignIn.setOnClickListener {view->
            viewModel.signInUser(
                binding.loginEmail.text.toString(),
                binding.loginPassword.text.toString()
            )
        }
        observeLoginStatus()
    }

    private fun observeLoginStatus(){
        viewModel.userSignInStatus.observe(viewLifecycleOwner){
            when (it) {
                is FirebaseResource.Loading -> {
                    Log.d("TAG1","LOADING") }
                is FirebaseResource.Success -> {
                    findNavController().navigate(R.id.mainActivity)
                }
                is FirebaseResource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
