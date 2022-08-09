package com.example.cropdoctor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myadapter3 extends RecyclerView.Adapter<myadapter3.ViewHolder3>   {
    private static final String TAG="RecyclerAdapter";
    private static final int REQUEST_CODE_IMAGE = 100;
    private Context mcontext;
    private ArrayList<model> datalist;

    public myadapter3(Context context,ArrayList<model> data){
        mcontext=context;
        datalist=data;
    }
    @NonNull
    @Override
    public myadapter3.ViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelayout,parent,false);

        return new myadapter3.ViewHolder3(view);
    }


    @NonNull
    @Override
    public void onBindViewHolder(@NonNull myadapter3.ViewHolder3 holder, int position) {
        final model temp=datalist.get(position);
        holder.name.setText(datalist.get(position).getName());
        Picasso.get().load(datalist.get(position).getPurl())
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


    class ViewHolder3 extends RecyclerView.ViewHolder
    {

        ImageView img;
        TextView name;
        public ViewHolder3(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.recimg);
            name=(TextView)itemView.findViewById(R.id.nametxt);

        }
    }
}
