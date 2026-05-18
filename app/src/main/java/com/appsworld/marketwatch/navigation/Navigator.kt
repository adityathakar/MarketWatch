package com.appsworld.marketwatch.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class Navigator {
    val backStack: SnapshotStateList<Any> = mutableStateListOf(Home)

    fun navigateTo(destination: Any) {
        backStack.add(destination)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }
}
