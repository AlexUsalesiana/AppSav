package com.roddwy.appsav.views.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.roddwy.appsav.R;
import com.roddwy.appsav.adapters.Adapter;
import com.roddwy.appsav.config.Constants;
import com.roddwy.appsav.database.AppDatabase;
import com.roddwy.appsav.entity.AVeterinario;
import com.roddwy.appsav.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListVetAssistedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListVetAssistedFragment extends Fragment implements View.OnClickListener {

    FragmentTransaction transaction;
    Fragment formVetAsistedFragment;

    ImageButton btnFormVetAssisted;

    private Adapter adapter;
    ListView lvAV;
    private List<AVeterinario> listaAveterinario;
    AppDatabase db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListVetAssistedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListVetAssistedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListVetAssistedFragment newInstance(String param1, String param2) {
        ListVetAssistedFragment fragment = new ListVetAssistedFragment();
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
        View main = inflater.inflate(R.layout.fragment_list_vet_assisted,container,false);
        btnFormVetAssisted = main.findViewById(R.id.imageButtonFormVetAssisted);
        btnFormVetAssisted.setOnClickListener(this);

        formVetAsistedFragment = new FormVetAssistedFragment();
        ((MainActivity)getActivity()).setDrawer_Locked();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_list_vet_assisted, container, false);



        db = Room.databaseBuilder(getActivity(),AppDatabase.class, Constants.DB_NAME)
                .allowMainThreadQueries()
                .build();

        //ArrayList<AVeterinario> model = new ArrayList<>();
        listaAveterinario = db.aVeterinarioDao().getAllAVeterinario();
        //ArrayAdapter adaptador = new ArrayAdapter ( getActivity() , android.R.layout.item_asistido_veterinario_v, listaAveterinario);

        //Configuració del ListView
        lvAV = main.findViewById(R.id.listViewAV);
        AdapterAv adapterAv = new AdapterAv((AppCompatActivity) getContext());
        lvAV.setAdapter(adapterAv);

        return main;
    }

    class AdapterAv extends ArrayAdapter<AVeterinario>{
        AppCompatActivity appCompatActivity;
        public AdapterAv(AppCompatActivity context){
            super(context, R.layout.item_asistido_veterinario_v, listaAveterinario);
            appCompatActivity = context;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.item_asistido_veterinario_v, null);

            TextView txtEdad = (TextView)item.findViewById(R.id.txtEdadVAcunoV);
            TextView txtEnfermedad = (TextView)item.findViewById(R.id.txtEnfermedadV);
            TextView txtAliemntoV = (TextView)item.findViewById(R.id.txtAlimentoV);
            TextView txtFechaHoraV = (TextView)item.findViewById(R.id.txtFechaHoraV);
            txtEdad.setText("Edad: "+listaAveterinario.get(position).getEdad());
            txtEnfermedad.setText(", Enfermedad: "+listaAveterinario.get(position).getEnfermedad());
            txtAliemntoV.setText("Alimento Balanceado: "+listaAveterinario.get(position).getAlimento());
            txtFechaHoraV.setText("Fecha: "+listaAveterinario.get(position).getFecha()+" Horas: "+listaAveterinario.get(position).getHora());
            return item;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).setDrawer_UnLocked();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (id){
            case R.id.imageButtonFormVetAssisted:
                transaction.replace(R.id.containerFragment,formVetAsistedFragment);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
    }
}