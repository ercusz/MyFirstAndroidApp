package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class StorageViewHolder extends RecyclerView.ViewHolder{
    public TextView id,username,title,description,st_username,st_password,date;

    public StorageViewHolder(View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.id);
        //username = itemView.findViewById(R.id.username);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        st_username = itemView.findViewById(R.id.st_username);
        st_password = itemView.findViewById(R.id.st_password);
        date = itemView.findViewById(R.id.date);
    }

}
public class StorageAdapter extends RecyclerView.Adapter<StorageViewHolder> {

    private Context context;
    private List<Storage> storages;

    public StorageAdapter(Context context, List<Storage> storages) {
        this.context = context;
        this.storages = storages;
    }

    @Override
    public StorageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.storage_items,parent,false);
        return new StorageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StorageViewHolder holder, int position) {
        holder.id.setText(Integer.toString(storages.get(position).getId()));
        //holder.username.setText(storages.get(position).getUsername());
        holder.title.setText(storages.get(position).getTitle());
        holder.description.setText(storages.get(position).getDescription());
        holder.st_username.setText("ชื่อผู้ใช้/อีเมล์: "+storages.get(position).getSt_username());
        holder.st_password.setText("รหัสผ่าน: "+storages.get(position).getSt_password());
        holder.date.setText("แก้ไขล่าสุดเมื่อ "+storages.get(position).getDate());

        final String getId = Integer.toString(storages.get(position).getId());
        final String getTiTle = storages.get(position).getTitle();
        final String getUsername = storages.get(position).getUsername();
        final String getSt_Username = storages.get(position).getSt_username();
        final String getSt_Password = storages.get(position).getSt_password();
        final String getDescription = storages.get(position).getDescription();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditPasswordActivity.class);
                intent.putExtra("id",getId);
                intent.putExtra("title",getTiTle);
                intent.putExtra("username",getUsername);
                intent.putExtra("st_username",getSt_Username);
                intent.putExtra("st_password",getSt_Password);
                intent.putExtra("description",getDescription);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storages.size();
    }
}
