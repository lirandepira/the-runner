package enseirb.fr.therunner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsersHandler {
    private final static int VERSION_BDD = 1;
    private SQLiteDatabase db;
    private Users_run users_run;

    public UsersHandler(Context context){
        users_run = new Users_run(context, "users", null, VERSION_BDD);
    }

    public void open(){
        db = users_run.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public SQLiteDatabase getDb(){
        return this.db;
    }

    public long insertUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        return db.insert("users", null, contentValues);
    }

    public User getUserByName(String name){
        Cursor cursor = db.query("users", new String[]{"id", "name"}, name, null, null, null, null);
        if(cursor.getCount() == 0){
            return null;
        }
        cursor.moveToFirst();
        User user = new User(cursor.getInt(0), cursor.getString(1));
        cursor.close();
        return user;
    }
}
