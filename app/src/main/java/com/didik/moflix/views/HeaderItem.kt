package com.didik.moflix.views

import android.view.View
import com.didik.moflix.R
import com.didik.moflix.databinding.ItemHeaderBinding
import com.xwray.groupie.viewbinding.BindableItem

class HeaderItem(private val title: String?) : BindableItem<ItemHeaderBinding>() {

    override fun getLayout(): Int = R.layout.item_header

    override fun initializeViewBinding(view: View): ItemHeaderBinding = ItemHeaderBinding.bind(view)

    override fun bind(viewBinding: ItemHeaderBinding, position: Int) {
        viewBinding.titleTextView.text = title
    }
}