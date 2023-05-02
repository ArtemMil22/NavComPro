package com.example.navcompro.screens.main.toobs.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions

import com.example.navcompro.*
import com.example.navcompro.databinding.FragmentProfileBinding
import com.example.navcompro.model.accounts.entities.Account
import com.example.navcompro.utils.findTopNavController
import com.example.navcompro.utils.observeEvent
import com.example.navcompro.utils.viewModelCreator
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel by viewModelCreator { ProfileViewModel(Repositories.accountsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.editProfileButton.setOnClickListener { onEditProfileButtonPressed() }
        binding.logoutButton.setOnClickListener { onLogoutButtonPressed() }

        observeAccountDetails()
        observeRestartAppFromLoginScreenEvent()
    }

    private fun observeAccountDetails() {
        val formatter = SimpleDateFormat.getDateTimeInstance()
        viewModel.account.observe(viewLifecycleOwner) { account ->
            if (account == null) return@observe
            binding.emailTextView.text = account.email
            binding.usernameTextView.text = account.username
            binding.createdAtTextView.text = if (account.createdAt == Account.UNKNOWN_CREATED_AT)
                getString(R.string.placeholder)
            else
                formatter.format(Date(account.createdAt))
        }
    }

    // поверх
    private fun onEditProfileButtonPressed() {
        //"Launch EditProfileFragment gere over tabs (tabs should not be available from EditProfileFragment")
        findTopNavController().navigate(R.id.editProfileFragment)
    }

    private fun observeRestartAppFromLoginScreenEvent() {
        viewModel.restartWithSignInEvent.observeEvent(viewLifecycleOwner) {
            // user has signed out from the app
            //"Close all tab screens and launch SignInFragment here")
            findTopNavController().navigate(
                R.id.signInFragment,
                null,
            navOptions {
                popUpTo(R.id.tabsFragment){
                    inclusive = true
                }
            })
        }
    }

    private fun onLogoutButtonPressed() {
        viewModel.logout()
    }
}