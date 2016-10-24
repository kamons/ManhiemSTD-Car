package manheim.kamolstd.manheimcar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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
                if (userString.equals("") || passwordString.equals("")) {
                    MyAlert myAlert = new MyAlert(MainActivity.this,
                            R.drawable.nobita48, "Have Space", "Please Fill All Every Blank");
                    myAlert.myDialog();

                } else {
                    // No Space
                    MyConstant myConstant = new MyConstant();
                    SynData synData = new SynData(MainActivity.this);
                    synData.execute(myConstant.getUrlJSON(),
                            myConstant.getTestTitle(),
                            myConstant.getTestMessage());
                }

            }
        });

    } // Main Method

    private class SynData extends AsyncTask<String, Void, String> {

        public SynData(Context context) {
            this.context = context;
        }

        //Explicit
        private Context context;
        private String titleString, messageString;



        @Override
        protected String doInBackground(String... params) {

            try {
                titleString = params[1];
                messageString = params[2];

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(params[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d ("24octV1", "e doInBack ==>" + e.toString());
                return null;

            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("24octV1", "JSON==>" + s);

            } //OnPost

    } // SynData Class


} // Main Class นี่คือ คลาสหลัก

