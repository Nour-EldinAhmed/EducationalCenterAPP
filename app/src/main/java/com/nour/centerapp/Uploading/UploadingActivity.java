package com.nour.centerapp.Uploading;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.nour.centerapp.R;
import com.nour.centerapp.databinding.ActivityUploadingBinding;
import com.squareup.picasso.Picasso;

public class UploadingActivity extends AppCompatActivity {

    ActivityUploadingBinding binding;
    private static final int PIC_IMAGE_REQUEST = 1;
    Uri uriImage;
    private StorageReference storageReference;
    private DatabaseReference reference;
    private StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        reference = FirebaseDatabase.getInstance().getReference("Uploads");

        binding.btnChoose.setOnClickListener(v -> {
            OpenFile();
        });

        binding.btnUpload.setOnClickListener(v -> {
            if (storageTask != null && storageTask.isInProgress()) {
                Toast.makeText(this, "Uploading in Progress", Toast.LENGTH_SHORT).show();
            } else {
                UploadFile();
            }
        });
        binding.showUpload.setOnClickListener(v -> {
            OpenImageActivity();

        });

    }

    private void OpenImageActivity() {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //Methods to Extension Image (.jpg,.png,...........)
    private String ExtensionFile(Uri uri) {
        ContentResolver Cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(Cr.getType(uri));
    }


    private void UploadFile() {

        if (uriImage != null) {
            StorageReference fileReference = storageReference.
                    child(System.currentTimeMillis() + "." + ExtensionFile(uriImage));

            storageTask = fileReference.putFile(uriImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    binding.progressUpload.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(UploadingActivity.this, "Upload Success", Toast.LENGTH_LONG).show();

                            Uploading upload = new Uploading(binding.editFile.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString()
                            );
                            String id = reference.push().getKey();
                            reference.child(id).setValue(upload);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(UploadingActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            binding.progressUpload.setProgress((int) progress);

                        }
                    });

        } else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            Picasso.with(this).load(uriImage).into(binding.imgUpload);
        }

    }

    private void OpenFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PIC_IMAGE_REQUEST);
    }


}