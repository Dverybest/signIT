package com.dverybest.promiserynote;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReceiptActivity extends AppCompatActivity implements MyDialogFragment.setImage,DatePickerFragment.Date {

    Button rSign , pSign;
    ImageView rImg, pImg;
    String where;
    EditText date,nameE;
    LinearLayout fab1,fab2;
    FloatingActionButton fab;
    private Bitmap bitmap;
    LinearLayout llPdf;
    TextView name,address,phone;
    SaveData data = new SaveData(this);
    private boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        rSign  = findViewById(R.id.rSign);
        pSign = findViewById(R.id.pSign);
        rImg = findViewById(R.id.rImg);
        pImg = findViewById(R.id.pImg);
        date = findViewById(R.id.date);
        llPdf = findViewById(R.id.container);
        name = findViewById(R.id.cName);
        nameE = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);

        load();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment  datePickerFragment = new DatePickerFragment();

                datePickerFragment.show(getSupportFragmentManager(),"date");

            }
        });

        rSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                where= "r";
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(getSupportFragmentManager(),"Dialog");
            }
        });

        pSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                where= "p";
                MyDialogFragment dialogFragment = new MyDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("","r");
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(),"Dialog");
            }
        });
       fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if(!click)  {

                 fab.setImageResource(R.drawable.ic_cancel_black_24dp);
                 fab1.setVisibility(View.VISIBLE);
                 fab2.setVisibility(View.VISIBLE);
                 click = true;
             }else {
                 fabClose();
             }


            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                createPNG(".png");
                fabClose();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                createPdf(".pdf");
                fabClose();
            }
        });
    }

    void fabClose(){
        fab.setImageResource(R.drawable.ic_print_black_24dp);
        fab1.setVisibility(View.GONE);
        fab2.setVisibility(View.GONE);
        click = false;
    }

    void load(){

       /* if (data.getData(data.SignPath) != ""){
            Bitmap bitmap = BitmapFactory.decodeFile(data.getData(data.SignPath));
            rImg.setImageBitmap(bitmap);
        }*/
        if(data.getData(data.bName) != ""){
            name.setText(data.getData(data.bName).toUpperCase());
            phone.setText(data.getData(data.bPhone).toUpperCase());
            address.setText(data.getData(data.bAddress).toUpperCase());
        }
       this.date.setText(new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date()));
    }

    @Override
    public void set(Bitmap bitmap) {

        if(where =="r"){
            rImg.setImageBitmap(bitmap);
        }else{
            pImg.setImageBitmap(bitmap);
        }

    }

    @Override
    public void OnDateSet(String date) {
        this.date.setText(date);
    }


    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(String extension){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content

        File filePath;
        if(TextUtils.isEmpty(nameE.getText())){
            filePath = new File(data.path(extension,""));
        }else {
            filePath = new File(data.path(extension,nameE.getText().toString()));
        }

        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            return;
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF(filePath.getPath());

    }

    private void createPNG(String extension){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);


        File filePath;
        if(TextUtils.isEmpty(nameE.getText())){
            filePath = new File(data.path(extension,""));
        }else {
            filePath = new File(data.path(extension,nameE.getText().toString()));
        }

        // write the document content
        try {
            // Output the file
            FileOutputStream mFileOutStream = new FileOutputStream(filePath);
            // Convert the output file to Image such as .png
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);

            mFileOutStream.flush();
            mFileOutStream.close();

        } catch (Exception e) {
            Log.v("log_tag", e.toString());
        }

        Toast.makeText(this, "PNG of Receipt is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPNG(filePath.getPath());

    }

    private void openGeneratedPDF(String pathName){
        File file = new File(pathName);
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(this, "No Application available to  view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openGeneratedPNG(String pathName){
        File file = new File(pathName);
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "image/png");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(this, "No Application " +
                        "available to view image", Toast.LENGTH_LONG).show();
            }
        }
    }
}