package com.keremkulac.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.keremkulac.movieapp.databinding.ActivityMainBinding
import com.keremkulac.movieapp.repository.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<MainActivityViewModel>()
    private var user : User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavMenuSelect()
        selectMovie()
        selectTVSeries()
        account()
        setUsernameLetter()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.tvSeriesFragment)
    }

    private fun bottomNavMenuSelect(){
        binding.bottomNav.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.search->{
                    navController.navigate(R.id.searchActivity)
                    true
                }
                R.id.home-> {
                    navController.navigate(R.id.mainActivity)

                    true
                }
                else -> {false   }
            }
        }
    }

    private fun selectTVSeries(){
        binding.selectTVSeries.setOnClickListener {
            if (binding.selectedTV.visibility != View.VISIBLE && binding.selectedMovie.visibility == View.VISIBLE   ) {
                binding.selectedMovie.visibility = View.INVISIBLE
                binding.selectedTV.visibility = View.VISIBLE
                navController.navigate(R.id.action_movieFragment_to_tvSeriesFragment)
            }
        }
    }
    private fun selectMovie(){
            binding.selectMovie.setOnClickListener {
                if (binding.selectedMovie.visibility != View.VISIBLE && binding.selectedTV.visibility == View.VISIBLE   ) {
                    binding.selectedMovie.visibility = View.VISIBLE
                    binding.selectedTV.visibility = View.INVISIBLE
                    navController.navigate(R.id.action_tvSeriesFragment_to_movieFragment)
            }
        }
    }

    private fun account(){
        binding.account.setOnClickListener {
            binding.bottomNav.visibility = View.GONE
            binding.toolBar.visibility = View.GONE
            val bundle = bundleOf("user" to user)
            navController.navigate(R.id.accountFragment,bundle)
        }
    }

   private fun setUsernameLetter(){
       viewModel.user.observe(this){
           it?.let {
               binding.account.text = it.firstname!![0].toString().uppercase()
               user = it
           }
       }
   }
}



