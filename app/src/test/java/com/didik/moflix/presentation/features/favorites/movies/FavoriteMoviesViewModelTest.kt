package com.didik.moflix.presentation.features.favorites.movies

import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.helpers.InstantExecutorListener
import com.didik.moflix.utils.helpers.FavoriteSortUtils
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class FavoriteMoviesViewModelTest : ShouldSpec({

    val movieUseCase: MovieUseCase = mockk()
    lateinit var viewModel: FavoriteMoviesViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        mockkObject(FavoriteSortUtils)
        viewModel = FavoriteMoviesViewModel(movieUseCase)
    }

    afterTest {
        unmockkAll()
    }

    context("initial properties") {
        should("return default value correctly") {
            viewModel.isSortedList shouldBe false
        }
    }

    context("get favorite movies") {
        should("get data from movie use case and run correctly") {
            // Given
            val sort = Sort.NEWEST
            val fakeQuery: SimpleSQLiteQuery = mockk()
            val fakeDataSourceFactory: DataSource.Factory<Int, MovieModel> = mockk()

            every { FavoriteSortUtils.getMovieSortedQuery(sort) } returns fakeQuery
            coEvery { movieUseCase.getFavoriteMovies(fakeQuery) } returns fakeDataSourceFactory

            // When
            viewModel.getFavoriteMovies(sort)

            // Then
            verify(exactly = 1) { FavoriteSortUtils.getMovieSortedQuery(sort) }
            coVerify(exactly = 1) { movieUseCase.getFavoriteMovies(fakeQuery) }
        }
    }

})