package com.example.millionare_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DialogActivity {

    private Context context;
    private QuestionActivity questionActivity;

    public DialogActivity(Context context, QuestionActivity questionActivity) {
        this.context = context;
        this.questionActivity = questionActivity;
    }

    public void showCallFriendDialog(String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.use_call_friend, null);
        dialogBuilder.setView(dialogView);

        TextView callFriendTextView = dialogView.findViewById(R.id.answer_text);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callFriendTextView.setText(message.substring(0, 1));
            }
        }, 1500);


        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        TextView btnThank = dialogView.findViewById(R.id.btn_thank);

        btnThank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                questionActivity.resumeTimer();
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            window.setGravity(Gravity.CENTER);
        }
    }

    public void showConfirm() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_exit, null);
        dialogBuilder.setView(dialogView);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_red_corner);

        Button btn_yes = dialogView.findViewById(R.id.btnYes);
        Button btn_no = dialogView.findViewById(R.id.btnNo);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionActivity.gameOver();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
