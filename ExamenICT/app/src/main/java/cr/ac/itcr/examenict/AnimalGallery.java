package cr.ac.itcr.examenict;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import DB.AnimalRepository;
import DB.IBD;
import Entity.Animal;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnimalGallery.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnimalGallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimalGallery extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView txtAnimal; //Variable para mostrar la información del animal seleccionado
    ListView lv1; //Lsita para publicar los animales registrados en la BD
    ArrayList<Animal> data; //Contiene el objeto devuelto por la base de datos
    ArrayAdapter<String> adapter; //Lista que contiene los elementos a mostrar en el Listview
    ArrayList<String> test; //Lista que se le asigna al adapter para el Listview

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AnimalGallery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnimalGallery.
     */
    // TODO: Rename and change types and number of parameters
    public static AnimalGallery newInstance(String param1, String param2) {
        AnimalGallery fragment = new AnimalGallery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Este método carga las opciones del menú contextual asignado al listview, las opciones estan contenidas en
     * menu > menucontextual.xml
     * Además asigna como título del menú la opción seleccionada por el usuario
     * @param menu información del menú en general
     * @param v Contiene el manejo de vista
     * @param menuInfo Información de la opción seleccionada por el usuario
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.lv1Gallery) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(data.get(info.position).getName());
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menucontextual,menu);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         *como es un fragment no se puede obtener los id directamente por ello debemos crear una variable que haga referencia
         * al fragment que estamos modificando
         */
        View view = inflater.inflate(R.layout.fragment_animal_gallery, container, false);

        //busca el txt para mostrar infomación
        txtAnimal = (TextView)view.findViewById(R.id.txtShowAnimal);

        /**
         * Realiza conexión con el repositorio y el implements de la BD, para poder utilizar las funciones relacionadas
         */
        IBD repository = new AnimalRepository(getContext().getApplicationContext());

        /**
         * solicita todos los animales que hayan en la base de datos por medio de la función GetAll(), pero recibe un objeto de tipo Animal
         * Pero como el listview utiliza un tipo string y solo el nombre, por ello extraemos todos lo nombre de la lista "data" y lo colocamos
         * en otra y se la asignamos al adapter  para que sea mostrada.
         */

        data = repository.GetAll();
        test = new ArrayList<String>();
        for (int x = 0; x < data.size(); x++){
            test.add(data.get(x).getName());
        }

        lv1 = (ListView)view.findViewById(R.id.lv1Gallery);

        //asignación del menu contextual al ListView
        registerForContextMenu(lv1);
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, test);

        //indica al adapter un cambio en el contenido del arreglo
        adapter.notifyDataSetChanged();

        //inicializa el arreglo adapter para el listview
        lv1.setAdapter(adapter);

        //cuandos se selecciona una opción se i
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtAnimal.setText(lv1.getItemAtPosition(position).toString()+" Categ: "+ data.get(position).getCategory()+" Popul: " +data.get(position).getPopulation() +" Weight: " +data.get(position).getWeight());
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * cuando se edita un elemento y se regresa a este activity, por ende necesitaba refrescar el arreglo test para refrescar el
     * listview, además el activity quedaba en resume, entonces aprovecho el metodo de regreso para refrescar
     */
    @Override
    public void onResume() {
        IBD repository = new AnimalRepository(getContext().getApplicationContext());
        data = repository.GetAll();
        test = new ArrayList<String>();
        for (int x = 0; x < data.size(); x++){
            test.add(data.get(x).getName());
        }
        adapter.notifyDataSetChanged();

        super.onResume();
    }
    //actions for context Menu

    /**
     * Método para indicar que acciones realizar dependiendo de la opción seleccionada por el usuario
     * Si selecciona Delete, eliminar el registro
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        IBD repository = new AnimalRepository(getContext().getApplicationContext());
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        data = repository.GetAll();

        switch (item.getItemId()){
            case R.id.delete:
                repository.Delete(data.get(info.position));
                test.remove(info.position);
                adapter.notifyDataSetChanged();
                break;
            case R.id.update:
                //invocar la vista para editar datos de un elemento, y enviar los datos a editat por medio de results
                Intent i = new Intent(getContext().getApplicationContext(),UpdateAnimal.class);

                Bundle bolsa = new Bundle();
                bolsa.putString("name",data.get(info.position).getName());
                bolsa.putString("category",data.get(info.position).getCategory());
                bolsa.putInt("weight", data.get(info.position).getWeight());
                bolsa.putInt("population", data.get(info.position).getPopulation());
                i.putExtras(bolsa);
                startActivity(i);
                break;
        }

        return super.onContextItemSelected(item);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
