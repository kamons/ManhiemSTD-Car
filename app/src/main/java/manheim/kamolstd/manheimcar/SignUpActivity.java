package manheim.kamolstd.manheimcar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, usEditText, passEditText;
    private ImageView imageView;
    private Button button;
    private String nameString,userString,passwordString, imageString,
            imagePathString, imageNameString;

    private Uri uri;
    private boolean aBoolean = true;



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
                } else if (aBoolean) {
                    // Non Choose Image
                    MyAlert myAlert = new MyAlert(SignUpActivity.this,
                            R.drawable.doremon48, "ยังไม่ได้เลือกรูปภาพ", "กรุณาเลือกรูปภาพด้วยคะ");
                    myAlert.myDialog();

                } else {
                    // Choose Image finished
                }

            }   //onClick
        });

        //Image Controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "โปรดเลือกรูป"), 1);

            } //onClick
        });

    } // Main Method


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1)&&(resultCode == RESULT_OK)) {

            Log.d("23octV1", "Result OK");
            aBoolean = false;

            //Setup Image

            uri = data.getData();
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(uri));

                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            } //try

            //Find Path and Name
            imagePathString = myFindPath(uri);
            Log.d("23octV1", "imagePathString ==> " + imagePathString);

            imageNameString = imagePathString.substring(imagePathString.lastIndexOf("/"));
            Log.d("23octV1", "imageNameString ==> " + imageNameString);



        } //if

    } //on ActivityResult

    private String myFindPath(Uri uri) {
        String result = null;

        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);

        if (cursor !=null) {

            cursor.moveToFirst();
            int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(i);

        } else {
            result = uri.getPath();

        }

        return result;


    }


} // Main Class