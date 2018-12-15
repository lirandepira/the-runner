package enseirb.fr.therunner;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;


public class Users_run extends SQLiteOpenHelper {

    private final String CREATE_TABLE = "create table users(" + "id integer primary key autoincrement," + "username varchar(50));";

    public Users_run(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newval){
        db.execSQL("drop table users;");
        db.execSQL(CREATE_TABLE);
    }

}
