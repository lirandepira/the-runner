package enseirb.fr.therunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import enseirb.fr.therunner.db.UserController;
import enseirb.fr.therunner.db.UsersHandler;


public class SignUp extends AppCompatActivity {

    private UsersHandler usersHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usersHandler = new UsersHandler(this);
    }

    /**if the layout is already login, don't change it back*/
    @Override
    public void onBackPressed(){
        setContentView(R.layout.activity_login);
    }

    public void signUpSubmit(View view){
        boolean alreadyExists = false;
        Toast.makeText(getBaseContext(), "sign up page", Toast.LENGTH_SHORT).show();
       /* SharedPreferences preferences = this.getSharedPreferences("my-preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
*/

        EditText login = findViewById(R.id.submitLogin);
        final String name = login.getText().toString();
        hideKeyboardFrom(login);
        if(name.length()==0)

        {
            login.requestFocus();
            login.setError("FIELD CANNOT BE EMPTY");
            return;
        }

        else if(!name.matches("[a-zA-Z ]+"))
        {
            login.requestFocus();
            login.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return;
        }
        usersHandler.open();
        UserController userController = usersHandler.getUserByName(name);
        if(userController == null){
            userController = new UserController(name);
            usersHandler.insertUser(userController);
        }
        else {
            alreadyExists = true;
        }
        usersHandler.close();
        login.setText("");
        if(alreadyExists){
            Toast.makeText(getBaseContext(), "User " + name + " already exists", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getBaseContext(), "User " + name +  " has been added", Toast.LENGTH_SHORT).show();
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