package com.bensek.groovy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bensek.groovy.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)
        val id = args.playlistId

        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistDetailsViewModel::class.java)

        viewModel.getPlaylistDetails(id)

        observeLoader()
        observePlaylistDetails()



        return view
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> details_loader.visibility = View.VISIBLE
                else -> details_loader.visibility = View.GONE
            }
        }
    }
    private fun observePlaylistDetails() {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                playlist_name.text = playlistDetails.getOrNull()!!.name
                playlist_details.text = playlistDetails.getOrNull()!!.details
            } else {
                Snackbar.make(playlist_detail_root,
                    R.string.generic_error,
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setupViewModel() {
         }


    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistDetailFragment()
    }
}