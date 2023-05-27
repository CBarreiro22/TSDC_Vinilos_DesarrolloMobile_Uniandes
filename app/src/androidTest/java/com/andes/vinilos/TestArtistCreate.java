package com.andes.vinilos;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;

import android.os.SystemClock;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.test.espresso.contrib.PickerActions;

import com.andes.vinilos.ui.MainActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.Test;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
public class TestArtistCreate {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void createArtist() {
        onView(withId(R.id.navigation_artist)).perform(click());
        SystemClock.sleep(1500);

        onView(withId(R.id.saveArtistFloatingButton)).perform(click());
        SystemClock.sleep(1500);

        onView(withId(R.id.artistName)).perform(typeText("Test Nombre Artista"));
        onView(withId(R.id.artistDescription)).perform(typeText("Tetst Descripcion Artista"));
        onView(withId(R.id.artistImage)).perform(typeText("https://es.wikipedia.org/wiki/Carlos_Vives#/media/Archivo:Carlos_Vives_Premios_Lo_Nuestro_2016.jpg"));

        onView(withId(R.id.artistBirthday)).perform(click());
        SystemClock.sleep(1500);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        onView(instanceOf(DatePicker.class)).perform(PickerActions.setDate(year, month, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());
        SystemClock.sleep(1500);
        onView(withId(R.id.guardarCrearArtista)).perform(click());
        SystemClock.sleep(1500);
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Lists of Artists")));
        SystemClock.sleep(1500);
    }
}