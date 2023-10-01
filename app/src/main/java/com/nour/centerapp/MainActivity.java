package com.nour.centerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nour.centerapp.UITeacher.SignINTeacher;
import com.nour.centerapp.UiStudent.SigninStd;
import com.nour.centerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTeacher.setOnClickListener(v -> {
            Intent intent =new Intent(getBaseContext(), SignINTeacher.class);
            startActivity(intent);
        });
        binding.btnStudent.setOnClickListener(v -> {
            Intent intent =new Intent(getBaseContext(), SigninStd.class);
            startActivity(intent);
        });

    }
}