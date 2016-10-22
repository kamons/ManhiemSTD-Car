package manheim.kamolstd.manheimcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    // Explicit  การประกาสศ ตัวแปล
    private Button singInButton, signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Widget
        signUpButton = (Button) findViewById(R.id.button);
        singInButton = (Button) findViewById(R.id.button2);

        //signUp Controller
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(MainActivity.this, SignUpActivity.class));}
        });
    } // Main Method

} // Main Class นี่คือ คลาสหลัก

