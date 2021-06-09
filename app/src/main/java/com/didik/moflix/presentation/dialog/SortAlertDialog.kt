package com.didik.moflix.presentation.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.didik.moflix.R
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort

class SortAlertDialog(private val context: Context) {

    private var alertBuilder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null

    private var currentItem = 0
    private var title: CharSequence? = null
    private var onItemSelectedListener: ((sort: Sort) -> Unit)? = null

    fun setTitle(titleText: CharSequence): SortAlertDialog {
        title = titleText
        return this
    }

    fun setOnItemSelectedListener(listener: (sort: Sort) -> Unit): SortAlertDialog {
        onItemSelectedListener = listener
        return this
    }

    fun create(): SortAlertDialog {
        val items = context.resources.getStringArray(R.array.array_favorites_sort)

        alertBuilder = AlertDialog.Builder(context, R.style.SortAlertDialog).apply {
            setTitle(title)
            setSingleChoiceItems(items, currentItem) { _, checkedItem ->
                if (currentItem != checkedItem) {
                    val sort = when (checkedItem) {
                        0 -> Sort.NEWEST
                        1 -> Sort.OLDEST
                        2 -> Sort.RANDOM
                        else -> Sort.NEWEST
                    }
                    currentItem = checkedItem
                    onItemSelectedListener?.invoke(sort)
                    alertDialog?.dismiss()
                }
            }
        }
        alertDialog = alertBuilder?.create()
        alertDialog?.setCanceledOnTouchOutside(true)
        return this
    }

    fun show() {
        alertDialog?.show()
    }
}