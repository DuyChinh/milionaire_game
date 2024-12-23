package com.example.millionare_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

public class GameOverActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.game_over_dialog);
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        TextView tvScore = findViewById(R.id.tv_ward);
        tvScore.setText(String.format("%,d", score));

        TextView homeBtn = findViewById(R.id.btn_start);
        TextView playAgainBtn = findViewById(R.id.btn_again);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });
    }
}
