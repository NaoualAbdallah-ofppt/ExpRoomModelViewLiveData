package com.example.exproomlivedata;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AnimalDao {
        @Query("SELECT * FROM Animal")
        LiveData<List<Animal>> getAll();
        @Insert
        void insert(Animal... animal);
        @Delete
        void delete(Animal animal);
        @Update
        public void update(Animal animal);

}
