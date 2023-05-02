package com.example.navcompro.screens.main.toobs.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navcompro.R

import com.example.navcompro.databinding.FragmentBoxBinding
import com.example.navcompro.Repositories
import com.example.navcompro.utils.observeEvent
import com.example.navcompro.utils.viewModelCreator
import com.example.navcompro.views.DashboardItemView


class BoxFragment : Fragment(R.layout.fragment_box) {

    private lateinit var binding: FragmentBoxBinding

    private val args by navArgs<BoxFragmentArgs>()

    private val viewModel by viewModelCreator { BoxViewModel(getBoxId(), Repositories.boxesRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBoxBinding.bind(view)

        binding.root.setBackgroundColor(DashboardItemView.getBackgroundColor(getColorValue()))
        binding.boxTextView.text = getString(R.string.this_is_box, getColorName())

        binding.goBackButton.setOnClickListener { onGoBackButtonPressed() }

        listenShouldExitEvent()
    }

    private fun onGoBackButtonPressed() {
       findNavController().popBackStack()
    }

    private fun listenShouldExitEvent() = viewModel.shouldExitEvent.observeEvent(viewLifecycleOwner) { shouldExit ->
        if (shouldExit) {
            // close the screen if the box has been deactivated
          findNavController().popBackStack()
        }
    }

    private fun getBoxId(): Long = args.boxId
        //"Extract box id from arguments here")


    private fun getColorValue(): Int = args.colorValue
        // "Extract color value from arguments here")


    private fun getColorName(): String = args.colorName
        //"Extract color name from arguments here")


}