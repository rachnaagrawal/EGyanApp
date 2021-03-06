package com.example.android.e_gyanapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadPdfActivity extends AppCompatActivity implements View.OnClickListener {

    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;
    private ProgressDialog dialog ;

    //these are the views
    TextView textViewStatus;
    EditText editTextFilename,editTextAuthorname,editTextDescription;
   // ProgressBar progressBar;

    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);


        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //getting the views
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextFilename = (EditText) findViewById(R.id.editTextFileName);
        editTextAuthorname = (EditText) findViewById(R.id.editTextAuthorName);
        editTextDescription = (EditText) findViewById(R.id.editTextDesc);

        //progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //attaching listeners to views
        findViewById(R.id.buttonUploadFile).setOnClickListener(this);
        findViewById(R.id.textViewUploads).setOnClickListener(this);
    }

    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        //Uri data = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(final Uri data) {
       // progressBar.setVisibility(View.VISIBLE);
        final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    // @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //progressBar.setVisibility(View.GONE);
                        textViewStatus.setText("File Uploaded Successfully");

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressLint("ResourceType")
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        final double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");
                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog = new ProgressDialog(UploadPdfActivity.this);
                                dialog.setTitle("Uploading Files");
                                dialog.setProgress(0);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setMax(100);
                                dialog.show();
                            }
                        });
                       // textViewStatus.setText((int) progress + "% Uploading...");
                       new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (dialog.getProgress() <= dialog.getMax()) {

                                        Thread.sleep(100);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.setMessage((int)progress + "% Uploading");
                                                dialog.setProgress((int)progress);

                                                if (dialog.getProgress() == dialog.getMax()) {
                                                    dialog.dismiss();
                                                }
                                            }
                                        });
                                        }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();*/
                    }
                    })


        .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return sRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downUri = task.getResult();
                    //Log.d(TAG, "onComplete: Url: "+ downUri.toString());

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();

                    String name=pref.getString("name", null);
                    String author=pref.getString("author", null);
                    String descr=pref.getString("descr", null);
                    String iurl=pref.getString("iurl", null);


                    Upload upload = new Upload(name,author,descr,iurl, downUri.toString());
                    mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonUploadFile:
                getPDF();
                break;
            case R.id.textViewUploads:
                startActivity(new Intent(this, ViewUploadsActivity.class));
                break;
        }
    }
}