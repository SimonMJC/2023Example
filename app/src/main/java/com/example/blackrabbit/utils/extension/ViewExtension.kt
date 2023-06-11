package com.example.blackrabbit.utils.extension

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener

fun ImageView.loadUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadUrl(url: String, requestListener: RequestListener<Drawable>) {
    Glide.with(this)
        .load(url)
        .listener(requestListener)
        .into(this)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}
