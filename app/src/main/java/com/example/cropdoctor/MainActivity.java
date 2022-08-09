package com.example.cropdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
//import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private static final int REQUEST_CODE_IMAGE = 100;
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

        navigationView.setCheckedItem(R.id.nav_home);


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
            case R.id.nav_home:
                break;
            case R.id.nav_photo:

                Intent intent = new Intent(MainActivity.this, UploadImage.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                Intent intent2 = new Intent(MainActivity.this, Settings.class);
                startActivity(intent2);

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    public void wheatCrop(View view) {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Please give permission for taking Image", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_IMAGE);

        }
        else {


            Intent i = new Intent(MainActivity.this, ImageSubmision.class);
            i.putExtra("Cropname", "Wheat");
            startActivity(i);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == REQUEST_CODE_IMAGE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can take image", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission image", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void paddyCrop(View view) {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Please give permission for taking Image", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_IMAGE);

        }
        else {

            Intent i=new Intent(MainActivity.this,ImageSubmision.class);
            i.putExtra("Cropname","Paddy");
            startActivity(i);

        }

    }

    public void maizeCrop(View view) {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Please give permission for taking Image", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_IMAGE);

        }
        else {

            Intent i = new Intent(MainActivity.this, ImageSubmision.class);
            i.putExtra("Cropname", "Maize");
            startActivity(i);
        }
    }

    public void gramCrop(View view) {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Please give permission for taking Image", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_IMAGE);

        }
        else {

            Intent i = new Intent(MainActivity.this, ImageSubmision.class);
            i.putExtra("Cropname", "Gram");
            startActivity(i);
        }
    }
}