package com.roddwy.appsav.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.roddwy.appsav.R;
import com.roddwy.appsav.views.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment implements View.OnClickListener {

    FragmentTransaction transaction;
    Fragment listVetAssistedFragment;

    ImageButton btnImageVetAs;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
        View main = inflater.inflate(R.layout.fragment_inicio,container,false);

        btnImageVetAs = main.findViewById(R.id.buttonImageVetAs);
        btnImageVetAs.setOnClickListener(this);

        listVetAssistedFragment = new ListVetAssistedFragment();

        ((MainActivity)getActivity()).setDrawer_Locked();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_inicio, container, false);
        return main;
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
            case R.id.buttonImageVetAs:
                transaction.replace(R.id.containerFragment,listVetAssistedFragment);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
    }
}