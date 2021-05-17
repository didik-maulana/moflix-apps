package com.didik.moflix.presentation.detail.component

import android.view.View
import coil.load
import coil.transform.RoundedCornersTransformation
import com.didik.moflix.R
import com.didik.moflix.databinding.ItemCoverBinding
import com.xwray.groupie.viewbinding.BindableItem

class CoverItem(
    private val coverUrl: String,
    private val thumbnailUrl: String
) : BindableItem<ItemCoverBinding>() {

    override fun getLayout(): Int = R.layout.item_cover

    override fun initializeViewBinding(view: View): ItemCoverBinding = ItemCoverBinding.bind(view)

    override fun bind(viewBinding: ItemCoverBinding, position: Int) {
        viewBinding.coverImageView.load(coverUrl) {
            crossfade(true)
            crossfade(500)
            placeholder(R.drawable.ic_movie)
            error(R.drawable.ic_movie)
        }

        viewBinding.thumbnailImageView.load(thumbnailUrl) {
            crossfade(true)
            crossfade(500)
            transformations(RoundedCornersTransformation(10f))
            placeholder(R.drawable.ic_movie)
            error(R.drawable.ic_movie)
        }
    }
}