package enseirb.fr.therunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Summary extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        int distance = this.getIntent().getExtras().getInt("distance");
        long chrono = this.getIntent().getExtras().getLong("chrono");
        long minutes = chrono / 60;
        long seconds = chrono - 60 * minutes;
        String displayChrono = minutes + " : " + seconds;
        TextView chronoView = findViewById(R.id.chronoresult);
        chronoView.setText(displayChrono);
        TextView distanceView = findViewById(R.id.distance);
        distanceView.setText(Integer.toString(distance));
        TextView avgSpeedView = findViewById(R.id.avgspeed);
        avgSpeedView.setText(Long.toString(averageSpeed(distance, chrono)));
    }

    public long averageSpeed(int distance, long chrono){
        return distance * 60 / chrono;
    }

    public void goToHomepage (View view){
        launchMain(view);
    }

    public void launchMain(View view){
        startActivity(new Intent(this, Main.class));
    }
    }
