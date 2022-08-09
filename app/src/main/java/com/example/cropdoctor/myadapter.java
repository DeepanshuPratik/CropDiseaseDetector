package com.example.cropdoctor;

import android.Manifest;
import android.app.Activity;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends RecyclerView.Adapter<myadapter.ViewHolder>
{
    private static final String TAG="RecyclerAdapter";
    private static final int REQUEST_CODE_IMAGE = 100;
    private Context mcontext;
    private ArrayList<model> datalist;

    public myadapter(Context context,ArrayList<model> data){
        mcontext=context;
        datalist=data;
    }

    @NonNull
    @Override
    public myadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelayout,parent,false);

        return new ViewHolder(view);
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final model temp=datalist.get(position);
        holder.name.setText(datalist.get(position).getName());
        Picasso.get().load(datalist.get(position).getPurl())
                .into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(mcontext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mcontext, "Please give permission in settings for taking Image", Toast.LENGTH_LONG).show();
                    //ActivityCompat.requestPermissions((Activity) mcontext, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_IMAGE);

                }
                else {
                    Intent i = new Intent(mcontext, ImageSubmision.class);
                    i.putExtra("url", temp.getPurl());
                    i.putExtra("Cropname", temp.getName());


                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(i);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView img;
        TextView name;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.recimg);
            name=(TextView)itemView.findViewById(R.id.nametxt);

        }
    }
}
