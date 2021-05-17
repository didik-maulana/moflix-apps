package com.didik.moflix.presentation.detail.component

import android.view.View
import com.didik.moflix.R
import com.didik.moflix.databinding.ItemCreditsBinding
import com.didik.moflix.domain.model.CastModel
import com.didik.moflix.utils.helpers.CustomItemDecoration
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem

class CreditsItem(
    private val title: String,
    private val credits: List<CastModel>
) : BindableItem<ItemCreditsBinding>() {

    private val castAdapter by lazy { GroupieAdapter() }

    override fun getLayout(): Int = R.layout.item_credits

    override fun initializeViewBinding(view: View): ItemCreditsBinding =
        ItemCreditsBinding.bind(view)

    override fun bind(viewBinding: ItemCreditsBinding, position: Int) {
        viewBinding.castTitleTextView.text = title

        val castItem = credits.map { cast ->
            CastItem(cast)
        }
        viewBinding.castRecyclerView.run {
            addItemDecoration(CustomItemDecoration(right = resources.getDimensionPixelSize(R.dimen.size_16dp)))
            adapter = castAdapter
        }
        castAdapter.addAll(castItem)
    }
}