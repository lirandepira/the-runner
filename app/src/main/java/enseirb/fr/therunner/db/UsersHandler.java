package enseirb.fr.therunner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsersHandler {
    private final static int VERSION_BDD = 1;
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public UsersHandler(Context context){
        databaseHelper = new DatabaseHelper(context, "users", null, VERSION_BDD);
    }

    public void open(){
        db = databaseHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public SQLiteDatabase getDb(){
        return this.db;
    }

    public long insertUser(UserController userController){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", userController.getName());
        return db.insert("users", null, contentValues);
    }

    public UserController getUserByName(String name){
        Cursor cursor = db.query("users", null, "username like \"" + name + "\"", null, null, null, null);
        if(cursor.getCount() < 1){
            return null;
        }
        cursor.moveToFirst();
        UserController userController = new UserController(cursor.getInt(0), cursor.getString(1));
        cursor.close();
        return userController;
    }

}
