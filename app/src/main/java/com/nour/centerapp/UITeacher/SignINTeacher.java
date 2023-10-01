package com.nour.centerapp.UITeacher;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nour.centerapp.DashBoard;
import com.nour.centerapp.MainActivity;
import com.nour.centerapp.PreferenceManger;
import com.nour.centerapp.databinding.ActivitySignINTeacherBinding;

public class SignINTeacher extends AppCompatActivity {

    ActivitySignINTeacherBinding binding;
    PreferenceManger preferenceManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignINTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManger = new PreferenceManger(this);
        binding.imgback.setOnClickListener(v -> onBackPressed());
        if (preferenceManger.getDataBoolean("isSignin")) {
            Intent intent = new Intent(getApplicationContext(), DashBoard.class);
            startActivity(intent);
            finish();
        }
        binding.btnSignUPtea.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), SignUPTeacher.class));
        });
        binding.btnSignInTea.setOnClickListener(v -> {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputemailsignintea.getText().toString()).matches()) {
                binding.inputemailsignintea.setError("Enter Vaild E-mail");
                Toast.makeText(this, "Enter Vaild E-mail", Toast.LENGTH_SHORT).show();
            } else if (binding.inputpasswordsignintea.getText().toString().trim().isEmpty()) {
                binding.inputpasswordsignintea.setError("Enter Your Password");
                Toast.makeText(this, "Enter Your Password", Toast.LENGTH_SHORT).show();
            } else {
                SignIn();
            }
        });

    }

    private void SignIn() {
        binding.btnSignInTea.setVisibility(View.INVISIBLE);
        binding.progressSignintae.setVisibility(View.VISIBLE);
        FirebaseFirestore firebase = FirebaseFirestore.getInstance();
        firebase.collection("Teacher")
                .whereEqualTo("emailteacher", binding.inputemailsignintea.getText().toString())
                .whereEqualTo("passwordteacher", binding.inputpasswordsignintea.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {

                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        Toast.makeText(this, " Sign in", Toast.LENGTH_SHORT).show();
                        preferenceManger.PutBoolean("isSignin", true);
                        preferenceManger.PutSting("firstnameteacher", document.getString("firstnameteacher"));
                        preferenceManger.PutSting("emailteacher", document.getString("emailteacher"));
                        preferenceManger.PutSting("passwordteacher", document.getString("passwordteacher"));
                        preferenceManger.PutSting("subjectname", document.getString("subjectname"));
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        {
                            startActivity(intent);
                        }
                    } else {
                        binding.btnSignInTea.setVisibility(View.INVISIBLE);
                        binding.progressSignintae.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Unable to Sign in", Toast.LENGTH_LONG).show();
                        binding.btnSignInTea.setVisibility(View.VISIBLE);

                    }
                });


    }
}