package DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import Entity.Animal;

/**
 * Created by usuario on 9/4/2016.
 */
public class AnimalRepository implements IBD<Animal> {

    private Connection connection;

    @Override
    public boolean Save(Animal animal) {
        try{
            SQLiteDatabase db = connection.getWritableDatabase();
            if(db != null) {
                ContentValues newData = new ContentValues();
                newData.put("id", animal.getName());
                newData.put("name", animal.getPopulation());
                newData.put("category", animal.getCategory());
                newData.put("weight", animal.getWeight());

                db.insert("ExamenICT", null, newData);

                connection.close();
                return false;
            }
        }catch (Exception error){
            Log.d("animal", error.getMessage());

        }
        return true;
    }

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

    @Override
    public ArrayList<Animal> GetAll() {
        ArrayList<Animal> lisPlace = new ArrayList<Animal>();
        try{
            SQLiteDatabase db = connection.getWritableDatabase();
            if(db != null) {
                Cursor cursor = db.query("animal", new String[]{"population","name","category","weight"},null,null,null,null,"name desc",null);
                if (cursor.moveToFirst()){
                    do{
                        int population = cursor.getInt(0);
                        String nombre = cursor.getString(1);
                        String category = cursor.getString(2);
                        int weight = cursor.getInt(3);

                        Animal animal = new Animal();
                        animal.setCategory(category);
                        animal.setPopulation(population);
                        animal.setWeight(weight);
                        animal.setName(nombre);
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

    @Override
    public ArrayList<Animal> GetBy(Animal animal) {
        ArrayList<Animal> lisPlace = new ArrayList<Animal>();
        try{
            SQLiteDatabase db = connection.getWritableDatabase();
            if(db != null) {
                String[] args = new String[]{String.valueOf(animal.getName())};
                Cursor cursor = db.query("place", new String[]{"population","name","category","weight"},"id=?",args,null,null,"name desc",null);
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
