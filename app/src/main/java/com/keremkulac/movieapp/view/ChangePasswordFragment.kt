package com.keremkulac.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentChangePasswordBinding


class ChangePasswordFragment : Fragment() {

    private lateinit var binding : FragmentChangePasswordBinding
    private lateinit var navController : NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChangePasswordBinding.inflate(inflater)
        changePasswordToAccount()
        changePassword()
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        navController = navHostFragment.navController
        return binding.root
    }

    private fun changePasswordToAccount(){
        binding.changePasswordToAccount.setOnClickListener {
            navController.navigate(R.id.action_changePasswordFragment_to_accountFragment)
        }
    }

    private fun changePassword(){
        binding.passwordConfirm.setOnClickListener {
            val oldPass : String? = binding.oldPassword.text.toString()
            val newPass1 : String? =  binding.newPassword1?.text.toString()
            val newPass2 : String? =  binding.newPassword2?.text.toString()
            val user = FirebaseAuth.getInstance().currentUser
            val userEmail = user!!.email.toString()
            if(userEmail == null || user.email.equals("")){
                Toast.makeText(requireContext(),"User email is incorrect",Toast.LENGTH_SHORT).show()

            }else{
                if(oldPass == null || oldPass.equals("") || newPass1 == null || newPass1.equals("") || newPass2 == null || newPass2.equals("")){
                    Toast.makeText(requireContext(),"Please enter all information completely",Toast.LENGTH_SHORT).show()
                }else{
                    val credential = EmailAuthProvider.getCredential(userEmail,oldPass)
                    user.reauthenticate(credential).addOnSuccessListener {
                        user.updatePassword(newPass1).addOnSuccessListener {
                            Toast.makeText(requireContext(),"Password updated",Toast.LENGTH_SHORT).show()
                            navController.navigate(R.id.action_changePasswordFragment_to_accountFragment)

                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), it.localizedMessage!!.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage!!.toString(),Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }


}