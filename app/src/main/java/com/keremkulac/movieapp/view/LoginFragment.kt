package com.keremkulac.movieapp.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentLoginBinding
import com.keremkulac.movieapp.util.replaceFragment


class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private  var currentUser : FirebaseUser? = null
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        auth = Firebase.auth
        if(auth.currentUser != null){
            currentUser = auth.currentUser
        }
        signIn()
        loggedUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchRegisterFragment()

    }

    private fun switchRegisterFragment(){
        binding.loginSignUpNow.setOnClickListener {
            binding.loginSignUpNow.setBackgroundResource(R.color.white2)
            replaceFragment(RegisterFragment(),requireActivity().supportFragmentManager,R.id.loginFrameLayout)
        }
    }
    private fun loggedUser(){
        if(currentUser != null){
            val mainActivityIntent = Intent(requireContext(), MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
    }

    private fun signIn(){
        binding.loginSignIn.setOnClickListener {
            val email : String? = binding.loginEmail.text!!.trim().toString()
            val password : String? = binding.loginPassword.text!!.trim().toString()
            if(email == null || email.equals("") || password.equals("") || password == null){
                Toast.makeText(requireContext(),"Please enter all information completely", Toast.LENGTH_SHORT).show()

            }else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        val mainActivityIntent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(mainActivityIntent)
                    }.addOnFailureListener{
                        Toast.makeText(requireContext(),it.localizedMessage.toString(), Toast.LENGTH_SHORT).show()

                    }
            }
        }

    }

}