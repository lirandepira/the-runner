package enseirb.fr.therunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import enseirb.fr.therunner.db.RunController;
import enseirb.fr.therunner.db.RunsHandler;
import enseirb.fr.therunner.db.UsersHandler;
import enseirb.fr.therunner.util.FiguresUtil;


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
            TextView chronoView1 = findViewById(R.id.time1);
            long chrono1 = runController.get(0).getTime();
            chronoView1.setText(FiguresUtil.formatTime(chrono1));

            TextView distanceView1 = findViewById(R.id.kilometers1);
            double distance1 = runController.get(0).getDistance();
            distanceView1.setText(FiguresUtil.formatDistance(distance1));

            TextView avgSpeedView1 = findViewById(R.id.avgspeed1);
            avgSpeedView1.setText(FiguresUtil.formatAverageSpeed(distance1, chrono1));

            //view2
            if (size >= 2) {
                TextView chronoView2 = findViewById(R.id.time2);
                long chrono2 = runController.get(1).getTime();
                chronoView2.setText(FiguresUtil.formatTime(chrono2));

                TextView distanceView2 = findViewById(R.id.kilometers2);
                double distance2 = runController.get(1).getDistance();
                distanceView2.setText(FiguresUtil.formatDistance(distance2));

                TextView avgSpeedView2 = findViewById(R.id.avgspeed2);
                avgSpeedView2.setText(FiguresUtil.formatAverageSpeed(distance2, chrono2));
            }

            //view3
            if (size >= 3) {
                TextView chronoView3 = findViewById(R.id.time3);
                long chrono3 = runController.get(2).getTime();
                chronoView3.setText(FiguresUtil.formatTime(chrono3));

                TextView distanceView3 = findViewById(R.id.kilometers3);
                double distance3 = runController.get(2).getDistance();
                distanceView3.setText(FiguresUtil.formatDistance(distance3));

                TextView avgSpeedView3 = findViewById(R.id.avgspeed3);
                avgSpeedView3.setText(FiguresUtil.formatAverageSpeed(distance3, chrono3));

            }

            if (size >= 4) {
                TextView chronoView4 = findViewById(R.id.time4);
                long chrono4 = runController.get(3).getTime();
                chronoView4.setText(FiguresUtil.formatTime(chrono4));

                TextView distanceView4 = findViewById(R.id.kilometers4);
                double distance4 = runController.get(3).getDistance();
                distanceView4.setText(FiguresUtil.formatDistance(distance4));

                TextView avgSpeedView4 = findViewById(R.id.avgspeed4);
                avgSpeedView4.setText(FiguresUtil.formatAverageSpeed(distance4, chrono4));
            }
        }
    }
}
