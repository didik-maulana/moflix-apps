package com.didik.moflix.presentation.features.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.didik.moflix.presentation.features.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(MainActivity.newIntent(this))
        finish()
    }
}