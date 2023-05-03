package com.example.navcompro.screens.main.toobs.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.example.navcompro.model.boxes.BoxesRepository
import com.example.navcompro.utils.MutableLiveEvent
import com.example.navcompro.utils.publishEvent
import com.example.navcompro.utils.share

class BoxViewModel(
    private val boxId: Long,
    private val boxesRepository: BoxesRepository
) : ViewModel() {

    private val _shouldExitEvent = MutableLiveEvent<Boolean>()
    val shouldExitEvent = _shouldExitEvent.share()

    init {
        viewModelScope.launch {
            boxesRepository.getBoxesAndSettings(onlyActive = true)
                .map { boxes -> boxes.firstOrNull { it.box.id == boxId } }
                .collect { currentBox ->
                    _shouldExitEvent.publishEvent(currentBox == null)
                }
        }
    }
}