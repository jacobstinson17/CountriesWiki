package com.jacobstinson.countrieswiki.view_viewmodel.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.jacobstinson.countrieswiki.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: DaggerAppCompatActivity() {

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    private val appBarConfiguration by lazy {
        val fragments = setOf(R.id.countriesFragment)
        AppBarConfiguration(fragments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}