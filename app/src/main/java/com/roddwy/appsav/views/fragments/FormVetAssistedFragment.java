package com.roddwy.appsav.views.fragments;

import android.app.DatePickerDialog;

import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.roddwy.appsav.R;
import com.roddwy.appsav.config.Constants;
import com.roddwy.appsav.database.AppDatabase;
import com.roddwy.appsav.entity.AVeterinario;
import com.roddwy.appsav.views.MainActivity;
import com.roddwy.appsav.views.RegisterUserActivity;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormVetAssistedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormVetAssistedFragment extends Fragment implements View.OnClickListener {
    FragmentTransaction transaction;
    Fragment listVetAssistedFragment;

    Spinner spTipoVacuno;
    String[] tipos = {"Seleccione Tipo Vacuno","Pardo Suizo","Holtein"};
    Button btnFecha, btnHora;
    EditText edFecha, edHora, edEdad, edTipoVacuno, edEnfermedad, edAlimento;

    AppDatabase db;

    private PendingIntent pendingIntent;
    private String CHANNEL_ID = "channelID";
    private int NOTIFICATION_ID = 0;
    Button btnRegisterVetAssisted;

    private int dia, mes, ano, hora, minutos;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormVetAssistedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormVetAssistedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormVetAssistedFragment newInstance(String param1, String param2) {
        FormVetAssistedFragment fragment = new FormVetAssistedFragment();
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
        View main = inflater.inflate(R.layout.fragment_form_vet_assisted, container, false);
        btnRegisterVetAssisted = main.findViewById(R.id.buttonRegisterVetAssisted);
        btnRegisterVetAssisted.setOnClickListener(this);

        btnFecha = main.findViewById(R.id.buttonFecha);
        btnHora = main.findViewById(R.id.buttonHora);

        edEdad = main.findViewById(R.id.editTextEdadVacuno);
        edEnfermedad = main.findViewById(R.id.editTextTextEnfermedad);
        edAlimento = main.findViewById(R.id.editTextTextABalanceado);
        edFecha = main.findViewById(R.id.editTextTextFecha);
        edHora = main.findViewById(R.id.editTextTextHora);
        btnFecha.setOnClickListener(this);
        btnHora.setOnClickListener(this);
        spTipoVacuno = main.findViewById(R.id.spinnerTipoVacuno);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, tipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoVacuno.setAdapter(adapter);
        spTipoVacuno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        db = Room.databaseBuilder(getActivity(),AppDatabase.class, Constants.DB_NAME)
                .allowMainThreadQueries()
                .build();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_form_vet_assisted, container, false);

        listVetAssistedFragment = new ListVetAssistedFragment();
        ((MainActivity)getActivity()).setDrawer_Locked();
        return main;
    }

    private void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_notification_important_24);
        builder.setContentTitle("SAV - NOTIFICACION");
        builder.setContentText("POR FAVOR NO SE OLVIDE DE ALIMENTAR AL VACUNO.");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.buttonRegisterVetAssisted:
                //Toast.makeText(getContext(),"Creando la notificacion para pruebas.",Toast.LENGTH_LONG).show();
                createNotificationChannel();
                createNotification();
                String edad = edEdad.getText().toString();
                String enfermedad = edEnfermedad.getText().toString();
                String fecha = edFecha.getText().toString();
                String horas = edHora.getText().toString();
                String alimentos = edAlimento.getText().toString();
                Log.i("dataAsistenciaVet",edad+" - "+enfermedad+" - "+fecha+" - "+horas);
                AVeterinario aVeterinario= new AVeterinario();
                aVeterinario.setEdad(edad);
                aVeterinario.setEnfermedad(enfermedad);
                aVeterinario.setAlimento(alimentos);
                aVeterinario.setFecha(fecha);
                aVeterinario.setHora(horas);
                long result = db.aVeterinarioDao().insert(aVeterinario);
                if(result > 0){
                    Toast.makeText(getContext(),"USTED A REGISTRADO CON EXITO",Toast.LENGTH_LONG).show();
                    transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.containerFragment,listVetAssistedFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else{
                    Toast.makeText(getContext(),"Error al registrar los datos",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonFecha:
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH);
                int dd = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String fecha = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                        edFecha.setText(fecha);
                    }
                }, yy, mm, dd);
                datePicker.show();
                break;
            case R.id.buttonHora:
                final Calendar c = Calendar.getInstance();
                hora = c.get(Calendar.HOUR_OF_DAY);
                minutos=c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horas = String.valueOf(hourOfDay)+":"+String.valueOf(minute);
                        edHora.setText(horas);
                    }
                },hora, minutos,false);
                timePickerDialog.show();
                break;
        }
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notification";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}