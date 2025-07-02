package com.dev.cura.ui.screen.home

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dev.cura.R
import com.dev.cura.data.api.ApiService
import com.dev.cura.data.api.RetrofitClient
import com.dev.cura.data.repository.SocialRepository
import com.dev.cura.domain.model.SurveyResponse
import com.dev.cura.databinding.ActivityHomeBinding
import com.dev.cura.domain.model.UserDetails
import com.dev.cura.ui.viewmodel.AuthViewModel
import com.dev.cura.ui.viewmodel.SocialViewModel
import com.dev.cura.ui.viewmodel.SocialViewModelFactory
import com.dev.cura.util.SharedPrefsHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private val socialViewModel: SocialViewModel by viewModels {
        SocialViewModelFactory(SocialRepository(RetrofitClient.instance))
    }
    private lateinit var sharedPrefsHelper: SharedPrefsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.socialFragment -> {
                    navController.navigate(R.id.socialFragment)
                    true
                }
                else -> false
            }
        }

        // Set the default selected item
        bottomNavigationView.selectedItemId = R.id.homeFragment
        sharedPrefsHelper = SharedPrefsHelper(this)


        // Fetch data from SharedPreferences and API
        fetchData()
    }
    private fun fetchData() {
        // Fetch from SharedPreferences
        val userToken = sharedPrefsHelper.getUserToken()
        val userName = sharedPrefsHelper.getUserName()
        val email = sharedPrefsHelper.getUserEmail()
        val photo = sharedPrefsHelper.getUserPhoto()

        if (email != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                val response = RetrofitClient.instance.getRemedy(email)
                if (response.isSuccessful) {
                    val successfulResponse = response.body()
                    if (successfulResponse != null) {
                        val surveyResponse = Gson().fromJson(successfulResponse, SurveyResponse::class.java)
                        // Update ViewModel with API data
                        val userData = UserDetails(
                            name = userName!!,
                            token = userToken!!,
                            email = email,
                            photo = photo!!,
                            surveyResponse = surveyResponse)
                        withContext(Dispatchers.Main) {
                            Log.d("HomeActivity", "API data: $userData")
                            socialViewModel.setUserData(userData)
                        }
                    }
                }
            }
        }

    }

}