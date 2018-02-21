package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.pobla.joker.teller.JokeTellerActivity;
import com.udacity.gradle.builditbigger.backend.jokerApi.JokerApi;
import com.udacity.gradle.builditbigger.backend.jokerApi.JokerApi.Builder;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity {

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
    new JokerApiClientTask().execute(this);

  }

  private static JokerApi jokerApiClient = null;

  class SimpleIdlingResource implements IdlingResource {
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);
    private ResourceCallback callback;

    @Override
    public String getName() {
      return "JokerApiClientTask";
    }

    @Override
    public boolean isIdleNow() {
      return mIsIdleNow.get();
    }

    public void setIdleState(boolean isIdleNow) {
      mIsIdleNow.set(isIdleNow);
      Log.d("TAG", "setIdleState: "+mIsIdleNow);
      if (isIdleNow && this.callback != null) {
        this.callback.onTransitionToIdle();
      }

    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
      this.callback = callback;
    }
  }

  class JokerApiClientTask extends AsyncTask<Context, Void, String> {

    private Context context;

    @Override
    protected String doInBackground(Context... params) {
      context = params[0];

      if (jokerApiClient == null) {
        initApiService();
      }

      try {
        return jokerApiClient.joke().execute().getValue();
      } catch (IOException e) {
        return null;
      }
    }

    private void initApiService() {
      Builder builder = new Builder(AndroidHttp.newCompatibleTransport(),
                                     new AndroidJsonFactory(), null)
                          .setRootUrl(BuildConfig.API_BASE_URL)
                          .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                              abstractGoogleClientRequest.setDisableGZipContent(BuildConfig.DEBUG);
                            }
                          });
      jokerApiClient = builder.build();
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      mIdlingResource.setIdleState(false);
    }

    @Override
    protected void onPostExecute(String result) {
      if (result != null) {
        startActivity(JokeTellerActivity.getIntent(context, result));
      }
      mIdlingResource.setIdleState(true);

    }

  }


}
