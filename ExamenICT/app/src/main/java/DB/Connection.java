package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by usuario on 7/4/2016.
 */
public class Connection extends SQLiteOpenHelper {

    /**
     * Método constructor para la BD
     * @param context Contexto actual de la aplicación
     * @param name Nombre de la BD
     * @param factory
     * @param version Versión Actual de la BD
     */
    public Connection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Método para crear las tablas y algunas inserciones en la BD, este métodos se llama cuando no existe la BD
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            final String sqlCreatePlace;
            final String sqlCreateAnimal;
            final String sqlInsertUsers;
            final String sqlInsertAnimal;

            //crea la tabla usuarios
            sqlCreatePlace = "CREATE TABLE " + "users " +
                    "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, password TEXT," + " user TEXT);";

            //crea la tabla animales
            sqlCreateAnimal = "CREATE TABLE " + "animal " +
                    "(" + " id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT," + " category TEXT," + " weight INTEGER,"
                    + " population INTEGER);";

            //inserta un usuario base
            sqlInsertUsers = "INSERT INTO users (user, password) Values ('hsolicha', '12345');";

            //inserta un animal base
            sqlInsertAnimal = "INSERT INTO animal (name, category, weight, population) Values ('Venado', 'Mamífero', 100, 50);";

            //ejecución de los anteriores query
            db.execSQL(sqlCreatePlace);
            db.execSQL(sqlCreateAnimal);
            db.execSQL(sqlInsertUsers);
            db.execSQL(sqlInsertAnimal);
        }catch (Exception error){
            Log.d("error", error.getMessage());
        }
    }

    /**
     * Se ejecuta cuando se cambia la versión de la BD y busca actualizar la BD de la APP hasta el estado actual
     * @param db
     * @param oldVersion Versión vieja de la BD
     * @param newVersion Versión nueva de la BD
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            StringBuilder sql = new StringBuilder();
            StringBuilder sqla = new StringBuilder();
            for (int indiceVersion = oldVersion; indiceVersion < newVersion; indiceVersion++){
                int nextVersion = indiceVersion + 1;
                switch (nextVersion){
                    case 1:
                        String sqlDropPlace = "DROP TABLE IF EXISTS users";
                        sql.append(sqlDropPlace);

                        String sqlDropAnimal = "DROP TABLE IF EXISTS animal";
                        sqla.append(sqlDropAnimal);
                        break;
                }
                db.execSQL(sql.toString());
                onCreate(db);
            }
        }catch(Exception error){
            Log.d("error", error.getMessage());
        }
    }
}
