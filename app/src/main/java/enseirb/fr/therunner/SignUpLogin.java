package enseirb.fr.therunner;

import android.content.Intent;
import android.os.Bundle;
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
        usersHandler.open();
        User user = new User(login.getText().toString());
        usersHandler.insertUser(user);
        usersHandler.close();
        login.setText("");
        Toast.makeText(getBaseContext(), "User added", Toast.LENGTH_SHORT).show();
    }

    public void submit(View view){
        String subLogin = findViewById(R.id.login).toString();
        usersHandler.open();
        User userByName = usersHandler.getUserByName(subLogin);
        if(userByName == null){
            Toast.makeText(getBaseContext(), "User not found, please sign up", Toast.LENGTH_SHORT).show();
        }
    }

    public void start(View view){
        startActivity(new Intent(this, Main.class));
    }
}
