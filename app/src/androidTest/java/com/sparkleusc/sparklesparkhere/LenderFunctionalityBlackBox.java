package com.sparkleusc.sparklesparkhere;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by josep on 11/9/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LenderFunctionalityBlackBox {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void InvalidLoginTest(){
        onView(withId(R.id.email)).perform(typeText("WILLFAIL"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("WILLFAIL"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        //onView(withText(R.string.TOAST_STRING)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //onView(withText(Toast.LENGTH_SHORT)).inRoot(withDecorView(not(is(LoginActivity).getDecorView())))
        //should stay on same page
        onView(withText("Sign In")).check(matches(isDisplayed()));


    }

    @Test
    public void ValidLoginTest(){
        onView(withId(R.id.email)).perform(typeText("EMAIL1"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("PASSWORD"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        //onView(withText(R.string.TOAST_STRING)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //onView(withText(Toast.LENGTH_SHORT)).inRoot(withDecorView(not(is(LoginActivity).getDecorView())))
        //should stay on same page
        onView(withText("Listings")).check(matches(isDisplayed()));
    }

    @Test
    public void LenderProfileNameListed(){
        onView(withId(R.id.email)).perform(typeText("EMAIL1"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("PASSWORD"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        //check that the name of the EMAIL1 lender is displayed on the listings page as expected
        onView(withText("NAME")).check(matches(isDisplayed()));
    }

    @Test
    public void LenderAddListing(){
        onView(withId(R.id.email)).perform(typeText("EMAIL1"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("PASSWORD"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        //check that the name of the EMAIL1 lender is displayed on the listings page as expected
        onView(withId(R.id.button13)).perform(click());
        onView(withText("Cancellation Policies")).check(matches(isDisplayed()));
    }


    @Test
    public void LenderEditListing(){
        onView(withId(R.id.email)).perform(typeText("EMAIL1"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("PASSWORD"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        //check that the name of the EMAIL1 lender is displayed on the listings page as expected
        onView(withText("nullTESTINGUPT")).perform(click());
        //transition to the page with Listing Title

        //should fail
        onView(withText("Update")).check(matches(isDisplayed()));
    }

    @Test
    public void LenderProfileTransition() {
        onView(withId(R.id.email)).perform(typeText("EMAIL1"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("PASSWORD"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.button9)).perform(click());
        //transition to the page with Listing Title
        onView(withText("Profile")).check(matches(isDisplayed()));
    }

    @Test
    public void InvalidPasswordTest() {
        onView(withId(R.id.email)).perform(typeText("EMAIL1"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("WILLFAIL!!!!"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withText("Sign In")).check(matches(isDisplayed()));
    }

    @Test
    public void LenderProfilePopulation() {
        onView(withId(R.id.email)).perform(typeText("EMAIL1"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("PASSWORD"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.button9)).perform(click());

        onView(withText("NAME")).check(matches(isDisplayed()));
    }
/*
    @Test
    public void LenderAddListingUniqueAddress(){
        onView(withId(R.id.email)).perform(typeText("EMAIL1"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("PASSWORD"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.button13)).perform(click());
        //transition to the page with Listing Title

        //should fail
        onView(withId(R.id.editText10).perform(typeText("Default"));

    }
   */
}