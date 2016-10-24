package manheim.kamolstd.manheimcar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

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
        private String titleString, messageString, truePasswordString;
        private String[] nameStrings, imageStrings, latStrings, lngStrings;
        private boolean aBoolean = true;




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

            try {
                JSONArray jsonArray = new JSONArray(s);

                nameStrings = new String[jsonArray.length()];
                imageStrings = new String[jsonArray.length()];
                latStrings = new String[jsonArray.length()];
                lngStrings = new String[jsonArray.length()];


                for (int i=0; i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // Check User
                    if (userString.equals(jsonObject.get("User"))) {

                        aBoolean = false;
                        truePasswordString = jsonObject.getString("Password");

                    } //if

                    //Setup Array
                    nameStrings[i] = jsonObject.getString("Name");
                    imageStrings[i] = jsonObject.getString("Image");
                    latStrings[i] = jsonObject.getString("Lat");
                    lngStrings[i] = jsonObject.getString("Lng");

                } // for

                if (aBoolean) {

                    MyAlert myAlert = new MyAlert(context, R.drawable.kon48, titleString, messageString);
                    myAlert.myDialog();

                } else if (passwordString.equals(truePasswordString)) {
                    //Password True
                    Toast.makeText(context,"Welcome", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ListService.class);
                    startActivity(intent);
                    finish();

                } else {
                    //Password False
                    MyAlert myAlert = new MyAlert(context, R.drawable.doremon48,
                            "Password False", "Please Try Again Password False");
                    myAlert.myDialog();
                }

            } catch (Exception e) {
                Log.d("24octV2", "e onPost==>" + e.toString());
            }

            } //OnPost

    } // SynData Class


} // Main Class นี่คือ คลาสหลัก

