package com.dverybest.promiserynote;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by BEN on 25/10/2018.
 */

public class ListViewAdapter extends ArrayAdapter <ReceiptListModel>{

    Context context;
    int resource;
    ArrayList<ReceiptListModel> list;

    public ListViewAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<ReceiptListModel>  objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){

            convertView  = LayoutInflater.from(context).inflate(resource,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {

            viewHolder =(ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(list.get(position).getName());
        viewHolder.date.setText(list.get(position).getDate());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder  alert = new AlertDialog.Builder(context);
                alert.setMessage("Delete Item?");
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file = new File(list.get(position).getFilePath());
                        file.delete();
                        list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context,"File deleted",Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });
                alert.create().show();
            }
        });
        return convertView;
    }
    public void add( ArrayList<ReceiptListModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public  void search(String query){
        this.list = Utils.original;

        if(query==null ||query==""|| TextUtils.isEmpty(query)){
            notifyDataSetChanged();
            return;
        }

        ArrayList<ReceiptListModel> filter = new ArrayList<>();
        for (ReceiptListModel object:list) {

            if(object.getName().toLowerCase().contains(query.toLowerCase())){
                filter.add(object);
            }
            list = filter;
            notifyDataSetChanged();
        }

    }

    @Override
    public ReceiptListModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    class ViewHolder {

        TextView name;
        TextView date;
        ImageView delete;

        public ViewHolder(View view) {
            name= view.findViewById(R.id.name);
            date = view.findViewById(R.id.date);
            delete = view.findViewById(R.id.dot);
        }
    }
}
