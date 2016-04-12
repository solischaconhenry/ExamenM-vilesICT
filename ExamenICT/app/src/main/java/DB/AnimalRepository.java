package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import Entity.Animal;

/**
 * Created by usuario on 9/4/2016.
 */
public class AnimalRepository implements IBD<Animal> {
    //inicialización de variables para la bd y instacia de la misma
    private Connection connection;
    private static final int VERSION_BDD =1;
    private static final String NAME_BDD = "ExamenICT.db";

    /**
     * Constructor para la BD, donde se idica nombre, versión y contexto de ejecución
     * @param context contexto actual de la aplicación
     */
    public AnimalRepository(Context context) {
        connection = new Connection(context,NAME_BDD, null,VERSION_BDD);
    }

    /**
     * Método para almacernar datos en la BD y la tabla animal en específico
     * @param animal Recibe un arreglo de tipo objeto animal que contiene información para almacenar ne la BD
     * @return retorna error en caso de fallar
     */
    @Override
    public boolean Save(Animal animal) {
        try{
            SQLiteDatabase db = connection.getWritableDatabase();
            if(db != null) {
                ContentValues newData = new ContentValues();
                newData.put("name", animal.getName());
                newData.put("population", animal.getPopulation());
                newData.put("category", animal.getCategory());
                newData.put("weight", animal.getWeight());

                db.insert("animal", null, newData);
                db.close();
                return false;
            }
        }catch (Exception error){
            Log.d("animal", error.getMessage());

        }
        return true;
    }

    /**
     * Método para editar datos en la BD y la tabla animal en específico
     * @param animal Recibe un arreglo de tipo objeto animal que contiene información para editar ne la BD
     * @return retorna error en caso de fallar
     */
    @Override
    public boolean Update(Animal animal) {
        try{
            SQLiteDatabase db = connection.getWritableDatabase();

            if(db != null) {
                ContentValues updateData = new ContentValues();
                updateData.put("name", animal.getName());
                updateData.put("category", animal.getCategory());
                updateData.put("weight", animal.getWeight());
                updateData.put("population", animal.getPopulation());

                db.update("animal", updateData, "name=?", new String[]{String.valueOf(animal.getName())});
                db.update("animal", updateData, "category=?",new String[]{String.valueOf(animal.getCategory())});
                db.update("animal", updateData, "weight=?",new String[]{String.valueOf(animal.getWeight())});
                db.update("animal", updateData, "population=?",new String[]{String.valueOf(animal.getPopulation())});

                connection.close();
                return false;
            }
        }catch (Exception error){
            Log.d("error", error.getMessage());

        }
        return true;
    }

    /**
     * Método para eliminar datos en la BD y la tabla animal en específico
     * @param animal Recibe un arreglo de tipo objeto animal que contiene información para eliminar ne la BD
     * @return retorna error en caso de fallar
     */
    @Override
    public boolean Delete(Animal animal) {
        try{
            SQLiteDatabase db = connection.getWritableDatabase();

            if(db != null) {

                String[] args = new String[]{String.valueOf(animal.getName())};
                db.delete("animal","name=?",args);

                connection.close();
                return false;
            }
        }catch (Exception error){
            Log.d("error", error.getMessage());

        }
        return true;
    }

    /**
     * Método para obtener todos los datos de la BD y la tabla animal en específico
     * @return retorna un arreglo de tipo animal con todos los datos
     */
    @Override
    public ArrayList<Animal> GetAll() {
        ArrayList<Animal> lisPlace = new ArrayList<Animal>();
        try{
            SQLiteDatabase db = connection.getWritableDatabase();
            if(db != null) {
                Cursor cursor = db.query("animal", new String[]{"population","name","category","weight"},null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do{
                        int population = cursor.getInt(cursor.getColumnIndex("population"));
                        String nombre = cursor.getString(cursor.getColumnIndex("name"));
                        String category = cursor.getString(cursor.getColumnIndex("category"));
                        int weight = cursor.getInt(cursor.getColumnIndex("weight"));

                        Animal animal = new Animal();
                        animal.setCategory(category);
                        animal.setPopulation(population);
                        animal.setWeight(weight);
                        animal.setName(nombre);
                        lisPlace.add(animal);
                    }while (cursor.moveToNext());
                }
                connection.close();
                return lisPlace;
            }

        }catch (Exception error){
            Log.d("error", error.getMessage());
        }
        return lisPlace;
    }

    /**
     * Método para obtener datos de la BD y la tabla animal en específico
     * @param animal Recibe un parametro de tipo objeto animal que contiene información para buscar en la BD
     * @return retorna la información solicitada en forma de arreglo tipo animal
     */
    @Override
    public ArrayList<Animal> GetBy(Animal animal) {
        ArrayList<Animal> lisPlace = new ArrayList<Animal>();
        try{
            SQLiteDatabase db = connection.getWritableDatabase();
            if(db != null) {
                String[] args = new String[]{String.valueOf(animal.getName())};
                Cursor cursor = db.query("animal", new String[]{"population","name","category","weight"},"id=?",args,null,null,"name desc",null);
                if (cursor.moveToFirst()){
                    do{
                        int population = cursor.getInt(0);
                        String nombre = cursor.getString(1);
                        String category = cursor.getString(2);
                        int weight = cursor.getInt(3);

                        Animal animalTemp = new Animal();
                        animalTemp.setCategory(category);
                        animalTemp.setPopulation(population);
                        animalTemp.setWeight(weight);
                        animalTemp.setName(nombre);
                    }while (cursor.moveToNext());
                }
                connection.close();
                return lisPlace;
            }

        }catch (Exception error){
            Log.d("error", error.getMessage());
        }
        return lisPlace;
    }

}
