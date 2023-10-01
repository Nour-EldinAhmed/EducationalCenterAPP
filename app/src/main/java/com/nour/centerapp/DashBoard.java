package com.nour.centerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nour.centerapp.Note.NoteActivityKotlin;
import com.nour.centerapp.Uploading.UploadFileActivity;
import com.nour.centerapp.Uploading.UploadingActivity;
import com.nour.centerapp.Votes.VoteActivityKotlin;
import com.nour.centerapp.databinding.ActivityDashBoardBinding;

public class DashBoard extends AppCompatActivity {

    ActivityDashBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.layoutNote.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(), NoteActivityKotlin.class);
            startActivity(intent);
        });

        binding.file.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(), UploadingActivity.class);
            startActivity(intent);
        });
        binding.layoutFile.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(), UploadFileActivity.class);
            startActivity(intent);
        });



    }
}