package com.didik.moflix.presentation.features.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.didik.moflix.R
import com.didik.moflix.utils.testing.EspressoIdlingResource
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    fun test_1_verify_movie_list_and_click_detail() {
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
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(20, click()))
        }

        onView(withId(R.id.movieDetailAppBar)).check(matches(isDisplayed()))
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
    fun test_2_verify_series_list_and_click_detail() {
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
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(20, click()))
        }

        onView(withId(R.id.seriesDetailAppBar)).check(matches(isDisplayed()))
        onView(withId(R.id.coverImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.thumbnailImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDateTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.ratingView)).check(matches(isDisplayed()))
        onView(withId(R.id.overviewTextView)).check(matches(isDisplayed()))

        with(onView(withId(R.id.seriesDetailRecyclerView))) {
            check(matches(isDisplayed()))
            perform(swipeUp())
        }
        onView(withId(R.id.castTitleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.castRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_3_verify_insert_movie_to_favorites() {
        with(onView(withId(R.id.moviesRecyclerView))) {
            check(matches(isDisplayed()))
            perform(scrollToPosition<RecyclerView.ViewHolder>(5))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        }

        with(onView(withId(R.id.favorite))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(isRoot()).perform(pressBack())

        with(onView(withId(R.id.moviesRecyclerView))) {
            perform(scrollToPosition<RecyclerView.ViewHolder>(10))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        }

        with(onView(withId(R.id.favorite))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(isRoot()).perform(pressBack())

        with(onView(withId(R.id.navigation_favorites))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.emptyImageView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.emptyTextView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.favoriteMoviesRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.sortingButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test_4_verify_favorite_movies_action_sorting_button() {
        with(onView(withId(R.id.navigation_favorites))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.sortingButton))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withText("Oldest"))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.sortingButton)).perform(click())

        with(onView(withText("Random"))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.sortingButton)).perform(click())

        with(onView(withText("Newest"))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.favoriteMoviesRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_5_verify_delete_movie_from_favorites() {
        with(onView(withId(R.id.moviesRecyclerView))) {
            check(matches(isDisplayed()))
            perform(scrollToPosition<RecyclerView.ViewHolder>(5))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        }

        with(onView(withId(R.id.favorite))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(isRoot()).perform(pressBack())

        with(onView(withId(R.id.navigation_favorites))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.favoriteMoviesRecyclerView))) {
            check(matches(isDisplayed()))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }

        with(onView(withId(R.id.favorite))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.emptyImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.favoriteMoviesRecyclerView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.sortingButton)).check(matches(not(isDisplayed())))
    }

    @Test
    fun test_6_verify_insert_series_to_favorites() {
        with(onView(withId(R.id.navigation_series))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.seriesRecyclerView))) {
            check(matches(isDisplayed()))
            perform(scrollToPosition<RecyclerView.ViewHolder>(5))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        }

        with(onView(withId(R.id.favorite))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(isRoot()).perform(pressBack())

        with(onView(withId(R.id.seriesRecyclerView))) {
            perform(scrollToPosition<RecyclerView.ViewHolder>(10))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        }

        with(onView(withId(R.id.favorite))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(isRoot()).perform(pressBack())

        with(onView(withId(R.id.navigation_favorites))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withText("SERIES"))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.emptyImageView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.emptyTextView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.favoriteSeriesRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.sortingButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test_7_verify_favorite_series_action_sorting_button() {
        with(onView(withId(R.id.navigation_favorites))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withText("SERIES"))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.sortingButton))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withText("Oldest"))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.sortingButton)).perform(click())

        with(onView(withText("Random"))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.sortingButton)).perform(click())

        with(onView(withText("Newest"))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.favoriteSeriesRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_8_verify_delete_series_from_favorites() {
        with(onView(withId(R.id.navigation_series))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.seriesRecyclerView))) {
            check(matches(isDisplayed()))
            perform(scrollToPosition<RecyclerView.ViewHolder>(5))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        }

        with(onView(withId(R.id.favorite))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(isRoot()).perform(pressBack())

        with(onView(withId(R.id.navigation_favorites))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withText("SERIES"))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        with(onView(withId(R.id.favoriteSeriesRecyclerView))) {
            check(matches(isDisplayed()))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }

        with(onView(withId(R.id.favorite))) {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.emptyImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.favoriteSeriesRecyclerView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.sortingButton)).check(matches(not(isDisplayed())))
    }

    @Test
    fun test_9_redirect_to_profile_menu() {
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