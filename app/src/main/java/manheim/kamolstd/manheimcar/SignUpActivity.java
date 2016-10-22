package manheim.kamolstd.manheimcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, usEditText, passEditText;
    private ImageView imageView;
    private Button button;
    private String nameString,userString,passwordString, imageString;

    public SignUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText3);
        usEditText = (EditText) findViewById(R.id.editText2);
        passEditText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button3);

        //Button Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value From Edit Text
                nameString = nameEditText.getText().toString().trim();
                userString = usEditText.getText().toString().trim();
                passwordString = passEditText.getText().toString();

                //Check Space
                if (nameString.equals("") ||
                        userString.equals("") ||
                        passwordString.equals("")) {
                    //Have space
                    MyAlert myAlert = new MyAlert(SignUpActivity.this,
                            R.drawable.bird48,"มีช่องว่าง","กรุณากรอกทุกช่อง คะ");
                    myAlert.myDialog();
                }

            }   //onClick
        });

    } // Main Method

} // Main Class