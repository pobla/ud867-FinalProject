package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pobla.joker.teller.JokeTellerActivity;
import com.udacity.gradle.builditbigger.JokerApiClientTask.JokerApiClientTaskComplete;


public class MainActivity extends AppCompatActivity implements JokerApiClientTaskComplete {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getIdlingResource();
  }

  @NonNull
  private SimpleIdlingResource mIdlingResource;

  @VisibleForTesting
  @NonNull
  public SimpleIdlingResource getIdlingResource() {
    if (mIdlingResource == null) {
      mIdlingResource = new SimpleIdlingResource();
    }
    return mIdlingResource;
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void tellJoke(View view) {
    new JokerApiClientTask(getIdlingResource(), this).execute();
  }


  @Override
  public void onComplete(String result) {
    startActivity(JokeTellerActivity.getIntent(this, result));
  }
}
