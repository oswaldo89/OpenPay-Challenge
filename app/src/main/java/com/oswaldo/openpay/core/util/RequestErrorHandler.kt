package com.oswaldo.openpay.core.util

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oswaldo.openpay.core.domain.DomainException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Simplify launching a coroutine in conjunction with DomainErrorFactory handling
 */
inline fun ViewModel.launch(
    crossinline onError: (error: DomainException) -> Unit,
    dispatcher: CoroutineContext = Dispatchers.IO,
    noinline block: suspend CoroutineScope.() -> Unit,
) {
    viewModelScope.launchSafe(
        dispatcher = dispatcher,
        onDomainException = onError,
        block = block
    )
}

/**
 * Simplify launching a coroutine for legacy
 */
@SuppressLint("LongLogTag")
inline fun CoroutineScope.launchSafe(
    dispatcher: CoroutineContext = EmptyCoroutineContext,
    crossinline onDomainException: (error: DomainException) -> Unit = {},
    noinline block: suspend CoroutineScope.() -> Unit,
) {
    this.launch(dispatcher) {
        try {
            block()
        } catch (error: DomainException) {
            Log.e("while executing a coroutine with launch", "message: ${error.message}")

            this.launch(Dispatchers.Main) {
                onDomainException.invoke(error)
            }
        } catch (error: Throwable) {
            Log.e("while executing a coroutine with launch", "message: ${error.message}")
        }
    }
}