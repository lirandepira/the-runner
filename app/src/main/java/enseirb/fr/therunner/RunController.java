package enseirb.fr.therunner;

import java.util.Date;

public class RunController {
    private int id;
    private long time;
    private double distance;
    private int runningUser;

    public RunController(int id, long time, double distance, int user){
        this.id = id;
        this.time = time;
        this.distance = distance;
        runningUser = user;
    }
    public RunController(long time, double distance, int user){
        this.time = time;
        this.distance = distance;
        this.runningUser = user;
    }

    public int getId(){
        return id;
    }

    public long getTime(){
        return time;
    }

    public double getDistance(){
        return distance;
    }

    public int getRunningUser(){
        return runningUser;
    }
}