package com.appsworld.marketwatch.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.IdentityHashMap

class Navigator {
    val backStack: SnapshotStateList<Any> = mutableStateListOf(Home)

    fun navigateTo(destination: Any) {
        backStack.add(destination)
    }

    fun goBack() {
        backStack.removeLastOrNull()?.let { resultCallbacks.remove(it) }
    }

    // ── Result bus ───────────────────────────────────────────────────
    // Keyed by NavKey instance identity — two Login() calls are two distinct slots.
    private val resultCallbacks = IdentityHashMap<Any, (Any) -> Unit>()

    fun <R : Any> navigateForResult(destination: NavKeyWithResult<R>, onResult: (R) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        resultCallbacks[destination] = { onResult(it as R) }
        backStack.add(destination)
    }

    fun <R : Any> completeWithResult(key: NavKeyWithResult<R>, result: R) {
        resultCallbacks.remove(key)?.invoke(result)
        goBack()
    }
}
