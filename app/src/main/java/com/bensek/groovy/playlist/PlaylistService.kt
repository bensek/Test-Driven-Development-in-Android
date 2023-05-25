package com.bensek.groovy.playlist

import kotlinx.coroutines.flow.Flow

class PlaylistService {
    suspend fun fetchPlaylists(): Flow<Result<List<Playlist>>> {
        TODO()
    }

}
