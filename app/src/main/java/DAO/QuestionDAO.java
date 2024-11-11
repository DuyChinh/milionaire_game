package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.millionare_app.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private SQLiteDatabase db;

    public QuestionDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertQuestion(Question question) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_QUESTION_CONTENT, question.getContent());
        return db.insert(DBHelper.TABLE_QUESTION, null, contentValues);
    }

    public List<Question> getAllQuestions() {
        List<Question> listQuestion = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.TABLE_QUESTION, null, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_ID));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_CONTENT));
                listQuestion.add(new Question(id, content, null));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listQuestion;
    }

    public void close() {
        db.close();
    }
}