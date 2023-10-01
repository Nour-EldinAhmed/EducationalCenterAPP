package com.nour.centerapp.UiStudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nour.centerapp.UITeacher.CodeConfirmed;
import com.nour.centerapp.Contains;
import com.nour.centerapp.MainActivity;
import com.nour.centerapp.PreferenceManger;
import com.nour.centerapp.R;

public class SigninStd extends AppCompatActivity {

    //ActivitySignINStudentBinding binding;
    PreferenceManger manger;
    ImageView imgback;

    Button btnSignUPstd, btnSignInstd;
    EditText inputemailsigninstd,inputpasswordsigninstd;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding=ActivitySignINStudentBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_sign_i_n_student);
        imgback = findViewById(R.id.imgback);
        btnSignUPstd = findViewById(R.id.btnSignUPstd);
        btnSignInstd = findViewById(R.id.btnSignInstd);
        inputemailsigninstd = findViewById(R.id.inputemailsigninstd);
        inputpasswordsigninstd=findViewById(R.id.inputpasswordsigninstd);
        progressBar = findViewById(R.id.progressSignintae);
        manger = new PreferenceManger(this);
        if (manger.getDataBoolean(Contains.KEY_STUDENT)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        imgback.setOnClickListener(v -> onBackPressed());
        btnSignUPstd.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), SignUpStudent.class));
        });
        btnSignInstd.setOnClickListener(v -> {
            if (!Patterns.EMAIL_ADDRESS.matcher(inputemailsigninstd.getText().toString()).matches()) {
                inputemailsigninstd.setError("Enter Vaild E-mail");
                Toast.makeText(this, "Enter Vaild E-mail", Toast.LENGTH_SHORT).show();
            } else if (inputpasswordsigninstd.getText().toString().trim().isEmpty()) {
                inputpasswordsigninstd.setError("Enter Your Password");
                Toast.makeText(this, "Enter Your Password", Toast.LENGTH_SHORT).show();
            }  else {
                SignIn();
            }
        });

    }

    private void SignIn() {
        btnSignInstd.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore firebase = FirebaseFirestore.getInstance();
        firebase.collection(Contains.KEY_STUDENT)
                .whereEqualTo(Contains.KEY_EMAIL, inputemailsigninstd.getText().toString())
                .whereEqualTo(Contains.KEY_PASSWORD, inputpasswordsigninstd.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {

                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        Toast.makeText(this, " Sign in", Toast.LENGTH_SHORT).show();
                        manger.PutBoolean(Contains.KEY_SIGNUP_STD, true);
                        manger.PutSting(Contains.KEY_NAME, document.getString(Contains.KEY_NAME));
                        manger.PutSting(Contains.KEY_EMAIL, document.getString(Contains.KEY_EMAIL));
                        manger.PutSting(Contains.KEY_PASSWORD, document.getString(Contains.KEY_PASSWORD));

                        Intent intent = new Intent(getBaseContext(), CodeConfirmed.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        {
                            startActivity(intent);
                        }
                    } else {
                        //binding.btnSignInTea.setVisibility(View.INVISIBLE);
                        //binding.progressSignintae.setVisibility(View.VISIBLE);
                        btnSignInstd.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);

                        Toast.makeText(this, "Unable to Sign in", Toast.LENGTH_SHORT).show();
                        btnSignInstd.setVisibility(View.VISIBLE);

                    }
                });


    }
}