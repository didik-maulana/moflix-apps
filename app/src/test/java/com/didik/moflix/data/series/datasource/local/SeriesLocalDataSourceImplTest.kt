package com.didik.moflix.data.series.datasource.local

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.series.datasource.local.entities.SeriesEntity
import com.didik.moflix.data.series.datasource.local.room.SeriesDao
import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesLocalDataSourceImplTest : ShouldSpec({

    val seriesDao: SeriesDao = mockk()
    lateinit var localDataSource: SeriesLocalDataSource

    beforeTest {
        localDataSource = SeriesLocalDataSourceImpl(seriesDao)
    }

    afterTest {
        unmockkAll()
    }

    context("getSeries") {
        should("call get series from series dao and return data source factory") {
            // Given
            val fakeQuery: SupportSQLiteQuery = mockk()
            val fakeFavoriteSeries: DataSource.Factory<Int, SeriesEntity> = mockk()

            every { seriesDao.getSeries(fakeQuery) } returns fakeFavoriteSeries

            // When
            val result = localDataSource.getSeries(fakeQuery)

            // Then
            result shouldBe fakeFavoriteSeries
            verify(exactly = 1) { seriesDao.getSeries(fakeQuery) }
        }
    }

    context("getCountSeriesById") {
        should("call get count series by id from series dao and return integer") {
            // Given
            val fakeCount = Faker.int
            val fakeSeriesId = Faker.int

            every { seriesDao.getCountSeriesById(fakeSeriesId) } returns fakeCount

            // When
            val result = localDataSource.getCountSeriesById(fakeSeriesId)

            // Then
            result shouldBe fakeCount
            verify(exactly = 1) { seriesDao.getCountSeriesById(fakeSeriesId) }
        }
    }

    context("insertSeries") {
        should("call insert series from series dao") {
            // Given
            val fakeSeries: SeriesEntity = mockk()

            every { seriesDao.insertSeries(fakeSeries) } just runs

            // When
            localDataSource.insertSeries(fakeSeries)

            // Then
            verify(exactly = 1) { seriesDao.insertSeries(fakeSeries) }
        }
    }

    context("deleteSeries") {
        should("call delete series from series dao") {
            // Given
            val fakeSeries: SeriesEntity = mockk()

            every { seriesDao.deleteSeries(fakeSeries) } just runs

            // When
            localDataSource.deleteSeries(fakeSeries)

            // Then
            verify(exactly = 1) { seriesDao.deleteSeries(fakeSeries) }
        }
    }

})