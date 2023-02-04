package com.example.exproomlivedata;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
//Si on ne déclare pas cette classe abstraite on sera obligé d'implémenter des méthodes
//de la classe mére même si on en a pas besoin. Il est donc recommandé de la rendre abstraite
@Database(entities={Animal.class}, version = 1)
public abstract class AnimalDatabase extends RoomDatabase {
    private static  AnimalDatabase instance;
    public abstract AnimalDao animalDao(); //pour avoir une référence à l'interface dao
    // a class-level lock such that only one thread will operate on the method
    public static synchronized AnimalDatabase getInstance(Context c) {
        // SingleTon one object
        // if (instance==null)
        instance= Room.databaseBuilder(c,AnimalDatabase.class,"AnimalsDb")
                .fallbackToDestructiveMigration()
              // .addCallback(call)
                .build();
        return  instance;
    }

}



