package com.nour.centerapp.Uploading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nour.centerapp.R;
import com.nour.centerapp.databinding.ActivityImageBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity implements ImageAdapter.onItemClickListener {

    ActivityImageBinding binding;
    private ImageAdapter adapter;
    private DatabaseReference reference;
    private List<Uploading> listImages;
    private FirebaseStorage mStorage;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listImages = new ArrayList<Uploading>();
        adapter = new ImageAdapter(getApplicationContext(), listImages);
        binding.recyclerImages.setAdapter(adapter);
        adapter.setOnItemClickListener(ImageActivity.this);
        binding.recyclerImages.setHasFixedSize(true);
        binding.recyclerImages.setLayoutManager(new LinearLayoutManager(this));
        mStorage = FirebaseStorage.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Uploads");
        listener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listImages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Uploading upload = dataSnapshot.getValue(Uploading.class);
                    upload.setMkey(dataSnapshot.getKey());
                    listImages.add(upload);
                }

                adapter.notifyDataSetChanged();
                binding.progressImagesUpload.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ImageActivity.this, "Error" + error.getMessage(), Toast.LENGTH_LONG).show();
                binding.progressImagesUpload.setVisibility(View.INVISIBLE);


            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Position : " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteClick(int position) {

        Uploading selectitem = listImages.get(position);
        String selectKey = selectitem.getMkey();
        StorageReference mStorageReference = mStorage.getReferenceFromUrl(selectitem.getImageUrl());
        mStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reference.child(selectKey).removeValue();
                Toast.makeText(ImageActivity.this, "Item Delete", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onWheteeverClick(int position) {
        Toast.makeText(this, "Position : " + position, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reference.removeEventListener(listener);
    }
}