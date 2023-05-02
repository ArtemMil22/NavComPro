package com.example.navcompro.model.boxes

import kotlinx.coroutines.flow.Flow
import com.example.navcompro.model.boxes.entities.Box
import com.example.navcompro.model.boxes.entities.BoxAndSettings

interface BoxesRepository {

    /**
     * Get the list of boxes.
     * @param onlyActive if set to `true` then only active boxes are emitted.
     */
    suspend fun getBoxesAndSettings(onlyActive: Boolean = false): Flow<List<BoxAndSettings>>

    /**
     * Mark the specified box as active. Only active boxes are displayed in dashboard screen.
     */
    suspend fun activateBox(box: Box)

    /**
     * Mark the specified box as inactive. Inactive boxes are not displayed in dashboard screen.
     */
    suspend fun deactivateBox(box: Box)

}