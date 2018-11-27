package enseirb.fr.therunner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UsersHandler {
    private final static int VERSION_BDD = 1;
    private SQLiteDatabase db;
    private Users_run users_run;

    public UsersHandler(Context context){
        users_run = new Users_run(context, "Users", null, VERSION_BDD);
    }

    public void open(){
        db = users_run.getWritableDatabase();
    }

    public void close(){
        db.close();
    }
}
