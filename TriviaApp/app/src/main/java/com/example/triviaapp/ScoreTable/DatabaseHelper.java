package com.example.triviaapp.ScoreTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.triviaapp.ScoreTable.Model.Score;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "scores_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Score.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Score.TABLE_NAME);

        onCreate(db);
    }

    public long insertScore(Score score) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Score.COLUMN_NAME, score.getName());
        values.put(Score.COLUMN_SCORE, score.getScore());

        long id = db.insert(Score.TABLE_NAME, null, values);

        db.close();

        return id;
    }
    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<>();

        final String GET_ALL_NOTES_QUERY = "SELECT * FROM " + Score.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(GET_ALL_NOTES_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Score.COLUMN_ID));
                String date = cursor.getString(cursor.getColumnIndex(Score.COLUMN_DATE));
                String name = cursor.getString(cursor.getColumnIndex(Score.COLUMN_NAME));
                String score = cursor.getString(cursor.getColumnIndex(Score.COLUMN_SCORE));
                Score note = new Score(id, score, name, date);
                scores.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return scores;
    }

    public Score getScore(long id) {


        String NOTE_QUERY = "SELECT * FROM " + Score.TABLE_NAME + " WHERE " + Score.COLUMN_ID + "= ?";
        String[] selectionArguments = new String[]{String.valueOf(id)};

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(NOTE_QUERY, selectionArguments);
        Score score1 = null;
        if (cursor.moveToFirst()) {
            int scoreId = cursor.getInt(cursor.getColumnIndex(Score.COLUMN_ID));
            String date = cursor.getString(cursor.getColumnIndex(Score.COLUMN_DATE));
            String name = cursor.getString(cursor.getColumnIndex(Score.COLUMN_NAME));
            String score = cursor.getString(cursor.getColumnIndex(Score.COLUMN_SCORE));
            score1 = new Score(scoreId, name, date, score);
        }

        db.close();
        cursor.close();

        return score1;
    }
}
