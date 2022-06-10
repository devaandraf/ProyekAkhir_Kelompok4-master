package com.example.proyekakhir_kelompok4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddBookActivity extends AppCompatActivity {

    EditText addJudul, addGenre, addPenulis, addImgUrl;
    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        addJudul = findViewById(R.id.et_add_judul);
        addGenre = findViewById(R.id.et_add_genre);
        addPenulis = findViewById(R.id.et_add_penulis);
        addImgUrl = findViewById(R.id.et_add_image);
        btnAdd = findViewById(R.id.btn_add);
//        btnBack = findViewById(R.id.btn_back);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBuku();
                clearAll();
            }
        });

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    private void insertBuku()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("bookurl", addImgUrl.getText().toString());
        map.put("genre", addGenre.getText().toString());
        map.put("judul", addJudul.getText().toString());
        map.put("penulis", addPenulis.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("buku").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddBookActivity.this, "Data Ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Toast.makeText(AddBookActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearAll()
    {
        addJudul.setText("");
        addPenulis.setText("");
        addGenre.setText("");
        addImgUrl.setText("");
    }
}