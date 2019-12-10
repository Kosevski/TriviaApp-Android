package com.example.triviaapp.ScoreTable;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviaapp.AppSingleton;
import com.example.triviaapp.MainActivity;
import com.example.triviaapp.R;
import com.example.triviaapp.ScoreTable.Model.Score;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ScoreTableList extends AppCompatActivity {

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    RecyclerView recyclerView;
    ScoreAdapter scoreAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_list_activity);



        FloatingActionButton floatingActionButton = findViewById(R.id.scoreList_floatingBTN);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHomeIntent = new Intent(ScoreTableList.this, MainActivity.class);
                startActivity(backToHomeIntent);
            }
        });

        final DatabaseHelper dbHelper = AppSingleton.getInstance(getApplicationContext()).getDbHelper();

        setGradient();


        List<Score> allScores = dbHelper.getAllScores();

        recyclerView = findViewById(R.id.scoresRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        scoreAdapter = new ScoreAdapter(allScores);
        recyclerView.setAdapter(scoreAdapter);


       new AddScoreListener() {
            @Override
            public void onScoreAdded(long id) {
                Score score = dbHelper.getScore(id);
                scoreAdapter.addScore(score);
            }
        };

    }

    private void setGradient() {
        CoordinatorLayout coordinatorLayout;
        LinearLayout linearLayout;
        AnimationDrawable animationDrawable;


        coordinatorLayout = findViewById(R.id.score_list_root_layout);
        animationDrawable = (AnimationDrawable) coordinatorLayout.getBackground();

        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(5000);

        animationDrawable.start();
    }
}
