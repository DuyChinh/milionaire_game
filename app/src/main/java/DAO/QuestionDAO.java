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

    // Thêm câu hỏi
    public long insertQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_QUESTION_CONTENT, question.getContent());
        return db.insert(DBHelper.TABLE_QUESTION, null, values);
    }

    // Lấy tất cả các câu hỏi
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.TABLE_QUESTION, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Sử dụng getColumnIndexOrThrow để đảm bảo tên cột hợp lệ
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_ID));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUESTION_CONTENT));

                questionList.add(new Question(id, content, null)); // Câu trả lời sẽ thêm sau
            } while (cursor.moveToNext());
            cursor.close();
        }
        return questionList;
    }


    public void close() {
        db.close();
    }
}
