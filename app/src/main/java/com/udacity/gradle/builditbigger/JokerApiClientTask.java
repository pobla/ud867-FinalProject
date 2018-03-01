package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.pobla.joker.teller.JokeTellerActivity;
import com.udacity.gradle.builditbigger.backend.jokerApi.JokerApi;
import com.udacity.gradle.builditbigger.backend.jokerApi.JokerApi.Builder;

import java.io.IOException;

class JokerApiClientTask extends AsyncTask<Void, Void, String> {

  public interface JokerApiClientTaskComplete {
    void onComplete(String result);
  }

  private static JokerApi jokerApiClient = null;

  private final SimpleIdlingResource idlingResource;
  private final JokerApiClientTaskComplete listener;

  JokerApiClientTask(SimpleIdlingResource idlingResource, JokerApiClientTaskComplete listener) {
    this.idlingResource = idlingResource;
    this.listener = listener;
  }

  @Override
  protected String doInBackground(Void... params) {

    if (jokerApiClient == null) {
      initApiService();
    }

    try {
      return jokerApiClient.joke().execute().getValue();
    } catch (IOException e) {
      Log.e(this.getClass().getCanonicalName(), "Error retrieving API", e);
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
    this.idlingResource.setIdleState(false);
  }

  @Override
  protected void onPostExecute(String result) {
    if (result != null) {
      listener.onComplete(result);
    }
    this.idlingResource.setIdleState(true);
  }

}
