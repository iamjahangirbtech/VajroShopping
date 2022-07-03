package com.vajro.shoppingcart.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vajro.shoppingcart.R

fun ImageView.load(context: Context, url: String) {

    Glide.with(context)
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.ic_splash_logo)
                .error(R.drawable.ic_splash_logo)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .into(this)
}

fun ImageView.loadCenterInside(context: Context, url: String) {

    Glide.with(context)
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.ic_splash_logo)
                .error(R.drawable.ic_splash_logo)
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .into(this)
}