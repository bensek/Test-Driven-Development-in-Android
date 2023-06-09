package com.bensek.groovy.playlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bensek.groovy.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_playlist.view.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {
    lateinit var viewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)

        observeLoader()

        observePlaylist(view)

        return view
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> loader.visibility = View.VISIBLE
                else -> loader.visibility = View.GONE
            }
        }
    }

    private fun observePlaylist(view: View) {
        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null) {
                with(view.playlists_list as RecyclerView) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = MyPlaylistRecyclerViewAdapter(playlists.getOrNull()!!) { id ->
                        val action =
                            PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(
                                id
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {
            }
    }
}