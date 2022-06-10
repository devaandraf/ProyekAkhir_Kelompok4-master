package com.example.proyekakhir_kelompok4.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyekakhir_kelompok4.R;
import com.example.proyekakhir_kelompok4.model.BukuModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class BukuAdapter extends FirebaseRecyclerAdapter<BukuModel, BukuAdapter.bukuViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BukuAdapter(@NonNull FirebaseRecyclerOptions<BukuModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull bukuViewHolder holder, final int position, @NonNull BukuModel model) {

        holder.tvJudul.setText(model.getJudul());
        holder.tvGenre.setText(model.getGenre());
        holder.tvPenulis.setText(model.getPenulis());

        Glide.with(holder.imgBuku.getContext())
                .load(model.getBookurl())
                .placeholder(R.drawable.ic_launcher_foreground)
//                .circleCrop()
                .override(60, 60)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgBuku);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imgBuku.getContext())
                        .setContentHolder(new ViewHolder(R.layout.popup_edit))
                        .setExpanded(true, 900)
                        .create();

//                dialogPlus.show();

                View view2 = dialogPlus.getHolderView();
                EditText editJudul = view2.findViewById(R.id.et_edit_judul);
                EditText editGenre = view2.findViewById(R.id.et_edit_genre);
                EditText editPenulis = view2.findViewById(R.id.et_edit_penulis);
                EditText editImgUrl = view2.findViewById(R.id.et_edit_image);
                Button btnUpdate = view2.findViewById(R.id.btn_update);

                editJudul.setText(model.getJudul());
                editGenre.setText(model.getGenre());
                editPenulis.setText(model.getPenulis());
                editImgUrl.setText(model.getBookurl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("judul", editJudul.getText().toString());
                        map.put("genre", editGenre.getText().toString());
                        map.put("penulis", editPenulis.getText().toString());
                        map.put("bookurl", editImgUrl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("buku")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.tvJudul.getContext(), "Data Berhasil di Update", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.tvJudul.getContext(), "Error", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.tvJudul.getContext());
                        builder.setTitle("Hapus Data ?");
                        builder.setMessage("Hapus Data Tidak Dapat Di Undo.");

                        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase.getInstance().getReference().child("buku")
                                        .child(getRef(position).getKey()).removeValue();

                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(holder.tvJudul.getContext(), "Cancelled.", Toast.LENGTH_SHORT).show();

                            }
                        });

                        builder.show();
                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public bukuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new bukuViewHolder(view);
    }

    class bukuViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imgBuku;
        TextView tvJudul, tvGenre, tvPenulis;
        Button btnEdit, btnDelete;

        public bukuViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBuku = itemView.findViewById(R.id.img_buku);
            tvJudul = itemView.findViewById(R.id.tv_judul_buku);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            tvPenulis = itemView.findViewById(R.id.tv_penulis);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
