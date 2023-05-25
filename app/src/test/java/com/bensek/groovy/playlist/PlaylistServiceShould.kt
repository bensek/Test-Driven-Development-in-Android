package com.bensek.groovy.playlist

import com.bensek.groovy.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistServiceShould: BaseUnitTest() {
    private lateinit var service: PlaylistService
    private val api: PlaylistAPI = mock()
    private val playlists = mock<List<Playlist>>()

    @Test
    fun fetchPlaylistFromAPI() = runBlockingTest {
        service = PlaylistService(api)

        service.fetchPlaylists().first()

        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        whenever(api.fetchAllPlaylists()).thenThrow(RuntimeException("Server error"))

        service = PlaylistService(api)
        assertEquals("Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

}