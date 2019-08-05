package com.example.sdk;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.sdk.Activities.CallScreenActivity;
import com.example.sdk.Activities.ContactActivity;
import com.example.sdk.Activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private static final String ADEM_6 = "adem6@spidr.com";
    private static final String ADEM_7 = "adem7@spidr.com";
    private static final String ADEM_8 = "adem8@spidr.com";

    @Rule
    public ActivityTestRule<LoginActivity> loginAct = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void yapbiseylerayamızalıssın() {
        //  CHECK THE ACTIVITY
        onView(withId(R.id.txtUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()));

        //  LOG IN
        onView(withId(R.id.txtUsername)).perform(typeText(ADEM_8));
        closeSoftKeyboard();
        onView(withId(R.id.btnLogin)).perform(click());

        //  CHECK THE SECOND ACTIVITY
        onView(withId(R.id.txttoCall)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCallThis)).check(matches(isDisplayed()));

        //  MAKE THE CALL
        onView(withId(R.id.txttoCall)).perform(typeText(ADEM_7));
        closeSoftKeyboard();
        onView(withId(R.id.btnCallThis)).perform(click());

        //  DELAY ?

        // BISEYLER FALAN
        onView(withId(R.id.localVideoView)).check(matches(isDisplayed()));
        onView(withId(R.id.remoteVideoView)).check(matches(isDisplayed()));
        onView(withId(R.id.ibtnHold)).check(matches(isDisplayed()));
        onView(withId(R.id.ibtnMute)).check(matches(isDisplayed()));
        onView(withId(R.id.ibtnEnd)).check(matches(isDisplayed()));

        //  END THE CALL
        onView(withId(R.id.ibtnEnd)).perform(click());

        //  CHECK THE SECOND ACTIVITY.. AGAIN..
        onView(withId(R.id.txttoCall)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCallThis)).check(matches(isDisplayed()));

        // GO BACK
        Espresso.pressBack();

        // CHECK THE LOGIN PAGE
        onView(withId(R.id.txtUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()));
    }


    @Test
    public void hataistiyorumkemal(){
        //  CHECK THE ACTIVITY
        onView(withId(R.id.txtUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()));

        //  LOG IN
        onView(withId(R.id.txtUsername)).perform(typeText(ADEM_8));
        closeSoftKeyboard();
        onView(withId(R.id.btnLogin)).perform(click());

        //  CHECK THE SECOND ACTIVITY
        onView(withId(R.id.txttoCall)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCallThis)).check(matches(isDisplayed()));

        //  MAKE THE CALL
        onView(withId(R.id.txttoCall)).perform(typeText(ADEM_7));
        closeSoftKeyboard();
        onView(withId(R.id.btnCallThis)).perform(click());

        //  DELAY ?

        // BISEYLER FALAN
        onView(withId(R.id.localVideoView)).check(matches(isDisplayed()));
        onView(withId(R.id.remoteVideoView)).check(matches(isDisplayed()));
        onView(withId(R.id.ibtnHold)).check(matches(isDisplayed()));
        onView(withId(R.id.ibtnMute)).check(matches(isDisplayed()));
        onView(withId(R.id.ibtnEnd)).check(matches(isDisplayed()));

        //  END THE CALL
        onView(withId(R.id.ibtnEnd)).perform(click());

        //  CHECK THE SECOND ACTIVITY.. AGAIN..
        onView(withId(R.id.txttoCall)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCallThis)).check(matches(isDisplayed()));

        // GO BACK
        Espresso.pressBack();

        // GIVE ME ERRORS
        onView(withId(R.id.txttoCall)).check(matches(isDisplayed()));   //  No views in hierarchy found matching
        onView(withId(R.id.ibtnStop)).check(matches(isDisplayed()));    //  No views in hierarchy found matching
    }
}
