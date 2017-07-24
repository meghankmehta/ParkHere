package com.sparkleusc.sparklesparkhere;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by josep on 11/9/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BlackBoxTestExample {

    @Rule
    public ActivityTestRule<LaunchPageActivity> mActivityRule = new ActivityTestRule(LaunchPageActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withText("Login")).check(matches(isDisplayed()));
    }
}