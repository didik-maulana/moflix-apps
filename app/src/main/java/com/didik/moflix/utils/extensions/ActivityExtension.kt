package com.didik.moflix.utils.extensions

import android.app.Activity
import android.content.Intent
import com.didik.moflix.R

fun Activity.shareContent(text: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(
        Intent.createChooser(
            shareIntent,
            getString(R.string.label_share)
        )
    )
}