package enseirb.fr.therunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import enseirb.fr.therunner.db.RunController;
import enseirb.fr.therunner.db.RunsHandler;
import enseirb.fr.therunner.db.UsersHandler;
import enseirb.fr.therunner.util.FormatUtil;


public class PreviousRuns extends AppCompatActivity {

    private UsersHandler usersHandler;
    private RunsHandler runsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);
        usersHandler = new UsersHandler(this);
        runsHandler = new RunsHandler(this);

        String username = this.getIntent().getExtras().getString("username");
        setViews(username);
    }

    private void setViews(String username) {
        usersHandler.open();
        runsHandler.open();
        List<RunController> runController = runsHandler.getRuns(usersHandler.getUserByName(username).getId());
        runsHandler.close();
        usersHandler.close();
        int size = runController.size();
        //view1
        if(size == 0){
            Toast.makeText(getBaseContext(), "No runs recorded", Toast.LENGTH_SHORT).show();
        }
        else {
            int index=0;
            addRun(R.id.time1,
                    runController.get(index).getTime(),
                    R.id.kilometers1,
                    runController.get(index).getDistance(),
                    R.id.avgspeed1);
            index++;
            //view2
            if (size >= 2) {
                addRun(R.id.time2,
                        runController.get(index).getTime(),
                        R.id.kilometers2,
                        runController.get(index).getDistance(),
                        R.id.avgspeed2);
                index++;
            }

            //view3
            if (size >= 3) {
                addRun(R.id.time3,
                        runController.get(index).getTime(),
                        R.id.kilometers3,
                        runController.get(index).getDistance(),
                        R.id.avgspeed3);
                index++;

            }

            if (size >= 4) {
                addRun(R.id.time4,
                        runController.get(index).getTime(),
                        R.id.kilometers4,
                        runController.get(index).getDistance(),
                        R.id.avgspeed4);
                index++;
            }
        }
    }

    private void addRun(int timeViewId,
                        long time,
                        int distanceViewId,
                        double distance,
                        int averageSpeedId){
        TextView chronoView = findViewById(timeViewId);
        chronoView.setText(FormatUtil.formatTime(time));

        TextView distanceView = findViewById(distanceViewId);
        distanceView.setText(FormatUtil.formatDistance(distance));

        TextView avgSpeedView = findViewById(averageSpeedId);
        avgSpeedView.setText(FormatUtil.formatAverageSpeed(distance, time));
    }
}
