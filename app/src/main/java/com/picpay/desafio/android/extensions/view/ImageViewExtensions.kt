package com.picpay.desafio.android.extensions.view

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.picpay.desafio.android.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


fun ImageView.loadImage(
    src: String,
    @DrawableRes errorImage: Int = R.drawable.ic_round_account_circle,
    loadView: View? = null
) {
    Picasso.get()
        .load(src)
        .error(errorImage)
        .into(this, object : Callback {

            override fun onSuccess() {
                loadView?.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                loadView?.visibility = View.VISIBLE
            }
        })
}