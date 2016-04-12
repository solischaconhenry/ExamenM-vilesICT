package cr.ac.itcr.examenict;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import DB.IBD;
import DB.UserRepository;
import Entity.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserRegister.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRegister extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserRegister.
     */
    // TODO: Rename and change types and number of parameters
    public static UserRegister newInstance(String param1, String param2) {
        UserRegister fragment = new UserRegister();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         *como es un fragment no se puede obtener los id directamente por ello debemos crear una variable que haga referencia
         * al fragment que estamos modificando
         */
        final View view = inflater.inflate(R.layout.fragment_user_register, container, false);
        //Inicialización de boton
        final Button addU = (Button)view.findViewById(R.id.btnAddUser);

        //acciones a realizar en el momento de activar el boton
        addU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Inicialización de la variables contenedoras de los datos a registrar
                TextView txtuser = (TextView)view.findViewById(R.id.txtEmailUser);
                TextView txtpassword = (TextView)view.findViewById(R.id.txtPasswordUser);

                //comprueba que los campos no esten vaciós
                if(txtuser.getText().toString().equals("") || txtpassword.getText().toString().equals("")){
                    Toast.makeText(getContext().getApplicationContext(), "Complete the Spaces", Toast.LENGTH_LONG).show();
                }
                else {
                    //Instancia  el Repositorio de Usuario que contiene los métodos para BD
                    IBD repository = new UserRepository(getContext().getApplicationContext());

                    //instancia user para crear un objeto con los datos que se van a enviar a almacenar a la BD
                    User users = new User();

                    //captura los datos a almcenar
                    users.setEmail(txtuser.getText().toString());
                    users.setPassword(txtpassword.getText().toString());

                    //envia a almacenar
                    repository.Save(users);
                    //limpiar campos
                    txtuser.setText("");
                    txtpassword.setText("");
                    Toast.makeText(getContext(),"Registrado",Toast.LENGTH_SHORT).show();
                }
            }
        });



        // Inflate the layout for this fragment
        return view;
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
