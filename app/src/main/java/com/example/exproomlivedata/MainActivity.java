package com.example.exproomlivedata;
/*
    implementation "androidx.lifecycle:lifecycle-viewmodel:2.5.1"
    implementation "androidx.lifecycle:lifecycle-livedata:2.5.1"
 */
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
implements CustomAdapter.OnItemListener{
    RecyclerView RV;
    ImageView img;
    EditText txtCode, txtNom;
    Button btnP, btnE;
    byte[] byteArray;
    AnimalViewModel animalViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);
        RV = (RecyclerView) findViewById(R.id.RV);
        img = findViewById(R.id.img);
        txtCode = findViewById(R.id.txtCode);
        txtNom = findViewById(R.id.txtNom);
        btnE = findViewById(R.id.btnEnregistrer);
        btnP = findViewById(R.id.btnParcourir);

        //Affichage de la liste des animaux à l'ouverture de l'activité
        //Cette liste sera automatiquement actualisée à chaque ajout d'animal
        CustomAdapter CA = new CustomAdapter(this, this);
        LinearLayoutManager LLM = new LinearLayoutManager(this);
        RV.setLayoutManager(LLM);
        RV.setAdapter(CA);

        //Ici à chaque changement de la liste getAllAnimals qui est LiveData
        // La liste associée au CustomAdapter sera actualisée

        animalViewModel.getAllAnimals().observe(this,
                new Observer<List<Animal>>() {
            @Override
            public void onChanged(List<Animal> animals)
            {

                CA.setLst(animals);
            }

        });

      //Bouton parcouir pour choisir une image
        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itt = new Intent(Intent.ACTION_PICK);
                itt.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                resultLaucher.launch(itt);
            }
        });
        //Bouton enregistrer
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animal A = new Animal(txtCode.getText().toString(), txtNom.getText().toString(), byteArray);
                //Appel de la méthode insert du viewModel
                animalViewModel.insert(A);
            }
        });

    }
//récupération de l'image choisie
    ActivityResultLauncher<Intent> resultLaucher
            = registerForActivityResult
            (
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Uri u = result.getData().getData();
                                //    img.setImageURI(u);
                                try {
                                    //Récupérer un objet Bitmap à partir un uri
                                    Bitmap BmpImg = MediaStore.Images.Media.getBitmap(getContentResolver(), u);
                                    //On peut aussi afficher sous format Bitmap
                                    img.setImageBitmap(BmpImg);
                                    //Conversion d'un Bitmap en Bits
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    BmpImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    byteArray = stream.toByteArray();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );

    @Override
    public void onClick(Animal animal) {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage("Etes-vous sûr de vouloir supprimer cet animal")
                        .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                animalViewModel.delete(animal);
                            }
                        })
                                .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                        .setTitle("Confirmation")
                .show();


    }
    //IL faut faire des modifications au niveau de l'adapter
    //de manière à ce que une fois on a cliqué sur le
    //itemview on appelle ce click
}