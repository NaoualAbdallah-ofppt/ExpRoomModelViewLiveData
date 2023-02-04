package com.example.exproomlivedata;
import android.app.Application;
import android.os.AsyncTask;
import java.util.List;
import androidx.lifecycle.LiveData;
public class AnimalRepository {
    private AnimalDao animalDao;
    private LiveData<List<Animal>>  allAnimals;
    public AnimalRepository(Application app)
    {
        AnimalDatabase DB = AnimalDatabase.getInstance(app);
        animalDao= DB.animalDao();
        allAnimals= animalDao.getAll();
    }
    public void insert(Animal...animals)
    {
       new InsertAnimalsAsyncTask(animalDao).execute(animals);
    }
    public void delete(Animal animal)
    {
        new DeleteAnimalsAsyncTask(animalDao).execute(animal);

    }
    public  LiveData<List<Animal>> getAllAnimals()
    {
        return allAnimals;
    }

    private static class InsertAnimalsAsyncTask
            extends AsyncTask<Animal,Void,Void>
    {

        private AnimalDao animalDao;
        private  InsertAnimalsAsyncTask(AnimalDao animalDao)
        {
            this.animalDao=animalDao;
        }
        @Override
        protected Void doInBackground(Animal... animals) {
            animalDao.insert(animals);
            return null;
        }
    }
private static  class DeleteAnimalsAsyncTask
extends AsyncTask<Animal,Void,Void>
{
    private AnimalDao animalDao;
    private  DeleteAnimalsAsyncTask(AnimalDao animalDao)
    {
        this.animalDao=animalDao;
    }

    @Override
    protected Void doInBackground(Animal... animals) {
        //On veut un seul animal mais doInBackground
        //prend un tableau d'argument
        animalDao.delete(animals[0]);
        return null;
    }
}


}
