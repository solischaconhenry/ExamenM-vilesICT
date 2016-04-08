package DB;

import java.util.ArrayList;

/**
 * Created by usuario on 7/4/2016.
 */
public interface IBD<Object> {
    public boolean Save(Object object);

    public boolean Update(Object object);

    public boolean Delete(Object object);

    public ArrayList<Object> GetAll();

    public ArrayList<Object> GetBy(Object object);
}
