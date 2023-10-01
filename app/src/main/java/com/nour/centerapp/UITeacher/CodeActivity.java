package com.nour.centerapp.UITeacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.nour.centerapp.DashBoard;
import com.nour.centerapp.PreferenceManger;
import com.nour.centerapp.databinding.ActivityCodeBinding;

import java.util.HashMap;

public class CodeActivity extends AppCompatActivity {

    ActivityCodeBinding binding;
    PreferenceManger manger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manger=new PreferenceManger(this);
        binding.btnSigningo.setOnClickListener(v -> {
          if(binding.inputcode.getText().toString().trim().isEmpty())
          {
              Toast.makeText(this, "Enter Your Code", Toast.LENGTH_LONG).show();

          }else{
              TeacherCode();
          }

        });
    }

    private void TeacherCode()
    {
        binding.btnSigningo.setVisibility(View.INVISIBLE);
        binding.progresscodego.setVisibility(View.VISIBLE);
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        HashMap<String,Object> code=new HashMap<String,Object>();
        code.put("codenumber",binding.inputcode.getText().toString());
        firestore.collection("Code").add(code)
                .addOnSuccessListener(documentReference -> {
                    manger.PutBoolean("coded",true);
                    manger.PutSting("codenumber",binding.inputcode.getText().toString());

                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(getBaseContext(), DashBoard.class);

                    startActivity(intent);

                })
                .addOnFailureListener(documentReference ->
                        {
                            Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show();

                        }
                );
    }

}