package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
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
        return e.getMessage();
      }
    }

    private void initApiService() {
      Builder builder = new Builder(AndroidHttp.newCompatibleTransport(),
                                                 new AndroidJsonFactory(), null)
//                                  // options for running against local devappserver
//                                  // - 10.0.2.2 is localhost's IP address in Android emulator
//                                  // - turn off compression when running against local devappserver
//                                     .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                                   //TODO extract url to build.gradle and remove compression in devserver
      // how to create the app
//                                      gcloud init
//                                      gcloud app deploy
//                                      gcloud app create
//                                      ./gradlew backend:appengineDeploy
                                   .setRootUrl("https://testing-core.appspot.com/_ah/api/")
                                   .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                     @Override
                                     public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                       abstractGoogleClientRequest.setDisableGZipContent(true);
                                     }
                                   });
      jokerApiClient = builder.build();
    }

    @Override
    protected void onPostExecute(String result) {
      startActivity(JokeTellerActivity.getIntent(context, result));
    }
  }


}
