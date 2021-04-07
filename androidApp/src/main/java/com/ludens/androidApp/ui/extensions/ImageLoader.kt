package com.ludens.androidApp.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DimenRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun ImageView.load(
    url: String,
    placeholder: Drawable? = this.drawable,
    onLoadFailed: (() -> Boolean)? = null,
    onLoadSuccessful: (() -> Boolean)? = null
) = load(this, url, placeholder, onLoadFailed, onLoadSuccessful)

fun ImageView.load(
    image: Bitmap
) = Glide.with(this)
    .load(image)
    .into(this)

fun ImageView.loadIntoCircle(
    url: String,
    placeholder: Drawable? = this.drawable,
    onLoadFailed: (() -> Boolean)? = null,
    onLoadSuccessful: (() -> Boolean)? = null
) = load(this, url, placeholder, onLoadFailed, onLoadSuccessful, RequestOptions.circleCropTransform())

fun ImageView.loadIntoCircle(
    image: Bitmap
) = Glide.with(this)
    .load(image)
    .apply(RequestOptions.circleCropTransform())
    .into(this)

fun ImageView.loadIntoRectangle(
    url: String,
    placeholder: Drawable? = this.drawable,
    onLoadFailed: (() -> Boolean)? = null,
    onLoadSuccessful: (() -> Boolean)? = null
) = load(this, url, placeholder, onLoadFailed, onLoadSuccessful, RequestOptions.centerCropTransform())

fun ImageView.loadWithRoundedCorners(
    url: String,
    @DimenRes radiusRes: Int,
    placeholder: Drawable? = this.drawable,
    onLoadFailed: (() -> Boolean)? = null,
    onLoadSuccessful: (() -> Boolean)? = null
) =
    load(
        this, url, placeholder, onLoadFailed, onLoadSuccessful,
        RequestOptions().transform(CenterCrop(), RoundedCorners(resources.getDimensionPixelSize(radiusRes)))
    )

private fun load(
    imageView: ImageView,
    url: String,
    placeholder: Drawable?,
    onLoadFailed: (() -> Boolean)?,
    onLoadSuccessful: (() -> Boolean)?,
    requestOptions: RequestOptions = RequestOptions()
) = Glide.with(imageView)
    .load(url)
    .apply(requestOptions)
    .placeholder(placeholder)
    .listener(initResourceLoaderListener(onLoadFailed, onLoadSuccessful))
    .into(imageView)

fun ImageView.cancelImageLoad() {
    Glide.with(this).clear(this)
}

private fun initResourceLoaderListener(
    onLoadFailed: (() -> Boolean)? = null,
    onLoadSuccessful: (() -> Boolean)? = null
): RequestListener<Drawable>? =
    if (onLoadFailed != null || onLoadSuccessful != null) object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            return onLoadFailed?.invoke() ?: false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return onLoadSuccessful?.invoke() ?: false
        }
    }
    else null
