package com.keremkulac.movieapp.ui.account.change_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentChangePasswordBinding
import com.keremkulac.movieapp.repository.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    private lateinit var binding : FragmentChangePasswordBinding
    private val viewModel by viewModels<ChangePasswordViewModel>()
    private var user : User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChangePasswordBinding.inflate(inflater)
        changePasswordToMembership()
        changePassword()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as User?
    }

    private fun changePasswordToMembership(){
        binding.changePasswordToAccount.setOnClickListener {
            val bundle = bundleOf("user" to user)
            findNavController().navigate(R.id.action_changePasswordFragment_to_myMembershipFragment,bundle)
        }
    }

    private fun changePassword(){
        binding.passwordConfirm.setOnClickListener {
            val oldPass : String = binding.oldPassword.text.toString()
            val newPass1 : String =  binding.newPassword1.text.toString()
            val newPass2 : String =  binding.newPassword2.text.toString()
            if(oldPass == "" ||  newPass1 == "" || newPass2 == ""){
                Toast.makeText(requireContext(),"Please enter all information completely",Toast.LENGTH_SHORT).show()
            }else{
                val bundle = bundleOf("user" to user)
                viewModel.changePassword(oldPass,newPass1,requireContext(),findNavController(),bundle)
            }
        }
    }
}