package com.nour.centerapp.UiStudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nour.centerapp.Contains;
import com.nour.centerapp.MainActivity;
import com.nour.centerapp.PreferenceManger;
import com.nour.centerapp.databinding.ActivitySignUpStudentBinding;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpStudent extends AppCompatActivity {

    ActivitySignUpStudentBinding binding;
    private FirebaseAuth mAuth;
    PreferenceManger preferenceManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManger = new PreferenceManger(this);
        mAuth = FirebaseAuth.getInstance();
        binding.imgbackStd.setOnClickListener(v -> onBackPressed());
        binding.btnSignupstd.setOnClickListener(v -> {
            if (binding.inputusernamestd.getText().toString().trim().isEmpty()) {
                binding.inputusernamestd.setError("Enter Your Name");
                Toast.makeText(getBaseContext(), "Enter Your Name ", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputemailstd.getText().toString().trim()).matches()) {
                binding.inputusernamestd.setError("Enter Vaild E-mail");
                Toast.makeText(getApplicationContext(), "Enter Your Vaild E-mail", Toast.LENGTH_SHORT).show();
            } else if (binding.inputpasswordstd.getText().toString().trim().isEmpty()) {
                binding.inputusernamestd.setError("Enter Your Password");
                Toast.makeText(getBaseContext(), "Enter Your Password ", Toast.LENGTH_SHORT).show();
            }  else {
                SignUP();
            }

        });




    }


    private void SignUP() {
        binding.btnSignupstd.setVisibility(View.INVISIBLE);
        binding.progressSignupstd.setVisibility(View.VISIBLE);
        FirebaseFirestore firebase = FirebaseFirestore.getInstance();
        HashMap<String, Object> students = new HashMap<>();
        students.put(Contains.KEY_NAME, binding.inputusernamestd.getText().toString());
        students.put(Contains.KEY_EMAIL, binding.inputemailstd.getText().toString());
        students.put(Contains.KEY_PASSWORD, binding.inputpasswordstd.getText().toString());
       // students.put("codenumber", binding.codestdup.getText().toString());

        // Path of Your Data
        firebase.collection(Contains.KEY_STUDENT)
                .add(students)
                .addOnSuccessListener(documentReference -> {
                    preferenceManger.PutBoolean(Contains.KEY_SIGNUP_STD, true);
                    preferenceManger.PutSting(Contains.KEY_NAME, binding.inputusernamestd.getText().toString());
                    preferenceManger.PutSting(Contains.KEY_EMAIL, binding.inputemailstd.getText().toString());
                    preferenceManger.PutSting(Contains.KEY_PASSWORD, binding.inputpasswordstd.getText().toString());
                   // preferenceManger.PutSting("codenumber", binding.codestdup.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(documentReference -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    binding.btnSignupstd.setVisibility(View.VISIBLE);
                    binding.progressSignupstd.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();


                });


    }
}