package DB;

import java.util.ArrayList;

/**
 * Created by usuario on 7/4/2016.
 */
public interface IBD<Object> {
    //Métodos que se deben implementar para los Repositorios y para indicar que métodos generar para el uso de la BD

    public boolean Save(Object object);

    public boolean Update(Object object);

    public boolean Delete(Object object);

    public ArrayList<Object> GetAll();

    public ArrayList<Object> GetBy(Object object);
}
