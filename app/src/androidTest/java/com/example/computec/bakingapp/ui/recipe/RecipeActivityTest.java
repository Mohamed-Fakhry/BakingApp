package com.example.computec.bakingapp.ui.recipe;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.computec.bakingapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityRule = new ActivityTestRule<>(RecipeActivity.class);

    private RecipeIdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        RecipeActivity activity = mActivityRule.getActivity();
        idlingResource = new RecipeIdlingResource(activity);
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void recipeActivityTest() {

        onView(withId(R.id.recipeRV))
                .check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.recipeRV))
                .perform(scrollToPosition(3));

        onView(withId(R.id.recipeRV))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(3, click()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipeDetailsRV))
                .perform(scrollToPosition(10));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipeDetailsRV))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(10, click()));

        pressBack();

        onView(withId(R.id.recipeDetailsRV))
                .perform(scrollToPosition(1));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        onView(withId(R.id.recipeRV))
                .perform(scrollToPosition(2));

        onView(withId(R.id.recipeRV))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(2, click()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipeDetailsRV))
                .perform(scrollToPosition(10));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipeDetailsRV))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(10, click()));

    }

    @After
    public void unRegisterIdlingResources() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
