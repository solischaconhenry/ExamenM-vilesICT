package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import Entity.Animal;
import Entity.User;

/**
 * Created by usuario on 10/4/2016.
 */
public class UserRepository implements IBD<User> {

    private Connection connection;
    private static final int VERSION_BDD =1;
    private static final String NAME_BDD = "ExamenICT.db";

    public UserRepository(Context context) {
        connection = new Connection(context,NAME_BDD, null,VERSION_BDD);
    }

    @Override
    public boolean Save(User user) {
        try{
            SQLiteDatabase db = connection.getWritableDatabase();
            if(db != null) {
                ContentValues newData = new ContentValues();
                newData.put("user", user.getEmail());
                newData.put("password", user.getPassword());

                db.insert("users", null, newData);
                db.close();
                return false;
            }
        }catch (Exception error){
            Log.d("users", error.getMessage());

        }
        return true;
    }

    @Override
    public boolean Update(User user) {
        return false;
    }

    @Override
    public boolean Delete(User user) {
        return false;
    }

    @Override
    public ArrayList<User> GetAll() {
        return null;
    }

    @Override
    public ArrayList<User> GetBy(User user) {
        return null;
    }

    public String getPass(String userName)
    {
        SQLiteDatabase db = connection.getWritableDatabase();
        Cursor cursor=db.query("users", null, " user=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        db.close();
        return password;
    }

}
