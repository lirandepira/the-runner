package enseirb.fr.therunner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    public ArrayList<RunController> getRuns(int userId){
        Cursor cursor = db.query("runs", null, null,
                 null, null, null, null);
        int nbRuns = cursor.getCount()/4;
        ArrayList<RunController> runs = new ArrayList<>();
        if(nbRuns == 0){
            return runs;
        }
        cursor.moveToFirst();
        int index = 0;
        while(nbRuns > 0) {
            runs.add(new RunController(cursor.getInt(index), cursor.getLong(index+1),
                    cursor.getInt(index+2), cursor.getInt(index+3)));
            index+=5;
            nbRuns--;
        }
        cursor.close();
        return runs;
    }
}