package com.example.navcompro.screens.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.navcompro.R

import com.example.navcompro.Repositories
import com.example.navcompro.databinding.FragmentSplashBinding
import com.example.navcompro.screens.main.MainActivity
import com.example.navcompro.utils.observeEvent
import com.example.navcompro.utils.viewModelCreator

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding

    private val viewModel by viewModelCreator { SplashViewModel(Repositories.accountsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        // just some animations example
        renderAnimations()

        viewModel.launchMainScreenEvent.observeEvent(viewLifecycleOwner) { launchMainScreen(it) }
    }

    //залогин пользователь или нет
    private fun launchMainScreen(isSignedIn: Boolean) {
        val intent = Intent(requireContext(), MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val direction =SplashFragmentDirections.actionSplashFragmentToMainActivity(isSignedIn)
        //val args = MainActivityArgs()
        intent.putExtras(direction.arguments)
        startActivity(intent)
    }

    private fun renderAnimations() {
        binding.loadingIndicator.alpha = 0f
        binding.loadingIndicator.animate()
            .alpha(0.7f)
            .setDuration(1000)
            .start()

        binding.pleaseWaitTextView.alpha = 0f
        binding.pleaseWaitTextView.animate()
            .alpha(1f)
            .setStartDelay(500)
            .setDuration(1000)
            .start()

    }
}