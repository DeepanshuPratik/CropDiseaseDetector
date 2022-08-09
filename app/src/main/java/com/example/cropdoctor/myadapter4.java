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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter4 extends RecyclerView.Adapter<myadapter4.ViewHolder4>
{
    private static final String TAG="RecyclerAdapter";
    private static final int REQUEST_CODE_IMAGE = 100;
    private Context mcontext;
    private ArrayList<model> datalist;

    public myadapter4(Context context,ArrayList<model> data){
        mcontext=context;
        datalist=data;
    }

    @NonNull
    @Override
    public myadapter4.ViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelayout,parent,false);

        return new myadapter4.ViewHolder4(view);
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder4 holder, int position) {
        final model temp=datalist.get(position);
        holder.name.setText(datalist.get(position).getName());
        Picasso.get().load(datalist.get(position).getPurl())
                .into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(mcontext, UploadImageMain.class);
                    i.putExtra("url", temp.getPurl());
                    i.putExtra("Cropname", temp.getName());


                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(i);
                }
            
        });


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class ViewHolder4 extends RecyclerView.ViewHolder
    {

        ImageView img;
        TextView name;
        public ViewHolder4(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.recimg);
            name=(TextView)itemView.findViewById(R.id.nametxt);

        }
    }
}
