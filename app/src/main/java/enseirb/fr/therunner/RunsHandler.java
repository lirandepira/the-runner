package enseirb.fr.therunner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
        contentValues.put("runningUser", runController.getRunningUser());
        return db.insert("runs", null, contentValues);
    }

    public List<RunController> getRuns(int userId){
        String where = "runningUser = "+ userId;
        String orderBy= "runId desc";

        Cursor cursor = db.query("runs", null, where,
                 null, null, null, orderBy);

        // get last 4
        int maxRun = 4;
        ArrayList<RunController> runs = new ArrayList<>();
        if(cursor.getCount() == 0){
            return runs;
        }

        // needs a max of 4 runs
        // in addition if we cannot move to next stop there
        while(maxRun > 0 && cursor.moveToNext()) {
            runs.add(new RunController( cursor.getLong(cursor.getColumnIndex("time")),
                    cursor.getDouble(cursor.getColumnIndex("distance")),
                    cursor.getInt(cursor.getColumnIndex("runningUser"))));
            maxRun--;
        }
        cursor.close();
        return runs;
    }
}