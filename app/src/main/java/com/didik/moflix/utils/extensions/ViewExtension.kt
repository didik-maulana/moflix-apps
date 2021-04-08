package com.didik.moflix.utils.extensions

import android.graphics.drawable.LayerDrawable
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.ContextCompat
import com.didik.moflix.R

fun AppCompatRatingBar.setupRatingDrawable() {
    (progressDrawable as LayerDrawable).takeIf {
        it.numberOfLayers >= 2
    }?.let { layerDrawable ->
        layerDrawable.getDrawable(0).run {
            setTint(ContextCompat.getColor(context, R.color.blue_solitude))
        }

        layerDrawable.getDrawable(2).run {
            setTint(ContextCompat.getColor(context, R.color.yellow_supernova))
        }
    }
}