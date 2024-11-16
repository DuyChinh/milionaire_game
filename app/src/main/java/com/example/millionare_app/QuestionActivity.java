package com.example.millionare_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
    private Button btnCallFriend;
    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private int amount = 100;
    private Button btnAmount;
    private static final long TIME_DURATION = 31000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_question);
        init();
        questionList = getQuestion();
        Log.d("QuestionList", "Size of questionList: " + questionList.size());
        if(questionList.isEmpty()) {
            return;
        }
        setDataQuestion(questionList.get(currentQuestion));
    }

    private void init() {
        tvTimer = findViewById(R.id.tv_timer);
        btnAmount = findViewById(R.id.btn_amount);
        btnCallFriend = findViewById(R.id.btn_call_friend);
        btnCallFriend.setEnabled(true);
        btnCallFriend.setOnClickListener(this);
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

    private void useCallFriend() {
        if(question_now == null || question_now.getListAnswer() == null) {
            return;
        }
        List<Answer> answers = question_now.getListAnswer();
        for(int i = 0; i < answers.size(); i++) {
            if(answers.get(i).getCorrect()) {
                showCenterDialog(i + 1, "Câu trả lời đúng là " + (i+1) +". " + answers.get(i).getContent());
                break;
            }
        }
        btnCallFriend.setEnabled(false);
    }

    private void setDataQuestion(Question question) {
        if(question == null) {
            return;
        }
        question_now = question;
        answer1.setBackgroundResource(R.drawable.blue_background);
        answer2.setBackgroundResource(R.drawable.blue_background);
        answer3.setBackgroundResource(R.drawable.blue_background);
        answer4.setBackgroundResource(R.drawable.blue_background);
        String number_question = "Question " + (currentQuestion + 1) + "/15";
        tvQuestion.setText(number_question);
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
                long secondRemaining = millisUntilFinished / 1000;
                tvTimer.setText(secondRemaining + "");
            }

            @Override
            public void onFinish() {
                tvTimer.setText("End");
                gameOver();
            }
        };
        countDownTimer.start();
   }


    private void insertSampleData() {
        QuestionDAO questionDAO = new QuestionDAO(this);
        AnswerDAO answerDAO = new AnswerDAO(this);
        // Câu hỏi 1
        long questionId1 = questionDAO.insertQuestion(new Question(1, "Mặt nạ giấy bồi là món đồ chơi truyền thống vào dịp nào?", 200000, null));
        answerDAO.insertAnswer(new Answer("A: Tết Nguyên Tiêu", false), questionId1);
        answerDAO.insertAnswer(new Answer("B: Tết Trung Thu", true), questionId1);
        answerDAO.insertAnswer(new Answer("C: Tết Đoan Ngọ", false), questionId1);
        answerDAO.insertAnswer(new Answer("D: Tết Nguyên Đán", false), questionId1);

// Câu hỏi 2
        long questionId2 = questionDAO.insertQuestion(new Question(2, "Từ nào sau đây viết đúng chính tả Tiếng Việt?", 400000, null));
        answerDAO.insertAnswer(new Answer("A: Trầy chật", false), questionId2);
        answerDAO.insertAnswer(new Answer("B: Chầy chật", false), questionId2);
        answerDAO.insertAnswer(new Answer("C: Trầy trật", true), questionId2);
        answerDAO.insertAnswer(new Answer("D: Chầy trật", false), questionId2);

// Câu hỏi 3
        long questionId3 = questionDAO.insertQuestion(new Question(3, "Câu thần chú \"Vừng ơi, mở ra!\" xuất hiện trong truyện cổ tích nào?", 600000, null));
        answerDAO.insertAnswer(new Answer("A: Cô bé bán diêm", false), questionId3);
        answerDAO.insertAnswer(new Answer("B: Nàng Bạch Tuyết và bảy chú lùn", false), questionId3);
        answerDAO.insertAnswer(new Answer("C: Cô bé quàng khăn đỏ", false), questionId3);
        answerDAO.insertAnswer(new Answer("D: Alibaba và bốn mươi tên cướp", true), questionId3);
    }


    private List<Question> getQuestion() {
//        insertSampleData();
        QuestionDAO questionDAO = new QuestionDAO(this);
        AnswerDAO answerDAO = new AnswerDAO(this);
        List<Question> listQuestion = questionDAO.getAllQuestions();
        for(Question question : listQuestion) {
            List<Answer> answers = answerDAO.getAnswersByQuestionId(question.getId());
            question.setListAnswer(answers);
        }
        return listQuestion;
    }

    private void checkAnswer(TextView textView, Question question, Answer answer) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(answer.getCorrect()) {
                    textView.setBackgroundResource(R.drawable.bg_green_corner_30);
                    amount *= 2;
                    btnAmount.setText(amount+ "$");
                    nextQuestion();
                } else {
                    textView.setBackgroundResource(R.drawable.bg_red_corner);
                    showAnswerCorrect(question);
                    gameOver();
                }
            }
        }, 1000);
    }


    private void nextQuestion() {
        if(currentQuestion == questionList.size() - 1) {
            showDialog("Bạn sẽ ra về với số tiền là: " + amount + "$. Xin chúc mừng!");
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentQuestion++;
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
                showDialog("Bạn sẽ ra về với số tiền là: " + amount + "$");
            }
        }, 1000);
    }

   private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
        } else if(id == R.id.btn_call_friend) {
            useCallFriend();
        }
    }

    private void showCenterDialog(int i, String content) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.use_call_friend, null);
        dialogBuilder.setView(dialogView);

        TextView callFriendTextView = dialogView.findViewById(R.id.call_friend);
        callFriendTextView.setText(content);

        AlertDialog dialog = dialogBuilder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }

//    private void showCenterDialog2(String content) {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.game_over, null);
//        dialogBuilder.setView(dialogView);
//
//        TextView callFriendTextView = dialogView.findViewById(R.id.call_friend);
//        callFriendTextView.setText(content);
//
//        AlertDialog dialog = dialogBuilder.create();
//
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//        dialog.show();
//        Window window = dialog.getWindow();
//        if (window != null) {
//            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            window.setGravity(Gravity.CENTER);
//        }
//    }


}
