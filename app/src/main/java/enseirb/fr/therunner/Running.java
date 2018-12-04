package enseirb.fr.therunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Running extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

    }

    public void finish(View view){
        setContentView(R.layout.activity_summary);
    }

    }
