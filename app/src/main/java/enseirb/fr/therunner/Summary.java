package enseirb.fr.therunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Summary extends AppCompatActivity {

    private RunsHandler runsHandler;
    private UsersHandler usersHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        runsHandler = new RunsHandler(this);
    }
    public void goToHomepage (View view){
        launchMain(view);
    }

    public void launchMain(View view){
        startActivity(new Intent(this, Main.class));
    }
    }
