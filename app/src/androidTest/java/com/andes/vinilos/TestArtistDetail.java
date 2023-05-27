package com.andes.vinilos;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.andes.vinilos.TestAlbum.withIndex;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.andes.vinilos.ui.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestArtistDetail {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.andes.vinilos", appContext.getPackageName());
        ViewInteraction toolBar = onView(allOf(withId(R.id.navigation_artist), withId(R.id.navigation_artist), isDisplayed()));
        toolBar.perform(click());

        SystemClock.sleep(1500);
    }

    @Test
    public void viewAlbumDetailTest() {
        ViewInteraction menu1 = onView(withId(R.id.navigation_artist));
        menu1.perform(click());
        SystemClock.sleep(1500);
        //Ir a detalle de Artista
        onView(withIndex(withId(R.id.textViewName), 0)).perform(click());
        SystemClock.sleep(1500);
        ViewInteraction originview = onView(allOf(withId(R.id.lblName))).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.lblName), 0)).check(matches(withText(startsWith("Nombre:"))));
    }

    @Test
    public void viewAlbumDetailPrizesListTestButtom() {
        ViewInteraction menu1 = onView(withId(R.id.navigation_artist));
        menu1.perform(click());
        SystemClock.sleep(1500);

        // Ir a detalle de Artista
        onView(withIndex(withId(R.id.textViewName), 0)).perform(click());
        SystemClock.sleep(1500);

        // Verificar la visibilidad del botón "Agregar premio"
        onView(withId(R.id.buttonPremio)).check(matches(isDisplayed()));

        // Verificar que el botón "Agregar premio" es clicable
        onView(withId(R.id.buttonPremio)).check(matches(isClickable()));
    }

    @Test
    public void viewAlbumDetailPrizesListTestClickListPrizes() {
        ViewInteraction menu1 = onView(withId(R.id.navigation_artist));
        menu1.perform(click());
        SystemClock.sleep(1500);

        // Ir a detalle de Artista
        onView(withIndex(withId(R.id.textViewName), 0)).perform(click());
        SystemClock.sleep(1500);

        // Verificar que el Spinner es clicable
        onView(withId(R.id.premiosLista)).check(matches(isClickable()));

        // Hacer clic en el Spinner
        onView(withId(R.id.premiosLista)).perform(click());
    }




}