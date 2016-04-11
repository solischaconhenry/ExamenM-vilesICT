package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by usuario on 7/4/2016.
 */
public class Connection extends SQLiteOpenHelper {
    private static final int VERSION_BDD =2;
    private static final String NAME_BDD = "ExamenICT.db";

    public Connection(Context context) {
        super(context, NAME_BDD, null, VERSION_BDD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            final String sqlCreatePlace;
            final String sqlCreateAnimal;
            //create
            sqlCreatePlace = "CREATE TABLE" + "users" +
                    "(" + "password INTEGER PRIMARY KEY," + " user TEXT);";

            sqlCreateAnimal = "CREATE TABLE" + "animal" +
                    "(" + "id" + "INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT," + "category TEXT," + "weight INTEGER,"
                        +"population INTEGER);";

            db.execSQL(sqlCreatePlace);
            db.execSQL(sqlCreateAnimal);
        }catch (Exception error){
            Log.d("error", error.getMessage());
        }
    }

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
            }
        }catch(Exception error){
            Log.d("error", error.getMessage());
        }
    }
}
