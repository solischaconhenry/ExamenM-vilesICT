package cr.ac.itcr.examenict;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import DB.AnimalRepository;
import DB.IBD;
import Entity.Animal;

public class UpdateAnimal extends AppCompatActivity {

    TextView txtname; //variable para obtener el nombre actualizado
    TextView txtpopulation; // variable para obtener la población actualizada
    TextView txtweight;// variable para obtener el peso actualizado
    TextView txtcategory;// variable para obtener la categoria actualizada
    Button btnUpdList; // variable para obtener la acción del boton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_animal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inicializar los componentes para su uso
        txtname = (TextView)findViewById(R.id.txtAnimalNameU);
        txtpopulation = (TextView)findViewById(R.id.txtpopulationU);
        txtcategory = (TextView)findViewById(R.id.txtCategoryU);
        txtweight = (TextView)findViewById(R.id.txtAnimalWeightU);
        btnUpdList = (Button)findViewById(R.id.btnUpdAnimal);


        //atrapa los datos enviados por el activity anterior (lsitview)
        Bundle bolsaR = getIntent().getExtras();
        txtname.setText(bolsaR.getString("name"));
        txtcategory.setText(bolsaR.getString("category"));
        txtpopulation.setText(String.valueOf(bolsaR.getInt("population")));
        txtweight.setText(String.valueOf(bolsaR.getInt("weight")));

        //acciones del botón
        btnUpdList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtname.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Complete the Spaces", Toast.LENGTH_LONG).show();
                }
                else {
                    IBD repository = new AnimalRepository(getApplicationContext());

                    Animal animal = new Animal();
                    //modificar los datos según lo digitado por el usuario
                    animal.setName(txtname.getText().toString());
                    animal.setCategory(txtcategory.getText().toString());
                    animal.setPopulation(Integer.parseInt(txtpopulation.getText().toString()));
                    animal.setWeight(Integer.parseInt(txtweight.getText().toString()));

                    //enviar los datos a la BD para modificarlo
                    repository.Update(animal);
                    //limpiar campos
                    txtcategory.setText("");
                    txtname.setText("");
                    txtpopulation.setText("");
                    txtweight.setText("");
                    Toast.makeText(getApplicationContext(),"Editado",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

}
