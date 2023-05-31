package com.bensek.groovy.details

import com.bensek.groovy.utils.BaseUnitTest
import com.bensek.groovy.utils.captureValues
import com.bensek.groovy.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistDetailsViewModelShould: BaseUnitTest() {

    private lateinit var viewModel: PlaylistDetailsViewModel
    private val id = "1"
    private val service: PlaylistDetailsService = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistDetails>(exception)
    @Test
    fun getPlaylistDetailsFromService() {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        viewModel.playlistDetails.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlaylistDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails() {
        mockErrorCase()

        assertEquals(error, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun showLoaderWhileLoading() {
        mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistDetailsLoad() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)
        viewModel.loader.captureValues {

            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values[0])
        }
    }

    private fun mockErrorCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(error)
            }
        )

        viewModel = PlaylistDetailsViewModel(service)

        viewModel.getPlaylistDetails(id)
    }

    private fun mockSuccessfulCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(expected)
            }
        )
        viewModel = PlaylistDetailsViewModel(service)
    }
}