package enseirb.fr.therunner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Coordinates implements Serializable {

    private List<Coordinate> coordinates = new ArrayList<>();

    public void addCoordinate(Double latitude, Double longitude){
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);
        coordinates.add(coordinate);
    }

    public List<Coordinate> getCoordinates(){
        return coordinates;
    }
}
