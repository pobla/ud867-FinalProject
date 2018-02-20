package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.pobla.joker.Joker;

/**
 * An endpoint class we are exposing
 */
@Api(
  name = "jokerApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.builditbigger.gradle.udacity.com",
    ownerName = "backend.builditbigger.gradle.udacity.com",
    packagePath = ""
  )
)
public class MyEndpoint {

  @ApiMethod(name = "joke")
  public Joke getJoke() {
    Joke joke = new Joke();
    joke.setValue(new Joker().getJoke());
    return joke;
  }

}
