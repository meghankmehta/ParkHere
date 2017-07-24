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
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by josep on 11/9/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SeekerFunctionalityBlackBox {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void ValidLoginTest(){
        onView(withId(R.id.email)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        //onView(withText(R.string.TOAST_STRING)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //onView(withText(Toast.LENGTH_SHORT)).inRoot(withDecorView(not(is(LoginActivity).getDecorView())))
        //should stay on same page
      //  onView(withId(R.id.button10)).check(matches(isClickable()));
    }

    @Test
    public void ValidUserInvalidPasswordLoginTest(){
        onView(withId(R.id.email)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("butts"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withText("Sign In")).check(matches(isDisplayed()));
    }
    @Test
    public void SeekerProfileTransition(){
        onView(withId(R.id.email)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.button11)).perform(click());

        onView(withText("Switch Profile")).check(matches(isDisplayed()));

    }

    @Test
    public void SeekerMakeReservationTransition(){
        onView(withId(R.id.email)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

//        onView(withId(R.id.button10)).perform(click());

        onView(withText("Add to Favorites")).check(matches(isDisplayed()));

    }

    @Test
    public void SeekerAdvancedTransition(){
        onView(withId(R.id.email)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.button1)).perform(click());


        onView(withText("Handicapped")).check(matches(isDisplayed()));

    }

    @Test
    public void SeekerProfilePopulation(){
        onView(withId(R.id.email)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("SEEKEREMMA"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.button11)).perform(click());

        onView(withText("SEEKEREMMA")).check(matches(isDisplayed()));

    }

}