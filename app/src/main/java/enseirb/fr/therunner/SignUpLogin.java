package enseirb.fr.therunner;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SettingsSlicesContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;


public class SignUpLogin extends AppCompatActivity {

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


    /**if the layout is already login, don't change it back*/
    @Override
    public void onBackPressed(){
        setContentView(R.layout.activity_login);
    }

    public void signUpSubmit(View view){
       /* SharedPreferences preferences = this.getSharedPreferences("my-preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
*/

        EditText login = findViewById(R.id.submitLogin);
        final String name = login.getText().toString();
        if(name.length()==0)

        {
            login.requestFocus();
            login.setError("FIELD CANNOT BE EMPTY");
        }

        else if(!name.matches("[a-zA-Z ]+"))
        {
            login.requestFocus();
            login.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        }
        usersHandler.open();
        User user = new User(login.getText().toString());
        usersHandler.insertUser(user);
        usersHandler.close();
        login.setText("");
        Toast.makeText(getBaseContext(), "User added", Toast.LENGTH_SHORT).show();
        launchMain(view);
    }

    public void launchMain(View view){
        startActivity(new Intent(this, Main.class));
    }
}
