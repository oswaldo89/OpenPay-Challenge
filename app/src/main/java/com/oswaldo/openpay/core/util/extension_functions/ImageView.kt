package com.oswaldo.openpay.core.util.extension_functions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.oswaldo.openpay.R

fun ImageView.loadUrl(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.bg_placeholder)
        .into(this)
}