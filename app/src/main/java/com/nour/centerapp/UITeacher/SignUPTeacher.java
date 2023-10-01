package com.nour.centerapp.UITeacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.nour.centerapp.PreferenceManger;
import com.nour.centerapp.databinding.ActivitySignUPTeacherBinding;

import java.util.HashMap;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SignUPTeacher extends AppCompatActivity {

    ActivitySignUPTeacherBinding binding;
    PreferenceManger preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUPTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgback.setOnClickListener(v -> onBackPressed());
        preference=new PreferenceManger(this);
        binding.btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(binding.inputusername.getText().toString().trim().isEmpty())
                {
                    binding.inputusername.setError("Enter Your Name");
                    Toast.makeText(SignUPTeacher.this, "Enter Yur Name", Toast.LENGTH_SHORT).show();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputemail.getText().toString()).matches())
                {
                    binding.inputemail.setError("Enter Vaild E-mail");
                    Toast.makeText(SignUPTeacher.this, "Enter Vaild E-mail", Toast.LENGTH_SHORT).show();
                }else if(binding.inputpassword.getText().toString().trim().isEmpty())
                {
                    binding.inputpassword.setError("Enter Your Password");
                    Toast.makeText(SignUPTeacher.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                }else if(binding.inputsubjectname.getText().toString().trim().isEmpty())
                {
                    binding.inputsubjectname.setError("Enter Your Subject");
                    Toast.makeText(SignUPTeacher.this, "Enter Your Subject", Toast.LENGTH_SHORT).show();
                }
                else{
                    SignUp();
                }

            }
        });
    }

    private void SignUp()
    {
        binding.btnSignup.setVisibility(INVISIBLE);
        binding.progressSignup.setVisibility(VISIBLE);
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        HashMap<String,Object> teacher=new HashMap<String,Object>();
        teacher.put("firstnameteacher",binding.inputusername.getText().toString());
        teacher.put("emailteacher",binding.inputemail.getText().toString());
        teacher.put("passwordteacher",binding.inputpassword.getText().toString());
        teacher.put("subjectname",binding.inputsubjectname.getText().toString());
        firestore.collection("Teacher")
                .add(teacher)
                .addOnSuccessListener(documentReference -> {
                    preference.PutBoolean("isSignin",true);
                    preference.PutSting("firstnameteacher",binding.inputusername.getText().toString());
                    preference.PutSting("emailteacher",binding.inputemail.getText().toString());
                    preference.PutSting("passwordteacher",binding.inputpassword.getText().toString());
                    preference.PutSting("subjectname",binding.inputsubjectname.getText().toString());
                    Intent intent=new Intent(getApplicationContext(), CodeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                })
                .addOnFailureListener(documentReference ->{
                    binding.btnSignup.setVisibility(INVISIBLE);
                    binding.progressSignup.setVisibility(VISIBLE);
                    Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();

                });


    }
}