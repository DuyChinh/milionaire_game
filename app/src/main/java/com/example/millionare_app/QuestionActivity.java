package com.example.millionare_app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import DAO.AnswerDAO;
import DAO.QuestionDAO;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvQuestion;
    private TextView tvContentQuestion;
    private TextView answer1, answer2, answer3, answer4;
    private List<Question> questionList;
    private int currentQuestion = 0;
    private Question question_now;
    private Button btnFiftyFifty;
    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private static final long TIME_DURATION = 31000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_question);
        init();
        questionList = getQuestion();
        if(questionList.isEmpty()) {
            return;
        }
        setDataQuestion(questionList.get(currentQuestion));
    }

    private void init() {
        tvTimer = findViewById(R.id.tv_timer);
        btnFiftyFifty = findViewById(R.id.btn_fifty_fifty);
        btnFiftyFifty.setEnabled(true);
        btnFiftyFifty.setOnClickListener(this);
        tvQuestion = findViewById(R.id.tv_question);
        tvContentQuestion = findViewById(R.id.tv_content);
        answer1 = findViewById(R.id.tv_answer1);
        answer2 = findViewById(R.id.tv_answer2);
        answer3 = findViewById(R.id.tv_answer3);
        answer4 = findViewById(R.id.tv_answer4);
    }


    private void useFiftyFifty() {
        if (question_now == null || question_now.getListAnswer() == null) {
            return;
        }

        List<Answer> answers = question_now.getListAnswer();
        List<TextView> answerViews = new ArrayList<>();
        answerViews.add(answer1);
        answerViews.add(answer2);
        answerViews.add(answer3);
        answerViews.add(answer4);

        int removedCount = 0;
        for (int i = 0; i < answers.size(); i++) {
            if (!answers.get(i).getCorrect() && removedCount < 2) {
                answerViews.get(i).setText("");
                answerViews.get(i).setEnabled(false);
                removedCount++;
            }
        }
        btnFiftyFifty.setEnabled(false);
    }


    private void setDataQuestion(Question question) {
        if(question == null) {
            return;
        }
        question_now = question;
        String numberQuestion = "Question " + question.getId();
        answer1.setBackgroundResource(R.drawable.blue_background);
        answer2.setBackgroundResource(R.drawable.blue_background);
        answer3.setBackgroundResource(R.drawable.blue_background);
        answer4.setBackgroundResource(R.drawable.blue_background);
        tvQuestion.setText(numberQuestion);
        tvContentQuestion.setText(question.getContent());
        answer1.setText(question.getListAnswer().get(0).getContent());
        answer2.setText(question.getListAnswer().get(1).getContent());
        answer3.setText(question.getListAnswer().get(2).getContent());
        answer4.setText(question.getListAnswer().get(3).getContent());
        startTimer();
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
    }


    private void startTimer() {
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(TIME_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                tvTimer.setText(secondsRemaining + "");
            }

            @Override
            public void onFinish() {
                tvTimer.setText("Time's up");
                gameOver();
            }
        };
        countDownTimer.start();
    }


    private void insertSampleData() {
        QuestionDAO questionDAO = new QuestionDAO(this);
        AnswerDAO answerDAO = new AnswerDAO(this);

        long questionId1 = questionDAO.insertQuestion(new Question(1, "Ghế ngồi dành cho người chơi chương trình \"Ai là triệu phú\" được gọi là gì?", null));
        answerDAO.insertAnswer(new Answer("Ghế đá", false), questionId1);
        answerDAO.insertAnswer(new Answer("Ghế nóng", true), questionId1);
        answerDAO.insertAnswer(new Answer("Ghế băng", false), questionId1);
        answerDAO.insertAnswer(new Answer("Ghế thư giãn", false), questionId1);

        long questionId2 = questionDAO.insertQuestion(new Question(2, "Tháng âm lịch nào còn được gọi là \"Tháng cô hồn\"?", null));
        answerDAO.insertAnswer(new Answer("Tháng bảy", true), questionId2);
        answerDAO.insertAnswer(new Answer("Tháng tám", false), questionId2);
        answerDAO.insertAnswer(new Answer("Tháng chín", false), questionId2);
        answerDAO.insertAnswer(new Answer("Tháng mười", false), questionId2);

        long questionId3 = questionDAO.insertQuestion(new Question(3, "Chương trình 'Ai là triệu phú' phát sóng trên kênh VTV3\n" +
                "được mua bản quyền từ quốc gia nào?", null));
        answerDAO.insertAnswer(new Answer("Mỹ", false), questionId3);
        answerDAO.insertAnswer(new Answer("Pháp", false), questionId3);
        answerDAO.insertAnswer(new Answer("Úc", false), questionId3);
        answerDAO.insertAnswer(new Answer("Anh", true), questionId3);
    }


    private List<Question> getQuestion() {
        insertSampleData();
        QuestionDAO questionDAO = new QuestionDAO(this);
        AnswerDAO answerDAO = new AnswerDAO(this);
        List<Question> listQuestion = questionDAO.getAllQuestions();
        for (Question question : listQuestion) {
            List<Answer> answers = answerDAO.getAnswersByQuestionId(question.getId());
            question.setListAnswer(answers);
        }

        return listQuestion;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_fifty_fifty) {
            useFiftyFifty();
            answer1.setEnabled(true);
            answer2.setEnabled(true);
            answer3.setEnabled(true);
            answer4.setEnabled(true);
        } else if (id == R.id.tv_answer1) {
            answer1.setBackgroundResource(R.drawable.orange_background);
            checkAnswer(answer1, question_now, question_now.getListAnswer().get(0));
        } else if (id == R.id.tv_answer2) {
            answer2.setBackgroundResource(R.drawable.orange_background);
            checkAnswer(answer2, question_now, question_now.getListAnswer().get(1));
        } else if (id == R.id.tv_answer3) {
            answer3.setBackgroundResource(R.drawable.orange_background);
            checkAnswer(answer3, question_now, question_now.getListAnswer().get(2));
        } else if (id == R.id.tv_answer4) {
            answer4.setBackgroundResource(R.drawable.orange_background);
            checkAnswer(answer4, question_now, question_now.getListAnswer().get(3));
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void checkAnswer(TextView textView, Question question, Answer answer) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(answer.getCorrect()) {
                    textView.setBackgroundResource(R.drawable.bg_green_corner_30);
                    nextQuestion();
                } else {
                    textView.setBackgroundResource(R.drawable.bg_red_corner);
                    showAnswerCorrect(question);
                    gameOver();
//                    Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
//                    startActivity(intent);
                }
            }
        }, 1000);
    }

    private void nextQuestion() {
        if(currentQuestion == questionList.size() - 1) {
            showDialog("You win");
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentQuestion += 1;
                    setDataQuestion(questionList.get(currentQuestion));
                }
            }, 1500);
        }
    }

    private void showAnswerCorrect(Question question) {
        if(question == null || question.getListAnswer() == null || question.getListAnswer().isEmpty()) {
            return;
        }
        if(question.getListAnswer().get(0).getCorrect()) {
            answer1.setBackgroundResource(R.drawable.bg_green_corner_30);

        } else if(question.getListAnswer().get(1).getCorrect()) {
            answer2.setBackgroundResource(R.drawable.bg_green_corner_30);
        } else if(question.getListAnswer().get(2).getCorrect()) {
            answer3.setBackgroundResource(R.drawable.bg_green_corner_30);
        } else if(question.getListAnswer().get(3).getCorrect()) {
            answer4.setBackgroundResource(R.drawable.bg_green_corner_30);
        }
    }

    private void gameOver() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialog("Game over");
            }
        }, 1000);
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentQuestion = 0;
                setDataQuestion(questionList.get(currentQuestion));
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
