package com.keremkulac.movieapp.ui.account.change_password

import android.os.Bundle
import android.util.Log
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
import com.keremkulac.movieapp.util.FirebaseResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    private lateinit var binding : FragmentChangePasswordBinding
    private val viewModel by viewModels<ChangePasswordViewModel>()
    private var user : User? = null
    private var bundle : Bundle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChangePasswordBinding.inflate(inflater)
        changePasswordToMembership()
        changePassword()
        observeChangePasswordStatus()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as User?
        bundle = bundleOf("user" to user)
    }

    private fun changePasswordToMembership(){
        binding.changePasswordToAccount.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordFragment_to_myMembershipFragment,bundle)
        }
    }

    private fun observeChangePasswordStatus(){
        viewModel.changePasswordStatus.observe(viewLifecycleOwner){
            when (it) {
                is FirebaseResource.Loading -> {
                    Log.d("TAG1","LOADING") }
                is FirebaseResource.Success -> {
                    if(it.data == true){
                        Toast.makeText(requireContext(),"Password updated", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_changePasswordFragment_to_myMembershipFragment,bundle)
                    }
                }
                is FirebaseResource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun changePassword(){
        binding.passwordConfirm.setOnClickListener {
            val oldPass : String = binding.oldPassword.text.toString()
            val newPass1 : String =  binding.newPassword1.text.toString()
            val newPass2 : String =  binding.newPassword2.text.toString()
            viewModel.changePassword(oldPass,newPass1,newPass2)
        }
    }


}