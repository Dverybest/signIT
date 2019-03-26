package com.dverybest.promiserynote;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SaveData {

  Context context;
  final String SignPath = "SignPath";
  final String bName = "bName";
  final String bAddress = "bAddress";
  final String bPhone = "bPhone";
  final String welcome = "welcome";

   SaveData(Context context) {
    this.context = context;

  }

  public String path(String path,String name ){
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath()+ "/signIT/";
    String pic_name = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
    File file = new File(DIRECTORY);
      if (!file.exists()) {
          file.mkdir();
      }
    if (name==""){
        pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }
    return DIRECTORY +name+ pic_name + path;
  }

  ArrayList<ReceiptListModel> ReadPath(){
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath()+ "/signIT/";
    File f = new File(DIRECTORY);
    f.mkdir();

    ArrayList<ReceiptListModel> list = new ArrayList<>();

    File [] files = f.listFiles();
    if (files !=null){
      for (File file: files) {
        try {
          String myFormat = "dd/MM/yyyy"; //In which you need put here
          SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

          list.add(new ReceiptListModel(file.getName(), sdf.format(file.lastModified()),file.getPath()));

        }catch (Exception e){

          Toast.makeText(context,e.getLocalizedMessage(),
                  Toast.LENGTH_SHORT).show();

        }
      }
    }
      Utils.original = list;
     return list;

  }

   void setData(String key, String value) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    Editor editor = sharedPreferences.edit();
    editor.putString(key, value);
    editor.apply();
  }

   String getData(String key) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getString(key, "");
  }

    void setData(String key,boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

  boolean gettData(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key,false);
  }
}
