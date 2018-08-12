package com.dycode.edu.datasiswa;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Context mContext;
    public static final String EXTRA_SISWA = "EXTRA_MEMBER";
    public List<SiswaModel> arrayList = new ArrayList<>();

    public MainAdapter(Context mContext, List<SiswaModel> albumList) {
        this.mContext = mContext;
        this.arrayList = albumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.siswa_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final SiswaModel siswa = arrayList.get(position);
        holder.textName.setText(siswa.name);
        holder.textAddress.setText(siswa.address);

        Glide.with(holder.itemView.getContext())
                .load(siswa.pathPicture)
                .into(holder.imgProfile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ActionSiswaActivity.class);
                intent.putExtra(EXTRA_SISWA, arrayList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView textName;
        @BindView(R.id.img_profile)
        ImageView imgProfile;
        @BindView(R.id.txt_address)
        TextView textAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
