package com.andes.vinilos;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasBackground;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.andes.vinilos.TestAlbum.withIndex;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import com.andes.vinilos.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestAlbumDetail {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.andes.vinilos", appContext.getPackageName());
        ViewInteraction toolBar = onView(allOf(withId(R.id.navigation_albums), withId(R.id.navigation_albums), isDisplayed()));
        toolBar.perform(click());

        SystemClock.sleep(1500);
    }

    @Test
    public void viewAlbumDetailTest() {
        ViewInteraction menu1 = onView(withId(R.id.navigation_albums));
        menu1.perform(click());
        SystemClock.sleep(1500);
        //Ir a detalle de Artista
        onView(withIndex(withId(R.id.textViewName), 0)).perform(click());
        SystemClock.sleep(1500);
        ViewInteraction originview = onView(allOf(withId(R.id.textViewNameLabel))).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.textViewNameLabel), 0)).check(matches(withText(startsWith("Nombre:"))));
    }

}

