package com.project.laundryapp.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.project.laundryapp.R
import com.project.laundryapp.databinding.ActivityMainBinding
import com.project.laundryapp.databinding.StatusInformationBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var binding: ActivityMainBinding
        const val FRAGMENT_ID_KEY = "fragment id key"

        fun showLoading() {
            with(binding.statusMain) {
                progressBar.visibility = View.VISIBLE
                tvMessage.visibility = View.INVISIBLE
                tvRetry.visibility = View.INVISIBLE
            }
        }

        fun showMessage(message: String? = "Terjadi masalah.") {
            with(binding.statusMain) {
                progressBar.visibility = View.INVISIBLE
                tvMessage.visibility = View.VISIBLE
                tvRetry.visibility = View.VISIBLE

                tvMessage.text = message
            }
        }

        fun clearStatusInformation() {
            with(binding.statusMain) {
                progressBar.visibility = View.INVISIBLE
                tvMessage.visibility = View.INVISIBLE
                tvRetry.visibility = View.INVISIBLE
            }
        }

        fun getStatusView(): StatusInformationBinding {
            return binding.statusMain
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_laundry, R.id.navigation_history, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val defaultFragment = R.id.navigation_home
        val selectedFragment = intent.getIntExtra(FRAGMENT_ID_KEY, defaultFragment)
        navView.selectedItemId = selectedFragment
    }
}