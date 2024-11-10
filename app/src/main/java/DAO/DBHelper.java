package DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MillionaireDB";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    public static final String TABLE_QUESTION = "Question";
    public static final String TABLE_ANSWER = "Answer";

    // Các cột của bảng Question
    public static final String COLUMN_QUESTION_ID = "id";
    public static final String COLUMN_QUESTION_CONTENT = "content";

    // Các cột của bảng Answer
    public static final String COLUMN_ANSWER_ID = "id";
    public static final String COLUMN_ANSWER_CONTENT = "content";
    public static final String COLUMN_ANSWER_IS_CORRECT = "isCorrect";
    public static final String COLUMN_ANSWER_QUESTION_ID = "questionId"; // Foreign Key

    // Tạo bảng Question
    private static final String CREATE_TABLE_QUESTION =
            "CREATE TABLE " + TABLE_QUESTION + " (" +
                    COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUESTION_CONTENT + " TEXT" +
                    ");";

    // Tạo bảng Answer
    private static final String CREATE_TABLE_ANSWER =
            "CREATE TABLE " + TABLE_ANSWER + " (" +
                    COLUMN_ANSWER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ANSWER_CONTENT + " TEXT, " +
                    COLUMN_ANSWER_IS_CORRECT + " INTEGER, " +
                    COLUMN_ANSWER_QUESTION_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_ANSWER_QUESTION_ID + ") REFERENCES " +
                    TABLE_QUESTION + "(" + COLUMN_QUESTION_ID + ")" +
                    ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_ANSWER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(db);
    }
}