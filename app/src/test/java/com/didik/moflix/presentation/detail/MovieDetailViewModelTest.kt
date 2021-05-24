package com.didik.moflix.presentation.detail

import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.helpers.InstantExecutorListener
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.spyk
//
//class MovieDetailViewModelTest : ShouldSpec({
//
//    lateinit var movieDetailViewModel: MovieDetailViewModel
//
//    listeners(InstantExecutorListener())
//
//    beforeTest {
//        movieDetailViewModel = spyk(MovieDetailViewModel())
//    }
//
//    context("loadMovie") {
//        should("return movie correct value") {
//            // Inject
//            val mockMovie: MovieModel = mockk()
//
//            // When
//            movieDetailViewModel.loadMovie(mockMovie)
//
//            // Then
//            movieDetailViewModel.movie.value shouldBe mockMovie
//        }
//    }
//
//})