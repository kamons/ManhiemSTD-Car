package manheim.kamolstd.manheimcar;

import android.content.Intent;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    // Explicit  การประกาสศ ตัวแปล
    private Button singInButton, signUpButton;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Widget
        signUpButton = (Button) findViewById(R.id.button2);
        singInButton = (Button) findViewById(R.id.button);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);



        //signUp Controller
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(MainActivity.this, SignUpActivity.class));}
        });

        //SignIn Controller
        singInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (userString.equals("") ||passwordString.equals("")) {
                    MyAlert myAlert = new MyAlert(MainActivity.this,
                            R.drawable.nobita48, "Have Space", "Please Fill All Every Blank");
                    myAlert.myDialog();

                }

            }
        });

    } // Main Method

} // Main Class นี่คือ คลาสหลัก

