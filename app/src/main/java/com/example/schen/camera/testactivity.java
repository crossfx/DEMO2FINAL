package com.example.schen.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.schen.camera.Adapter.PostListActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class testactivity extends AppCompatActivity {
    //Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private static final String SCHEME_FIREBASE_STORAGE = "gs";

    public boolean canHandleRequest(Request data) {
        String scheme = data.uri.getScheme();
        return (SCHEME_FIREBASE_STORAGE.equals(scheme) );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testactivity);
        final EditText descptext;
        Button testbtn;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Photo details");
        storage.getReference();

        testbtn = findViewById(R.id.savebutton);

        descptext = findViewById(R.id.descriptiontextfinal);

        final File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        final File storageDir2 = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        final int numOfFiles = storageDir1.listFiles().length;
        final File[] LoF = storageDir1.listFiles();
        final File fileloc = LoF[numOfFiles-1];

        Uri filepath = Uri.fromFile(fileloc);

        final StorageReference uploadlocation = storage.getReference(filepath.getLastPathSegment());
        final String userID = FirebaseAuth.getInstance().getUid();
        final String filename=filepath.getLastPathSegment();
        UploadTask uploadTask = uploadlocation.putFile(filepath);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(),"Not working boss",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Wait for image to load",Toast.LENGTH_LONG).show();
                StorageReference imageloc = storageRef.child(filename);
                imageloc.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageView iv1 = findViewById(R.id.annotateimage);
                        String blah= uri.toString();
                        Picasso.get().load(String.valueOf(blah)).into(iv1, new Callback() {
                            @Override
                            public void onSuccess() {
                                ProgressBar progressBar1 = findViewById(R.id.progress);
                                progressBar1.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        });

        File dir = new File(String.valueOf(storageDir1));
        dir.mkdirs();

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(testactivity.this,PostListActivity.class);
                final String titlemessage = descptext.getText().toString();
                final HashMap <String, String> dataMap = new HashMap<String,String>();

                File file = new File(storageDir2+"/titledescp.txt");
                String [] saveText = String.valueOf(titlemessage).split(System.getProperty("line.separator"));

                descptext.setText("");

                Save (file,saveText);

                StorageReference imageloc = storageRef.child(filename);
                imageloc.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageView iv1 = findViewById(R.id.annotateimage);
                        String imageurl= uri.toString();
                        dataMap.put("description", (titlemessage));
                        dataMap.put("image", imageurl);
                        dataMap.put("uid",userID.toString());
                        mDatabase.push().setValue(dataMap);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

                startActivity(intent);

            }

                public void Save(File file, String[] data)
                {
                    OutputStream fos= null;
                    try {
                        fos = new FileOutputStream(file , true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    OutputStreamWriter out = new OutputStreamWriter(fos);
                    try {

                        out.write(data[0]);
                        out.write("\n");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            });

        }

    public static class ReadFile
    {
        public String[] readLines(String filename) throws IOException
        {
            FileReader fileReader = new FileReader(filename);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> lines = new ArrayList<String>();
            String line = null;

            while ((line = bufferedReader.readLine()) != null)
            {
                lines.add(line);
            }

            bufferedReader.close();

            return lines.toArray(new String[lines.size()]);
        }
    }



}


