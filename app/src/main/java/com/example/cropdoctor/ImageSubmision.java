package com.example.cropdoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageSubmision extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int REQUEST_CODE_IMAGE = 100;
    private ImageView imageViewAdd,headImg;
    private  String currentPhotoPath;

    private Button btnTakeImg;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_submision);
        imageViewAdd = findViewById(R.id.imageViewAdd);
        btnTakeImg=findViewById(R.id.takeImage);
        heading=findViewById(R.id.Header);
        headImg=findViewById(R.id.Head_pic);
        Intent i=getIntent();
        String scrn=i.getExtras().getString("Cropname","Crop");
        heading.setText(scrn);
        String url=(i.getExtras().getString("url"));

        dispatchTakePictureIntent();
        if(scrn.equals("paddy"))
            Picasso.get().load(url).into(headImg);
        else if(scrn.equals("wheat"))
            Picasso.get().load(url).into(headImg);
        else if(scrn.equals("maize"))
            Picasso.get().load(url).into(headImg);
        else
            Picasso.get().load(url).into(headImg);



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode== Activity.RESULT_OK) {
            File f=new File(currentPhotoPath);

            imageViewAdd.
                    setImageURI(Uri.fromFile(f));
        }


    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.cropdoctor.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE);
            }
        }
    }


    public void takeimage(View view) {
        dispatchTakePictureIntent();
    }

    public void openChooser(View view) {
        dispatchTakePictureIntent();
    }
}