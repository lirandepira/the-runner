package enseirb.fr.therunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.util.Log;


public class SignUpLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        SharedPreferences preferences = this.getSharedPreferences("my-preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        EditText login = findViewById(R.id.submitLogin);

        editor.putString("login", login.getText().toString());
        if(!editor.commit()){
            Log.i("login", "commit");
        }
        start(view);
    }

    public void submit(View view){
        SharedPreferences preferences = this.getSharedPreferences("my-prefs", MODE_PRIVATE);
        String login;
        login = preferences.getString("login", "");
        String subLogin = findViewById(R.id.login).toString();
        try{
        if(login.compareTo(subLogin) == 0){
            start(view);
        }
        else{
            setContentView(R.layout.activity_sign_up);
        }}catch (NullPointerException ne){
            ne.printStackTrace();
        }
    }

    public void start(View view){
        startActivity(new Intent(this, Main.class));
    }
}
