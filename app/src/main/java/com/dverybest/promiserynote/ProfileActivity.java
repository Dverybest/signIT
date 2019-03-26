package com.dverybest.promiserynote;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity implements MyDialogFragment.setImage {

    ImageView signature;
   // Button sign;
    EditText bName,bAddress,bPhone;
    SaveData data = new SaveData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bName = findViewById(R.id.name);
        bAddress = findViewById(R.id.address);
        bPhone = findViewById(R.id.phone);
        //sign = findViewById(R.id.btnSign);
        signature = findViewById(R.id.imgSign);

        load();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


       /* sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(getSupportFragmentManager(),"Dialog");
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (TextUtils.isEmpty(bAddress.getText())||TextUtils.isEmpty(bName.getText())||TextUtils.isEmpty(bPhone.getText())){

                Toast.makeText(getApplicationContext(),"Please fill the form to save",Toast.LENGTH_SHORT).show();
                return;
            }


            data.setData(data.bName,bName.getText().toString());
            data.setData(data.bAddress,bAddress.getText().toString());
            data.setData(data.bPhone,bPhone.getText().toString());
            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
            finish();
            }
        });
    }

    void load(){
     /*  if (data.getData(data.SignPath) != ""){
           Bitmap bitmap = BitmapFactory.decodeFile(data.getData(data.SignPath));
           signature.setImageBitmap(bitmap);
       }*/
       if(data.getData(data.bName) != ""){
           bName.setText(data.getData(data.bName));
           bPhone.setText(data.getData(data.bPhone));
           bAddress.setText(data.getData(data.bAddress));
       }
    }
    @Override
    public void set(Bitmap bitmap) {
      /*  try {
            // Output the file
            String path = data.path(".png");
            File filePath;
            filePath = new File(path);
            FileOutputStream mFileOutStream = new FileOutputStream(filePath);

            // Convert the output file to Image such as .png

            bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
            data.setData(data.SignPath,path);

            mFileOutStream.flush();
            mFileOutStream.close();

        } catch (Exception e) {
            Log.v("log_tag", e.toString());
        }
        signature.setImageBitmap(bitmap);*/
    }

}
