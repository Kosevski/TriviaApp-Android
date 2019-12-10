package com.example.triviaapp.TriviaLogic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviaapp.MainActivity;
import com.example.triviaapp.R;
import com.example.triviaapp.ScoreTable.AddScoreActivity;
import com.example.triviaapp.TriviaLogic.Model.TriviaData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TriviaActivity extends AppCompatActivity {
    public static List<String> incorrectAnswers;
    Uri uri;
    private ProgressBar progressBar;
    RecyclerView rvQA;
    public TriviaAdapter triviaAdapter;
    String questionsJSON, correct_answerJSON, inc1, inc2, inc3;
    String score, showScore;
    public ArrayList<TriviaData> triviaData = new ArrayList<>();
    SharedPreferences myPrefs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        myPrefs = getSharedPreferences("preferencesID", Context.MODE_PRIVATE);

        rvQA = findViewById(R.id.rv_QA);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvQA.setLayoutManager(linearLayoutManager);

        progressBar = findViewById(R.id.progressBar);

        String category = getIntent().getStringExtra("category");
        String difficulty = getIntent().getStringExtra("difficulty");
        String amountOfQ = getIntent().getStringExtra("amountOfQ");

        final String BASE_URL = "https://opentdb.com/api.php?";
        final String AMOUNT_OFQ = "amount";
        final String CATEGORY = "category";
        final String DIFFICULTY = "difficulty";
        final String MULTIPLE_OPTIONS = "type ";

        Uri.Builder uriBuilder = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(AMOUNT_OFQ, amountOfQ)
                .appendQueryParameter(MULTIPLE_OPTIONS, "multiple");

        if (category != null) {
            uriBuilder.appendQueryParameter(CATEGORY, category);
        }
        if (difficulty != null) {
            uriBuilder.appendQueryParameter(DIFFICULTY, difficulty);
        }

        uri = uriBuilder.build();

        try {
            URL url = new URL(uri.toString());

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            okHttpClient.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            progressBar.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TriviaActivity.this, "Error", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.VISIBLE);
                                    rvQA.setVisibility(View.GONE);
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                            final ResponseBody body = response.body();
                            if (body != null) {
                                final String result = body.string();

                                try {
                                    JSONObject object = new JSONObject(result);

                                    JSONArray jsonArray = object.getJSONArray("results");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                        questionsJSON = jsonObject.getString("question");
                                        correct_answerJSON = jsonObject.getString("correct_answer");


                                        incorrectAnswers = new ArrayList<>();
                                        JSONArray jsonArrayI = jsonObject.getJSONArray("incorrect_answers");

                                        for (int j = 0; j < jsonArrayI.length(); j++) {
                                            String incorrectAnswer = (String) jsonArrayI.get(j);
                                            incorrectAnswers.add(incorrectAnswer);
                                        }
                                        if (incorrectAnswers.size() == 1) {
                                            continue;
                                        }
                                        inc1 = incorrectAnswers.get(0);
                                        inc2 = incorrectAnswers.get(1);
                                        inc3 = incorrectAnswers.get(2);

                                        triviaData.add(new TriviaData(questionsJSON, correct_answerJSON, inc2, inc1, inc3));
                                    }

                                    rvQA.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            triviaAdapter = new TriviaAdapter(triviaData, TriviaActivity.this);
                                            triviaAdapter.notifyDataSetChanged();
                                            rvQA.setAdapter(triviaAdapter);
                                            rvQA.setVisibility(View.VISIBLE);

                                        }
                                    });
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                    progressBar.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(TriviaActivity.this, "Error", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                            rvQA.setVisibility(View.VISIBLE);

                                        }
                                    });
                                }
                            }
                        }
                    });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Button finish = findViewById(R.id.FINISHBtn);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;
                boolean[] user_input = triviaAdapter.getUserInput();
                for (int i = 0; i < user_input.length; i++) {
                    if (user_input[i]) {
                        counter++;
                    }
                }

                String lastScore = "Your last score: " + counter + "/" + triviaData.size();
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("counterKey", lastScore);
                editor.apply();


                showScore = "Your score: " + counter + "/" + triviaData.size();
                score = counter + "/" + triviaData.size();
                if (counter == triviaData.size()) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TriviaActivity.this);

                    alertDialog.setTitle("CONGRATULATIONS! Your score is: " + counter + "/" + triviaData.size());
                    alertDialog.setMessage("Do you want to save your score in the scores table?");
                    alertDialog.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent saveScoreIntent = new Intent(TriviaActivity.this, AddScoreActivity.class);
                            saveScoreIntent.putExtra("showScore", showScore);
                            saveScoreIntent.putExtra("Score", score);
                            startActivity(saveScoreIntent);
                        }
                    });
                    alertDialog.setNegativeButton("No, just another game", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent restartIntent = new Intent(TriviaActivity.this, MainActivity.class);
                            startActivity(restartIntent);
                        }
                    });
                    alertDialog.show();
                } else {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TriviaActivity.this);
                    alertDialog.setTitle("Your score: " + counter + "/" + triviaData.size());
                    alertDialog.setMessage("Do you want to save your score in the scores table?");
                    alertDialog.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent saveScoreIntent = new Intent(TriviaActivity.this, AddScoreActivity.class);
                            saveScoreIntent.putExtra("Score", score);
                            saveScoreIntent.putExtra("showScore", showScore);
                            startActivity(saveScoreIntent);
                        }
                    });
                    alertDialog.setNegativeButton("No, just another game", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent restartIntent = new Intent(TriviaActivity.this, MainActivity.class);
                            startActivity(restartIntent);
                        }
                    });
                    alertDialog.show();

                }
            }
        });
    }
}
