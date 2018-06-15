package com.example.schen.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.schen.camera.Adapter.EasyAdapter;
import com.example.schen.camera.Adapter.MyData;
import com.google.android.gms.common.internal.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView mListView;
    EasyAdapter mAdapter;
    List<MyData> mDataList = new ArrayList<>();
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button opencamBTN = findViewById(R.id.opencambtn);
        Button signoutBTN = findViewById(R.id.signoutbtn);

        firebaseAuth = FirebaseAuth.getInstance();

        opencamBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });

        signoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();

                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });


        mListView = findViewById(R.id.listView1);

        try {
            generateData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String mCurrentPhotoPath;

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".png",storageDir);
        //int numOfFiles = (int) storageDir.listFiles().length;

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
//        updateDataSet();
        return image;

    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent passpictureintent = new Intent(MainActivity.this,testactivity.class);
        startActivity(passpictureintent);
    }

    public void generateData() throws IOException {
        File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        int numOfFiles = storageDir1.listFiles().length;
        File[] LoF = storageDir1.listFiles();
        testactivity.ReadFile rf = new testactivity.ReadFile();
        String filename ="/storage/emulated/0/Android/data/com.example.schen.camera/files/Documents/titledescp.txt";
        String listtitlename[]=rf.readLines(filename);


            for (int i = 0;i<numOfFiles;i++){
                String imagefilpath = LoF[i].getAbsolutePath();
                String titlename = listtitlename[i];
                mDataList.add(new MyData(imagefilpath,titlename));
            }

        mAdapter = new EasyAdapter(this, mDataList);
        //Set the adapter with MyData list to the listView;
        mListView.setAdapter(mAdapter);
    }


}
