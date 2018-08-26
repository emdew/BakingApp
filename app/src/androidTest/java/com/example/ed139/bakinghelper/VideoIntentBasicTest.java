package com.example.ed139.bakinghelper;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class VideoIntentBasicTest {

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a step item and checks it opens up the VideoActivity with the correct details.
     */
    @Test
    public void clickStepItem_OpensVideoActivity() {

        // click Nutella Pie
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // is quantity text view visible?
        onView(withId(R.id.steps_rv)).check(matches(hasDescendant(withId(R.id.desc_tv))));

        // click on a step
        onView(withId(R.id.steps_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // is exoplayer appearing?
        onView(withId(R.id.exo_play)).check(matches(isDisplayed()));
    }
}
