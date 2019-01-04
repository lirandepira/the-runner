package enseirb.fr.therunner;

import java.util.ArrayList;


public class RunController {
    private int id;
    private long time;
    private double distance;
    private int runningUser;
    private ArrayList<Double> longitudes = new ArrayList<>();
    private ArrayList<Double> latitudes = new ArrayList<>();

    public RunController(int id, long time, double distance, int user){
        this.id = id;
        this.time = time;
        this.distance = distance;
        runningUser = user;
    }
    public RunController(long time, double distance, int user, ArrayList<Double> longitudes, ArrayList<Double> latitudes){
        this.time = time;
        this.distance = distance;
        this.runningUser = user;
        this.longitudes = longitudes;
        this.latitudes = latitudes;
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

    public ArrayList<Double> getLongitudes(){return longitudes;}

    public ArrayList<Double> getLatitudes(){ return latitudes;}
}