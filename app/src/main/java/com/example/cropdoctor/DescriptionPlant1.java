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
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.denzcoskun.imageslider.ImageSlider;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class DescriptionPlant1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_CODE_IMAGE = 100;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    RecyclerView recyclerView,recyclerView2,recyclerView3;

    DatabaseReference databaseReference;
    StorageReference storageReference;
    ArrayList<model> dataList;
    ArrayList<model> dataList2;
    ArrayList<model> dataList3;
    ImageSlider mainslider;
    Context mcontext=DescriptionPlant1.this;
    private myadapter myadapter;
    private myadapter2 myadapter2;
    private myadapter3 myadapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_plant1);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        recyclerView=(RecyclerView)findViewById(R.id.recview);
        recyclerView2=(RecyclerView)findViewById(R.id.recview2);
        recyclerView3=(RecyclerView)findViewById(R.id.recview3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);

        storageReference= FirebaseStorage.getInstance().getReference();
        dataList=new ArrayList<>();
        dataList2=new ArrayList<>();
        dataList3=new ArrayList<>();

        init();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainslider = (ImageSlider) findViewById(R.id.image_slider);
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

        if(ContextCompat.checkSelfPermission(mcontext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(DescriptionPlant1.this, "Please give permission for accessing camera", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions((Activity) DescriptionPlant1.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_IMAGE);

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
                    dataList2.add(nmodel);
                    dataList3.add(nmodel);
                }
                myadapter=new myadapter(getApplicationContext(),dataList);
                myadapter2=new myadapter2(getApplicationContext(),dataList2);
                myadapter3= new myadapter3(getApplicationContext(),dataList3);
                recyclerView.setAdapter(myadapter);
                recyclerView2.setAdapter(myadapter2);
                recyclerView3.setAdapter(myadapter3);
                myadapter.notifyDataSetChanged();
                myadapter2.notifyDataSetChanged();
                myadapter3.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAll() {
        if(dataList!=null){
            dataList.clear();
            dataList2.clear();
            dataList3.clear();

            if(myadapter!=null){
                myadapter.notifyDataSetChanged();
            }
            if(myadapter2!=null){
                myadapter2.notifyDataSetChanged();
            }
            if(myadapter3!=null){
                myadapter3.notifyDataSetChanged();
            }
        }
        dataList=new ArrayList<>();
        dataList2=new ArrayList<>();
        dataList3=new ArrayList<>();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_photo:

                Intent intent = new Intent(DescriptionPlant1.this, UploadImage.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                Intent intent2 = new Intent(DescriptionPlant1.this, Settings.class);
                startActivity(intent2);

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}





/*package com.example.ecommerce;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBarDrawerToggle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;

        import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.Homepage);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.Homepage:
                Intent intent1=new Intent(Retro.this,MainActivity.this);
                startActivity(intent1);
                break;

            case R.id.nav_fruits:

                Intent intent = new Intent(Retro.this, Fruits.class);
                startActivity(intent);
                break;
            case R.id.nav_veg:
                Intent intent2 = new Intent(Retro.this, Vegetable.class);
                startActivity(intent2);

                break;
            case R.id.nav_leaf_veg:
                Intent intent3 = new Intent(Retro.this, Lvegetable.class);
                startActivity(intent3);

                break;
            case R.id.nav_brand:
                Intent intent4 = new Intent(Retro.this, Brand.class);
                startActivity(intent4);
                break;

            case R.id.nav_heera:
                Intent intent5 = new Intent(Retro.this, Heera.class);
                startActivity(intent5);
                break;
            case R.id.nav_retro:

                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.id_cart) {
            Intent intentprofile = new Intent(MainActivity.this , cart.class);
            startActivity(intentprofile);
            return true;
        }
        if(id == R.id.id_login){
            Intent intentsettings = new Intent(MainActivity.this , Login.class);
            startActivity(intentsettings);
            return true;
        }
        if(id == R.id.id_register){
            Intent intentdownload = new Intent(MainActivity.this , Register.class);
            startActivity(intentdownload);
            return true;
        }
        return false;
    }*/
