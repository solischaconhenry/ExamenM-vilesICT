package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by usuario on 7/4/2016.
 */
public class Connection extends SQLiteOpenHelper {
    private static final int VERSION_BDD =1;
    private static final String NAME_BDD = "ExamenICT";

    public Connection(Context context) {
        super(context, NAME_BDD, null, VERSION_BDD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            StringBuilder sql = new StringBuilder();
            //create
            String sqlCreatePlace = "CREATE TABLE IF NOT EXISTS users" +
                    "(password INTEGER PRIMARY KEY, user CHAR(100))";
            sql.append(sqlCreatePlace);

            StringBuilder sqla = new StringBuilder();

            String sqlCreateAnimal = "CREATE TABLE IF NOT EXISTS animal" +
                    "(name char(100) PRIMARY KEY, category CHAR(100), weight INTEGER, population INTEGER, )";
            sqla.append(sqlCreateAnimal);

            db.execSQL(sql.toString());
            db.execSQL(sqla.toString());
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
