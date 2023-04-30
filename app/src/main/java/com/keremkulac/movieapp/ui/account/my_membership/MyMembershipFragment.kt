package com.keremkulac.movieapp.ui.account.my_membership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.FragmentMyMembershipBinding
import com.keremkulac.movieapp.repository.model.User


class MyMembershipFragment : Fragment() {

    private lateinit var binding : FragmentMyMembershipBinding
    private lateinit var navController : NavController
    private var user : User? = null

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
    }


    private fun membershipToChangePassword(){
        binding.changePassword.setOnClickListener {
            val bundle = bundleOf("user" to user)
            navController.navigate(R.id.action_myMembershipFragment_to_changePasswordFragment,bundle)
        }
    }

    private fun backToAccount(){
        binding.membershipToAccount.setOnClickListener {
            val bundle = bundleOf("user" to user)
            navController.navigate(R.id.action_myMembershipFragment_to_accountFragment,bundle)
        }
    }

    private fun setMemberFullName(){
        user?.let {
            val userFullname = user!!.firstname!!+" "+ user!!.lastname!!
            binding.memberFullName.setText(userFullname)
            binding.memberEmail.setText(user!!.email)
        }

    }

}