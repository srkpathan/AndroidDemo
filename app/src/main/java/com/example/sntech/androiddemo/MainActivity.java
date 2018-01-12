package com.example.sntech.androiddemo;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView quoteTextView, quoteAuthorTextView;
    Call<Quote> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // turn this app into random quote app

        startAllTasks();
    }

    private void startAllTasks() {
        quoteTextView = (TextView) findViewById(R.id.quote_textView);
        quoteAuthorTextView = (TextView) findViewById(R.id.quote_author_textView);

        quoteTextView.setVisibility(View.GONE);
        quoteAuthorTextView.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        call = api.getQuote();

        getQuoteFromServer();

        loadQuotePeriodically();
    }

    private void getQuoteFromServer() {
        call.clone().enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                final Quote quote = response.body();

                if (quote != null) {
                    Log.d(TAG, "onResponse: Quote = " + quote.getQuoteText());
                    Log.d(TAG, "onResponse: Author = " + quote.getQuoteAuthor());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (quote.getQuoteText() != null) {
                                if (quote.getQuoteText().length() != 0) {
                                    quoteTextView.setText(quote.getQuoteText());
                                } else {
                                    quoteTextView.setText("wait...");
                                }
                                quoteTextView.setVisibility(View.VISIBLE);
                            } else {
                                quoteAuthorTextView.setVisibility(View.GONE);
                            }

                            if (quote.getQuoteAuthor() != null) {
                                quoteAuthorTextView.setText(getString(R.string.quote_author_hifen));
                                if (quote.getQuoteAuthor().length() != 0) {
                                    quoteAuthorTextView.append(quote.getQuoteAuthor());
                                } else {
                                    quoteAuthorTextView.append("Unknown");
                                }
                                quoteAuthorTextView.setVisibility(View.VISIBLE);
                            } else {
                                quoteAuthorTextView.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void loadQuotePeriodically() {
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                getQuoteFromServer();
                loadQuotePeriodically();
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.quote_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_button_menu:
                getQuoteFromServer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}






