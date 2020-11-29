package com.example.foodproject

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.foodproject.model.CountriesResponse
import com.example.foodproject.repository.RetrofitRepository
import com.example.foodproject.util.Constants
import com.example.foodproject.viewmodel.RestaurantViewModel
import com.example.foodproject.viewmodel.RetrofitViewModel
import com.example.foodproject.viewmodel.RetrofitViewModelFactory

class SplashScreen : AppCompatActivity() {

    private lateinit var mRestaurantViewModel: RestaurantViewModel
    private lateinit var viewModel: RetrofitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mRestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        val repository = RetrofitRepository()
        val viewModelFactory = RetrofitViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(RetrofitViewModel::class.java)
        viewModel.getCountries()
        viewModel.getCities()

        viewModel.myResponsCountry.observe(this, Observer { response ->

            val states = arrayListOf<String>()
            response.body()!!.countries.forEach() { states.add(it) }

            //filling up utils class which contains every possible filter (state)
            Constants.states = states

        })

        viewModel.myResponsCities.observe(this, Observer { response ->

            val cities = arrayListOf<String>()
            response.body()!!.cities.forEach() { cities.add(it) }

            //filling up utils class which contains every possible filter (cities)
            Constants.cities = cities

        })


        //delay for the splash screem
        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },3000)
    }
}