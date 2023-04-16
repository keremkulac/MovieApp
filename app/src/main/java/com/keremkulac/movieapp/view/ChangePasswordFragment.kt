package com.keremkulac.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentChangePasswordBinding
import com.keremkulac.movieapp.util.replaceFragment


class ChangePasswordFragment : DialogFragment() {

    private lateinit var binding : FragmentChangePasswordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChangePasswordBinding.inflate(inflater)
        changePasswordToAccount()
        changePassword()
        return binding.root
    }

    private fun changePasswordToAccount(){
        binding.changePasswordToAccount.setOnClickListener {
            replaceFragment(AccountFragment(),requireActivity().supportFragmentManager,R.id.accountFrameLayout)
        }
    }

    private fun changePassword(){
        binding.passwordConfirm.setOnClickListener {
            val oldPass = binding.oldPassword?.text.toString()
            val newPass1 = binding.newPassword1?.text.toString()
            val newPass2 = binding.newPassword2?.text.toString()
            val user = FirebaseAuth.getInstance().currentUser
            user?.email.let {
                if(oldPass != null) {
                    val credential = EmailAuthProvider.getCredential(user!!.email.toString(),oldPass)
                    user.reauthenticate(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            if (newPass1 != null && newPass2 != null){
                                user.updatePassword(newPass1).addOnCompleteListener {
                                    if(it.isSuccessful){
                                        Toast.makeText(requireContext(),"Password updated",Toast.LENGTH_SHORT).show()
                                        replaceFragment(AccountFragment(),requireActivity().supportFragmentManager,R.id.accountFrameLayout)
                                    }else {
                                        Toast.makeText(requireContext(),"Error password not updated",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                Toast.makeText(requireContext(),"Passwords do not match",Toast.LENGTH_SHORT).show()
                            }

                        }else {
                            Toast.makeText(requireContext(),"Error auth failed",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(requireContext(),"Your password is wrong",Toast.LENGTH_SHORT).show()
                }


            }

        }
    }


}