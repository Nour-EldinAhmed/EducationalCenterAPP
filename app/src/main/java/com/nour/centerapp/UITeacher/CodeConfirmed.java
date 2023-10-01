package com.nour.centerapp.UITeacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nour.centerapp.DashBoard;
import com.nour.centerapp.Note.DashBoardStd;
import com.nour.centerapp.PreferenceManger;
import com.nour.centerapp.databinding.ActivityCodeConfirmedBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class CodeConfirmed extends AppCompatActivity {

    ActivityCodeConfirmedBinding binding;
    PreferenceManger manger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCodeConfirmedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manger=new PreferenceManger(this);
        binding.btnSigningoconfirm.setOnClickListener(v -> {
            if(binding.inputcodeconfirm.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this, "Enter Your Code", Toast.LENGTH_LONG).show();

            }else{
                ConfirmCode();
            }

        });
    }

    private void ConfirmCode()
    {
        binding.btnSigningoconfirm.setVisibility(INVISIBLE);
        binding.progresscodegoconfirm.setVisibility(VISIBLE);
        FirebaseFirestore firebase=FirebaseFirestore.getInstance();
        firebase.collection("Code")
                .whereEqualTo("codenumber",binding.inputcodeconfirm.getText().toString())
                .get()
                .addOnCompleteListener(task ->{
                    if(task.isSuccessful() && task.getResult()!=null && task.getResult().getDocuments().size()>0)
                    {
                        DocumentSnapshot document=task.getResult().getDocuments().get(0);
                        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
                        manger.PutBoolean("coded",true);
                        manger.PutSting("codenumber",document.getString("codenumber"));
                        Toast.makeText(this, "Code Confirmed", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getBaseContext(), DashBoardStd.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show();
                    }
                });

        }


    }

