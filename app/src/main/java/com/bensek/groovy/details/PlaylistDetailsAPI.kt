package com.bensek.groovy.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

interface PlaylistDetailsAPI {

    @GET("playlist-details/{id}")
    suspend fun fetchPlaylistDetails(@Path("id") id: String): PlaylistDetails

}
