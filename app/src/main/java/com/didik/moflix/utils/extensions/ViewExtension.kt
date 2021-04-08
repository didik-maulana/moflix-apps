package com.didik.moflix.utils.extensions

import android.graphics.drawable.LayerDrawable
import androidx.appcompat.widget.AppCompatRatingBar
import com.didik.moflix.utils.helpers.ColorPalette

fun AppCompatRatingBar.setupRatingDrawable() {
    (progressDrawable as LayerDrawable).takeIf {
        it.numberOfLayers >= 2
    }?.let { layerDrawable ->
        layerDrawable.getDrawable(0).run {
            setTint(ColorPalette.BLUE_PERIWINKLE)
        }

        layerDrawable.getDrawable(2).run {
            setTint(ColorPalette.YELLOW_SUPERNOVA)
        }
    }
}