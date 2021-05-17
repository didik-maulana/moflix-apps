package com.didik.moflix.presentation.detail.component

import android.view.View
import coil.load
import coil.transform.RoundedCornersTransformation
import com.didik.moflix.R
import com.didik.moflix.databinding.ItemCastBinding
import com.didik.moflix.domain.model.CastModel
import com.xwray.groupie.viewbinding.BindableItem

class CastItem(private val cast: CastModel) : BindableItem<ItemCastBinding>() {

    override fun getLayout(): Int = R.layout.item_cast

    override fun initializeViewBinding(view: View): ItemCastBinding = ItemCastBinding.bind(view)

    override fun bind(viewBinding: ItemCastBinding, position: Int) {
        with(viewBinding) {
            nameTextView.text = cast.name
            characterTextView.text = cast.character

            photoImageView.load(cast.photoUrl) {
                crossfade(true)
                crossfade(500)
                transformations(RoundedCornersTransformation(10f))
                placeholder(R.drawable.ic_movie)
                error(R.drawable.ic_movie)
            }
        }
    }
}