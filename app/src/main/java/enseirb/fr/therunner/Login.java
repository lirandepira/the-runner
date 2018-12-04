package enseirb.fr.therunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private UsersHandler usersHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usersHandler = new UsersHandler(this);

    }

    public void signUp(View view){
        setContentView(R.layout.activity_sign_up);
    }

    public void submit(View view){
        EditText subLogin = findViewById(R.id.submitLogin);
        final String name = subLogin.getText().toString();

        Log.e("HELLO", "");

        usersHandler.open();
        User userByName = new User("m"); //usersHandler.getUserByName(name);
        usersHandler.close();
        if(userByName == null){
            Toast.makeText(getBaseContext(), "User not found, please sign up", Toast.LENGTH_SHORT).show();
        }
        else{
            launchMain(view);
        }
    }

    public void launchMain(View view){
        startActivity(new Intent(this, Main.class));
    }
}
