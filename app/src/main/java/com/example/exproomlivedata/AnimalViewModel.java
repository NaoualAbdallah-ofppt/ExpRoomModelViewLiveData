package com.example.exproomlivedata;

import android.app.Application;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AnimalViewModel
extends AndroidViewModel
{
    private AnimalRepository repository;

    public AnimalViewModel(@NonNull Application application) {
        super(application);
        repository= new AnimalRepository(application);
      }
    public void insert(Animal ... animals) {

        repository.insert(animals);
    }
    public void delete(Animal animal) {
        repository.delete(animal);
    }
    public LiveData<List<Animal>> getAllAnimals() {
        return repository.getAllAnimals();
    }

}
