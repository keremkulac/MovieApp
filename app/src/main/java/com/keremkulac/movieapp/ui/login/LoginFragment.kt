package com.keremkulac.movieapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var navController : NavController
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater)
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        signIn()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchRegisterFragment()
        viewModel.loggedUser(navController)

    }

    private fun switchRegisterFragment(){
        binding.loginSignUpNow.setOnClickListener {
            binding.loginSignUpNow.setBackgroundResource(R.color.white2)
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


    private fun signIn(){
        binding.loginSignIn.setOnClickListener {view->
            val email : String = binding.loginEmail.text!!.trim().toString()
            val password : String = binding.loginPassword.text!!.trim().toString()
            if(email == "" || password == ""){
                Toast.makeText(requireContext(),"Please enter all information completely", Toast.LENGTH_SHORT).show()

            }else{
               viewModel.signIn(email,password,navController,requireContext())
            }
        }

    }

}