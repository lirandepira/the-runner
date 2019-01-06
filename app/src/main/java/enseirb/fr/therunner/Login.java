package enseirb.fr.therunner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import enseirb.fr.therunner.db.UserController;
import enseirb.fr.therunner.db.UsersHandler;

public class Login extends AppCompatActivity {
    private UsersHandler usersHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 10);


        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET}, 1);
            }
        }



        setContentView(R.layout.activity_login);
        usersHandler = new UsersHandler(this);
    }

    public void signUp(View view){
        //setContentView(R.layout.activity_sign_up);
        launchSignUp(view);
    }

    public void launchSignUp(View view){
        startActivity(new Intent(this, SignUp.class));
    }

    @Override
    public void onBackPressed(){
        // setContentView(R.layout.activity_main);
    }
    public void submit(View view){
        EditText subLogin = findViewById(R.id.login);
        final String name = subLogin.getText().toString();
        hideKeyboardFrom(subLogin);

        usersHandler.open();
        UserController userControllerByName = usersHandler.getUserByName(name);
        usersHandler.close();
        if(userControllerByName == null){
            Toast.makeText(getBaseContext(), "User not found, please sign up", Toast.LENGTH_SHORT).show();
        }

        else {
            subLogin.setText("");
            launchMain(view, name);
        }
    }

    public void launchMain(View view, String name){
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("username", name);
        startActivity(intent);
    }

    private void hideKeyboardFrom (View view){
        InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}