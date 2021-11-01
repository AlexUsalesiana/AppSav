package com.roddwy.appsav.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roddwy.appsav.LoginActivity;
import com.roddwy.appsav.R;
import com.roddwy.appsav.config.Constants;
import com.roddwy.appsav.database.AppDatabase;
import com.roddwy.appsav.entity.User;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRegisterUser;
    TextView txtReturnLogin;
    EditText etName, etLastName, etCi, etUserName, etPassword;

    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //getSupportActionBar().hide();

        btnRegisterUser = findViewById(R.id.buttonRegisterUser);
        btnRegisterUser.setOnClickListener(this);

        txtReturnLogin = findViewById(R.id.textViewReturnLogin);
        txtReturnLogin.setOnClickListener(this);

        etName = findViewById(R.id.editTextName);
        etLastName = findViewById(R.id.editTextLastName);
        etCi = findViewById(R.id.editTextCi);
        etUserName = findViewById(R.id.editTextUserName);
        etPassword = findViewById(R.id.editTextPassword);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, Constants.DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.buttonRegisterUser:
                insertUser();
                break;
            case R.id.textViewReturnLogin:
                returnLogin();
                break;
        }
    }

    private void returnLogin(){
        Intent i = new Intent(RegisterUserActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void insertUser(){
        String name = etName.getText().toString();
        String lastname = etLastName.getText().toString();
        String ci = etCi.getText().toString();
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if(name.isEmpty()){
            etName.setError("El nombre es requirido.");
        }else if(lastname.isEmpty()){
            etLastName.setError("Los apellidos son requeridos");
        }else if(ci.isEmpty()){
            etCi.setError("El numero de CI es requerido");
        }else if(username.isEmpty()){
            etUserName.setError("El usuario es requerido");
        }else if(password.isEmpty() || password.length()<4){
            etPassword.setError("La contraseña es requerida y debe tener al menos 4 caracteres");
        }else{
            int existUser = db.userDao().getUserByCi(ci);
            if(existUser > 0){
                Toast.makeText(RegisterUserActivity.this,"LO SIENTO YA EXISTE EL USUARIO EN LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
            }else{
                User user = new User();
                user.setName(name);
                user.setLastName(lastname);
                user.setCi(ci);
                user.setUsername(username);
                user.setPassword(password);

                long result = db.userDao().insert(user);

                if(result > 0){
                    Toast.makeText(RegisterUserActivity.this,"USTED A SIDO REGISTRADO EN LA APPSAV, BIENIDO A LA APLICACION",Toast.LENGTH_LONG).show();
                    Intent menu = new Intent(RegisterUserActivity.this, MainActivity.class);
                    startActivity(menu);
                }else{
                    Toast.makeText(RegisterUserActivity.this,"Error al registrar usuario",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}