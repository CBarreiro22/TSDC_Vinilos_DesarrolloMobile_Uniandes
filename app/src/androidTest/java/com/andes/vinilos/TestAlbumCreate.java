package com.andes.vinilos;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.ViewMatchers.hasBackground;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.SystemClock;
import android.widget.DatePicker;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.andes.vinilos.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
public class TestAlbumCreate {

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
    public void testAlbumCreate() {

        ViewInteraction album = onView(withText("Poeta del pueblo")).check(matches(isDisplayed()));
        ViewInteraction genero = onView(allOf(withText("Género")));

        ViewInteraction saveButton = onView(withId(R.id.saveAlbumFloatingButton));
        saveButton.perform(click());

    }

    @Test
    public void Escenario1() {

        // Desplegar pantalla para creación de Album
        onView(withId(R.id.saveAlbumFloatingButton)).perform(click());
        SystemClock.sleep(1500);

        // Diligenciar campos del formulario
        onView(withId(R.id.nombreAlbum)).perform(typeText("Mi primer Album"));
        onView(withId(R.id.albumGeneros)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Rock"))).perform(click());
        onView(withId(R.id.albumGeneros)).check(matches(withSpinnerText(containsString("Rock"))));

        ViewInteraction etiquetasGrabacionesSpinner = onView(withId(R.id.etiquetasGrabaciones));
        etiquetasGrabacionesSpinner.perform(scrollTo(), click());
        onView(withText("EMI")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        onView(withId(R.id.cover)).perform(typeText("https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg"));

        ViewInteraction descripcionAlbumEditText = onView(withId(R.id.descripcionAlbum));
        descripcionAlbumEditText.perform(scrollTo(), clearText(), typeText("Esto es un superalbum infinito"), closeSoftKeyboard());

        // Seleccionar fecha de lanzamiento
        onView(withId(R.id.datePickerButton)).perform(scrollTo(), click());
        SystemClock.sleep(1000);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        onView(instanceOf(DatePicker.class)).perform(PickerActions.setDate(year, month, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());

        // Guardar y crear el album
        onView(withId(R.id.guardarCrearAlbum)).perform(scrollTo(), click());

        SystemClock.sleep(1500);
    }

}
