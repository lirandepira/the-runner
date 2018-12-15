package enseirb.fr.therunner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RunsHandler {
    private final static int VERSION_BDD = 1;
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public RunsHandler(Context context){
        databaseHelper = new DatabaseHelper(context, "runs", null, VERSION_BDD);
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

    public long insertRun(RunController runController){
        ContentValues contentValues = new ContentValues();
        contentValues.put("distance", runController.getDistance());
        contentValues.put("time", runController.getTime());
        contentValues.put("date", runController.getDate_run());
        return db.insert("runs", null, contentValues);
    }

    public RunController getAllRuns(int userId){
        Cursor cursor = db.query("runs", null, "runningUser like \"" + userId + "\"", null, null, null, null);
        if(cursor.getCount() < 1){
            return null;
        }
        cursor.moveToFirst();
        RunController runController = new RunController(cursor.getInt(0), cursor.getLong(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4));
        cursor.close();
        return runController;
    }
}
