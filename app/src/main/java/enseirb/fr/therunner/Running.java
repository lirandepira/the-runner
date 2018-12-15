package enseirb.fr.therunner;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

public class Running extends AppCompatActivity {

    private RunsHandler runsHandler;
    private UsersHandler usersHandler;

    private Chronometer chronometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        usersHandler = new UsersHandler(this);
        runsHandler = new RunsHandler(this);
        Toast.makeText(getBaseContext(), "User " + this.getIntent().getExtras().getString("username") + " is connected", Toast.LENGTH_SHORT).show();
        chronometer = (Chronometer) findViewById(R.id.chrono);
        chronometer.start();
    }

    public void finish (View view){
        chronometer.stop();
        long elapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
        int distance =0;//fixme find how to calculate me
        Toast.makeText(getBaseContext(), "You ran " + elapsed/1000 + " seconds"  , Toast.LENGTH_SHORT).show();
        String username = this.getIntent().getExtras().getString("username");
        runsHandler.open();
        runsHandler.insertRun(new RunController(elapsed, distance, "01-01-2019", usersHandler.getUserByName(username).getId()));
        runsHandler.close();
        //save in the db
        launchSummary(view);
    }

    public void launchSummary(View view){
        startActivity(new Intent(this, Summary.class));
    }

    }