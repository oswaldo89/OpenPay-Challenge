package com.oswaldo.openpay.core.util.extension_functions

import android.view.View
import com.oswaldo.openpay.core.util.SafeClickListener

fun View.setOnSafeClickListener(onSafeClick: (View) -> Unit) {
    setOnClickListener(SafeClickListener { v ->
        onSafeClick(v)
    })
}