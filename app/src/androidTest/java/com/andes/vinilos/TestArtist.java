package com.andes.vinilos;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.andes.vinilos.ui.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestArtist {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testAppContext() {
        assertEquals("com.andes.vinilos", context.getPackageName());
    }

    @Test
    public void artistListTest() {
        Espresso.onView(withId(R.id.navigation_artist)).perform(ViewActions.click());
        SystemClock.sleep(1500);
        ViewInteraction nombre = Espresso.onView(Matchers.allOf(ViewMatchers.withText("Album name")));
        //validar Nombre de un album
        ViewInteraction album = Espresso.onView(withId(R.id.musicianName));
        //validar Nombre de genero
        ViewInteraction generoName = Espresso.onView(withId(R.id.albumDescription));

    }
}
