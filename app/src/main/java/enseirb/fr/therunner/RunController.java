package enseirb.fr.therunner;

public class RunController {
    private long time;
    private double distance;
    private int runningUser;

    public RunController(long time, double distance, int user){
        this.time = time;
        this.distance = distance;
        runningUser = user;
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