package com.example.navcompro.tabs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.navcompro.R
import com.example.navcompro.tabs.screens.splash.SplashFragment
import com.example.navcompro.tabs.screens.splash.SplashViewModel

/**
 * Entry point of the app.
 *
 * Splash activity contains only window background, all other initialization logic is placed to
 * [SplashFragment] and [SplashViewModel].
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

}