package com.didik.moflix.network.helper

import android.net.Uri
import com.didik.moflix.BuildConfig

object ImageHelper {
    fun getImageURL(
        imageSize: ImageSize,
        imagePath: String
    ): String {
        return Uri.parse(BuildConfig.IMAGE_BASE_URL).buildUpon().apply {
            appendPath("t")
            appendPath("p")
            appendPath(imageSize.resolution)
            appendPath(imagePath)
        }.build().toString()
    }
}