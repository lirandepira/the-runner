package enseirb.fr.therunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

    }

    public void done(View view){
        setContentView(R.layout.activity_main);
    }

    }
