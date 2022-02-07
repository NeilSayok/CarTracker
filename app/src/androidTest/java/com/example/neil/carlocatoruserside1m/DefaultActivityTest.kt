package com.example.neil.carlocatoruserside1m

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test


class DefaultActivityTest{
    @Test
    fun is_activity_in_view() {
        val defaultActivityScenario = ActivityScenario.launch(DefaultActivity::class.java)

        onView(withId(R.id.fragmentContainerView)).check(matches(isDisplayed()))
    }
}