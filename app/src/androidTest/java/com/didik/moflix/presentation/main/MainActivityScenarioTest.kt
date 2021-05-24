package com.didik.moflix.presentation.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.didik.moflix.R
import com.didik.moflix.utils.testing.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityScenarioTest {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun verifyMovieListScrolling() {
        with(onView(withId(R.id.navigation_movies))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.moviesRecyclerView))) {
            check(matches(isDisplayed()))

            perform(scrollToPosition<RecyclerView.ViewHolder>(5))
            perform(scrollToPosition<RecyclerView.ViewHolder>(10))
            perform(scrollToPosition<RecyclerView.ViewHolder>(15))
            perform(scrollToPosition<RecyclerView.ViewHolder>(20))
        }
    }

    @Test
    fun verifyClickMovieRedirectToDetail() {
        with(onView(withId(R.id.navigation_movies))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.moviesRecyclerView))) {
            check(matches(isDisplayed()))

            perform(scrollToPosition<RecyclerView.ViewHolder>(5))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        }

        onView(withId(R.id.coverImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.thumbnailImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDateTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.ratingView)).check(matches(isDisplayed()))
        onView(withId(R.id.overviewTextView)).check(matches(isDisplayed()))

        with(onView(withId(R.id.movieDetailRecyclerView))) {
            check(matches(isDisplayed()))
            perform(swipeUp())
        }
        onView(withId(R.id.castTitleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.castRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun verifySeriesListScrolling() {
        with(onView(withId(R.id.navigation_series))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.seriesRecyclerView))) {
            check(matches(isDisplayed()))

            perform(scrollToPosition<RecyclerView.ViewHolder>(5))
            perform(scrollToPosition<RecyclerView.ViewHolder>(10))
            perform(scrollToPosition<RecyclerView.ViewHolder>(15))
            perform(scrollToPosition<RecyclerView.ViewHolder>(20))
        }
    }

    @Test
    fun verifyClickSeriesRedirectToDetail() {
        with(onView(withId(R.id.navigation_series))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.seriesRecyclerView))) {
            check(matches(isDisplayed()))

            perform(scrollToPosition<RecyclerView.ViewHolder>(5))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        }

        onView(withId(R.id.coverImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.thumbnailImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDateTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.ratingView)).check(matches(isDisplayed()))
        onView(withId(R.id.overviewTextView)).check(matches(isDisplayed()))

        with(onView(withId(R.id.movieDetailRecyclerView))) {
            check(matches(isDisplayed()))
            perform(swipeUp())
        }
        onView(withId(R.id.castTitleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.castRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyProfileMenuIsDisplayed() {
        with(onView(withId(R.id.navigation_profile))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.userPhotoImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.nameTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.emailTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.changeLanguageButton)).check(matches(isDisplayed()))
        onView(withId(R.id.appVersionTextView)).check(matches(isDisplayed()))
    }

}