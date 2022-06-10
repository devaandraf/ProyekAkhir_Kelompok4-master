package com.example.proyekakhir_kelompok4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.proyekakhir_kelompok4.adapter.BukuAdapter;
import com.example.proyekakhir_kelompok4.model.BukuModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class BookListActivity extends AppCompatActivity {

    RecyclerView rvBooklist;
    BukuAdapter bukuAdapter;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        rvBooklist = findViewById(R.id.rv_list_buku);
        rvBooklist.setLayoutManager(new LinearLayoutManager(this));
        rvBooklist.setHasFixedSize(true);

        FirebaseRecyclerOptions<BukuModel> options =
                new FirebaseRecyclerOptions.Builder<BukuModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("buku"), BukuModel.class)
                        .build();

        bukuAdapter = new BukuAdapter(options);
        rvBooklist.setAdapter(bukuAdapter);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddBookActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        rvBooklist.getRecycledViewPool().clear();
        bukuAdapter.notifyDataSetChanged();
        bukuAdapter.startListening();
//        bukuAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bukuAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void textSearch(String string)
    {
        FirebaseRecyclerOptions<BukuModel> options =
                new FirebaseRecyclerOptions.Builder<BukuModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("buku")
                                .orderByChild("judul").startAt(string).endAt(string + "~"), BukuModel.class)
                        .build();

        bukuAdapter = new BukuAdapter(options);
        bukuAdapter.startListening();
        rvBooklist.setAdapter(bukuAdapter);
    }
}