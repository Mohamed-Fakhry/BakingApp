package com.example.computec.bakingapp.ui.recipe;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.computec.bakingapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

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

        ViewInteraction recipeRV = onView(
                allOf(withId(R.id.recipeRV),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)));
        recipeRV.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recipeDetailsRV = onView(
                allOf(withId(R.id.recipeDetailsRV),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)));
        recipeDetailsRV.perform(actionOnItemAtPosition(9, click()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        ViewInteraction recipeDetailsRV2 = onView(
                allOf(withId(R.id.recipeDetailsRV),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)));
        recipeDetailsRV2.perform(actionOnItemAtPosition(10, click()));

        pressBack();
        pressBack();

        ViewInteraction recipeRV2 = onView(
                allOf(withId(R.id.recipeRV),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                2)));
        recipeRV2.perform(actionOnItemAtPosition(2, click()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @After
    public void unRegisterIdlingResources() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
