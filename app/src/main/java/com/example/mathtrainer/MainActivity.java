package com.example.mathtrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textViewScore;
    private TextView textViewTimer;
    private TextView textViewQuestion;
    private TextView textViewAnswer0;
    private TextView textViewAnswer1;
    private TextView textViewAnswer2;
    private TextView textViewAnswer3;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private int score;
    private int rightTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewScore = findViewById(R.id.textViewScore);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewAnswer0 = findViewById(R.id.textViewAnswer0);
        textViewAnswer1 = findViewById(R.id.textViewAnswer1);
        textViewAnswer2 = findViewById(R.id.textViewAnswer2);
        textViewAnswer3 = findViewById(R.id.textViewAnswer3);

        textViews.add(textViewAnswer0);
        textViews.add(textViewAnswer1);
        textViews.add(textViewAnswer2);
        textViews.add(textViewAnswer3);

        textViewScore.setText(String.format(getString(R.string.score), 0));

        generateQuestion();
    }

    public void generateQuestion() {
        textViewTimer.setTextColor(getResources().getColor(R.color.green));
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) (millisUntilFinished / 1000);
                textViewTimer.setText(String.valueOf(time+1));
                if (time <= 9) {
                    textViewTimer.setTextColor(getResources().getColor(R.color.red));
                }
            }

            @Override
            public void onFinish() {
                GameOver();
            }
        };

        int negative = (int) (Math.random() * 2);
        int num1 = (int) (Math.random() * 100 + 1);
        int num2 = (int) (Math.random() * 100 + 1);

        if (negative == 0) {
            String question = String.valueOf(num1) + " + " + String.valueOf(num2);
            textViewQuestion.setText(question);
            generateAnswers(num1, num2, negative);
        } else {
            String question = String.valueOf(num1) + " - " + String.valueOf(num2);
            textViewQuestion.setText(question);
            generateAnswers(num1, num2, negative);
        }

        countDownTimer.start();
    }

    public void generateAnswers(int num1, int num2, int negative) {
        String rightAnswer;
        String wrongAnswer;
        if (negative == 0) {
            rightAnswer = String.valueOf(num1 + num2);
        } else {
            rightAnswer = String.valueOf(num1 - num2);
        }

        rightTextView = (int) (Math.random() * 4);
        for (int i = 0; i < 4; i++) {
            if (i == rightTextView) {
                textViews.get(i).setText(rightAnswer);
            } else {
                if (Integer.valueOf(rightAnswer) >= 0) {
                    wrongAnswer = String.valueOf((int) (Math.random() * 100 + 1));
                } else {
                    wrongAnswer = String.valueOf((int) (Math.random() * 100 + 1) * -1);
                }
                textViews.get(i).setText(wrongAnswer);
            }
        }
    }

    public void Answer(View view) {
        countDownTimer.cancel();
        if (view.getTag().equals(String.valueOf(rightTextView))) {
            score++;
            textViewScore.setText(String.format(getString(R.string.score), score));
            generateQuestion();
        } else {
            GameOver();
        }


    }

    public void GameOver() {
        countDownTimer.cancel();
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }
}