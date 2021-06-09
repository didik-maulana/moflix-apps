package com.didik.moflix.presentation.features.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentFavoritesBinding
import com.didik.moflix.presentation.features.favorites.movies.FavoriteMoviesFragment
import com.didik.moflix.presentation.features.favorites.series.FavoriteSeriesFragment
import com.didik.moflix.presentation.features.main.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupFavoritesTab()
    }

    private fun setupFavoritesTab() {
        val fragments = listOf(
            FavoriteMoviesFragment(),
            FavoriteSeriesFragment()
        )
        val titles = listOf(
            R.string.title_movies,
            R.string.title_series
        )
        binding.favoritesViewPager.adapter = ViewPagerAdapter(requireActivity(), fragments)

        TabLayoutMediator(binding.favoritesTabLayout, binding.favoritesViewPager) { tab, position ->
            tab.text = getString(titles[position])
            binding.favoritesViewPager.currentItem = position
        }.attach()

        binding.favoritesViewPager.currentItem = 0
    }
}