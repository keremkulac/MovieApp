package com.keremkulac.movieapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private  var currentUser : FirebaseUser? = null
    private  var auth : FirebaseAuth? = null
    private lateinit var navController : NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater)
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        auth = Firebase.auth
        auth.let {
            currentUser = auth!!.currentUser
        }

        signIn()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchRegisterFragment()
        loggedUser()

    }

    private fun switchRegisterFragment(){
        binding.loginSignUpNow.setOnClickListener {
            binding.loginSignUpNow.setBackgroundResource(R.color.white2)
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun loggedUser(){
        if(currentUser != null){
            navController.navigate(R.id.mainActivity)

        }
    }


    private fun signIn(){
        binding.loginSignIn.setOnClickListener {view->
            val email : String? = binding.loginEmail.text!!.trim().toString()
            val password : String? = binding.loginPassword.text!!.trim().toString()
            if(email == null || email.equals("") || password.equals("") || password == null){
                Toast.makeText(requireContext(),"Please enter all information completely", Toast.LENGTH_SHORT).show()

            }else{
                auth!!.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        navController.navigate(R.id.mainActivity)
                    }.addOnFailureListener{
                        Toast.makeText(requireContext(), it.localizedMessage!!.toString(), Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }

}