package com.bensek.groovy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bensek.groovy.R
import com.bensek.groovy.playlist.PlaylistFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, PlaylistFragment.newInstance())
        }
    }
}