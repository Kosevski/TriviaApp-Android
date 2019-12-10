package com.example.triviaapp.ScoreTable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaapp.AppSingleton;
import com.example.triviaapp.R;
import com.example.triviaapp.ScoreTable.Model.Score;

public class AddScoreActivity extends AppCompatActivity {

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }


    public AddScoreListener addScoreListener;
    Context context;
    String name, score;
    EditText nameET;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_score_activity);

        nameET = findViewById(R.id.etNewScore);
        nameET.setMaxLines(1);
        TextView showScoreTV = findViewById(R.id.yourScoreTV);
        String yourScore = getIntent().getStringExtra("showScore");
        score = getIntent().getStringExtra("Score");

        showScoreTV.setText(yourScore);

        Button button = findViewById(R.id.save_score_BTN);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameET.getText().toString();
                if (name.isEmpty()){
                  nameET.setError("Must pick a name..");
                  nameET.requestFocus();
                } else {

                    Intent saveScoreIntent = new Intent(AddScoreActivity.this, ScoreTableList.class);
                    startActivity(saveScoreIntent);


                    Score addNewScoreToDB = new Score(score, name);

                    DatabaseHelper dbHelper = AppSingleton.getInstance(getApplicationContext()).getDbHelper();

                    long id = dbHelper.insertScore(addNewScoreToDB);

                    if (addScoreListener != null) {
                        addScoreListener.onScoreAdded(id);
                    }
                }
            }
        });

    }
}

