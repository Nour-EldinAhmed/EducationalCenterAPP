package com.nour.centerapp.Uploading;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nour.centerapp.databinding.ActivityRetriveFileBinding;

import java.util.ArrayList;
import java.util.List;

public class RetriveFileActivity extends AppCompatActivity {

    ActivityRetriveFileBinding binding;
    List<UploadFile> listFiles;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRetriveFileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listFiles=new ArrayList<>();

        RetriveFilefromFireBase();

        binding.listFilesPdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UploadFile FilePDF=listFiles.get(position);

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(FilePDF.getUrl()));
                startActivity(intent);
            }
        });
    }

    private void RetriveFilefromFireBase() {

        reference= FirebaseDatabase.getInstance().getReference("UploadFile");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datSnapshot:snapshot.getChildren())
                {
                    UploadFile filepdf=  datSnapshot.getValue(UploadFile.class);
                    listFiles.add(filepdf);
                }

                String[] filesName=new String[listFiles.size()];
                for (int i=0;i<filesName.length;i++)
                {
                    filesName[i]=listFiles.get(i).getNameFile();
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,filesName)
                {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View v=super.getView(position, convertView, parent);
                        TextView textView=v.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return v;

                    }
                };

             binding.listFilesPdf.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}