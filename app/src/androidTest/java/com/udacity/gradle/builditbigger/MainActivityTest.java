package com.udacity.gradle.builditbigger;

import android.content.ComponentName;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pobla.joker.teller.JokeTellerActivity;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  @Rule
  public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);
  private SimpleIdlingResource mIdlingResource;

  @Before
  public void registerIdlingResource() {
    mIdlingResource = mActivityRule.getActivity().getIdlingResource();
    IdlingRegistry.getInstance().register(mIdlingResource);
  }

  //
  @After
  public void unregisterIdlingResource() {
    if (mIdlingResource != null) {
      IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
  }

  @Test
  public void afterClickButton_shouldShowJoke() throws Exception {
    //when
    mActivityRule.getActivity().tellJoke(null);

    //then
    intended(allOf(
      hasComponent(new ComponentName(getTargetContext(), JokeTellerActivity.class)),
      hasExtra(Matchers.equalToIgnoringCase("joke"), Matchers.any(String.class)))
    );

    //And
    onView(withId(R.id.tv_joke)).check(matches(withText(any(String.class))));
    onView(withId(R.id.tv_joke)).check(matches(not(withText("")))); //Text can't be empty  but we can't know the joke
  }


}