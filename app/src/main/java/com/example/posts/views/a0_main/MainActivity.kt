package com.example.posts.views.a0_main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.posts.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //remove action bar
        supportActionBar?.hide()

        //Setup fragment
        setupActionBarWithNavController(findNavController(R.id.main_fragment))
    }
}