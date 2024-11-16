package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.millionare_app.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionDAO {
    private SQLiteDatabase db;

    public QuestionDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertQuestion(Question question) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_QUESTION_CONTENT, question.getContent());
        contentValues.put(DBHelper.COLUMN_QUESTION_SCORE, question.getScore());
        return db.insert(DBHelper.TABLE_QUESTION, null, contentValues);
    }

//    public List<Question> getAllQuestions() {
//        List<Question> listQuestion = new ArrayList<>();
//        Cursor cursor = db.query(DBHelper.TABLE_QUESTION, null, null, null, null, null, null);
//        if(cursor != null && cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_ID));
//                String content = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_CONTENT));
//                int score = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_SCORE));
//                listQuestion.add(new Question(id, content, score,null));
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        return listQuestion;
//    }

    public List<Question> getAllQuestions() {
        List<Question> listQuestion = new ArrayList<>();

        // Random giá trị group từ 1 đến 3
        Random random = new Random();
        int group = random.nextInt(3) + 1; // Random từ 0 -> 2, cộng thêm 1 để thành 1 -> 3

        // Tính toán OFFSET dựa trên group
        int offset = (group - 1) * 15;

        // Lấy 15 câu theo thứ tự điểm tăng dần và áp dụng OFFSET
        Cursor cursor = db.query(DBHelper.TABLE_QUESTION,
                null,
                null,
                null,
                null,
                null,
                null,
                offset + ",15");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_ID));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_CONTENT));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_SCORE));
                listQuestion.add(new Question(id, content, score, null));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return listQuestion;
    }

    public void close() {
        db.close();
    }
}