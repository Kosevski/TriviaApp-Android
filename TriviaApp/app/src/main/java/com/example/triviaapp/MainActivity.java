package com.example.triviaapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.triviaapp.DrawerOptions.SettingsActivity;
import com.example.triviaapp.ScoreTable.ScoreTableList;
import com.example.triviaapp.TriviaLogic.TriviaActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCAT, spinnerDIFF, spinnerAMOUNT;
    Button playButton;
    String categoryPicked, difficultyPicked, amountPicked;
    TextView lastScoreTV;
    String lastScore;
    SharedPreferences myPrefs;

    private DrawerLayout dl;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("My_Shared_Preference_Name", MODE_PRIVATE);



        dl = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dl.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.drawer_settings) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.drawer_score_table) {
                    Intent intent = new Intent(MainActivity.this, ScoreTableList.class);
                    startActivity(intent);
                    return true;
                }

                return true;
            }
        });


        lastScoreTV = findViewById(R.id.TVScore);

        setUpAlarm();
        setGradient();

        myPrefs = getSharedPreferences("preferencesID", Context.MODE_PRIVATE);
        lastScore = myPrefs.getString("counterKey", "");

        lastScoreTV.setText(lastScore);

        spinnerCAT = findViewById(R.id.CategorySpinner);
        spinnerCAT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String[] category = getResources().getStringArray(R.array.Category);
                if (category[position].equalsIgnoreCase("Sport")) {
                    categoryPicked = "21";
                } else if (category[position].equalsIgnoreCase("Music")) {
                    categoryPicked = "12";
                } else if (category[position].equalsIgnoreCase("Film")) {
                    categoryPicked = "11";
                } else if (category[position].equalsIgnoreCase("Geography")) {
                    categoryPicked = "22";
                } else if (category[position].equalsIgnoreCase("Any Category")) {
                    categoryPicked = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerDIFF = findViewById(R.id.DiffSpinner);
        spinnerDIFF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String[] difficulty = getResources().getStringArray(R.array.Difficulty);
                if (difficulty[position].equalsIgnoreCase("Easy")) {
                    difficultyPicked = "easy";
                } else if (difficulty[position].equalsIgnoreCase("Medium")) {
                    difficultyPicked = "medium";
                } else if (difficulty[position].equalsIgnoreCase("Any Difficulty")) {
                    difficultyPicked = null;
                } else if (difficulty[position].equalsIgnoreCase("Hard")) {
                    difficultyPicked = "hard";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAMOUNT = findViewById(R.id.AmountSpinner);
        spinnerAMOUNT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String[] amount = getResources().getStringArray(R.array.AmountOfQuestions);
                if (amount[position].equalsIgnoreCase("Just a few")) {
                    amountPicked = "5";
                } else if (amount[position].equalsIgnoreCase("More than a few")) {
                    amountPicked = "10";
                } else if (amount[position].equalsIgnoreCase("Not too much")) {
                    amountPicked = "15";
                } else if (amount[position].equalsIgnoreCase("A lot of questions")) {
                    amountPicked = "20";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        playButton = findViewById(R.id.PlayBtn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playIntent = new Intent(MainActivity.this, TriviaActivity.class);
                playIntent.putExtra("category", categoryPicked);
                playIntent.putExtra("difficulty", difficultyPicked);
                playIntent.putExtra("amountOfQ", amountPicked);
                startActivity(playIntent);
            }
        });
    }

    public void setGradient() {
        LinearLayout linearLayout;
        AnimationDrawable animationDrawable;


        linearLayout = findViewById(R.id.root_layout);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(5000);

        animationDrawable.start();
    }

    private void setUpAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, TriviaNotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR, 11);
            calendar.set(Calendar.MINUTE, 20);
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    60000 * 60 * 24,
                    pendingIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}


