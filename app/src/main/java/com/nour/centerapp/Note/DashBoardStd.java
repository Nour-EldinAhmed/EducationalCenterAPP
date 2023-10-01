package com.nour.centerapp.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nour.centerapp.R;
import com.nour.centerapp.Uploading.RetriveFileActivity;
import com.nour.centerapp.Votes.VoteActivityKotlin;
import com.nour.centerapp.databinding.ActivityDashBoardStdBinding;

public class DashBoardStd extends AppCompatActivity {

    ActivityDashBoardStdBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashBoardStdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.layoutNoteStd.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),NoteShowStd.class);
            startActivity(intent);
        });
        binding.pdf.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(), RetriveFileActivity.class);
            startActivity(intent);

        });

    }
}