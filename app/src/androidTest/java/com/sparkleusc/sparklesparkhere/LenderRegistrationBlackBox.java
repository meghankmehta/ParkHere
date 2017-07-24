package com.sparkleusc.sparklesparkhere;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by josep on 11/9/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LenderRegistrationBlackBox {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityRule = new ActivityTestRule(SignUpActivity.class);

    @Test
    public void RegisterLenderTest() {
        onView(withId(R.id.FirstNameET)).perform(typeText("LENDERBENDER"), closeSoftKeyboard());

        onView(withId(R.id.LastNameET)).perform(typeText("BENDERLENDER"), closeSoftKeyboard());

        onView(withId(R.id.PhoneNumberET)).perform(typeText("3102177638"), closeSoftKeyboard());

        onView(withId(R.id.EmailAddressET)).perform(typeText(""+ UUID.randomUUID()), closeSoftKeyboard());

        onView(withId(R.id.passwordET)).perform(typeText("PASSWORD12"), closeSoftKeyboard());


        onView(withId(R.id.rbLender)).perform(click());


        onView(withId(R.id.rbLender)).check(matches(isChecked()));

        onView(withId(R.id.CreateProfileButton)).perform(click());

        onView(withText("Listings")).check(matches(isDisplayed()));

    }
}