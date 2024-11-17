package com.example.millionare_app;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class AskAudienceActivity {

    private final Context context;
    private QuestionActivity questionActivity;

    public AskAudienceActivity(Context context, QuestionActivity questionActivity) {
        this.context = context;
        this.questionActivity = questionActivity;
    }

    public void showAskAudienceDialog(Question question) {
        if (question == null || question.getListAnswer() == null) {
            return;
        }

        List<Answer> answers = question.getListAnswer();
        int correctAnswerIndex = -1;
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).getCorrect()) {
                correctAnswerIndex = i;
                break;
            }
        }

        if (correctAnswerIndex == -1) {
            return;
        }

        int correctPercentage = (int) (Math.random() * 20) + 40;
        int remainingPercentage = 100 - correctPercentage;

        int wrongPercentage1 = (int) (Math.random() * remainingPercentage);
        int wrongPercentage2 = (int) (Math.random() * (remainingPercentage - wrongPercentage1));
        int wrongPercentage3 = remainingPercentage - wrongPercentage1 - wrongPercentage2;

        int[] percentages = new int[4];
        percentages[correctAnswerIndex] = correctPercentage;

        int wrongIndex = 0;
        for (int i = 0; i < percentages.length; i++) {
            if (i != correctAnswerIndex) {
                if (wrongIndex == 0) {
                    percentages[i] = wrongPercentage1;
                } else if (wrongIndex == 1) {
                    percentages[i] = wrongPercentage2;
                } else {
                    percentages[i] = wrongPercentage3;
                }
                wrongIndex++;
            }
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_ask_audience, null);
        dialogBuilder.setView(dialogView);

        new Handler().postDelayed(() -> updateBarHeights(dialogView, percentages), 1500);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.blue_background);

        TextView btn_thank = dialogView.findViewById(R.id.btn_thank_ask);
        btn_thank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                questionActivity.resumeTimer();
                dialog.dismiss();
            }
        });
        dialog.show();
//        questionActivity.resumeTimer();
//        questionActivity.pauseTimer();
    }

    private void updateBarHeights(View dialogView, int[] percentages) {
        int maxHeight = 1400;

        LinearLayout perA = dialogView.findViewById(R.id.per_a);
        ViewGroup.LayoutParams paramsA = perA.getLayoutParams();
        paramsA.height = (percentages[0] * maxHeight) / 100 + 70;
        perA.setLayoutParams(paramsA);

        LinearLayout perB = dialogView.findViewById(R.id.per_b);
        ViewGroup.LayoutParams paramsB = perB.getLayoutParams();
        paramsB.height = (percentages[1] * maxHeight) / 100 + 70;
        perB.setLayoutParams(paramsB);

        LinearLayout perC = dialogView.findViewById(R.id.per_c);
        ViewGroup.LayoutParams paramsC = perC.getLayoutParams();
        paramsC.height = (percentages[2] * maxHeight) / 100 + 70;
        perC.setLayoutParams(paramsC);

        LinearLayout perD = dialogView.findViewById(R.id.per_d);
        ViewGroup.LayoutParams paramsD = perD.getLayoutParams();
        paramsD.height = (percentages[3] * maxHeight) / 100 + 70;
        perD.setLayoutParams(paramsD);

        ((TextView) dialogView.findViewById(R.id.tv_percentage_a)).setText(percentages[0] + "%");
        ((TextView) dialogView.findViewById(R.id.tv_percentage_b)).setText(percentages[1] + "%");
        ((TextView) dialogView.findViewById(R.id.tv_percentage_c)).setText(percentages[2] + "%");
        ((TextView) dialogView.findViewById(R.id.tv_percentage_d)).setText(percentages[3] + "%");
    }
}
