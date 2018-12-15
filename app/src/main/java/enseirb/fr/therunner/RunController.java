package enseirb.fr.therunner;

import java.util.Date;

public class RunController {
    private int id;
    private long time;
    private int distance;
    private String date_run;
    private int runningUser;

    public RunController(int id, long time, int distance, String date_run, int user){
        this.id = id;
        this.time = time;
        this.distance = distance;
        this.date_run = date_run;
        runningUser = user;
    }
    public RunController(long time, int distance, String date_run, int user){
        this.time = time;
        this.distance = distance;
        this.date_run = date_run;
        this.runningUser = user;
    }

    public int getId(){
        return id;
    }

    public long getTime(){
        return time;
    }

    public int getDistance(){
        return distance;
    }

    public String getDate_run(){
        return date_run;
    }

    public int getRunningUser(){
        return runningUser;
    }
}