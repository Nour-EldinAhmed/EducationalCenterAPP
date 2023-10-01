package com.nour.centerapp.Uploading;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nour.centerapp.R;
import com.nour.centerapp.databinding.ActivityUploadFileBinding;

public class UploadFileActivity extends AppCompatActivity {

    ActivityUploadFileBinding binding;
    DatabaseReference reference;
    StorageReference storageReference;
    private static final int PDF_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadFileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storageReference = FirebaseStorage.getInstance().getReference("UploadFile");
        reference = FirebaseDatabase.getInstance().getReference("UploadFile");
        binding.btnUploadFile.setEnabled(false);

        binding.editFile.setOnClickListener(v -> {
            SelectFile();
        });


        binding.btnRetriveFile.setOnClickListener(v -> {
            Intent intent = new Intent(this,RetriveFileActivity.class);
            startActivity(intent);
        });

    }

    private void SelectFile() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pdf File Selected"), PDF_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            binding.btnUploadFile.setEnabled(true);
            binding.editFile.setText(data.getDataString().
                    substring(data.getDataString().lastIndexOf("/") + 1));
            binding.btnUploadFile.setOnClickListener(v -> {
                UploadFileinFireBase(data.getData());
            });
        }
    }

    private void UploadFileinFireBase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is Loading .....");
        progressDialog.show();
        StorageReference filereference = storageReference.child(System.currentTimeMillis() + ".pdf");
        filereference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();

                        while (!task.isComplete()) ;
                        Uri uriData = task.getResult();


                        UploadFile uploadFile = new UploadFile(binding.editFile.getText().toString()
                                , uriData.toString());
                        reference.child(reference.push().getKey()).setValue(uploadFile);
                        Toast.makeText(UploadFileActivity.this, "Upload File Success", Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadFileActivity.this, "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("File Upload " + (int) progress + "%");

            }
        })

        ;


    }
}