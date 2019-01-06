package enseirb.fr.therunner.db;

public class UserController {
    private int id;
    private String username;

    public UserController(int id, String name){
        this.id = id;
        this.username = name;
    }
    public UserController(String name){
        this.username = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return username;
    }
}
