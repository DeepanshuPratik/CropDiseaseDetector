package com.example.cropdoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UploadImageMain extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    private ImageView imageViewAdd2,headImg2;
    private Button uploadImg;
    TextView heading2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image_main);
        imageViewAdd2 = findViewById(R.id.imageViewAdd2);
        uploadImg=findViewById(R.id.uploadImg);
        heading2=findViewById(R.id.Header2);
        headImg2=findViewById(R.id.Head_pic2);


        Intent i=getIntent();
        String scrn=i.getExtras().getString("Cropname","Crop");
        heading2.setText(scrn);
        String url=(i.getExtras().getString("url"));


        if(scrn.equals("paddy"))
            Picasso.get().load(url).into(headImg2);
        else if(scrn.equals("wheat"))
            Picasso.get().load(url).into(headImg2);
        else if(scrn.equals("maize"))
            Picasso.get().load(url).into(headImg2);
        else
            Picasso.get().load(url).into(headImg2);


        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UploadImageMain.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(UploadImageMain.this, "Please give permission for accessing storage", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(UploadImageMain.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                }
                else {

                    showFileChooser();
                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission storage", Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null) {
            Uri filepath = data.getData();
            Bitmap takenImage = null;
            try {
                takenImage = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageViewAdd2.setImageBitmap(takenImage);
        }
    }




    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }
}