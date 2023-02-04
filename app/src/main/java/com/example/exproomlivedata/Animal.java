package com.example.exproomlivedata;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Animal {
    @NonNull
    @PrimaryKey
    public String code;
    @ColumnInfo(name = "nom")
    public String nom;
    @ColumnInfo(name = "photo")
    public byte[] photo;
    public Animal(String code,String nom, byte[] photo) {
        this.code=code;
        this.nom = nom;
        this.photo = photo;
    }
}
