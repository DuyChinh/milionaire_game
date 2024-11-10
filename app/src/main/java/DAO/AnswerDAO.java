package DAO;

import com.example.millionare_app.Answer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    private SQLiteDatabase db;

    public AnswerDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm câu trả lời
    public long insertAnswer(Answer answer, long questionId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ANSWER_CONTENT, answer.getContent());
        values.put(DBHelper.COLUMN_ANSWER_IS_CORRECT, answer.getCorrect() ? 1 : 0);
        values.put(DBHelper.COLUMN_ANSWER_QUESTION_ID, questionId);
        return db.insert(DBHelper.TABLE_ANSWER, null, values);
    }

    // Lấy danh sách câu trả lời theo ID câu hỏi
    public List<Answer> getAnswersByQuestionId(long questionId) {
        List<Answer> answerList = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.TABLE_ANSWER, null,
                DBHelper.COLUMN_ANSWER_QUESTION_ID + "=?",
                new String[]{String.valueOf(questionId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String content = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ANSWER_CONTENT));
                boolean isCorrect = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ANSWER_IS_CORRECT)) == 1;
                answerList.add(new Answer(content, isCorrect));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return answerList;
    }

    public void close() {
        db.close();
    }
}

