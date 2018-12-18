package enseirb.fr.therunner;

import java.util.Date;

public class RunController {
    private int id;
    private long time;
    private int distance;
    private int runningUser;

    public RunController(int id, long time, int distance, int user){
        this.id = id;
        this.time = time;
        this.distance = distance;
        runningUser = user;
    }
    public RunController(long time, int distance, int user){
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

    public int getDistance(){
        return distance;
    }

    public int getRunningUser(){
        return runningUser;
    }
}