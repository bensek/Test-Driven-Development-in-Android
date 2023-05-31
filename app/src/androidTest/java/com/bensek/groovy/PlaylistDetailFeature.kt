package com.bensek.groovy

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.bensek.groovy.playlist.idlingResource
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import org.hamcrest.core.AllOf
import org.junit.Test

class PlaylistDetailFeature: BaseUITest() {

    @Test
    fun displaysPlaylistNameAndDetails() {
        // First navigate to that details screen.
        navigateToDetails()

        assertDisplayed("Hard Rock Cafe")

        assertDisplayed("Rock your senses with this timeless signature vibe list. \\n\\n • Poison \\n • You shook me all night \\n • Zombie \\n • Rock'n Me \\n • Thunderstruck \\n • I Hate Myself for Loving you \\n • Crazy \\n • Knockin' on Heavens Door")
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetails() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(5000)
        navigateToDetails()

        assertDisplayed(R.id.details_loader)
    }

    @Test
    fun hidesErrorMessage() {
        navigateToDetails(2)

        Thread.sleep(3000)

        assertNotExist(R.string.generic_error)

    }

    @Test
    fun hideLoader() {
        navigateToDetails()

        assertNotDisplayed(R.id.details_loader)
    }

    @Test
    fun displaysErrorMessageWhenNetworkFails() {
        //Click second item
        navigateToDetails(row = 1)

        assertDisplayed(R.string.generic_error)

    }

    private fun navigateToDetails(row: Int = 0) {
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.playlists_list),
                        row
                    )
                )
            )
        ).perform(ViewActions.click())
    }
}