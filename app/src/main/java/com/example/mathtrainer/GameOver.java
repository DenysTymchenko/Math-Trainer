package com.example.mathtrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    TextView textViewFinalScore;
    TextView textViewHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        textViewFinalScore = findViewById(R.id.textViewFinalScore);
        textViewHighScore = findViewById(R.id.textViewHighScore);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int highScore = sharedPreferences.getInt("highScore", 0);
        if (score > highScore) {
            highScore = score;
            sharedPreferences.edit().putInt("highScore",highScore).apply();
        }

        textViewFinalScore.setText(String.format(getString(R.string.score), score));
        textViewHighScore.setText(String.format(getString(R.string.high_score), highScore));
    }

    public void Restart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}