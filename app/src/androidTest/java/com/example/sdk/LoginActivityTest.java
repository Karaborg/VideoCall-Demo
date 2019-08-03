package com.example.sdk;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.sdk.Activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void login_test_with_user6() {
        //  LOG IN
        onView(withId(R.id.txtUsername)).perform(typeText("adem6@spidr.com"));
        onView(withId(R.id.btnLogin)).perform(click());

        // LOG OUT
        closeSoftKeyboard();
        Espresso.pressBack();
    }

    @Test
    public void login_test_with_user7() {
        //  LOG IN
        onView(withId(R.id.txtUsername)).perform(typeText("adem7@spidr.com"));
        onView(withId(R.id.btnLogin)).perform(click());

        // LOG OUT
        closeSoftKeyboard();
        Espresso.pressBack();
    }

    @Test
    public void login_test_with_user8(){
        //  LOG IN
        onView(withId(R.id.txtUsername)).perform(typeText("adem8@spidr.com"));
        onView(withId(R.id.btnLogin)).perform(click());

        // LOG OUT
        closeSoftKeyboard();
        Espresso.pressBack();
    }
}
