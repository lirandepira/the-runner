package enseirb.fr.therunner.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;


public class DatabaseHelper extends SQLiteOpenHelper {

    private final String CREATE_TABLE_USERS = "create table users(" + "userId integer primary key autoincrement," + "username varchar(50));";
    private final String CREATE_TABLE_RUNS = "create table runs(" + "runId integer primary key autoincrement," +
            "distance double," + "time long," + "runningUser int," +
            "FOREIGN KEY(runningUser)" + "REFERENCES users(userId)\n);";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_RUNS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newval){

        db.execSQL("drop table tracks");
        db.execSQL("drop table runs");
        db.execSQL("drop table users;");
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_RUNS);
    }

}
