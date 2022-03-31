package com.example.bikerx.ui.home

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bikerx.R
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RecommendationsFragmentTest : AutoCloseKoinTest() {
    private lateinit var scenario: FragmentScenario<HomeFragment>
    private val homeViewModel: HomeViewModel = mockk(relaxed = true)

    @Before
    fun setUp() {
        startKoin {
            modules(
                //module { viewModel { recommendationsViewModel}}
            )
        }
        scenario = launchFragmentInContainer()
        scenario.moveToState(newState = Lifecycle.State.STARTED)
    }

    @Test
    fun queryRoutes() {
        scenario.onFragment { fragment ->
            //fragment.on
        }
        onView(withId(R.id.recommended_route_see_all)).perform(click())
        //onView(withId(R.id.recommended_route_text_view)).check(matches(withText("Central")))
    }
}