package com.example.cropdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UploadImage extends AppCompatActivity implements OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    RecyclerView recyclerView;
    private myadapter4 myadapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ArrayList<model> dataList;
    private static final int REQUEST_CODE = 100;
    ImageSlider mainslider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);


            drawerLayout = findViewById(R.id.drawer_layout2);
            navigationView = findViewById(R.id.nav_view2);
            toolbar = findViewById(R.id.toolbar2);
            setSupportActionBar(toolbar);

            navigationView.bringToFront();
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_photo);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        recyclerView=(RecyclerView)findViewById(R.id.recview4);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setHasFixedSize(true);

        storageReference= FirebaseStorage.getInstance().getReference();
        dataList=new ArrayList<>();

        init();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainslider = (ImageSlider) findViewById(R.id.image_slider2);
        final List<SlideModel> remoteimages = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren())
                            remoteimages.add(new SlideModel(data.child("url").getValue().toString(), data.child("title").getValue().toString(), ScaleTypes.FIT));

                        mainslider.setImageList(remoteimages, ScaleTypes.FIT);

                        mainslider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                Toast.makeText(getApplicationContext(), remoteimages.get(i).getTitle().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });

        if(ContextCompat.checkSelfPermission(UploadImage.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(UploadImage.this, "Please give permission for storage access", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(UploadImage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

        }
    }

    private void init() {
        clearAll();
        Query query=databaseReference.child("CropName");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    model nmodel=new model();
                    nmodel.setPurl(snapshot.child("purl").getValue().toString());
                    nmodel.setName(snapshot.child("name").getValue().toString());

                    dataList.add(nmodel);

                }
                myadapter=new myadapter4(getApplicationContext(),dataList);

                recyclerView.setAdapter(myadapter);

                myadapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAll() {
        if(dataList!=null){
            dataList.clear();

            if(myadapter!=null){
                myadapter.notifyDataSetChanged();
            }
        }
        dataList=new ArrayList<>();
    }

    public void gramCropnew(View view) {
        Intent i=new Intent(UploadImage.this,UploadImageMain.class);
        i.putExtra("Cropname","Gram");
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                Intent intent = new Intent(UploadImage.this, DescriptionPlant1.class);
                startActivity(intent);
                break;
            case R.id.nav_photo:
                break;
            case R.id.nav_settings:
                Intent intent2 = new Intent(UploadImage.this, Settings.class);
                startActivity(intent2);

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}