package com.example.exproomlivedata;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.VH> {
    Context c;
    List<Animal> lst= new ArrayList<>();
    OnItemListener onItemListener;
    public CustomAdapter(Context c, OnItemListener onItemListener) {
    this.onItemListener =onItemListener;
        this.c = c;
    }
public void setLst(List<Animal> lst){
    this.lst=lst;
    notifyDataSetChanged();
}
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V=LayoutInflater.from(c).inflate(R.layout.layout_item,parent  ,false);
       return new VH (V, onItemListener);
    }
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Animal A =lst.get(position);
        holder.txtCode.setText(A.code);
        holder.txtNom.setText(A.nom);
        Bitmap bmp = BitmapFactory.decodeByteArray(A.photo,0,A.photo.length);
        holder.img.setImageBitmap(bmp);
    }
    @Override
    public int getItemCount() {
        return lst.size();
    }
    public class VH extends  RecyclerView.ViewHolder {
        private TextView txtNom;
        private TextView txtCode;
        private ImageView img;
        OnItemListener onItemListener;
        public VH(@NonNull View itemView,OnItemListener onItemListener ) {
            super(itemView);
            this.onItemListener=onItemListener;
            txtCode=(TextView) itemView.findViewById(R.id.txtCode);
            txtNom=(TextView) itemView.findViewById(R.id.txtNom);
            img=(ImageView) itemView.findViewById(R.id.img);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=getAdapterPosition();
               onItemListener.onClick(lst.get(position));
                // Log.i("aa","rr" + String.valueOf(position));

            }
        });
        }
    } //fin classe
    public interface  OnItemListener{

        void onClick(Animal a);
    }
}
