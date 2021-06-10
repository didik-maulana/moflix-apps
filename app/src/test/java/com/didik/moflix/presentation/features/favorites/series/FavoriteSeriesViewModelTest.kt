package com.didik.moflix.presentation.features.favorites.series

import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.helpers.InstantExecutorListener
import com.didik.moflix.utils.helpers.FavoriteSortUtils
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class FavoriteSeriesViewModelTest : ShouldSpec({

    val seriesUseCase: SeriesUseCase = mockk()
    lateinit var viewModel: FavoriteSeriesViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        mockkObject(FavoriteSortUtils)
        viewModel = FavoriteSeriesViewModel(seriesUseCase)
    }

    afterTest {
        unmockkAll()
    }

    context("initial properties") {
        should("return default value correctly") {
            viewModel.isSortedList shouldBe false
        }
    }

    context("get favorite series") {
        should("get data from series use case and run correctly") {
            // Given
            val sort = Sort.NEWEST
            val fakeQuery: SimpleSQLiteQuery = mockk()
            val fakeDataSourceFactory: DataSource.Factory<Int, MovieModel> = mockk()

            every { FavoriteSortUtils.getSeriesSortedQuery(sort) } returns fakeQuery
            coEvery { seriesUseCase.getFavoriteSeries(fakeQuery) } returns fakeDataSourceFactory

            // When
            viewModel.getSeries(sort)

            // Then
            verify(exactly = 1) { FavoriteSortUtils.getSeriesSortedQuery(sort) }
            coVerify(exactly = 1) { seriesUseCase.getFavoriteSeries(fakeQuery) }
        }
    }

})