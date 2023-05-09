package com.keremkulac.movieapp.ui.account.my_membership

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentMyMembershipBinding
import com.keremkulac.movieapp.repository.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyMembershipFragment : Fragment() {

    private lateinit var binding : FragmentMyMembershipBinding
    private lateinit var navController : NavController
    private var user : User? = null
    private  val viewModel by viewModels<MyMembershipViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyMembershipBinding.inflate(inflater)
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        navController = navHostFragment.navController
        membershipToChangePassword()
        backToAccount()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as User?
        setMemberFullName()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                updateUserFullname()
            }

        })
    }


    private fun membershipToChangePassword(){
        binding.changePassword.setOnClickListener {
            val bundle = bundleOf("user" to user)
            navController.navigate(R.id.action_myMembershipFragment_to_changePasswordFragment,bundle)
        }
    }

    private fun backToAccount(){
        binding.membershipToAccount.setOnClickListener {
            updateUserFullname()
        }
    }

    private fun setMemberFullName(){
        user?.let {
            val userFullname = user!!.firstname!!+" "+ user!!.lastname!!
            binding.memberFullName.setText(userFullname)
            binding.memberEmail.setText(user!!.email)
        }

    }

    private fun updateUserFullname(){
        user?.let {
            val userFullname = user!!.firstname!! + " " + user!!.lastname!!
            val updatedUserFullname = binding.memberFullName.text.toString()
            if(userFullname != updatedUserFullname){
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Do you want to save the changes made?")
                    .setPositiveButton("Yes") { dialog, id ->
                        viewModel.updateFullname(updatedUserFullname)
                        val userList = updatedUserFullname.split(" ")
                        user!!.firstname = userList[0]
                        user!!.lastname = userList[1]
                        navigateAccountFragment(user)
                    }
                    .setNegativeButton("Cancel") { dialog, id ->
                        dialog.dismiss()
                    }
                builder.create()
                builder.show()
            }else{
                navigateAccountFragment(user)
            }

        }
    }

    private fun navigateAccountFragment(user : User?){
        val bundle = bundleOf("user" to user)
        navController.navigate(R.id.action_myMembershipFragment_to_accountFragment,bundle)
    }

}