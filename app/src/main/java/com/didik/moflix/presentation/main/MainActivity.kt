package com.didik.moflix.presentation.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.didik.moflix.R
import com.didik.moflix.base.BindingActivity
import com.didik.moflix.databinding.ActivityMainBinding
import com.didik.moflix.presentation.main.MainActivity.MainMenu.*
import com.didik.moflix.presentation.movies.MoviesFragment
import com.didik.moflix.presentation.profile.ProfileFragment
import com.didik.moflix.presentation.series.SeriesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {

    override fun initViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun renderView() {
        setupViewPager()
        setupBottomNavigationView()
    }

    private fun setupViewPager() {
        val fragments = listOf(
            MoviesFragment(),
            SeriesFragment(),
            ProfileFragment()
        )
        binding.fragmentViewPager.run {
            adapter = ViewPagerAdapter(supportFragmentManager, fragments)
            setSwipePagingEnabled(false)
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_movies -> {
                    binding.fragmentViewPager.currentItem = HOME.index
                    true
                }
                R.id.navigation_series -> {
                    binding.fragmentViewPager.currentItem = SERIES.index
                    true
                }
                R.id.navigation_profile -> {
                    binding.fragmentViewPager.currentItem = PROFILE.index
                    true
                }
                else -> false
            }
        }
    }

    private enum class MainMenu(val index: Int) {
        HOME(0),
        SERIES(1),
        PROFILE(2)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}