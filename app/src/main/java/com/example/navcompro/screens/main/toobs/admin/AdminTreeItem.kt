package com.example.navcompro.screens.main.toobs.admin

enum class ExpansionStatus {
    EXPANDED,
    COLLAPSED,
    NOT_EXPANDABLE
}

data class AdminTreeItem(
    val id: Long,
    val level: Int,
    val expansionStatus: ExpansionStatus,
    val attributes: Map<String, String>
)