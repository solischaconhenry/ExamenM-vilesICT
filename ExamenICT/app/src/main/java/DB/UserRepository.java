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

    public UserRepository(Context context) {
        connection = new Connection(context);
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
        ArrayList<User> lisPlace = new ArrayList<User>();
        try{
            SQLiteDatabase db = connection.getWritableDatabase();
            if(db != null) {
                String[] args = new String[]{String.valueOf(user.getEmail())};
                Cursor cursor = db.query("users", new String[]{"user","password"},"user=?",args,null,null,"user desc",null);
                if (cursor.moveToFirst()){
                    do{
                        String password = cursor.getString(1);
                        String users = cursor.getString(0);

                        User userTemp = new User();
                        userTemp.setEmail(users);
                        userTemp.setPassword(password);

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
