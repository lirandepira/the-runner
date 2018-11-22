package enseirb.fr.therunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SignUpLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void signUp(View view){
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public void onBackPressed(){
        setContentView(R.layout.activity_login);
    }

    public void submit(View view){

    }
}
