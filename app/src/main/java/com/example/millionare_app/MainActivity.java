package com.example.millionare_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;

public class MainActivity extends AppCompatActivity {
    private TextView btnStartGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_main);

        View mainView = findViewById(R.id.main);
        init();
//        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
//            WindowInsetsCompat systemBarsInsets = insets.getInsets(Type.systemBars());
//            v.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom);
//            return insets;
//        });

        btnStartGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        }));
    }

    private void init() {
         btnStartGame = findViewById(R.id.btnStart);
    }
}
