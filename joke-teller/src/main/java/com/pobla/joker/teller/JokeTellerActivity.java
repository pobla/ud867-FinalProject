package com.pobla.joker.teller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class JokeTellerActivity extends AppCompatActivity {
  private static final String JOKE = "joke";

  public static Intent getIntent(Context context, String joke) {
    Intent intent = new Intent(context, JokeTellerActivity.class);
    intent.putExtra(JOKE, joke);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_joke_teller);
    String joke = getIntent().getStringExtra(JOKE);

    TextView jokeView = findViewById(R.id.tv_joke);
    jokeView.setText(joke);
  }
}
