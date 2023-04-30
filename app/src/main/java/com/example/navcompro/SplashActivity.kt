package com.example.navcompro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.navcompro.screens.splash.SplashFragment
import com.example.navcompro.screens.splash.SplashViewModel

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