package enseirb.fr.therunner;

public class User {
    private int id;
    private String username;

    public User(int id, String name){
        this.id = id;
        this.username = name;
    }
    public User(String name){
        this.username = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return username;
    }
}
