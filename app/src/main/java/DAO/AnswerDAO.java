package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.millionare_app.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    private SQLiteDatabase db;

    public AnswerDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertAnswer(Answer answer, long questionId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_ANSWER_CONTENT, answer.getContent());
        contentValues.put(DBHelper.COLUMN_ANSWER_IS_CORRECT, answer.getCorrect() ? 1 : 0);
        contentValues.put(DBHelper.COLUMN_ANSWER_QUESTION_ID, questionId);
        return db.insert(DBHelper.TABLE_ANSWER, null, contentValues);
    }

    public List<Answer> getAnswersByQuestionId(long questionId) {
        List<Answer> listAnswer = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.TABLE_ANSWER, null, DBHelper.COLUMN_ANSWER_QUESTION_ID + "=?", new String[]{String.valueOf(questionId)}, null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                String content = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ANSWER_CONTENT));
                Boolean isCorrect = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ANSWER_IS_CORRECT)) == 1;
                listAnswer.add(new Answer(content, isCorrect));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listAnswer;
    }

    public void close() {
        db.close();
    }
}